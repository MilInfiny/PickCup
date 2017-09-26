package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 9/22/17.
 */

public class ShopDetail {
    private String cafe_name;

    private String _id;

    private String status;

    private String imageurl;

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

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getImageurl ()
    {
        return imageurl;
    }

    public void setImageurl (String imageurl)
    {
        this.imageurl = imageurl;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cafe_name = "+cafe_name+", _id = "+_id+", status = "+status+", imageurl = "+imageurl+"]";
    }
}
