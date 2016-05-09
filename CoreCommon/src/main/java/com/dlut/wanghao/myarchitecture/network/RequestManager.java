package com.dlut.wanghao.myarchitecture.network;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.dlut.wanghao.myarchitecture.CustomApplication;
import com.dlut.wanghao.myarchitecture.utils.CacheUtils;

/**
 * Created by wanghao on 2015/9/22.
 */
public class RequestManager {

    private RequestManager(){

    }

    private static Cache openCache() {
        return new DiskBasedCache(CacheUtils.getExternalCacheDir(CustomApplication.getContext()),
                10 * 1024 * 1024);
    }

    private static RequestQueue newRequestQueue() {
        RequestQueue requestQueue = new RequestQueue(openCache(), new BasicNetwork(new HurlStack()));
        requestQueue.start();
        return requestQueue;
    }

    public static RequestQueue mRequestQueue = newRequestQueue();


    public static void addRequest(Request request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        mRequestQueue.add(request);
    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }
    public static void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }
}
