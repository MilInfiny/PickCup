package com.example.infiny.pickup.Interfaces;

import com.example.infiny.pickup.Helpers.CafeLIstingHelpers;
import com.example.infiny.pickup.Model.Cafes;
import com.example.infiny.pickup.Model.ItemData;
import com.example.infiny.pickup.Model.Ordered;

import java.util.ArrayList;

/**
 * Created by infiny on 9/5/17.
 */

public interface OnItemClickListener {
    void OnItemClickListener(Cafes item);
    void voidOnAddCart(ItemData itemData);
    void totalPrice(String total);
    void ordereddata(Ordered[] ordered);

}
