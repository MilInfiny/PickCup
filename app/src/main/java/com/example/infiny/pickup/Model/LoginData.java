package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 9/18/17.
 */

public class LoginData {
    private String title;

    private String error;

    private String token;

    private User user;

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
        return "ClassPojo [title = "+title+", error = "+error+", token = "+token+", user = "+user+"]";
    }
}
