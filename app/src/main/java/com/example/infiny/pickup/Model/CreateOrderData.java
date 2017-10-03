package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 9/28/17.
 */

public class CreateOrderData {
    private String message;

    private String error;

    private Obj obj;

    private String otp;

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

    public Obj getObj ()
    {
        return obj;
    }

    public void setObj (Obj obj)
    {
        this.obj = obj;
    }

    public String getOtp ()
    {
        return otp;
    }

    public void setOtp (String otp)
    {
        this.otp = otp;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", error = "+error+", obj = "+obj+", otp = "+otp+"]";
    }
}
