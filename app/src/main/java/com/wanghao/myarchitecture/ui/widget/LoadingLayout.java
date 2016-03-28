package com.wanghao.myarchitecture.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.wanghao.myarchitecture.R;

/**
 * Created by wanghao on 2015/9/25.
 */
public class LoadingLayout extends FrameLayout{

    private int emptyView, errorView, unnetView, loadingView;
    private OnClickListener onRetryClickListener;

    public LoadingLayout(Context context) {
        this(context,null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingLayout,0,0);
        try {
            emptyView = a.getResourceId(R.styleable.LoadingLayout_emptyView,R.layout.empty_view);
            errorView = a.getResourceId(R.styleable.LoadingLayout_errorView,R.layout.error_view);
            unnetView = a.getResourceId(R.styleable.LoadingLayout_unnetView,R.layout.unnet_view);
            loadingView = a.getResourceId(R.styleable.LoadingLayout_loadingView,R.layout.loading_view);

            LayoutInflater inflater = LayoutInflater.from(getContext());
            inflater.inflate(emptyView, this, true);
            inflater.inflate(errorView,this,true);
            inflater.inflate(unnetView,this,true);
            inflater.inflate(loadingView,this,true);
        }finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        for (int i=0;i<getChildCount()-1;i++){
            getChildAt(i).setVisibility(GONE);
        }

        findViewById(R.id.btn_empty_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null!=onRetryClickListener){
                    onRetryClickListener.onClick(view);
                }
            }
        });

        findViewById(R.id.btn_error_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onRetryClickListener) {
                    onRetryClickListener.onClick(view);
                }
            }
        });
        findViewById(R.id.btn_unnet_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onRetryClickListener) {
                    onRetryClickListener.onClick(view);
                }
            }
        });
    }

    public void setOnRetryClickListener(OnClickListener onRetryClickListener){
        this.onRetryClickListener = onRetryClickListener;
    }

    public void showEmpty(){
        for (int i=0;i<this.getChildCount();i++){
            View child = this.getChildAt(i);
            if (i==0){
                child.setVisibility(VISIBLE);
            }else {
                child.setVisibility(GONE);
            }
        }
    }

    public void showError(){
        for (int i=0;i<this.getChildCount();i++){
            View child = this.getChildAt(i);
            if (i == 1) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    public void showUnnet(){
        for(int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 2) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    public void showLoading(){
        for(int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 3) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

    public void showContent() {
        for(int i = 0; i < this.getChildCount(); i++) {
            View child = this.getChildAt(i);
            if (i == 4) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }
}
