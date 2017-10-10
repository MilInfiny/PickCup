package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 9/21/17.
 */

public class Data {
    private ItemData[] itemData;

    private String itemCategory;

    private String userDetail;

    private Ordered[] Ordered;

    private String shopDetail;


    private String _id;


    public String getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(String userDetail) {
        this.userDetail = userDetail;
    }

    public com.example.infiny.pickup.Model.Ordered[] getOrdered() {
        return Ordered;
    }

    public void setOrdered(com.example.infiny.pickup.Model.Ordered[] ordered) {
        Ordered = ordered;
    }

    public String getShopDetail() {
        return shopDetail;
    }

    public void setShopDetail(String shopDetail) {
        this.shopDetail = shopDetail;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }

    private String __v;

    public ItemData[] getItemData() {
        return itemData;
    }

    public void setItemData(ItemData[] itemData) {
        this.itemData = itemData;
    }

    public String getItemCategory ()
    {
        return itemCategory;
    }

    public void setItemCategory (String itemCategory)
    {
        this.itemCategory = itemCategory;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [itemData = "+itemData+", itemCategory = "+itemCategory+"]";
    }
}
