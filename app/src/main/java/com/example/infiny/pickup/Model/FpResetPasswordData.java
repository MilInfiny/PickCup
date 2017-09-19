package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 9/19/17.
 */

public class FpResetPasswordData {
    private String detail;

    private String title;

    private String error;

    public String getDetail ()
    {
        return detail;
    }

    public void setDetail (String detail)
    {
        this.detail = detail;
    }

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
        return "ClassPojo [detail = "+detail+", title = "+title+", error = "+error+"]";
    }
}
