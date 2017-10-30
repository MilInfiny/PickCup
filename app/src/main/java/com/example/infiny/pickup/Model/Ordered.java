package com.example.infiny.pickup.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by infiny on 9/22/17.
 */

public class Ordered implements Parcelable
{
    private String itemCat;
    private String eligibleForRewards;
    private String itemSize;
    private String _id;
    private  String  itemName;
    private String itemPrice;
    private String itemQuantity;
    private String itemId;
    private Float adminTax;


    protected Ordered(Parcel in) {
        itemCat = in.readString();
        eligibleForRewards = in.readString();
        itemSize = in.readString();
        _id = in.readString();
        itemName = in.readString();
        itemPrice = in.readString();
        itemQuantity = in.readString();
        itemId = in.readString();
        image = in.readInt();
    }

    public static final Creator<Ordered> CREATOR = new Creator<Ordered>() {
        @Override
        public Ordered createFromParcel(Parcel in) {
            return new Ordered(in);
        }

        @Override
        public Ordered[] newArray(int size) {
            return new Ordered[size];
        }
    };

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    private int image;

    public String getEligibleForRewards() {
        return eligibleForRewards;
    }

    public void setEligibleForRewards(String eligibleForRewards) {
        this.eligibleForRewards = eligibleForRewards;
    }

    public Float getAdminTax() {
        return adminTax;
    }

    public void setAdminTax(Float adminTax) {
        this.adminTax = adminTax;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemCat ()
    {
        return itemCat;
    }

    public void setItemCat (String itemCat)
    {
        this.itemCat = itemCat;
    }

    public String getItemSize ()
    {
        return itemSize;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemSize (String itemSize)
    {
        this.itemSize = itemSize;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getItemPrice ()
    {
        return itemPrice;
    }

    public void setItemPrice (String itemPrice)
    {
        this.itemPrice = itemPrice;
    }

    public String getItemId ()
    {
        return itemId;
    }

    public void setItemId (String itemId)
    {
        this.itemId = itemId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [itemCat = "+itemCat+", itemSize = "+itemSize+", _id = "+_id+", itemPrice = "+itemPrice+", itemId = "+itemId+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemCat);
        dest.writeString(eligibleForRewards);
        dest.writeString(itemSize);
        dest.writeString(_id);
        dest.writeString(itemName);
        dest.writeString(itemPrice);
        dest.writeString(itemQuantity);
        dest.writeString(itemId);
        dest.writeInt(image);
    }
}