package com.wanghao.myarchitecture.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by wanghao on 2015/9/24.
 */
public abstract class BaseMultipleItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public enum ITEM_TYPE{
        ITEM_TYPE_HEADER,
        ITEM_TYPE_CONTENT,
        ITEM_TYPE_BOTTOM
    }

    protected LayoutInflater mLayoutInflater;
    protected Context mContext;
    protected int mHeaderCount;//头部View个数
    protected int mBottomCount;//底部View个数

    public BaseMultipleItemAdapter(Context context){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setHeaderCount(int headerCount){
        this.mHeaderCount = headerCount;
    }

    public void setmBottomCount(int bottomCount){
        this.mBottomCount = bottomCount;
    }

    public boolean isHeaderView(int position){
        return mHeaderCount !=0 && position < mHeaderCount;
    }

    public boolean isBottomView(int position){
        return mBottomCount != 0 && position >= (mHeaderCount + getContentItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (mHeaderCount != 0 && position < mHeaderCount){
            return ITEM_TYPE.ITEM_TYPE_HEADER.ordinal();
        }else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)){
            return ITEM_TYPE.ITEM_TYPE_BOTTOM.ordinal();
        }else {
            return ITEM_TYPE.ITEM_TYPE_CONTENT.ordinal();
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()){
            return onCreateHeaderView(parent);
        }else if (viewType == ITEM_TYPE.ITEM_TYPE_CONTENT.ordinal()){
            return onCreateContentView(parent);
        }else if (viewType == ITEM_TYPE.ITEM_TYPE_BOTTOM.ordinal()){
            return onCreateBottomView(parent);
        }
        return null;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() +mBottomCount;
    }

    protected abstract RecyclerView.ViewHolder onCreateHeaderView(ViewGroup parent);//创建头部View

    protected abstract RecyclerView.ViewHolder onCreateContentView(ViewGroup parent);//创建中间内容View

    protected abstract RecyclerView.ViewHolder onCreateBottomView(ViewGroup parent);//创建底部View

    public abstract int getContentItemCount();//获取中间内容个数
}
