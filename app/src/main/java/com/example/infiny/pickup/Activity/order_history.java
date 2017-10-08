package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.Adapters.Order_History_Adapter;
import com.example.infiny.pickup.Adapters.RewardAdapter;
import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Model.DataOrderHistory;
import com.example.infiny.pickup.Model.DataRewards;
import com.example.infiny.pickup.Model.Order_History_Data;
import com.example.infiny.pickup.Model.RewardData;
import com.example.infiny.pickup.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
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
    @BindView(R.id.view)
    View view;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    Context context;
    @BindView(R.id.progressBar_cyclic)
    ProgressBar progressBarCyclic;
    Retrofit retroFitClient;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    Order_History_Data Order_History_Data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appbar.setOutlineProvider(null);
        getSupportActionBar().setTitle("");
        context = this;
        sessionManager=new SessionManager(context);
        sharedPreferences=getSharedPreferences(sessionManager.PREF_NAME,0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        twReward.setText("Order history");
        icon.setBackground(getResources().getDrawable(R.drawable.ic_history_black_48dp));
        view.setVisibility(View.VISIBLE);
        progressBarCyclic.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        retroFitClient = new RetroFitClient(context).getBlankRetrofit();
        Call<Order_History_Data> call = retroFitClient
                .create(ApiIntegration.class)
                .getOrder_History_Listing(sharedPreferences.getString("token",null));
        call.enqueue(new Callback<Order_History_Data>() {

            @Override
            public void onResponse(Call<Order_History_Data> call, Response<Order_History_Data> response) {
                if (response != null) {
                    Order_History_Data = response.body();
                    if (Order_History_Data != null) {
                        if (Order_History_Data.getError().equals("true")) {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(context,Order_History_Data.getTitle(), Toast.LENGTH_SHORT).show();
                        } else {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            ArrayList<DataOrderHistory> orderHistoryList = new ArrayList<DataOrderHistory>(Arrays.asList(Order_History_Data.getData()));
                            order_history_adapter = new Order_History_Adapter(context, orderHistoryList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recycleView.setLayoutManager(mLayoutManager);
                            recycleView.setAdapter(order_history_adapter);
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
            public void onFailure(Call<Order_History_Data> call, Throwable t) {
                progressBarCyclic.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();

            }
        });



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackpresss();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackpresss();

        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackpresss() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
