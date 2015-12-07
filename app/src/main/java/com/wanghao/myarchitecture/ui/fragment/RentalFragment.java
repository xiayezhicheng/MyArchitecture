package com.wanghao.myarchitecture.ui.fragment;

import android.widget.Toast;

import com.wanghao.myarchitecture.enums.TYPE_LAYOUT;
import com.wanghao.myarchitecture.adapter.RentalItemAdapter;
import com.wanghao.myarchitecture.bean.Rental;
import com.wanghao.myarchitecture.vendor.API;

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
                Toast.makeText(getActivity(), "租房了啊，注意:" + position, Toast.LENGTH_SHORT).show();
            }
        });
        return rentalItemAdapter;
    }

    @Override
    protected Class<Rental[]> getDataType() {
        return Rental[].class;
    }

    @Override
    protected String getUrlString() {
        return API.RENTAL;
    }

    @Override
    protected TYPE_LAYOUT getLayoutType() {
        return TYPE_LAYOUT.TYPE_GRID_LAYOUT;
    }

}
