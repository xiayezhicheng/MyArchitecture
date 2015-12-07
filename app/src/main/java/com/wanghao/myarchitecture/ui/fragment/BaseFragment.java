package com.wanghao.myarchitecture.ui.fragment;

import android.support.v4.app.Fragment;

import com.android.volley.Request;
import com.wanghao.myarchitecture.network.RequestManager;

/**
 * Created by wanghao on 2015/9/23.
 */
public class BaseFragment extends Fragment {

    protected void executeRequest(Request request) {
        RequestManager.addRequest(request, this);
    }

    @Override
    public void onStop() {
        super.onStop();
        RequestManager.cancelAll(this);
    }

}
