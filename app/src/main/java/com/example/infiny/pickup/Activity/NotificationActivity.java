package com.example.infiny.pickup.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.infiny.pickup.Adapters.NotificationAdapter;
import com.example.infiny.pickup.Helpers.CafeLIstingHelpers;
import com.example.infiny.pickup.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity {


    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    NotificationAdapter notificationAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Notifications");


        ArrayList<CafeLIstingHelpers> cafeLIstingHelperseslist = new ArrayList<>();
        CafeLIstingHelpers cafeLIstingHelpers = new CafeLIstingHelpers();
        CafeLIstingHelpers cafeLIstingHelpers1 = new CafeLIstingHelpers();
        CafeLIstingHelpers cafeLIstingHelpers2 = new CafeLIstingHelpers();
        CafeLIstingHelpers cafeLIstingHelpers3 = new CafeLIstingHelpers();
        CafeLIstingHelpers cafeLIstingHelpers4 = new CafeLIstingHelpers();
        cafeLIstingHelpers.setStatus("You are order is ready for pickup");
        cafeLIstingHelpers.setPartyname("urban cafe");
        cafeLIstingHelpers.setImage(R.drawable.cofee3);
        cafeLIstingHelpers.setRating("2");
        cafeLIstingHelperseslist.add(cafeLIstingHelpers);
        cafeLIstingHelpers1.setStatus("You are order is ready for pickup");
        cafeLIstingHelpers1.setPartyname("Yorks");
        cafeLIstingHelpers1.setRating("4");

        cafeLIstingHelpers1.setImage(R.drawable.cofee2);
        cafeLIstingHelperseslist.add(cafeLIstingHelpers1);
        cafeLIstingHelpers2.setStatus("You are order is ready for pickup");
        cafeLIstingHelpers2.setRating("1");
        cafeLIstingHelpers2.setPartyname("7/8 kafe");
        cafeLIstingHelpers2.setImage(R.drawable.cofee3);
        cafeLIstingHelperseslist.add(cafeLIstingHelpers2);
        cafeLIstingHelpers3.setRating("2");
        cafeLIstingHelpers3.setStatus("You are order is ready for pickup");
        cafeLIstingHelpers3.setPartyname("snack adda");
        cafeLIstingHelpers3.setImage(R.drawable.cofee4);
        cafeLIstingHelperseslist.add(cafeLIstingHelpers3);
        cafeLIstingHelpers4.setStatus("You are order is ready for pickup");
        cafeLIstingHelpers4.setRating("3");
        cafeLIstingHelpers4.setPartyname("urban adda");
        cafeLIstingHelpers4.setImage(R.drawable.cofee5);
        cafeLIstingHelperseslist.add(cafeLIstingHelpers4);
        notificationAdapter = new NotificationAdapter(this, cafeLIstingHelperseslist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setAdapter(notificationAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
