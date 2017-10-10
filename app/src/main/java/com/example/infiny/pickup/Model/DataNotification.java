package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 10/10/17.
 */

public class DataNotification {
    private String message;

    private String userDetail;

    private String updatedAt;

    private ShopDetail shopDetail;

    private String _id;

    private String createdAt;

    private String __v;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getUserDetail ()
    {
        return userDetail;
    }

    public void setUserDetail (String userDetail)
    {
        this.userDetail = userDetail;
    }

    public String getUpdatedAt ()
    {
        return updatedAt;
    }

    public void setUpdatedAt (String updatedAt)
    {
        this.updatedAt = updatedAt;
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

    public String get__v ()
    {
        return __v;
    }

    public void set__v (String __v)
    {
        this.__v = __v;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", userDetail = "+userDetail+", updatedAt = "+updatedAt+", shopDetail = "+shopDetail+", _id = "+_id+", createdAt = "+createdAt+", __v = "+__v+"]";
    }
}
