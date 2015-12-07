package com.wanghao.myarchitecture.ui.fragment;


import android.widget.Toast;

import com.wanghao.myarchitecture.enums.TYPE_LAYOUT;
import com.wanghao.myarchitecture.adapter.GroupItemAdapter;
import com.wanghao.myarchitecture.bean.Group;
import com.wanghao.myarchitecture.vendor.API;



public class GroupFragment extends BaseRefreshListFragment<Group> {

    @Override
    protected GroupItemAdapter getAdapter() {
        GroupItemAdapter groupItemAdapter = new GroupItemAdapter(getActivity(), data);
        groupItemAdapter.setOnItemClickListener(new GroupItemAdapter.OnItemClick() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(),"团购了啊，注意:"+position,Toast.LENGTH_SHORT).show();
            }
        });
        return groupItemAdapter;
    }

    @Override
    protected Class<Group[]> getDataType() {
        return Group[].class;
    }

    @Override
    protected String getUrlString() {
        return API.GROUP;
    }

    @Override
    protected TYPE_LAYOUT getLayoutType() {
        return TYPE_LAYOUT.TYPE_LINEAR_LAYOUT;
    }

}
