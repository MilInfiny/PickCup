package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.Adapters.CustomPagerAdapter;
import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Interfaces.OnItemClickListener;
import com.example.infiny.pickup.Model.Cafes;
import com.example.infiny.pickup.Model.CardDetails;
import com.example.infiny.pickup.Model.CardListingData;
import com.example.infiny.pickup.Model.ItemData;
import com.example.infiny.pickup.Model.Ordered;
import com.example.infiny.pickup.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Card_Activity extends AppCompatActivity implements OnItemClickListener {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    Context context;
    @BindView(R.id.progressBar_cyclic)
    ProgressBar progressBarCyclic;
    Retrofit retroFitClient;
    CardListingData cardListingData;
    CustomPagerAdapter mAdapter;
    Boolean status;
    ArrayList<CardDetails> cardDetailses;
    @BindView(R.id.nocards)
    TextView nocards;
    OnItemClickListener onItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appbar.setOutlineProvider(null);
        context = this;
        onItemClickListener=this;
        sessionManager = new SessionManager(context);
        sharedPreferences = getSharedPreferences(sessionManager.PREF_NAME, 0);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cardDetailses = new ArrayList<>();
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mAdapter = new CustomPagerAdapter(this, cardDetailses,onItemClickListener);
        viewpager.setAdapter(mAdapter);
        indicator.setFillColor(getResources().getColor(R.color.colorPrimary));
        indicator.setPageColor(getResources().getColor(R.color.gray));
        indicator.setViewPager(viewpager);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Add_Card_Activity.class);
                startActivity(intent);
            }
        });

        progressBarCyclic.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        retroFitClient = new RetroFitClient(context).getBlankRetrofit();
        Call<CardListingData> call = retroFitClient
                .create(ApiIntegration.class)
                .getCardListing(sharedPreferences.getString("token", null));
        call.enqueue(new Callback<CardListingData>() {

            @Override
            public void onResponse(Call<CardListingData> call, Response<CardListingData> response) {
                if (response != null) {
                    cardListingData = response.body();
                    if (cardListingData != null) {
                        if (cardListingData.getError().equals("true")) {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(context, cardListingData.getTitle(), Toast.LENGTH_SHORT).show();
                        } else {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            cardDetailses.addAll(Arrays.asList(cardListingData.getUser().getCardDetails()));
                            if (cardDetailses.size() == 0) {
                                nocards.setVisibility(View.VISIBLE);

                            } else {
                                mAdapter.notifyDataSetChanged();
                            }


                        }

                    } else {
                        if (response.code() == 404 || response.code() == 500) {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<CardListingData> call, Throwable t) {
                progressBarCyclic.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
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

    @Override
    public void OnItemClickListener(Cafes item) {

    }

    @Override
    public void voidOnAddCart(ItemData itemData) {

    }

    @Override
    public void totalPrice(String total) {

    }

    @Override
    public void ordereddata(Ordered[] ordered) {

    }

    @Override
    public void ordered(Ordered ordered) {

    }

    @Override
    public void cardSet(ArrayList<CardDetails> cardDetailses) {
        this.cardDetailses=cardDetailses;

    }
}
