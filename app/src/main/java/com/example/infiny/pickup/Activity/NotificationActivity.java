package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.infiny.pickup.Adapters.NotificationAdapter;
import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Model.CafeListingData;
import com.example.infiny.pickup.Model.DataNotification;
import com.example.infiny.pickup.Model.NotificationData;
import com.example.infiny.pickup.R;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NotificationActivity extends AppCompatActivity {


    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    NotificationAdapter notificationAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.progressBar_cyclic)
    ProgressBar progressBarCyclic;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    Context context;
    NotificationData notificationData;
    ArrayList<DataNotification> dataNotifications;
    private boolean loading = true;
    Retrofit retroFitClient;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int current_page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appbar.setOutlineProvider(null);
        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context=this;
        sessionManager=new SessionManager(context);
        sharedPreferences=getSharedPreferences(sessionManager.PREF_NAME,0);
        dataNotifications=new ArrayList<DataNotification>();
        notificationAdapter = new NotificationAdapter(context, dataNotifications);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setAdapter(notificationAdapter);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                try {
                    if (dy > 0) //check for scroll down
                    {
                        visibleItemCount = recyclerView.getChildCount();
                        //                    totalItemCount = recyclerView.getLayoutManager().getItemCount();
                        totalItemCount = recyclerView.getAdapter().getItemCount();
                        pastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                        Log.d("value", "visibleItemCount" + visibleItemCount + "\ntotalItemCount" + totalItemCount + "\npastVisiblesItems" + pastVisiblesItems);
                        Log.d("value", "\ncurrent_page" + current_page);

                        if (totalItemCount == (current_page * 20)) {
                            if (loading) {
                                if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                    //                                loading = false;
                                    Log.v("...", "Last Item Wow !");
                                    ++current_page;
                                    getData(current_page);
                                    //Do pagination.. i.e. fetch new data
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        getData(current_page);



    }
    public void getData(int currentPage)
    {
        progressBarCyclic.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        retroFitClient = new RetroFitClient(context).getBlankRetrofit();
        Call<NotificationData> call = retroFitClient
                .create(ApiIntegration.class)
                .getNotificationLIsting(sharedPreferences.getString("token",null),
                                         String.valueOf(currentPage),
                                         String.valueOf(currentPage-1));
        call.enqueue(new Callback<NotificationData>() {

            @Override
            public void onResponse(Call<NotificationData> call, Response<NotificationData> response) {
                if (response != null) {
                    notificationData = response.body();
                    if (notificationData != null) {
                        if (notificationData.getError().equals("true")) {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(context,notificationData.getTitle(), Toast.LENGTH_SHORT).show();
                        } else {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            dataNotifications.addAll(Arrays.asList(notificationData.getData()));
                            notificationAdapter.notifyDataSetChanged();

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
            public void onFailure(Call<NotificationData> call, Throwable t) {
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
        finish();
    }

}


