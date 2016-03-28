package com.wanghao.myarchitecture.utils.databinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.wanghao.myarchitecture.ui.adapter.HeaderBottomItemAdapter;

/**
 * Created by wanghao on 2016/3/25.
 */
public class DBRecyclerView {


    @BindingAdapter({"bind:adapter"})
    public static void bindAdapter(RecyclerView recyclerView, HeaderBottomItemAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }
}
