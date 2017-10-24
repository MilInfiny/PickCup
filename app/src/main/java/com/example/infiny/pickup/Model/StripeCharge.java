package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 10/16/17.
 */

public class StripeCharge {
    private String additional;

    private String percentCharge;

    public String getAdditional ()
    {
        return additional;
    }

    public void setAdditional (String additional)
    {
        this.additional = additional;
    }

    public String getPercentCharge ()
    {
        return percentCharge;
    }

    public void setPercentCharge (String percentCharge)
    {
        this.percentCharge = percentCharge;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [additional = "+additional+", percentCharge = "+percentCharge+"]";
    }
}
