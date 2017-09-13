package com.example.infiny.pickup.Helpers;

import com.example.infiny.pickup.R;

/**
 * Created by infiny on 9/13/17.
 */



public enum ModelObject {

    MASTERCARD(R.string.master_card, R.layout.view_master_card),
    VISACARD(R.string.visa_card, R.layout.view_visa_card);



    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}