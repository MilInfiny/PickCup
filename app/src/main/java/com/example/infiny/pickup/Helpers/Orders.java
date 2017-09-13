package com.example.infiny.pickup.Helpers;

/**
 * Created by infiny on 9/12/17.
 */

public class Orders {
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    String ordername;
    String  price;
    String size;
    String quantity;

    public Orders(String ordername, String price,  String size,String quantity) {
        this.ordername = ordername;
        this.price = price;
        this.size=size;
        this.quantity=quantity;
    }



    public String getSize() {

        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getOrdername() {


        return ordername;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }


}
