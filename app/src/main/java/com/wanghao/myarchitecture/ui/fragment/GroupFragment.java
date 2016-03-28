package com.wanghao.myarchitecture.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wanghao.myarchitecture.di.component.DaggerFragmentComponent;
import com.wanghao.myarchitecture.di.module.GroupModule;
import com.wanghao.myarchitecture.di.module.RentalModule;
import com.wanghao.myarchitecture.domain.entity.Group;
import com.wanghao.myarchitecture.enums.TYPE_LAYOUT;
import com.wanghao.myarchitecture.ui.adapter.GroupItemAdapter;
import com.wanghao.myarchitecture.ui.viewmodel.ItemGroupViewModel;
import com.wanghao.myarchitecture.vendor.Config;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

public class GroupFragment extends BaseRefreshListFragment<Group> {

    @Inject
    ItemGroupViewModel itemGroupViewModel;

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
    protected GroupItemAdapter getAdapter() {
        GroupItemAdapter adapter = itemGroupViewModel.getAdapter();
        adapter.setGroups(data);
        return adapter;
    }

    @Override
    protected Subscription loadObservable(Subscriber<List<Group>> subscriber, int page) {

        return itemGroupViewModel.getGroupList(subscriber, page, Config.LIST_COUNT);
    }

    @Override
    protected TYPE_LAYOUT getLayoutType() {
        return TYPE_LAYOUT.TYPE_LINEAR_LAYOUT;
    }

}
