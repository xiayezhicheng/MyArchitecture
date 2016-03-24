package com.wanghao.myarchitecture.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * Created by wanghao on 2015/9/23.
 */
public class NetUtils {

    private static boolean isNETWAP = false;

    public enum NetworkOperator {
        CHINA_MOBILE,
        CHINA_UNION,
        CHINA_NET,
        UNKOWN,
        NONE,
    }

    public enum NetType {
        NONE,
        UNKNOW,
        WIFI,
        MOBILE_GPRS,
        MOBILE_EDGE,
        MOBILE_3G
    }

    public static <Params, Progress, Result> void executeAsyncTask(
            AsyncTask<Params, Progress, Result> task, Params... params) {
        if (Build.VERSION.SDK_INT >= 11) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }

    /**
     * 检测网络是否连接（注：需要在配置文件即AndroidManifest.xml加入权限）
     *
     * @param context
     * @return true : 网络连接成功
     * @return false : 网络连接失败
     **/
    public static boolean isConnect(Context context) {
        NetworkInfo info = getCurrentActiveNetworkInfo(context);

        return info != null && info.getState() == NetworkInfo.State.CONNECTED;
    }

    public static NetType getCurrentNetType(Context context) {
        NetType type = NetType.NONE;

        // 获取当前活动的网络
        NetworkInfo info = getCurrentActiveNetworkInfo(context);
        if (info == null) {
            return type;
        }

        // 判断当前网络是否已经连接
        if (info.getState() == NetworkInfo.State.CONNECTED) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                type = NetType.WIFI;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                String subTypeName = info.getSubtypeName().toUpperCase();

                if (subTypeName.indexOf("GPRS") > -1) {
                    type = NetType.MOBILE_GPRS;
                } else if (subTypeName.indexOf("EDGE") > -1) {
                    type = NetType.MOBILE_EDGE;
                } else {
                    type = NetType.MOBILE_3G;
                }
            } else {
                type = NetType.UNKNOW;
            }
        } else if (info.getState() == NetworkInfo.State.CONNECTING) {
            type = NetType.UNKNOW;
            System.out.println("connecting " + info.getType());
        }

        return type;
    }

    private static NetworkInfo getCurrentActiveNetworkInfo(Context context) {
        NetworkInfo networkInfo = null;
        // 获取手机所有连接管理对象（包括对wi-fi,net,gsm,cdma等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        return networkInfo;
    }


    public static NetworkOperator getNetworkOperator(Context context) {
        NetworkOperator operator = NetworkOperator.NONE;
        String imsi = getIMSI(context);
        if (imsi != null) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                //因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，
                //134/159号段使用了此编号
                //中国移动
                operator = NetworkOperator.CHINA_MOBILE;
            } else if (imsi.startsWith("46001")) {
                //中国联通
                operator = NetworkOperator.CHINA_UNION;
            } else if (imsi.startsWith("46003")) {
                //中国电信
                operator = NetworkOperator.CHINA_NET;
            } else {
                operator = NetworkOperator.UNKOWN;
            }
        } else {
            operator = NetworkOperator.NONE;
        }

        return operator;
    }
    public static String getIMSI(Context context) {
        TelephonyManager telManager = (TelephonyManager)context.getSystemService(
                Context.TELEPHONY_SERVICE);
        String imsi = telManager.getSubscriberId();

        return imsi;
    }

    public static boolean isNETWAP() {
        return isNETWAP;
    }
}
