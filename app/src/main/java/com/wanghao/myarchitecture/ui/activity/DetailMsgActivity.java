package com.wanghao.myarchitecture.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wanghao.myarchitecture.R;
import com.wanghao.myarchitecture.utils.Config;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wanghao on 2015/12/13.
 */
public class DetailMsgActivity extends AppCompatActivity {

    @Bind(R.id.iv_header) ImageView mIvHeader;
    @Bind(R.id.tv_source) TextView mTvSource;
    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.collapsingToolbarLayout) CollapsingToolbarLayout
            mCollapsingToolbarLayout;
    @Bind(R.id.nested_view) NestedScrollView mNestedScrollView;


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_msg);
        ButterKnife.bind(this);
        init();
        loadData();
    }

    private void init() {
        // 如果想使用 CollapsingToolbarLayout和Toolbar，那么设置标题就使用CollapsingToolbarLayout的setTitle()
        //为可折叠toolbar设置标题
        mCollapsingToolbarLayout.setTitle("房屋详情");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mNestedScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        if (Build.VERSION.SDK_INT>=21){
            mNestedScrollView.setElevation(0);
        }

    }

    private void loadData() {
        String title = getIntent().getStringExtra(Config.Key_House_Title);
        String imgUrl = getIntent().getStringExtra(Config.Key_House_Img);
        mTvSource.setText(title);
        ImageLoader.getInstance().displayImage(imgUrl,mIvHeader);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
