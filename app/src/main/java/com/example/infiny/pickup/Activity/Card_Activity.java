package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.example.infiny.pickup.Adapters.CustomPagerAdapter;
import com.example.infiny.pickup.R;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Card_Activity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appbar.setOutlineProvider(null);
        context=this;
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        CustomPagerAdapter mAdapter = new CustomPagerAdapter(this);
        viewpager.setAdapter(mAdapter);
        indicator.setFillColor(getResources().getColor(R.color.colorPrimary));
        indicator.setPageColor(getResources().getColor(R.color.gray));
        indicator.setViewPager(viewpager);


    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackpresss();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackpresss();

        }
        return super.onOptionsItemSelected(item);
    }
    public void onBackpresss()
    {
        Intent intent=new Intent(context,MainActivity.class);
        startActivity(intent);

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
