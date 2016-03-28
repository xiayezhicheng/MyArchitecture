package com.wanghao.myarchitecture.domain.service;

import com.wanghao.myarchitecture.domain.entity.Group;
import com.wanghao.myarchitecture.domain.entity.Rental;
import com.wanghao.myarchitecture.vendor.Config;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wanghao on 2016/3/23.
 */
public interface HouseService {

    @GET(Config.GROUP)
    Observable<List<Group>> getGroupList(@Query("page")int page, @Query("count")int count);

    @GET(Config.RENTAL)
    Observable<List<Rental>> getRentalList(@Query("page")int page, @Query("count")int count);
}
