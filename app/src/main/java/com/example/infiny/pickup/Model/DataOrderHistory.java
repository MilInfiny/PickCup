package com.example.infiny.pickup.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by infiny on 10/6/17.
 */

public class DataOrderHistory implements Serializable,Parcelable {
    private static final long serialVersionUID = 1L;
    private String userDetail;

    private String timeForPickcup;

    private String __v;

    private String orderStatus;

    private String updatedAt;

    private String parcel;

    private Ordered[] Ordered;

    private ShopDetail shopDetail;

    private String _id;

    private String createdAt;

    private String otp;

    private String orderId;

    private String note;

    private String totalPrice;


    protected DataOrderHistory(Parcel in) {
        userDetail = in.readString();
        timeForPickcup = in.readString();
        __v = in.readString();
        orderStatus = in.readString();
        updatedAt = in.readString();
        parcel = in.readString();
        _id = in.readString();
        createdAt = in.readString();
        otp = in.readString();
        orderId = in.readString();
        note = in.readString();
        totalPrice = in.readString();
    }

    public static final Creator<DataOrderHistory> CREATOR = new Creator<DataOrderHistory>() {
        @Override
        public DataOrderHistory createFromParcel(Parcel in) {
            return new DataOrderHistory(in);
        }

        @Override
        public DataOrderHistory[] newArray(int size) {
            return new DataOrderHistory[size];
        }
    };

    public String getUserDetail ()
    {
        return userDetail;
    }

    public void setUserDetail (String userDetail)
    {
        this.userDetail = userDetail;
    }

    public String getTimeForPickcup ()
    {
        return timeForPickcup;
    }

    public void setTimeForPickcup (String timeForPickcup)
    {
        this.timeForPickcup = timeForPickcup;
    }

    public String get__v ()
    {
        return __v;
    }

    public void set__v (String __v)
    {
        this.__v = __v;
    }

    public String getOrderStatus ()
    {
        return orderStatus;
    }

    public void setOrderStatus (String orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public String getUpdatedAt ()
    {
        return updatedAt;
    }

    public void setUpdatedAt (String updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public String getParcel ()
    {
        return parcel;
    }

    public void setParcel (String parcel)
    {
        this.parcel = parcel;
    }

    public Ordered[] getOrdered ()
    {
        return Ordered;
    }

    public void setOrdered (Ordered[] Ordered)
    {
        this.Ordered = Ordered;
    }

    public ShopDetail getShopDetail ()
    {
        return shopDetail;
    }

    public void setShopDetail (ShopDetail shopDetail)
    {
        this.shopDetail = shopDetail;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getOtp ()
    {
        return otp;
    }

    public void setOtp (String otp)
    {
        this.otp = otp;
    }

    public String getOrderId ()
    {
        return orderId;
    }

    public void setOrderId (String orderId)
    {
        this.orderId = orderId;
    }

    public String getNote ()
    {
        return note;
    }

    public void setNote (String note)
    {
        this.note = note;
    }

    public String getTotalPrice ()
    {
        return totalPrice;
    }

    public void setTotalPrice (String totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [userDetail = "+userDetail+", timeForPickcup = "+timeForPickcup+", __v = "+__v+", orderStatus = "+orderStatus+", updatedAt = "+updatedAt+", parcel = "+parcel+", Ordered = "+Ordered+", shopDetail = "+shopDetail+", _id = "+_id+", createdAt = "+createdAt+", otp = "+otp+", orderId = "+orderId+", note = "+note+", totalPrice = "+totalPrice+"]";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userDetail);
        dest.writeString(timeForPickcup);
        dest.writeString(__v);
        dest.writeString(orderStatus);
        dest.writeString(updatedAt);
        dest.writeString(parcel);
        dest.writeString(_id);
        dest.writeString(createdAt);
        dest.writeString(otp);
        dest.writeString(orderId);
        dest.writeString(note);
        dest.writeString(totalPrice);
    }
}
