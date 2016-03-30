package com.wanghao.myarchitecture.ui.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wanghao.myarchitecture.R;
import com.wanghao.myarchitecture.databinding.ListRentalBinding;
import com.wanghao.myarchitecture.di.component.DaggerFragmentComponent;
import com.wanghao.myarchitecture.di.module.GroupModule;
import com.wanghao.myarchitecture.di.module.RentalModule;
import com.wanghao.myarchitecture.domain.entity.Rental;
import com.wanghao.myarchitecture.ui.adapter.RentalItemAdapter;
import com.wanghao.myarchitecture.ui.viewmodel.RentalViewModel;
import com.wanghao.myarchitecture.ui.widget.LoadingFooter;
import com.wanghao.myarchitecture.ui.widget.LoadingLayout;
import com.wanghao.myarchitecture.utils.NetUtils;
import com.wanghao.myarchitecture.vendor.Config;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import rx.Subscriber;

/**
 * Created by wanghao on 2015/9/23.
 */
public class RentalFragment extends SwipeRefreshBaseFragment{

    private Context context;
    private LoadingFooter mLoadingFooter;
    protected ArrayList<Rental> data;
    private RentalItemAdapter adapter;
    private boolean isInitLoad = true;//是否第一次加载
    private int mPage = 0;
    private boolean isVisible ;
    private boolean isInit ;
    
    @Inject
    RentalViewModel rentalViewModel;

    @Bind(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout mPtrFrame;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.loading_layout)
    LoadingLayout loadingLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFragmentComponent
                .builder()
                .groupModule(new GroupModule(getActivity()))
                .rentalModule(new RentalModule(getActivity()))
                .build().inject(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        ListRentalBinding binding = DataBindingUtil.inflate(inflater,R.layout.list_rental,container,false);
        binding.setViewModel(rentalViewModel);
        View view  = binding.getRoot();
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

        data = new ArrayList<Rental>();
        rentalViewModel.setData(data);
        adapter = rentalViewModel.getAdapter();

        mLoadingFooter = rentalViewModel.getLoadingFooter();
        mLoadingFooter.setOnClickLoadListener(new LoadingFooter.onClickLoadListener() {
            @Override
            public void onClick() {
                loadNextPage();
            }
        });

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

        Subscriber subscriber = new Subscriber<List<Rental>>() {

            @Override
            public void onNext(List<Rental> lists) {
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

        addSubscription(rentalViewModel.getRentalList(subscriber, page, Config.LIST_COUNT));
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
        rentalViewModel.destroy();
        ButterKnife.unbind(this);
    }
}
