package com.example.infiny.pickup.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by infiny on 9/22/17.
 */

public class ShopDetail  implements Parcelable{
    private String cafe_name;

    private String _id;

    private String status;

    private String imageurl;

    protected ShopDetail(Parcel in) {
        cafe_name = in.readString();
        _id = in.readString();
        status = in.readString();
        imageurl = in.readString();
    }

    public static final Creator<ShopDetail> CREATOR = new Creator<ShopDetail>() {
        @Override
        public ShopDetail createFromParcel(Parcel in) {
            return new ShopDetail(in);
        }

        @Override
        public ShopDetail[] newArray(int size) {
            return new ShopDetail[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cafe_name);
        dest.writeString(_id);
        dest.writeString(status);
        dest.writeString(imageurl);
    }
}
