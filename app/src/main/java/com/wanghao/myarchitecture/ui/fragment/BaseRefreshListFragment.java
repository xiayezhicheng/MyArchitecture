package com.wanghao.myarchitecture.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wanghao.myarchitecture.R;
import com.wanghao.myarchitecture.adapter.HeaderBottomItemAdapter;
import com.wanghao.myarchitecture.enums.TYPE_LAYOUT;
import com.wanghao.myarchitecture.ui.base.SwipeRefreshBaseFragment;
import com.wanghao.myarchitecture.utils.Config;
import com.wanghao.myarchitecture.utils.NetUtils;
import com.wanghao.myarchitecture.view.LoadingFooter;
import com.wanghao.myarchitecture.view.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by wanghao on 2015/9/23.
 */
public abstract class BaseRefreshListFragment<T> extends SwipeRefreshBaseFragment {

    private Context context;
    private GridLayoutManager gridLayoutManager;
    private LoadingFooter mLoadingFooter;
    protected ArrayList<T> data;
    private HeaderBottomItemAdapter<T> adapter;
    private boolean isInitLoad = true;//是否第一次加载
    private int mPage = 0;
    private boolean isVisible ;
    private boolean isInit ;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.loading_layout) LoadingLayout loadingLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.ptr_list, container,false);
        ButterKnife.bind(this,view);

        //加载视图展示
        loadingLayout.setOnRetryClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isInitLoad = true;
                loadFirstPage();
            }
        });

        mRecyclerView.setHasFixedSize(true);
        if (getLayoutType() == TYPE_LAYOUT.TYPE_GRID_LAYOUT) {
            gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);//这里用线性宫格显示 类似于grid view
        } else if (getLayoutType() ==  TYPE_LAYOUT.TYPE_STAGGERED_GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//这里用线性显示 类似于list view
        }

        mLoadingFooter = new LoadingFooter(context);
        mLoadingFooter.setOnClickLoadListener(new LoadingFooter.onClickLoadListener() {

            @Override
            public void onClick() {
                loadNextPage();
            }
        });


        data = new ArrayList<T>();
        adapter = getAdapter();
        adapter.setBottomView(mLoadingFooter.getView());
        mRecyclerView.setAdapter(adapter);
        if (gridLayoutManager != null) {//设置头部及底部View占据整行空间
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (adapter.isHeaderView(position) || adapter.isBottomView(position)) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }

        mRecyclerView.addOnScrollListener(recyclerScrollListener);

        //延迟加载
        isInit = true;
        onFirstLoad();
        return view;
    }

    RecyclerView.OnScrollListener recyclerScrollListener = new RecyclerView.OnScrollListener(){

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount    = mRecyclerView.getLayoutManager().getChildCount();
            int totalItemCount      = mRecyclerView.getLayoutManager().getItemCount();
            int firstVisibleItem = 0;

            RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager){
                firstVisibleItem = ((GridLayoutManager)layoutManager).findFirstVisibleItemPosition();
            }else if(layoutManager instanceof LinearLayoutManager){
                firstVisibleItem = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
            }


            if (mLoadingFooter.getState() == LoadingFooter.State.Idle) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount
                        && totalItemCount != 0
                        && totalItemCount != adapter.getHeaderViewCount() + adapter.getBottomViewCount()
                        && adapter.getContentItemCount() > 0) {
                    loadNextPage();
                }
            } else if (mLoadingFooter.getState() == LoadingFooter.State.InvalidateNet) {
                if (!mLoadingFooter.getView().isShown()) {
                    mLoadingFooter.setState(LoadingFooter.State.Idle);
                }
            }

        }
    };

    private void loadData(final int page) {

        final boolean isRefreshFromTop = page == 0;

        Subscriber subscriber = new Subscriber<List<T>>() {

            @Override
            public void onNext(List<T> lists) {
                if (isRefreshFromTop) {
                    data.clear();
                    if (isInitLoad) {
                        if (lists.isEmpty()){
                            loadingLayout.showEmpty();
                        }else {
                            loadingLayout.showContent();
                        }
                    }else {
                        mPtrFrame.refreshComplete();
                    }
                }
                if (lists.size() < Config.LIST_COUNT) {
                    mLoadingFooter.setState(LoadingFooter.State.TheEnd);
                } else {
                    mLoadingFooter.setState(LoadingFooter.State.Idle);
                }
                data.addAll(lists);
            }

            @Override
            public void onCompleted() {
                mPage = page;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
                if (isRefreshFromTop) {
                    if(isInitLoad){
                        loadingLayout.showError();
                    }else{
                        mPtrFrame.refreshComplete();
                    }
                } else {
                    mLoadingFooter.setState(LoadingFooter.State.Idle);
                }
                if (!NetUtils.isConnect(getActivity())) {
                    if(isRefreshFromTop){
                        if (data.isEmpty()) {
                            loadingLayout.showUnnet();
                        }else {
                            Toast.makeText(getActivity(), "网络异常，请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        mLoadingFooter.setState(LoadingFooter.State.InvalidateNet);
                    }
                }
            }
        };

        addSubscription(loadObservable(subscriber, page));
    }

    @Override
    public void onBeginRefresh() {
        isInitLoad = false;
        loadFirstPage();
    }

    private void loadNextPage() {
        mLoadingFooter.setState(LoadingFooter.State.Loading);
        loadData(mPage+1);
    }

    protected void loadFirstPage() {
        if (isInitLoad) loadingLayout.showLoading();
        mPage = 0;
        loadData(mPage);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        //显示该fragment之前载入activity的时候就已经初始化完成
        onFirstLoad();
    }

    public void onFirstLoad() {
        //延迟加载(懒加载)
        if (data != null && data.size() == 0) {
            if(isVisible&&isInit){
                loadFirstPage();
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    protected abstract TYPE_LAYOUT getLayoutType();

    protected abstract HeaderBottomItemAdapter<T> getAdapter();

    protected abstract Subscription loadObservable(Subscriber<List<T>> subscriber, int page);
}
