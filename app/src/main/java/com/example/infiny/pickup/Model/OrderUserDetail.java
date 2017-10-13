package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 10/13/17.
 */

public class OrderUserDetail {
    private String _id;

    private String[] cardDetails;

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String[] getCardDetails ()
    {
        return cardDetails;
    }

    public void setCardDetails (String[] cardDetails)
    {
        this.cardDetails = cardDetails;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [_id = "+_id+", cardDetails = "+cardDetails+"]";
    }
}
