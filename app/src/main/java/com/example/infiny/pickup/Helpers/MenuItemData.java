package com.example.infiny.pickup.Helpers;

/**
 * Created by infiny on 9/12/17.
 */

public class MenuItemData {
    String menuItem,price;

    public MenuItemData(String menuItem, String price) {
        this.menuItem = menuItem;
        this.price = price;
    }

    public String getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(String menuItem) {
        this.menuItem = menuItem;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
