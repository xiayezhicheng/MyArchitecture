package com.wanghao.myarchitecture.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.wanghao.myarchitecture.BR;
import com.wanghao.myarchitecture.R;
import com.wanghao.myarchitecture.bean.Rental;
import com.wanghao.myarchitecture.databinding.ListRentalBinding;

import java.util.List;

/**
 * Created by wanghao on 2015/9/24.
 */
public class RentalItemAdapter extends HeaderBottomItemAdapter<Rental>  {

    private Drawable mDefaultImageDrawable = new ColorDrawable(Color.argb(255, 201, 201, 201));
    private DisplayImageOptions options;

    public RentalItemAdapter(Context context, List<Rental> list) {
        super(context, list);
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(mDefaultImageDrawable)
                .showImageOnFail(mDefaultImageDrawable)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位 ,防止图片显示错位
                .build();
    }

    @Override
    public void bindContentViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Rental rental = getList().get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClick.onClick(position);
            }
        });
        ImageLoader.getInstance().displayImage(rental.getThumb(), ((ContentViewHolder) holder).binding.imgRental, options);
        ((ContentViewHolder)holder).binding.setVariable(BR.rental, rental);
        ((ContentViewHolder)holder).binding.executePendingBindings();
    }

    @Override
    protected RecyclerView.ViewHolder onCreateContentView(ViewGroup parent) {
        ListRentalBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.list_rental,parent,false);
        ContentViewHolder holder = new ContentViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder{

        ListRentalBinding binding;

        public ContentViewHolder(View itemView) {
            super(itemView);
        }

        public ListRentalBinding getBinding() {
            return binding;
        }

        public void setBinding(ListRentalBinding binding) {
            this.binding = binding;
        }
    }

    private static OnItemClick mOnItemClick;

    public interface OnItemClick{
        void onClick(int position);
    }

    public static void setOnItemClickListener(OnItemClick onItemClick){
        mOnItemClick = onItemClick;
    }
}
