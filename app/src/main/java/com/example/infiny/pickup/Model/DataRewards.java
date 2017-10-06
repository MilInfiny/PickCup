package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 10/6/17.
 */

public class DataRewards {
    private ShopDetail shopDetail;

    private String startdate;

    private String _id;

    private String rewardName;

    private String __v;

    private String quantity;

    private String enddate;

    public ShopDetail getShopDetail ()
    {
        return shopDetail;
    }

    public void setShopDetail (ShopDetail shopDetail)
    {
        this.shopDetail = shopDetail;
    }

    public String getStartdate ()
    {
        return startdate;
    }

    public void setStartdate (String startdate)
    {
        this.startdate = startdate;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getRewardName ()
    {
        return rewardName;
    }

    public void setRewardName (String rewardName)
    {
        this.rewardName = rewardName;
    }

    public String get__v ()
    {
        return __v;
    }

    public void set__v (String __v)
    {
        this.__v = __v;
    }

    public String getQuantity ()
    {
        return quantity;
    }

    public void setQuantity (String quantity)
    {
        this.quantity = quantity;
    }

    public String getEnddate ()
    {
        return enddate;
    }

    public void setEnddate (String enddate)
    {
        this.enddate = enddate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [shopDetail = "+shopDetail+", startdate = "+startdate+", _id = "+_id+", rewardName = "+rewardName+", __v = "+__v+", quantity = "+quantity+", enddate = "+enddate+"]";
    }
}
