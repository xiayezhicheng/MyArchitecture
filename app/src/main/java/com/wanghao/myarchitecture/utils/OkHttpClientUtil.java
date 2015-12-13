package com.wanghao.myarchitecture.utils;

import com.squareup.okhttp.OkHttpClient;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanghao on 2015/12/12.
 */
public class OkHttpClientUtil {
    private static OkHttpClient okHttpClient = null;

    public static OkHttpClient get() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(Config.OKHTTP_CLIENT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
            okHttpClient.setWriteTimeout(Config.OKHTTP_CLIENT_WRITE_TIMEOUT, TimeUnit.SECONDS);
            okHttpClient.setReadTimeout(Config.OKHTTP_CLIENT_READ_TIMEOUT, TimeUnit.SECONDS);
        }

        return okHttpClient;
    }
}
