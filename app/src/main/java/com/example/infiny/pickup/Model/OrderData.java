package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 9/22/17.
 */

public class OrderData {
    private User userDetail;

    private Ordered[] Ordered;

    private ShopDetail shopDetail;

    private String _id;

    private String __v;

    public User getUserDetail ()
    {
        return userDetail;
    }

    public void setUserDetail (User userDetail)
    {
        this.userDetail = userDetail;
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
        return "ClassPojo [userDetail = "+userDetail+", Ordered = "+Ordered+", shopDetail = "+shopDetail+", _id = "+_id+", __v = "+__v+"]";
    }
}
