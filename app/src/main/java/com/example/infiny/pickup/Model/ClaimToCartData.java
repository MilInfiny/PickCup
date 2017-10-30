package com.example.infiny.pickup.Model;

/**
 * Created by infiny on 10/24/17.
 */

public class ClaimToCartData {
    String title;
    String error;
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
