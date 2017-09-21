package com.example.infiny.pickup.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by infiny on 9/21/17.
 */

public class ItemData implements Parcelable {
    private String itemName;

    private String _id;

    private String itemMediumPrice;

    private String itemLargePrice;

    private String itemPrice;

    private String itemSmallPrice;

    protected ItemData(Parcel in) {
        itemName = in.readString();
        _id = in.readString();
        itemMediumPrice = in.readString();
        itemLargePrice = in.readString();
        itemPrice = in.readString();
        itemSmallPrice = in.readString();
    }

    public static final Creator<ItemData> CREATOR = new Creator<ItemData>() {
        @Override
        public ItemData createFromParcel(Parcel in) {
            return new ItemData(in);
        }

        @Override
        public ItemData[] newArray(int size) {
            return new ItemData[size];
        }
    };

    public String getItemName ()
    {
        return itemName;
    }

    public void setItemName (String itemName)
    {
        this.itemName = itemName;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getItemMediumPrice ()
    {
        return itemMediumPrice;
    }

    public void setItemMediumPrice (String itemMediumPrice)
    {
        this.itemMediumPrice = itemMediumPrice;
    }

    public String getItemLargePrice ()
    {
        return itemLargePrice;
    }

    public void setItemLargePrice (String itemLargePrice)
    {
        this.itemLargePrice = itemLargePrice;
    }

    public String getItemPrice ()
    {
        return itemPrice;
    }

    public void setItemPrice (String itemPrice)
    {
        this.itemPrice = itemPrice;
    }

    public String getItemSmallPrice ()
    {
        return itemSmallPrice;
    }

    public void setItemSmallPrice (String itemSmallPrice)
    {
        this.itemSmallPrice = itemSmallPrice;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [itemName = "+itemName+", _id = "+_id+", itemMediumPrice = "+itemMediumPrice+", itemLargePrice = "+itemLargePrice+", itemPrice = "+itemPrice+", itemSmallPrice = "+itemSmallPrice+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(_id);
        dest.writeString(itemMediumPrice);
        dest.writeString(itemLargePrice);
        dest.writeString(itemPrice);
        dest.writeString(itemSmallPrice);
    }
}
