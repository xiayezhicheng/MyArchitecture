package com.wanghao.myarchitecture.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.wanghao.myarchitecture.R;
import com.wanghao.myarchitecture.adapter.SlidingPagerAdapter;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    @Bind(R.id.app_toolbar) Toolbar toolbar;
    @Bind(R.id.viewpager) ViewPager viewPager;
    @Bind(R.id.sliding_tabs) TabLayout tabLayout;
    @BindString(R.string.app_name) String app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewPager.setAdapter(new SlidingPagerAdapter(getSupportFragmentManager()));
        toolbar.setTitle(app_name);
        tabLayout.setupWithViewPager(viewPager);
    }


}
