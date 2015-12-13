package com.wanghao.myarchitecture.vendor;

import com.wanghao.myarchitecture.bean.Group;
import com.wanghao.myarchitecture.bean.Rental;
import com.wanghao.myarchitecture.utils.Config;
import java.util.List;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by wanghao on 2015/12/12.
 */
public interface ApiService {

    @GET(Config.GROUP)
    Observable<List<Group>> getGroupList(@Query("page")int page, @Query("count")int count);

    @GET(Config.RENTAL)
    Observable<List<Rental>> getRentalList(@Query("page")int page, @Query("count")int count);

}
