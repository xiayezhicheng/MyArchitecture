package com.wanghao.myarchitecture.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.wanghao.myarchitecture.R;

/**
 * Created by wanghao on 2015/12/14.
 */
public abstract class ToolbarActivity extends BaseActivity {

    public void onToolbarClick() {}

    protected Toolbar toolbar;
    protected AppBarLayout mAppBar;
    protected boolean mIsHidden = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        mAppBar = (AppBarLayout)findViewById(R.id.app_bar_layout);

        if (toolbar==null || mAppBar==null){
            throw new IllegalStateException("No Toolbar");
        }

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToolbarClick();
            }
        });
        setSupportActionBar(toolbar);

        if (canBack()){
            ActionBar actionbar = getSupportActionBar();
            if (actionbar!=null){
                actionbar.setDisplayHomeAsUpEnabled(true);
            }
        }

        if (Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(10.6f);
        }
    }

    /**
     * 是否提供返回
     * @return true:能返回；false：不能返回。
     */
    public boolean canBack() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected void setAppBarAlpha(float alpha) {
        mAppBar.setAlpha(alpha);
    }


    protected void hideOrShowToolbar() {
        mAppBar.animate()
                .translationY(mIsHidden ? 0 : -mAppBar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();

        mIsHidden = !mIsHidden;
    }

}
