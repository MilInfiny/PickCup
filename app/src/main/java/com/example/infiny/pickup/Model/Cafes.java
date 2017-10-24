package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 9/19/17.
 */

public class Cafes
{
    private Position position;

    private String cafe_name;

    private String _id;
    private String rewardCompleted;

    private String rewardQuan;
    private String rewardId;


    private String storePass;

    private String status;

    private String isLoggedIn;

    private String __v;

    private String rating;

    private String imageurl;

    private String storeId;

    public Position getPosition ()
    {
        return position;
    }

    public void setPosition (Position position)
    {
        this.position = position;
    }

    public String getCafe_name ()
    {
        return cafe_name;
    }

    public void setCafe_name (String cafe_name)
    {
        this.cafe_name = cafe_name;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getStorePass ()
    {
        return storePass;
    }

    public String getRewardCompleted() {
        return rewardCompleted;
    }

    public void setRewardCompleted(String rewardCompleted) {
        this.rewardCompleted = rewardCompleted;
    }

    public String getRewardQuan() {
        return rewardQuan;
    }

    public void setRewardQuan(String rewardQuan) {
        this.rewardQuan = rewardQuan;
    }

    public String getRewardId() {
        return rewardId;
    }

    public void setRewardId(String rewardId) {
        this.rewardId = rewardId;
    }

    public void setStorePass (String storePass)
    {
        this.storePass = storePass;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getIsLoggedIn ()
    {
        return isLoggedIn;
    }

    public void setIsLoggedIn (String isLoggedIn)
    {
        this.isLoggedIn = isLoggedIn;
    }

    public String get__v ()
    {
        return __v;
    }

    public void set__v (String __v)
    {
        this.__v = __v;
    }

    public String getRating ()
    {
        return rating;
    }

    public void setRating (String rating)
    {
        this.rating = rating;
    }

    public String getImageurl ()
    {
        return imageurl;
    }

    public void setImageurl (String imageurl)
    {
        this.imageurl = imageurl;
    }

    public String getStoreId ()
    {
        return storeId;
    }

    public void setStoreId (String storeId)
    {
        this.storeId = storeId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [position = "+position+", cafe_name = "+cafe_name+", _id = "+_id+", storePass = "+storePass+", status = "+status+", isLoggedIn = "+isLoggedIn+", __v = "+__v+", rating = "+rating+", imageurl = "+imageurl+", storeId = "+storeId+"]";
    }
}