package com.wanghao.myarchitecture.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wanghao.myarchitecture.R;
import com.wanghao.myarchitecture.databinding.ListRentalBinding;
import com.wanghao.myarchitecture.domain.entity.Rental;
import com.wanghao.myarchitecture.ui.viewmodel.ItemRentalViewModel;

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
        ((ContentViewHolder)holder).bindRental(getList().get(position));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateContentView(ViewGroup parent) {
        ListRentalBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.list_rental,parent,false);
        return new ContentViewHolder(binding);
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder{

        ListRentalBinding binding;
        ItemRentalViewModel viewModel;

        public ContentViewHolder(ListRentalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindRental(Rental rental) {
            viewModel = binding.getViewModel();
            if (viewModel == null) {
                binding.setViewModel(new ItemRentalViewModel(itemView.getContext(), rental));
            } else {
                viewModel.setRental(rental);
            }
        }
    }
}
