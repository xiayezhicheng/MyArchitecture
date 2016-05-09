package com.wanghao.myarchitecture.ui.fragment;

import com.android.volley.VolleyError;
import com.wanghao.myarchitecture.ui.BasePresenter;

/**
 * Created by wanghao on 2016/5/9.
 */
public interface BaseRefreshListContract {

    interface View<T>{

        void onLoadResponse(T[] response, int page);

        void onLoadError(VolleyError error, int page);

        void onLoadNext();

        void onLoadFirst();

    }

    interface Presenter extends BasePresenter{

        void loadData(int page);

        void cancelRequest();

        void loadNextPage(int page);

        void loadFirstPage();
    }
}
