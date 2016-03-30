package com.wanghao.myarchitecture.di.module;

import android.content.Context;

import com.wanghao.myarchitecture.ui.viewmodel.GroupViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wanghao on 2016/3/27.
 */
@Singleton
@Module
public class GroupModule {

    private Context context;

    public GroupModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    protected GroupViewModel provideGroupViewModel(){
        return new GroupViewModel(context);
    }

    @Singleton
    @Provides
    protected Context provideContext(){
        return context;
    }
}
