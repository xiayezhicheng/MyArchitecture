package com.wanghao.myarchitecture.utils;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by wanghao on 2015/12/12.
 */
public class RxUtils {
    private static final String TAG = "RxUtils";
    //    public static String UA = "acfun/1.0 (Linux; U; Android " + Build.VERSION.RELEASE + "; " +
    //            Build.MODEL + "; " + Locale.getDefault().getLanguage() + "-" +
    //            Locale.getDefault().getCountry().toLowerCase() +
    //            ") AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 ";

    private RxUtils() {

    }

    public static void unsubscribeIfNotNull(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public static CompositeSubscription getNewCompositeSubIfUnsubscribed(CompositeSubscription subscription) {
        if (subscription == null || subscription.isUnsubscribed()) {
            return new CompositeSubscription();
        }

        return subscription;
    }


    public static <T> T createApi(Class<T> c, String url) {
        OkHttpClient client = OkHttpClientUtil.get(); //create OKHTTPClient
        //        client.interceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        client.interceptors().add(new LoggingInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(c);

    }

    //    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
    //        @Override
    //        public Response intercept(final Chain chain) throws IOException {
    //            Response originalResponse = chain.proceed(chain.request());
    //            return originalResponse.newBuilder()
    //                    .header("User-Agent", UA)
    //                    .header("Accept", "application/json; q=0.5")
    //                    .build();
    //        }
    //    };

    /**
     * see http://stackoverflow.com/questions/24952199/okhttp-enable-logs
     */
    static class LoggingInterceptor implements Interceptor {
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
