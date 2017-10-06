package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 10/6/17.
 */

public class RewardData {
    private String title;

    private String error;

    private DataRewards[] data;

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

    public DataRewards[] getData() {
        return data;
    }

    public void setData(DataRewards[] data) {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [title = "+title+", error = "+error+", data = "+data+"]";
    }
}
