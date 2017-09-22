package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 9/22/17.
 */

public class AddToCartData {
    private String message;

    private String error;

    private Data data;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getError ()
    {
        return error;
    }

    public void setError (String error)
    {
        this.error = error;
    }

    public Data getData ()
    {
        return data;
    }

    public void setData (Data data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", error = "+error+", data = "+data+"]";
    }
}
