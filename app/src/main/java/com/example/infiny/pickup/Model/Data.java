package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 9/21/17.
 */

public class Data {
    private ItemData[] itemData;

    private String itemCategory;

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
