package com.wanghao.myarchitecture.vendor;

import com.wanghao.myarchitecture.bean.Group;
import com.wanghao.myarchitecture.bean.Rental;
import com.wanghao.myarchitecture.utils.Config;
import com.wanghao.myarchitecture.utils.OkHttpClientUtil;
import com.wanghao.myarchitecture.utils.SchedulersUtils;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by wanghao on 2016/3/18.
 */
public class ApiUtils {
    private Retrofit retrofit;

    public static ApiUtils instance;

    public static ApiUtils getInstance(){
        if (null==instance){
            synchronized (ApiUtils.class){
                if (null==instance){
                    instance = new ApiUtils();
                }
            }
        }
        return instance;
    }

    private final ApiService mApiService;

    public ApiUtils(){

        OkHttpClient client = OkHttpClientUtil.getClient();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Config.BASE_URL)
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    public interface ApiService {

        @GET(Config.GROUP)
        Observable<List<Group>> getGroupList(@Query("page")int page, @Query("count")int count);

        @GET(Config.RENTAL)
        Observable<List<Rental>> getRentalList(@Query("page")int page, @Query("count")int count);

    }

    /**
     * 用于获取团购房的数据
     * @param subscriber  由调用者传过来的观察者对象
     * @param page 页数
     * @param count 获取长度
     */
    public Subscription getGroupList(Subscriber<List<Group>> subscriber, int page, int count){

        return mApiService.getGroupList(page, count)
//                .map(new HttpResultFunc<List<Subject>>())
                .retry(2)
                .compose(SchedulersUtils.<List<Group>>applyExecutorSchedulers())
                .subscribe(subscriber);

    }

    /**
     * 用于获取出租房的数据
     * @param subscriber  由调用者传过来的观察者对象
     * @param page 页数
     * @param count 获取长度
     */
    public Subscription getRentalList(Subscriber<List<Rental>> subscriber, int page, int count){

        return mApiService.getRentalList(page, count)
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
}
