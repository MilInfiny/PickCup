package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 9/22/17.
 */

public class OrderListData {
    private String canClaimedReward;

    private String title;

    private String error;

    private AdminTax adminTax;

    private OrderData data;

    private StripeCharge stripeCharge;

    public String getCanClaimedReward ()
    {
        return canClaimedReward;
    }

    public void setCanClaimedReward (String canClaimedReward)
    {
        this.canClaimedReward = canClaimedReward;
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

    public AdminTax getAdminTax() {
        return adminTax;
    }

    public void setAdminTax(AdminTax adminTax) {
        this.adminTax = adminTax;
    }

    public OrderData getData ()
    {
        return data;
    }

    public void setData (OrderData data)
    {
        this.data = data;
    }

    public StripeCharge getStripeCharge ()
    {
        return stripeCharge;
    }

    public void setStripeCharge (StripeCharge stripeCharge)
    {
        this.stripeCharge = stripeCharge;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [canClaimedReward = "+canClaimedReward+", title = "+title+", error = "+error+", adminTax = "+adminTax+", data = "+data+", stripeCharge = "+stripeCharge+"]";
    }
}
