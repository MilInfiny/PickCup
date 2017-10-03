package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 9/28/17.
 */


public class Obj
{
    private String n;

    private String ok;

    public String getN ()
    {
        return n;
    }

    public void setN (String n)
    {
        this.n = n;
    }

    public String getOk ()
    {
        return ok;
    }

    public void setOk (String ok)
    {
        this.ok = ok;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [n = "+n+", ok = "+ok+"]";
    }
}