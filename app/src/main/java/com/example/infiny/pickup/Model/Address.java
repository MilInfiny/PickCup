package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 9/19/17.
 */

public class Address
{
    private String postalCode;

    private String address;

    private String city;

    public String getPostalCode ()
    {
        return postalCode;
    }

    public void setPostalCode (String postalCode)
    {
        this.postalCode = postalCode;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getCity ()
    {
        return city;
    }

    public void setCity (String city)
    {
        this.city = city;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [postalCode = "+postalCode+", address = "+address+", city = "+city+"]";
    }
}


