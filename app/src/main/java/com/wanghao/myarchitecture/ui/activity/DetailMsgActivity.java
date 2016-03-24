package com.wanghao.myarchitecture.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wanghao.myarchitecture.R;
import com.wanghao.myarchitecture.ui.base.ToolbarActivity;
import com.wanghao.myarchitecture.Config;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wanghao on 2015/12/13.
 */
public class DetailMsgActivity extends ToolbarActivity {

    @Bind(R.id.iv_header) ImageView mIvHeader;
    @Bind(R.id.tv_source) TextView mTvSource;
    @Bind(R.id.collapsingToolbarLayout) CollapsingToolbarLayout
            mCollapsingToolbarLayout;
    @Bind(R.id.nested_view) NestedScrollView mNestedScrollView;
    @Bind(R.id.fragment_webview) WebView fragment_webview;


    @Override
    public boolean canBack() {
        return true;
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
        loadData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_detail_msg;
    }

    private void init() {
        // 如果想使用 CollapsingToolbarLayout和Toolbar，那么设置标题就使用CollapsingToolbarLayout的setTitle()
        // 为可折叠toolbar设置标题
        mCollapsingToolbarLayout.setTitle("房屋详情");

        mNestedScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        if (Build.VERSION.SDK_INT>=21){
            mNestedScrollView.setElevation(0);
        }

    }

    private void loadData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(Config.Key_House_Title);
        String imgUrl = intent.getStringExtra(Config.Key_House_Img);
        mTvSource.setText(title);
        ImageLoader.getInstance().displayImage(imgUrl,mIvHeader);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragment_webview.removeAllViews();
        fragment_webview.destroy();
    }
}
