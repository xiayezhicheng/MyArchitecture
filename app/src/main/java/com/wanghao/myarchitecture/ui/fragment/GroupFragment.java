package com.wanghao.myarchitecture.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wanghao.myarchitecture.Config;
import com.wanghao.myarchitecture.adapter.GroupItemAdapter;
import com.wanghao.myarchitecture.bean.Group;
import com.wanghao.myarchitecture.di.component.DaggerFragmentComponent;
import com.wanghao.myarchitecture.di.module.ApiModule;
import com.wanghao.myarchitecture.enums.TYPE_LAYOUT;
import com.wanghao.myarchitecture.ui.activity.DetailMsgActivity;
import com.wanghao.myarchitecture.vendor.Api;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

public class GroupFragment extends BaseRefreshListFragment<Group> {

    @Inject
    Api api;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFragmentComponent
                .builder()
                .apiModule(new ApiModule())
                .build().inject(this);
    }

    @Override
    protected GroupItemAdapter getAdapter() {
        GroupItemAdapter groupItemAdapter = new GroupItemAdapter(getActivity(), data);
        GroupItemAdapter.setOnItemClickListener(new GroupItemAdapter.OnItemClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), DetailMsgActivity.class);
                Group clickGroup = data.get(position);
                intent.putExtra(Config.Key_House_Title,clickGroup.getTitle());
                intent.putExtra(Config.Key_House_Img,clickGroup.getThumb());
                startActivity(intent);
            }
        });
        return groupItemAdapter;
    }

    @Override
    protected Subscription loadObservable(Subscriber<List<Group>> subscriber, int page) {

        return api.getGroupList(subscriber, page, Config.LIST_COUNT);
    }


    @Override
    protected TYPE_LAYOUT getLayoutType() {
        return TYPE_LAYOUT.TYPE_LINEAR_LAYOUT;
    }

}
