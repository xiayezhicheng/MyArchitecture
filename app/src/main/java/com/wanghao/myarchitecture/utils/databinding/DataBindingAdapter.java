package com.wanghao.myarchitecture.utils.databinding;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by wanghao on 2016/3/25.
 */
public class DataBindingAdapter {

    private static Drawable mDefaultImageDrawable = new ColorDrawable(Color.argb(255, 201, 201, 201));
    private static DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageForEmptyUri(mDefaultImageDrawable)
                        .showImageOnFail(mDefaultImageDrawable)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                        .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位 ,防止图片显示错位
                        .build();

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView iv, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)){
            ImageLoader.getInstance().displayImage(imageUrl, iv, options);
        }
    }



   /* @BindingAdapter({"android:onClick"})
    public static void setOnClick(View view,View.OnClickListener clickListener){
        view.setOnClickListener(clickListener);
    }*/
}
