package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 9/19/17.
 */

public class SignUpData {
    private String message;

    private String error;

    private String token;

    private User user;

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

    public String getToken ()
    {
        return token;
    }

    public void setToken (String token)
    {
        this.token = token;
    }

    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", error = "+error+", token = "+token+", user = "+user+"]";
    }
}
