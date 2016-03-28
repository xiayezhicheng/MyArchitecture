package com.wanghao.myarchitecture.di.component;

import com.wanghao.myarchitecture.di.module.GroupModule;
import com.wanghao.myarchitecture.di.module.RentalModule;
import com.wanghao.myarchitecture.ui.fragment.GroupFragment;
import com.wanghao.myarchitecture.ui.fragment.RentalFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by wanghao on 2016/3/23.
 */
@Singleton
@Component(modules = {GroupModule.class,RentalModule.class})
public interface FragmentComponent{

    void inject(GroupFragment groupFragment);

    void inject(RentalFragment rentalFragment);
}
