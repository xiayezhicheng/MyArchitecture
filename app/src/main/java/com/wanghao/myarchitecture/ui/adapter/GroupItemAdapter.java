package com.wanghao.myarchitecture.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wanghao.myarchitecture.R;
import com.wanghao.myarchitecture.databinding.ListGroupBinding;
import com.wanghao.myarchitecture.domain.entity.Group;
import com.wanghao.myarchitecture.ui.viewmodel.ItemGroupViewModel;

import java.util.List;

/**
 * Created by wanghao on 2015/9/24.
 */
public class GroupItemAdapter extends HeaderBottomItemAdapter<Group> {


    public GroupItemAdapter(Context context, List<Group> list) {
        super(context, list);
    }

    @Override
    public void bindContentViewHolder(RecyclerView.ViewHolder holder,final int position) {
        ((ContentViewHolder)holder).bindGroup(getList().get(position));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateContentView(ViewGroup parent) {
        ListGroupBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.list_group,parent,false);
        return new ContentViewHolder(binding);
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder{

        ListGroupBinding binding;
        ItemGroupViewModel viewModel;

        public ContentViewHolder(ListGroupBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindGroup(Group group) {
            viewModel = binding.getViewModel();
            if (viewModel == null) {
                binding.setViewModel(new ItemGroupViewModel(itemView.getContext(), group));
            } else {
                viewModel.setGroup(group);
            }
        }
    }
}
