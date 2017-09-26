package com.example.infiny.pickup.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.Adapters.OrderAdapter;
import com.example.infiny.pickup.Adapters.TimeviewAdapter;
import com.example.infiny.pickup.Helpers.MenuItem;
import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Model.Cafes;
import com.example.infiny.pickup.Model.LoginData;
import com.example.infiny.pickup.Model.OrderData;
import com.example.infiny.pickup.Model.OrderListData;
import com.example.infiny.pickup.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.card_view1)
    CardView cardView1;
    @BindView(R.id.rel_time)
    RelativeLayout relTime;
    @BindView(R.id.tw_order)
    TextView twOrder;
    @BindView(R.id.list_item_genre_arrow)
    ImageView listItemGenreArrow;
    @BindView(R.id.paylayout)
    RelativeLayout paylayout;
    @BindView(R.id.card_pay)
    CardView cardPay;
    @BindView(R.id.progressBar_cyclic)
    ProgressBar progressBarCyclic;
  Context context;
    AlertDialog.Builder builder;
    OrderListData orderListData;
    Retrofit retroFitClient;
    SessionManager sessionManager;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appbar.setOutlineProvider(null);
        context=this;
         sessionManager=new SessionManager(this);
        sharedPreferences=getSharedPreferences(sessionManager.PREF_NAME,0);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        progressBarCyclic.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        retroFitClient = new RetroFitClient(context).getBlankRetrofit();
        Call<OrderListData> call = retroFitClient
                .create(ApiIntegration.class)
                .getOrderListing(sharedPreferences.getString("token",null));
        call.enqueue(new Callback<OrderListData>() {

            @Override
            public void onResponse(Call<OrderListData> call, Response<OrderListData> response) {
                if (response != null) {
                    orderListData = response.body();
                    if (orderListData != null) {
                        if (orderListData.getError().equals("true")) {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        } else {
                            Picasso.with(context)
                                    .load(orderListData.getData().getShopDetail().getImageurl())
                                    .placeholder(R.drawable.cofeecup)
                                    .into(tittleimage);
                            tittle.setText(orderListData.getData().getShopDetail().getCafe_name());
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            ArrayList<OrderData> orderDatas = new ArrayList<>(Arrays.asList(orderListData.getData()));
                            orderAdapter = new OrderAdapter(context,orderDatas);
                            orderrecycleview.setLayoutManager(layoutManager);
                            orderrecycleview.setAdapter(orderAdapter);
                            orderrecycleview.setNestedScrollingEnabled(false);

                        }

                    }else {
                        if (response.code() == 404 || response.code() == 500) {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<OrderListData> call, Throwable t) {

            }
        });


        timeviewAdapter = new TimeviewAdapter(MenuItem.makeorder(), this);
        timerecycleview.setLayoutManager(layoutManager1);
        timerecycleview.setAdapter(timeviewAdapter);
        timerecycleview.setNestedScrollingEnabled(false);

    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();  // optional depending on your needs
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
