package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
    Retrofit retroFitClient;
    NotificationData notificationData;
    ArrayList<DataNotification> dataNotifications;

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

        progressBarCyclic.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        retroFitClient = new RetroFitClient(context).getBlankRetrofit();
        Call<NotificationData> call = retroFitClient
                .create(ApiIntegration.class)
                .getNotificationLIsting(sharedPreferences.getString("token",null));
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
                            dataNotifications=new ArrayList<DataNotification>(Arrays.asList(notificationData.getData()));
                            notificationAdapter = new NotificationAdapter(context, dataNotifications);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recycleView.setLayoutManager(mLayoutManager);
                            recycleView.setAdapter(notificationAdapter);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();  // optional depending on your needs
        }
        return super.onOptionsItemSelected(item);
    }

}


