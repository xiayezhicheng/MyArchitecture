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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.wanghao.myarchitecture.R;
import com.wanghao.myarchitecture.adapter.HeaderBottomItemAdapter;
import com.wanghao.myarchitecture.enums.TYPE_LAYOUT;
import com.wanghao.myarchitecture.network.GsonRequest;
import com.wanghao.myarchitecture.utils.NetUtils;
import com.wanghao.myarchitecture.view.LoadingFooter;
import com.wanghao.myarchitecture.view.LoadingLayout;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wanghao on 2015/9/23.
 */
public abstract class BaseRefreshListFragment<T> extends BaseFragment{

    private Context context;
    private GridLayoutManager gridLayoutManager;
    private LoadingFooter mLoadingFooter;
    protected ArrayList<T> data;
    private HeaderBottomItemAdapter<T> adapter;
    private boolean isInitLoad = true;//是否第一次加载
    private int mPage = 0;
    private int mCount = 16;
    private boolean isVisible ;
    private boolean isInit ;

    @Bind(R.id.rotate_header_list_view_frame) PtrClassicFrameLayout mPtrFrame;
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

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                isInitLoad = false;
                loadFirstPage();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false(release to refresh )true is mean pulling to refresh
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);

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
        GsonRequest<T[]> request = new GsonRequest<T[]>(String.format(getUrlString(), page, mCount), getDataType(),
                new Response.Listener<T[]>() {

                    @Override
                    public void onResponse(final T[] response) {

                        Observable.create(new Observable.OnSubscribe<LinkedList<T>>() {
                            @Override
                            public void call(Subscriber<? super LinkedList<T>> subscriber) {
                                        LinkedList<T> temp = new LinkedList<T>();
                                        for (T object : response) {
                                            temp.add(object);
                                        }
                                        subscriber.onNext(temp);
                                        subscriber.onCompleted();
                                    }
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<LinkedList<T>>() {
                                    @Override
                                    public void onCompleted() {
                                        adapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(LinkedList<T> result) {

                                        if (isRefreshFromTop) {
                                            data.clear();
                                            if (isInitLoad) {
                                                if (result.isEmpty()){
                                                    loadingLayout.showEmpty();
                                                }else {
                                                    loadingLayout.showContent();
                                                }
                                            }else {
                                                mPtrFrame.refreshComplete();
                                            }
                                        }
                                        if (result.size() < mCount) {
                                            mLoadingFooter.setState(LoadingFooter.State.TheEnd);
                                        } else {
                                            mLoadingFooter.setState(LoadingFooter.State.Idle);
                                        }
                                        mPage = page;
                                        data.addAll(result);
                                    }
                                });
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (isRefreshFromTop) {
                    if(isInitLoad){
                        loadingLayout.showError();
                    }else{
                        mPtrFrame.refreshComplete();
                    }
                } else {
                    mLoadingFooter.setState(LoadingFooter.State.Idle);
                }
                if (!NetUtils.isConnect(context)) {
                    if(isRefreshFromTop){
                        if (data.isEmpty()) {
                            loadingLayout.showUnnet();
                        }else {
                            Toast.makeText(context, "网络异常，请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        mLoadingFooter.setState(LoadingFooter.State.InvalidateNet);
                    }
                }

            }
        }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        executeRequest(request);

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

    protected abstract Class<T[]> getDataType();

    protected abstract String getUrlString();
}
