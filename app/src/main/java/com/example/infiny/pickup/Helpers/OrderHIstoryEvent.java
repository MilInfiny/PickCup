package com.example.infiny.pickup.Helpers;

import com.example.infiny.pickup.Model.DataOrderHistory;

/**
 * Created by infiny on 10/9/17.
 */

public class OrderHIstoryEvent {
    DataOrderHistory dataOrderHistory;
    public OrderHIstoryEvent(DataOrderHistory dataOrderHistory)
    {
        this.dataOrderHistory=dataOrderHistory;
    }


    public DataOrderHistory getDataOrderHistory() {
        return dataOrderHistory;
    }

    public void setDataOrderHistory(DataOrderHistory dataOrderHistory) {
        this.dataOrderHistory = dataOrderHistory;
    }
}
