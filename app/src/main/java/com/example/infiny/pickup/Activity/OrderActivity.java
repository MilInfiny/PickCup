package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.infiny.pickup.Adapters.OrderAdapter;
import com.example.infiny.pickup.Adapters.TimeviewAdapter;
import com.example.infiny.pickup.Helpers.MenuItem;
import com.example.infiny.pickup.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OrderActivity extends AppCompatActivity {


    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.tittleimage)
    ImageView tittleimage;
    @BindView(R.id.tittle)
    TextView tittle;
    @BindView(R.id.status_button)
    Button statusButton;
    @BindView(R.id.rating1)
    ImageView rating1;
    @BindView(R.id.rating2)
    ImageView rating2;
    @BindView(R.id.rating3)
    ImageView rating3;
    @BindView(R.id.toplayout)
    RelativeLayout toplayout;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.orderrecycleview)
    RecyclerView orderrecycleview;


    OrderAdapter orderAdapter;
    TimeviewAdapter timeviewAdapter;
    @BindView(R.id.timerecycleview)
    RecyclerView timerecycleview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);

        orderAdapter = new OrderAdapter(MenuItem.makeorder());
        orderrecycleview.setLayoutManager(layoutManager);
        orderrecycleview.setAdapter(orderAdapter);
        timeviewAdapter=new TimeviewAdapter(MenuItem.makeorder(),this);
        timerecycleview.setLayoutManager(layoutManager1);
        timerecycleview.setAdapter(timeviewAdapter);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
