package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 10/4/17.
 */

public class EditProfileData {
    private String title;

    private String error;

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
        return "ClassPojo [title = "+title+", error = "+error+", user = "+user+"]";
    }
}
