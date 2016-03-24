package com.wanghao.myarchitecture.utils;

import com.wanghao.myarchitecture.Config;

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

    private static OkHttpClient client;

    /**
     * retryOnConnectionFailure 方法为设置出现错误进行重新连接。
     *connectTimeout 设置超时时间
     * @return OkHttpClient实例
     */
    public static OkHttpClient getClient() {
        if (null == client ) {
            client = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .addNetworkInterceptor(mTokenInterceptor)
//                    .authenticator(mAuthenticator)
                    .connectTimeout(Config.OKHTTP_CLIENT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(Config.OKHTTP_CLIENT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(Config.OKHTTP_CLIENT_READ_TIMEOUT, TimeUnit.SECONDS)
                    .build();

        }

        return client;
    }


//    addNetworkInterceptor
//    * 让所有网络请求都附上你的拦截器，我这里设置了一个 token 拦截器，就是在所有网络请求的 header 加上 token 参数。
//    解释：那个 if 判断意思是，如果你的 token 是空的，就是还没有请求到 token，比如对于登陆请求，是没有 token 的，
//    只有等到登陆之后才有 token，这时候就不进行附着上 token。另外，如果你的请求中已经带有验证 header 了，
//    比如你手动设置了一个另外的 token，那么也不需要再附着这一个 token.
//    header 的 key 通常是 Authorization，如果你的不是这个，可以修改。
    static Interceptor mTokenInterceptor = new Interceptor() {
        @Override public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
//            if (Your.sToken == null || alreadyHasAuthorizationHeader(originalRequest)) {
//                return chain.proceed(originalRequest);
//            }
//            Request authorised = originalRequest.newBuilder()
//                    .header("Authorization", Your.sToken)
//                    .build();
//            return chain.proceed(authorised);

            //Log.v("OkHttp", String.format("Sending request %s on %s%n%s",
            //        originalRequest.url(), chain.connection(), originalRequest.headers()));
            return chain.proceed(originalRequest);
        }
    };


//    如果你需要在遇到诸如 401 Not Authorised 的时候进行刷新 token，可以使用 Authenticator，
//    这是一个专门设计用于当验证出现错误的时候，进行询问获取处理的拦截器：
 /*   static Authenticator mAuthenticator = new Authenticator() {
        @Override public Request authenticate(Route route, Response response)
                throws IOException {
            Your.sToken = service.refreshToken();
            return response.request().newBuilder()
                    .addHeader("Authorization", Your.sToken)
                    .build();
        }
    }*/
}
