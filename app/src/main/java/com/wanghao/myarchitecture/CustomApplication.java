package com.wanghao.myarchitecture;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.wanghao.myarchitecture.utils.SharedPreferencesUtils;

import me.drakeet.library.CrashWoodpecker;

/**
 * Created by wanghao on 2015/9/23.
 */
public class CustomApplication extends Application {

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        //LeakCanary
        mRefWatcher = LeakCanary.install(this);
        //CrashWoodpecker
        CrashWoodpecker.init(this);
        //初始化SharedPreferences
        SharedPreferencesUtils.getInstance().Builder(this);
        // Occasional EOFException when reading cached file
        // 1. Try to load lots of previously cached images in a ListView by
        // quickly scrolling back and forth
        // 2. Some images will fail to load
        System.setProperty("http.keepAlive", "false");
        initImageLoader(this);

    }

    /**
     * 初始化UIL
     *
     * @author wanghao
     * @since 2014-11-17 下午1:37:16
     * @param context
     * @return void
     */
    private void initImageLoader(Context context) {

        // 磁盘缓存：如配置了diskCacheSize和diskCacheFileCount，就使用的是LruDiscCache，否则使用的是UnlimitedDiscCache
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(3)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(10 * 1024 * 1024))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .imageDownloader(
                        new BaseImageDownloader(context, 5 * 1000, 20 * 1000))
                 //.writeDebugLogs()// 发布版本时删除
                .build();
        // 用configuration初始化ImageLoader
        ImageLoader.getInstance().init(configuration);

    }

}
