package com.wanghao.myarchitecture.service;

import com.wanghao.myarchitecture.Config;
import com.wanghao.myarchitecture.bean.Group;
import com.wanghao.myarchitecture.bean.Rental;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wanghao on 2016/3/23.
 */
public interface ApiService {

    @GET(Config.GROUP)
    Observable<List<Group>> getGroupList(@Query("page")int page, @Query("count")int count);

    @GET(Config.RENTAL)
    Observable<List<Rental>> getRentalList(@Query("page")int page, @Query("count")int count);
}
