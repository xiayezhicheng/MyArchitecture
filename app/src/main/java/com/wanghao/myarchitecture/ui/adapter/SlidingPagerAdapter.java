package com.wanghao.myarchitecture.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wanghao.myarchitecture.ui.fragment.GroupFragment;
import com.wanghao.myarchitecture.ui.fragment.RentalFragment;

/**
 * Created by wanghao on 2015/9/22.
 */
public class SlidingPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[]{"团购","租房"};

    public SlidingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        if (position==0){
            fragment = new GroupFragment();
        }else {
            fragment = new RentalFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
