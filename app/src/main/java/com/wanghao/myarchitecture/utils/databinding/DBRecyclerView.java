package com.wanghao.myarchitecture.utils.databinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.wanghao.myarchitecture.enums.TYPE_LAYOUT;
import com.wanghao.myarchitecture.ui.adapter.HeaderBottomItemAdapter;
import com.wanghao.myarchitecture.ui.widget.LoadingFooter;

/**
 * Created by wanghao on 2016/3/25.
 */
public class DBRecyclerView {


    @BindingAdapter({"bind:adapter","bind:loadingFooter","bind:layoutType"})
    public static void bindAdapter(RecyclerView recyclerView, final HeaderBottomItemAdapter adapter
                    ,final LoadingFooter mLoadingFooter, TYPE_LAYOUT layoutType) {
        adapter.setBottomView(mLoadingFooter.getView());
        recyclerView.setAdapter(adapter);

        if (layoutType == TYPE_LAYOUT.TYPE_GRID_LAYOUT) {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 2);
            recyclerView.setLayoutManager(gridLayoutManager);//这里用线性宫格显示 类似于grid view
            if (gridLayoutManager != null) {//设置头部及底部View占据整行空间
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (adapter.isHeaderView(position) || adapter.isBottomView(position)) ? gridLayoutManager.getSpanCount() : 1;
                    }
                });
            }
        } else if (layoutType ==  TYPE_LAYOUT.TYPE_STAGGERED_GRID_LAYOUT) {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));//这里用线性显示 类似于list view
        }
    }

}
