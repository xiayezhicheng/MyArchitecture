package com.wanghao.myarchitecture.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import com.wanghao.myarchitecture.R;
import com.wanghao.myarchitecture.ui.adapter.SlidingPagerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends ToolbarActivity {

    @Bind(R.id.viewpager) ViewPager viewPager;
    @Bind(R.id.sliding_tabs) TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null) actionBar.setTitle("简悦架构");
        viewPager.setAdapter(new SlidingPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onCreateBinding() {
        DataBindingUtil.setContentView(this,R.layout.activity_main);
    }
}
