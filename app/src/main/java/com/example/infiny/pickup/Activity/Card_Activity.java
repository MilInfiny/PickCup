package com.example.infiny.pickup.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.infiny.pickup.Adapters.CustomPagerAdapter;
import com.example.infiny.pickup.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Card_Activity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_);
        ButterKnife.bind(this);
        viewpager.setAdapter(new CustomPagerAdapter(this));

    }

}
