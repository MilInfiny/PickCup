package com.example.infiny.pickup.Model;

import android.graphics.Bitmap;

/**
 * Created by infiny on 9/19/17.
 */

public class User
{
    private String imageUrl;

    private String isLoggedIn;

    private String __v;

    private String lastname;

    private String firstname;

    private String password;

    private CardDetails[] cardDetails;

    private String resetPasswordToken;

    private String _id;

    private Address address;

    private String email;

    private String[] deviceToken;

    private String resetPasswordExpires;

    private String dob;

    private String stripeId;

    public String getImageUrl ()
    {
        return imageUrl;
    }

    public void setImageUrl (String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getIsLoggedIn ()
    {
        return isLoggedIn;
    }

    public void setIsLoggedIn (String isLoggedIn)
    {
        this.isLoggedIn = isLoggedIn;
    }

    public String get__v ()
    {
        return __v;
    }

    public void set__v (String __v)
    {
        this.__v = __v;
    }

    public String getLastname ()
    {
        return lastname;
    }

    public void setLastname (String lastname)
    {
        this.lastname = lastname;
    }

    public String getFirstname ()
    {
        return firstname;
    }

    public void setFirstname (String firstname)
    {
        this.firstname = firstname;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public CardDetails[] getCardDetails ()
    {
        return cardDetails;
    }

    public void setCardDetails (CardDetails[] cardDetails)
    {
        this.cardDetails = cardDetails;
    }

    public String getResetPasswordToken ()
    {
        return resetPasswordToken;
    }

    public void setResetPasswordToken (String resetPasswordToken)
    {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public Address getAddress ()
    {
        return address;
    }

    public void setAddress (Address address)
    {
        this.address = address;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String[] getDeviceToken ()
    {
        return deviceToken;
    }

    public void setDeviceToken (String[] deviceToken)
    {
        this.deviceToken = deviceToken;
    }

    public String getResetPasswordExpires ()
    {
        return resetPasswordExpires;
    }

    public void setResetPasswordExpires (String resetPasswordExpires)
    {
        this.resetPasswordExpires = resetPasswordExpires;
    }

    public String getDob ()
    {
        return dob;
    }

    public void setDob (String dob)
    {
        this.dob = dob;
    }

    public String getStripeId ()
    {
        return stripeId;
    }

    public void setStripeId (String stripeId)
    {
        this.stripeId = stripeId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [imageUrl = "+imageUrl+", isLoggedIn = "+isLoggedIn+", __v = "+__v+", lastname = "+lastname+", firstname = "+firstname+", password = "+password+", cardDetails = "+cardDetails+", resetPasswordToken = "+resetPasswordToken+", _id = "+_id+", address = "+address+", email = "+email+", deviceToken = "+deviceToken+", resetPasswordExpires = "+resetPasswordExpires+", dob = "+dob+", stripeId = "+stripeId+"]";
    }
}
