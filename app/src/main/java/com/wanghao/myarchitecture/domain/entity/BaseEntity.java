package com.wanghao.myarchitecture.domain.entity;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;


/**
 * Created by wanghao on 2015/9/23.
 */
public class BaseEntity extends BaseObservable implements Parcelable {

    public String toJson(){
        return new Gson().toJson(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public BaseEntity() {
    }

    private BaseEntity(Parcel in) {
    }

    public static final Parcelable.Creator<BaseEntity> CREATOR = new Parcelable.Creator<BaseEntity>() {
        public BaseEntity createFromParcel(Parcel source) {
            return new BaseEntity(source);
        }

        public BaseEntity[] newArray(int size) {
            return new BaseEntity[size];
        }
    };
}
