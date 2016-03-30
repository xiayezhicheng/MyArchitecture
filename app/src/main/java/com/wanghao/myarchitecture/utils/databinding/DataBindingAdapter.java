package com.wanghao.myarchitecture.utils.databinding;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wanghao.myarchitecture.domain.entity.Group;
import com.wanghao.myarchitecture.domain.entity.Rental;
import com.wanghao.myarchitecture.ui.activity.DetailMsgActivity;

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

    @BindingAdapter({"bind:imgUrl"})
    public static void loadImage(ImageView iv, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)){
            ImageLoader.getInstance().displayImage(imageUrl, iv, options);
        }
    }

   @BindingAdapter({"bind:groupClick"})
    public static void setOnGroupClick(final View view, final Group group){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = view.getContext();
                Intent intent = DetailMsgActivity.newIntent(context, group.getTitle(), group.getThumb());
                context.startActivity(intent);
            }
        });
    }

    @BindingAdapter({"bind:rentalClick"})
    public static void setOnRentalClick(final View view, final Rental rental){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = view.getContext();
                Intent intent = DetailMsgActivity.newIntent(context, rental.getTitle(), rental.getThumb());
                context.startActivity(intent);
            }
        });
    }
}
