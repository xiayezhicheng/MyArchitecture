package com.wanghao.myarchitecture.ui.fragment;

import android.content.Intent;

import com.wanghao.myarchitecture.adapter.GroupItemAdapter;
import com.wanghao.myarchitecture.bean.Group;
import com.wanghao.myarchitecture.enums.TYPE_LAYOUT;
import com.wanghao.myarchitecture.ui.activity.DetailMsgActivity;
import com.wanghao.myarchitecture.utils.Config;
import com.wanghao.myarchitecture.utils.RxUtils;
import com.wanghao.myarchitecture.vendor.ApiService;

import java.util.List;

import rx.Observable;

public class GroupFragment extends BaseRefreshListFragment<Group> {

    @Override
    protected GroupItemAdapter getAdapter() {
        GroupItemAdapter groupItemAdapter = new GroupItemAdapter(getActivity(), data);
        groupItemAdapter.setOnItemClickListener(new GroupItemAdapter.OnItemClick() {
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

    @Override protected Observable<List<Group>> loadObservable(int page) {
        ApiService service = RxUtils.createApi(ApiService.class, Config.BASE_URL);
        return service.getGroupList(page, Config.LIST_COUNT);
    }


    @Override
    protected TYPE_LAYOUT getLayoutType() {
        return TYPE_LAYOUT.TYPE_LINEAR_LAYOUT;
    }

}
