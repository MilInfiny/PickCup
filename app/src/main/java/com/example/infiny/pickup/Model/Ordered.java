package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 9/22/17.
 */

public class Ordered
{
    private String itemCat;

    private String itemSize;

    private String _id;

    private String itemPrice;

    private String itemId;

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
}