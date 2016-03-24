package com.wanghao.myarchitecture.di.component;

import com.wanghao.myarchitecture.di.module.ApiModule;
import com.wanghao.myarchitecture.ui.fragment.GroupFragment;
import com.wanghao.myarchitecture.ui.fragment.RentalFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by wanghao on 2016/3/23.
 */
@Singleton
@Component(modules = ApiModule.class)
public interface FragmentComponent{

    void inject(GroupFragment groupFragment);

    void inject(RentalFragment rentalFragment);
}
