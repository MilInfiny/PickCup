package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.infiny.pickup.Adapters.CustomPagerAdapter;
import com.example.infiny.pickup.R;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Card_Activity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_);
        ButterKnife.bind(this);
        CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.indicator);
        CustomPagerAdapter mAdapter= new CustomPagerAdapter(this);
        viewpager.setAdapter(mAdapter);
        indicator.setFillColor(getResources().getColor(R.color.colorPrimary));
        indicator.setPageColor(getResources().getColor(R.color.gray));
        indicator.setViewPager(viewpager);


    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
