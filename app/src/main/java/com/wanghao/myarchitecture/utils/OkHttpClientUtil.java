package com.wanghao.myarchitecture.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wanghao on 2016/3/18.
 */
public class OkHttpClientUtil {

    private static OkHttpClient.Builder builder;

    public static OkHttpClient.Builder get() {
        if (null == builder ) {
            builder = new OkHttpClient.Builder();
            builder.connectTimeout(Config.OKHTTP_CLIENT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
            builder.writeTimeout(Config.OKHTTP_CLIENT_WRITE_TIMEOUT, TimeUnit.SECONDS);
            builder.readTimeout(Config.OKHTTP_CLIENT_READ_TIMEOUT, TimeUnit.SECONDS);
            builder.interceptors().add(new LoggingInterceptor());
        }

        return builder;
    }

    /**
     * see http://stackoverflow.com/questions/24952199/okhttp-enable-logs
     */
    private static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            //Log.v("OkHttp", String.format("Sending request %s on %s%n%s",
            //        request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            //Log.v("OkHttp", String.format("Received response for %s in %.1fms%n%s",
            //        response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }
}
