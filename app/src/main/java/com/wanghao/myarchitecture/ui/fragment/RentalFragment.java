package com.wanghao.myarchitecture.ui.fragment;

import android.content.Intent;

import com.wanghao.myarchitecture.adapter.RentalItemAdapter;
import com.wanghao.myarchitecture.bean.Rental;
import com.wanghao.myarchitecture.enums.TYPE_LAYOUT;
import com.wanghao.myarchitecture.ui.activity.DetailMsgActivity;
import com.wanghao.myarchitecture.utils.Config;
import com.wanghao.myarchitecture.vendor.ApiUtils;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by wanghao on 2015/9/23.
 */
public class RentalFragment extends BaseRefreshListFragment<Rental>{

    @Override
    protected RentalItemAdapter getAdapter() {
        RentalItemAdapter rentalItemAdapter = new RentalItemAdapter(getActivity(), data);
        rentalItemAdapter.setOnItemClickListener(new RentalItemAdapter.OnItemClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), DetailMsgActivity.class);
                Rental clickRental = data.get(position);
                intent.putExtra(Config.Key_House_Title,clickRental.getTitle());
                intent.putExtra(Config.Key_House_Img,clickRental.getThumb());
                startActivity(intent);
            }
        });
        return rentalItemAdapter;
    }


    @Override protected Subscription loadObservable(Subscriber<List<Rental>> subscriber, int page) {

        return ApiUtils.getInstance().getRentalList(subscriber, page, Config.LIST_COUNT);
    }

    @Override
    protected TYPE_LAYOUT getLayoutType() {
        return TYPE_LAYOUT.TYPE_GRID_LAYOUT;
    }

}
