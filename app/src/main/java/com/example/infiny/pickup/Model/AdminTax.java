package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 10/27/17.
 */

public class AdminTax
{
    private String below_10_pound;

    private String below_5_pound;

    public String getBelow_10_pound ()
    {
        return below_10_pound;
    }

    public void setBelow_10_pound (String below_10_pound)
    {
        this.below_10_pound = below_10_pound;
    }

    public String getBelow_5_pound ()
    {
        return below_5_pound;
    }

    public void setBelow_5_pound (String below_5_pound)
    {
        this.below_5_pound = below_5_pound;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [below_10_pound = "+below_10_pound+", below_5_pound = "+below_5_pound+"]";
    }
}

