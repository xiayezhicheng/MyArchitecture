package com.wanghao.myarchitecture.ui.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.wanghao.myarchitecture.domain.RetrofitClient;
import com.wanghao.myarchitecture.domain.SchedulersUtils;
import com.wanghao.myarchitecture.domain.entity.Rental;
import com.wanghao.myarchitecture.domain.service.HouseService;
import com.wanghao.myarchitecture.ui.activity.DetailMsgActivity;
import com.wanghao.myarchitecture.ui.adapter.RentalItemAdapter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by wanghao on 2016/3/28.
 */
public class ItemRentalViewModel extends BaseObservable implements ViewModel{

    private Rental rental;
    private Context context;
    private List<Rental> data;
    private HouseService mHouseService;

    @Inject
    public ItemRentalViewModel(Context context, Rental rental) {
        this.context = context;
        this.rental = rental;
        mHouseService = RetrofitClient.getInstance().create(HouseService.class);
    }

    public void onItemClick(View view){
        Intent intent = DetailMsgActivity.newIntent(context,rental.getTitle(),rental.getThumb());
        context.startActivity(intent);
    }

    public void setRental(Rental rental){
        this.rental = rental;
        notifyChange();
    }

    public RentalItemAdapter getAdapter(){
        return new RentalItemAdapter(context,data);
    }

    public void setData(List<Rental> data){
        this.data = data;
    }

    public String getPrice() {
        return rental.getPrice();
    }
    public String getThumb() {
        return rental.getThumb();
    }
    public String getTitle() {
        return rental.getTitle();
    }
    public String getAddress() {
        return rental.getAddress();
    }
    public String getRoom() {
        return rental.getRoom();
    }
    public String getHall() {
        return rental.getHall();
    }
    public String getToilet() {
        return rental.getToilet();
    }
    public String getHouseearm() {
        return rental.getHouseearm();
    }

    /**
     * 用于获取出租房的数据
     * @param subscriber  由调用者传过来的观察者对象
     * @param page 页数
     * @param count 获取长度
     */
    public Subscription getRentalList(Subscriber<List<Rental>> subscriber, int page, int count){

        return mHouseService.getRentalList(page, count)
//                .map(new HttpResultFunc<List<Subject>>())
                .retry(2)
                .compose(SchedulersUtils.<List<Rental>>applyExecutorSchedulers())
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
