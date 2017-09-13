package com.example.infiny.pickup.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.infiny.pickup.Helpers.ModelObject;
import com.example.infiny.pickup.R;

/**
 * Created by infiny on 9/13/17.
 */

public class CustomPagerAdapter  extends PagerAdapter {
    Context mContext;
    public CustomPagerAdapter(Context context) {
        this.mContext = context;
    }
    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        ModelObject modelObject = ModelObject.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(modelObject.getLayoutResId(), collection, false);
        TextView card1=(TextView)layout.findViewById(R.id.card_1);
        TextView card2=(TextView)layout.findViewById(R.id.card_2);
        TextView card3=(TextView)layout.findViewById(R.id.card_3);
        TextView card4=(TextView)layout.findViewById(R.id.card_4);
        TextView holdername=(TextView)layout.findViewById(R.id.cardholdername);
        TextView expires=(TextView)layout.findViewById(R.id.expires);


        Typeface typeFace=Typeface.createFromAsset(mContext.getAssets(),"fonts/CREDC___.ttf");
        card1.setTypeface(typeFace);
        card2.setTypeface(typeFace);
        card3.setTypeface(typeFace);
        card4.setTypeface(typeFace);
        holdername.setTypeface(typeFace);
        expires.setTypeface(typeFace);

        collection.addView(layout);
        return layout;
    }
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return ModelObject.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        ModelObject customPagerEnum = ModelObject.values()[position];
        return mContext.getString(customPagerEnum.getTitleResId());
    }

}
