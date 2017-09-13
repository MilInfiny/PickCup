package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infiny.pickup.Adapters.Order_History_Adapter;
import com.example.infiny.pickup.Helpers.Order_History_Helpers;
import com.example.infiny.pickup.Helpers.Orders;
import com.example.infiny.pickup.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class order_history extends AppCompatActivity {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.tw_reward)
    TextView twReward;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.icon)
    ImageView icon;
    Order_History_Adapter order_history_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        ButterKnife.bind(this);
        twReward.setText("Order history");
        icon.setBackground(getResources().getDrawable(R.drawable.order));
        Orders orders=new Orders("Espresso","£ 2.20","small","1");
        Orders orders1=new Orders("Latte","£ 15","medium","3");
        Orders orders2=new Orders("Brownie","£ 12","large","4");
        ArrayList<Orders>  orderses=new ArrayList<>();
        orderses.add(orders);
        orderses.add(orders1);
        orderses.add(orders2);
        Order_History_Helpers order_history_helpers =new Order_History_Helpers("urban cafe","sit in",orderses,"123","21-2-2017");
        ArrayList<Order_History_Helpers> order_history_helperses=new ArrayList<>();
        order_history_helperses.add(order_history_helpers);
        order_history_adapter=new Order_History_Adapter(this,order_history_helperses);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setAdapter(order_history_adapter);



    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
