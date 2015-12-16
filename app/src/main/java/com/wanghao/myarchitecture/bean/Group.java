package com.wanghao.myarchitecture.bean;


import android.databinding.Bindable;

import com.wanghao.myarchitecture.BR;

/**
 * Created by wanghao on 2015/9/23.
 */
public class Group extends BaseModel{

    private String itemid;
    private String latitude;
    private String longitude;
    private String title;
    private String price;
    private String address;
    private String thumb;
    private String orders;
    private String collid;
    private String alarm_id;

    @Bindable
    public String getItemid() {

        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
        notifyPropertyChanged(BR.itemid);
    }

    @Bindable
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
        notifyPropertyChanged(BR.latitude);
    }

    @Bindable
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
        notifyPropertyChanged(BR.longitude);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
        notifyPropertyChanged(BR.thumb);
    }

    @Bindable
    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
        notifyPropertyChanged(BR.orders);
    }

    @Bindable
    public String getCollid() {
        return collid;
    }

    public void setCollid(String collid) {
        this.collid = collid;
        notifyPropertyChanged(BR.collid);
    }

    @Bindable
    public String getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(String alarm_id) {
        this.alarm_id = alarm_id;
        notifyPropertyChanged(BR.alarm_id);
    }

}