package com.example.infiny.pickup.Helpers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by infiny on 9/5/17.
 */

public  class Drinks implements Parcelable {
    String small;
    String medium;
    String large;
    String smallPrice;
    String mediumPrice;
    String largePrice;


    public String getSmallPrice() {
        return smallPrice;
    }

    public void setSmallPrice(String smallPrice) {
        this.smallPrice = smallPrice;
    }

    public String getMediumPrice() {
        return mediumPrice;
    }

    public void setMediumPrice(String mediumPrice) {
        this.mediumPrice = mediumPrice;
    }

    public String getLargePrice() {
        return largePrice;
    }

    public void setLargePrice(String largePrice) {
        this.largePrice = largePrice;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public static Creator<Drinks> getCREATOR() {
        return CREATOR;
    }

    public Drinks(String small, String medium, String large) {

        this.small = small;
        this.medium = medium;
        this.large = large;
    }

    public Drinks(String small, String medium, String large, String smallPrice, String mediumPrice, String largePrice) {
        this.small = small;
        this.medium = medium;
        this.large = large;
        this.smallPrice = smallPrice;
        this.mediumPrice = mediumPrice;
        this.largePrice = largePrice;
    }

    protected Drinks(Parcel in) {
        small = in.readString();
        medium = in.readString();
        large = in.readString();
        smallPrice = in.readString();
        mediumPrice = in.readString();
        largePrice = in.readString();
    }

    public static final Creator<Drinks> CREATOR = new Creator<Drinks>() {
        @Override
        public Drinks createFromParcel(Parcel in) {
            return new Drinks(in);
        }

        @Override
        public Drinks[] newArray(int size) {
            return new Drinks[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(small);
        dest.writeString(medium);
        dest.writeString(large);
        dest.writeString(smallPrice);
        dest.writeString(mediumPrice);
        dest.writeString(largePrice);
    }
}
