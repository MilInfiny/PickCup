package com.example.infiny.pickup.Helpers;

import java.util.ArrayList;

/**
 * Created by infiny on 9/12/17.
 */

public class Order_History_Helpers {
    String  Partyname;
    String type;
    String orderno;
    String date;
    int image;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Order_History_Helpers(String partyname, String type, int image, String orderno, String date) {
        Partyname = partyname;
        this.type = type;
        this.image = image;
        this.orderno=orderno;
        this.date=date;

    }

    ArrayList<Orders> orders;

    public String getPartyname() {
        return Partyname;
    }

    public void setPartyname(String partyname) {
        Partyname = partyname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Orders> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Orders> orders) {
        this.orders = orders;
    }
}
