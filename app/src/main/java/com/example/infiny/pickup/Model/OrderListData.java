package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 9/22/17.
 */

public class OrderListData {
    private String canClaimedReward;

    private String title;

    private String error;

    private OrderData data;

    public String getCanClaimedReward ()
    {
        return canClaimedReward;
    }

    public void setCanClaimedReward (String canClaimedReward)
    {
        this.canClaimedReward = canClaimedReward;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getError ()
    {
        return error;
    }

    public void setError (String error)
    {
        this.error = error;
    }

    public OrderData getData ()
    {
        return data;
    }

    public void setData (OrderData data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [canClaimedReward = "+canClaimedReward+", title = "+title+", error = "+error+", data = "+data+"]";
    }
}
