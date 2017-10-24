package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 10/16/17.
 */


public class CardDetails
{
    private String expiryYear;

    private String cardId;

    private String isPrimary;

    private String _id;

    private String card_number;

    private String expiryMonth;

    private String brand;

    private String card_name;

    public String getExpiryYear ()
    {
        return expiryYear;
    }

    public void setExpiryYear (String expiryYear)
    {
        this.expiryYear = expiryYear;
    }

    public String getCardId ()
    {
        return cardId;
    }

    public void setCardId (String cardId)
    {
        this.cardId = cardId;
    }

    public String getIsPrimary ()
    {
        return isPrimary;
    }

    public void setIsPrimary (String isPrimary)
    {
        this.isPrimary = isPrimary;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getCard_number ()
    {
        return card_number;
    }

    public void setCard_number (String card_number)
    {
        this.card_number = card_number;
    }

    public String getExpiryMonth ()
    {
        return expiryMonth;
    }

    public void setExpiryMonth (String expiryMonth)
    {
        this.expiryMonth = expiryMonth;
    }

    public String getBrand ()
    {
        return brand;
    }

    public void setBrand (String brand)
    {
        this.brand = brand;
    }

    public  String getCard_name ()
{
    return card_name;
}

    public void setCard_name (String card_name)
    {
        this.card_name = card_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [expiryYear = "+expiryYear+", cardId = "+cardId+", isPrimary = "+isPrimary+", _id = "+_id+", card_number = "+card_number+", expiryMonth = "+expiryMonth+", brand = "+brand+", card_name = "+card_name+"]";
    }
}
