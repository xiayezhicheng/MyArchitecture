package com.wanghao.myarchitecture.ui.viewmodel;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by wanghao on 2016/3/27.
 */
public class DetailMsgViewModel implements ViewModel{

    private final String  title;
    private final String  imgUrl;

    public DetailMsgViewModel(String title, String imgUrl) {
        this.title = title;
        this.imgUrl = imgUrl;
    }

    public String getTitle(){
        return this.title;
    }

    public String getImgUrl(){
        return this.imgUrl;
    }

    /**
     * 这个方法必须是static的
     * @param view
     * @param imgUrl
     */
    @BindingAdapter({"bind:imgUrl"})
    public static void loadImage(ImageView view, String imgUrl){
        if (!TextUtils.isEmpty(imgUrl)) {
            ImageLoader.getInstance().displayImage(imgUrl, view);
        }
    }

    @Override
    public void destroy() {

    }
}
