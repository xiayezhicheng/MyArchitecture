package com.wanghao.myarchitecture.ui.fragment;

import android.widget.Toast;
import com.wanghao.myarchitecture.adapter.RentalItemAdapter;
import com.wanghao.myarchitecture.bean.Rental;
import com.wanghao.myarchitecture.enums.TYPE_LAYOUT;
import com.wanghao.myarchitecture.utils.Config;
import com.wanghao.myarchitecture.utils.RxUtils;
import com.wanghao.myarchitecture.vendor.ApiService;
import java.util.List;
import rx.Observable;

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


    @Override protected Observable<List<Rental>> loadObservable(int page) {
        ApiService service = RxUtils.createApi(ApiService.class, Config.BASE_URL);
        return service.getRentalList(page, Config.LIST_COUNT);
    }

    @Override
    protected TYPE_LAYOUT getLayoutType() {
        return TYPE_LAYOUT.TYPE_GRID_LAYOUT;
    }

}
