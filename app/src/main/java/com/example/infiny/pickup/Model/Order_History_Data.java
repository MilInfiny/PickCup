package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 10/6/17.
 */

public class Order_History_Data {
    private String title;

    private String error;

    private DataOrderHistory[] data;

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getError ()
    {
        return error;
    }

    public void setError (String error)
    {
        this.error = error;
    }

    public DataOrderHistory[] getData() {
        return data;
    }

    public void setData(DataOrderHistory[] data) {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [title = "+title+", error = "+error+", data = "+data+"]";
    }
}
