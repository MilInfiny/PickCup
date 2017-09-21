package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 9/21/17.
 */

public class MenuListData {
    private String title;

    private String error;

    private Cafes cafes;

    private Data[] data;

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

    public Cafes getCafes ()
    {
        return cafes;
    }

    public void setCafes (Cafes cafes)
    {
        this.cafes = cafes;
    }

    public Data[] getData ()
    {
        return data;
    }

    public void setData (Data[] data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [title = "+title+", error = "+error+", cafes = "+cafes+", data = "+data+"]";
    }
}
