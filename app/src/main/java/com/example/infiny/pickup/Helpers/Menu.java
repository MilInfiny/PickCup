package com.example.infiny.pickup.Helpers;

import android.os.Parcel;

import com.example.infiny.pickup.Model.ItemData;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by infiny on 9/5/17.
 */
public class Menu extends ExpandableGroup<Drinks> {
    List<Drinks> drinkses;
    String title;

    public String getTitlePrice() {
        return titlePrice;
    }

    public void setTitlePrice(String titlePrice) {
        this.titlePrice = titlePrice;
    }

    String titlePrice;
    public List<Drinks> getDrinkses() {
        return drinkses;
    }

    public void setDrinkses(List<Drinks> drinkses) {
        this.drinkses = drinkses;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Menu(String jazz, List<Drinks> drinkses) {
        super(jazz,drinkses);
        this.title=jazz;
        this.drinkses=drinkses;
    }
}