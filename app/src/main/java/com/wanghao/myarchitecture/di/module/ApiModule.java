package com.wanghao.myarchitecture.di.module;

import com.wanghao.myarchitecture.vendor.Api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wanghao on 2016/3/24.
 */
@Singleton
@Module
public class ApiModule {
    @Singleton
    @Provides
    protected Api provideApi(){

        return new Api();
    }
}
