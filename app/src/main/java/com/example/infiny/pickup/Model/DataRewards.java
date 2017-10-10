package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 10/6/17.
 */

public class DataRewards {
    private String cafe_name;

    private String rewardCompleted;

    private String startdate;

    private String cafe_id;

    private String rewardName;

    private String quantity;

    private String imageurl;

    private String enddate;

    private String cafeStatus;

    public String getCafe_name ()
    {
        return cafe_name;
    }

    public void setCafe_name (String cafe_name)
    {
        this.cafe_name = cafe_name;
    }

    public String getRewardCompleted ()
    {
        return rewardCompleted;
    }

    public void setRewardCompleted (String rewardCompleted)
    {
        this.rewardCompleted = rewardCompleted;
    }

    public String getStartdate ()
    {
        return startdate;
    }

    public void setStartdate (String startdate)
    {
        this.startdate = startdate;
    }

    public String getCafe_id ()
    {
        return cafe_id;
    }

    public void setCafe_id (String cafe_id)
    {
        this.cafe_id = cafe_id;
    }

    public String getRewardName ()
    {
        return rewardName;
    }

    public void setRewardName (String rewardName)
    {
        this.rewardName = rewardName;
    }

    public String getQuantity ()
    {
        return quantity;
    }

    public void setQuantity (String quantity)
    {
        this.quantity = quantity;
    }

    public String getImageurl ()
    {
        return imageurl;
    }

    public void setImageurl (String imageurl)
    {
        this.imageurl = imageurl;
    }

    public String getEnddate ()
    {
        return enddate;
    }

    public void setEnddate (String enddate)
    {
        this.enddate = enddate;
    }

    public String getCafeStatus ()
    {
        return cafeStatus;
    }

    public void setCafeStatus (String cafeStatus)
    {
        this.cafeStatus = cafeStatus;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cafe_name = "+cafe_name+", rewardCompleted = "+rewardCompleted+", startdate = "+startdate+", cafe_id = "+cafe_id+", rewardName = "+rewardName+", quantity = "+quantity+", imageurl = "+imageurl+", enddate = "+enddate+", cafeStatus = "+cafeStatus+"]";
    }
}
