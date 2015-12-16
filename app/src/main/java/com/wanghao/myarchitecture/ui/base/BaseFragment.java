package com.wanghao.myarchitecture.ui.base;

import android.support.v4.app.Fragment;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by wanghao on 2015/12/14.
 */
public class BaseFragment extends Fragment {

    private CompositeSubscription mCompositeSubscription;


    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        return this.mCompositeSubscription;
    }


    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
