package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 10/16/17.
 */

public class AddCardData {
    private String title;

    private String error;

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

    @Override
    public String toString()
    {
        return "ClassPojo [title = "+title+", error = "+error+"]";
    }
}
