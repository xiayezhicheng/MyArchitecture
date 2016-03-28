package com.wanghao.myarchitecture.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wanghao.myarchitecture.di.component.DaggerFragmentComponent;
import com.wanghao.myarchitecture.di.module.GroupModule;
import com.wanghao.myarchitecture.di.module.RentalModule;
import com.wanghao.myarchitecture.domain.entity.Rental;
import com.wanghao.myarchitecture.enums.TYPE_LAYOUT;
import com.wanghao.myarchitecture.ui.adapter.RentalItemAdapter;
import com.wanghao.myarchitecture.ui.viewmodel.ItemRentalViewModel;
import com.wanghao.myarchitecture.vendor.Config;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by wanghao on 2015/9/23.
 */
public class RentalFragment extends BaseRefreshListFragment<Rental>{

    @Inject
    ItemRentalViewModel itemRentalViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFragmentComponent
                .builder()
                .groupModule(new GroupModule(getActivity()))
                .rentalModule(new RentalModule(getActivity()))
                .build().inject(this);
    }

    @Override
    protected RentalItemAdapter getAdapter() {
        itemRentalViewModel.setData(data);
        return itemRentalViewModel.getAdapter();
    }


    @Override protected Subscription loadObservable(Subscriber<List<Rental>> subscriber, int page) {

        return itemRentalViewModel.getRentalList(subscriber, page, Config.LIST_COUNT);
    }

    @Override
    protected TYPE_LAYOUT getLayoutType() {
        return TYPE_LAYOUT.TYPE_GRID_LAYOUT;
    }

}
