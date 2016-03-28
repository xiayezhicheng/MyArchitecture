package com.wanghao.myarchitecture.ui.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.wanghao.myarchitecture.domain.RetrofitClient;
import com.wanghao.myarchitecture.domain.SchedulersUtils;
import com.wanghao.myarchitecture.domain.entity.Group;
import com.wanghao.myarchitecture.domain.service.HouseService;
import com.wanghao.myarchitecture.ui.activity.DetailMsgActivity;
import com.wanghao.myarchitecture.ui.adapter.GroupItemAdapter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by wanghao on 2016/3/27.
 */
public class ItemGroupViewModel extends BaseObservable implements ViewModel{

    private Group group;
    private Context context;
    private List<Group> data;
    private GroupItemAdapter adapter;
    private HouseService mHouseService;

    @Inject
    public ItemGroupViewModel(Context context, Group group) {
        this.context = context;
        this.group = group;
        mHouseService = RetrofitClient.getInstance().create(HouseService.class);
    }

    public void onItemClick(View view){
        Intent intent = DetailMsgActivity.newIntent(context,group.getTitle(),group.getThumb());
        context.startActivity(intent);
    }

    public void setGroup(Group group){
        this.group = group;
        notifyChange();
    }

    public GroupItemAdapter getAdapter(){
        adapter = new GroupItemAdapter(context,data);
        return adapter;
    }

    public void setData(List<Group> data){
        this.data = data;
    }

    public String getPrice() {
        return group.getPrice();
    }
    public String getThumb() {
        return group.getThumb();
    }
    public String getTitle() {
        return group.getTitle();
    }
    public String getAddress() {
        return group.getAddress();
    }
    public String getOrders() {
        return group.getOrders();
    }

    /**
     * 用于获取团购房的数据
     * @param subscriber  由调用者传过来的观察者对象
     * @param page 页数
     * @param count 获取长度
     */
    public Subscription getGroupList(Subscriber<List<Group>> subscriber, int page, int count){

        return mHouseService.getGroupList(page, count)
//                .map(new HttpResultFunc<List<Subject>>())
                .retry(2)
                .compose(SchedulersUtils.<List<Group>>applyExecutorSchedulers())
                .subscribe(subscriber);

    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
//    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
//
//        @Override
//        public T call(HttpResult<T> httpResult) {
//            if (httpResult.getCount() == 0) {
//                throw new ApiException(100);
//            }
//            return httpResult.getSubjects();
//        }
//    }

    @Override
    public void destroy() {

    }
}
