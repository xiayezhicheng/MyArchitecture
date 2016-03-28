package com.wanghao.myarchitecture.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

/**
 * Created by wanghao on 2015/9/24.
 */
public abstract class HeaderBottomItemAdapter<E> extends BaseMultipleItemAdapter {

    private List<E> list;
    private View view;

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void add(E e) {
        this.list.add(e);
        notifyItemInserted(mHeaderCount + list.size());
    }

    public void addAll(List<E> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        this.list.remove(position);
        notifyItemRemoved(position);
    }

    public HeaderBottomItemAdapter(Context context, List<E> list) {
        super(context);
        this.list = list;
        mHeaderCount = 0;
        mBottomCount = 1;
    }

    public int getHeaderViewCount(){
        return mHeaderCount;
    }

    public int getBottomViewCount(){
        return mBottomCount;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder){

        }else if (holder instanceof BottomViewHolder){

        }else {
            bindContentViewHolder(holder,position);
        }
    }


    @Override
    protected RecyclerView.ViewHolder onCreateHeaderView(ViewGroup parent) {
//        return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.headerView,parent,false));
        return null;
    }


    @Override
    protected RecyclerView.ViewHolder onCreateBottomView(ViewGroup parent) {

        return new BottomViewHolder(view);
    }

    @Override
    public int getContentItemCount() {
        return list==null?0:list.size();
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder{

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }


    public static class BottomViewHolder extends RecyclerView.ViewHolder{

        public BottomViewHolder(View itemView) {
            super(itemView);
        }
    }

    public abstract void bindContentViewHolder(RecyclerView.ViewHolder holder, int position);

    public void setBottomView(View view){
        view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        this.view = view;
    }
}
