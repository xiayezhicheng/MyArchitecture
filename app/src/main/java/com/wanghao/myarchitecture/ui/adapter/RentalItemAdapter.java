package com.wanghao.myarchitecture.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wanghao.myarchitecture.R;
import com.wanghao.myarchitecture.databinding.ItemRentalBinding;
import com.wanghao.myarchitecture.domain.entity.Rental;

import java.util.List;

/**
 * Created by wanghao on 2015/9/24.
 */
public class RentalItemAdapter extends HeaderBottomItemAdapter<Rental>  {

    public RentalItemAdapter(Context context, List<Rental> list) {
        super(context, list);
    }

    @Override
    public void bindContentViewHolder(RecyclerView.ViewHolder holder,final int position) {
        ((ContentViewHolder)holder).binding.setVariable(com.wanghao.myarchitecture.BR.rental,getList().get(position));
        ((ContentViewHolder)holder).binding.executePendingBindings();
    }

    @Override
    protected RecyclerView.ViewHolder onCreateContentView(ViewGroup parent) {
        ItemRentalBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_rental,parent,false);
        return new ContentViewHolder(binding);
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder{

        ItemRentalBinding binding;

        public ContentViewHolder(ItemRentalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
