package com.wanghao.myarchitecture.di.module;

import android.content.Context;

import com.wanghao.myarchitecture.ui.viewmodel.RentalViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wanghao on 2016/3/28.
 */
@Singleton
@Module
public class RentalModule {

    private Context context;

    public RentalModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    protected RentalViewModel provideRentalViewModel(){
        return new RentalViewModel(context);
    }

    @Singleton
    @Provides
    protected Context provideContext(){
        return context;
    }
}
