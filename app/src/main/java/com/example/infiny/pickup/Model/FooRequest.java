package com.example.infiny.pickup.Model;

import java.util.ArrayList;

/**
 * Created by infiny on 9/28/17.
 */

public class FooRequest {
    private String parcel;

    private String shopDetail;

    private Ordered[] order;

    private String userToken;

    private String timeForPickcup;

    private String note;
    private String eligibleForRewards;

    private String totalPrice;

    public String getParcel ()
    {
        return parcel;
    }

    public void setParcel (String parcel)
    {
        this.parcel = parcel;
    }

    public String getShopDetail ()
    {
        return shopDetail;
    }

    public void setShopDetail (String shopDetail)
    {
        this.shopDetail = shopDetail;
    }

    public Ordered[] getOrder() {
        return order;
    }

    public void setOrder(Ordered[] order) {
        this.order = order;
    }

    public String getUserToken ()
    {
        return userToken;
    }

    public void setUserToken (String userToken)
    {
        this.userToken = userToken;
    }

    public String getTimeForPickcup ()
    {
        return timeForPickcup;
    }

    public void setTimeForPickcup (String timeForPickcup)
    {
        this.timeForPickcup = timeForPickcup;
    }

    public String getNote ()
    {
        return note;
    }

    public String getEligibleForRewards() {
        return eligibleForRewards;
    }

    public void setEligibleForRewards(String eligibleForRewards) {
        this.eligibleForRewards = eligibleForRewards;
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
        return "ClassPojo [parcel = "+parcel+", shopDetail = "+shopDetail+", order = "+order+", userToken = "+userToken+", timeForPickcup = "+timeForPickcup+", note = "+note+", totalPrice = "+totalPrice+"]";
    }
}
