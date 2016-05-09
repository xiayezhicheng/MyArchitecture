package com.wanghao.myarchitecture.ui.fragment;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dlut.wanghao.myarchitecture.network.GsonRequest;
import com.dlut.wanghao.myarchitecture.network.RequestManager;

/**
 * Created by wanghao on 2016/5/9.
 */
public class BaseRefreshListPresenter<T> implements BaseRefreshListContract.Presenter {

    private BaseRefreshListContract.View baseRefreshListView;

    private String url;

    private int mCount;

    private Class<T[]> dataType;

    public BaseRefreshListPresenter(BaseRefreshListContract.View baseRefreshListView,
                                    Class<T[]> dataType, int mCount, String url) {

        this.baseRefreshListView = baseRefreshListView;
        this.dataType = dataType;
        this.mCount = mCount;
        this.url = url;

    }

    @Override
    public void start() {

    }

    @Override
    public void loadFirstPage() {
        loadData(0);
        baseRefreshListView.onLoadFirst();
    }

    @Override
    public void loadNextPage(int page) {
        loadData(page+1);
        baseRefreshListView.onLoadNext();
    }

    @Override
    public void loadData(final int page) {
        final boolean isRefreshFromTop = page == 0;

        GsonRequest<T[]> request = new GsonRequest<T[]>(String.format(url, page, mCount), dataType,
                new Response.Listener<T[]>() {

                    @Override
                    public void onResponse(final T[] response) {

                        baseRefreshListView.onLoadResponse(response,page);

                    }
                }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    baseRefreshListView.onLoadError(error,page);

                }
            }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestManager.addRequest(request, this);
    }

    @Override
    public void cancelRequest() {
        RequestManager.cancelAll(this);
    }

}
