package com.wanghao.myarchitecture.di.module;

import android.content.Context;

import com.wanghao.myarchitecture.domain.entity.Group;
import com.wanghao.myarchitecture.ui.viewmodel.ItemGroupViewModel;

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
    protected ItemGroupViewModel provideGroupViewModel(){
        return new ItemGroupViewModel(context,new Group());
    }

    @Singleton
    @Provides
    protected Context provideContext(){
        return context;
    }
}
