package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.Adapters.CafeListAdapter;
import com.example.infiny.pickup.Helpers.CafeLIstingHelpers;
import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Interfaces.OnItemClickListener;
import com.example.infiny.pickup.Model.CafeListingData;
import com.example.infiny.pickup.Model.Cafes;
import com.example.infiny.pickup.Model.ItemData;
import com.example.infiny.pickup.Model.LoginData;
import com.example.infiny.pickup.Model.Ordered;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    CafeListAdapter CafeListAdapter;
    CafeLIstingHelpers cafeLIstingHelpers;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    ImageView imageView;
    @BindView(R.id.progressBar_cyclic)
    ProgressBar progressBarCyclic;

    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    CafeListingData cafeListingData;
    Context context;
    Retrofit retroFitClient;
TextView username,email;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appbar.setOutlineProvider(null);
        getSupportActionBar().setTitle("");
         context=this;
        sessionManager=new SessionManager(context);
        sharedPreferences=getSharedPreferences(sessionManager.PREF_NAME,0);
        navView.setNavigationItemSelectedListener(this);
        imageView = (ImageView) navView.getHeaderView(0).findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        progressBarCyclic.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        retroFitClient = new RetroFitClient(context).getBlankRetrofit();

        Call<CafeListingData> call = retroFitClient
                .create(ApiIntegration.class)
                .getcafelisting(sharedPreferences.getString("token",null),
                        "134.05839","73.00754");
        call.enqueue(new Callback<CafeListingData>() {

            @Override
            public void onResponse(Call<CafeListingData> call, Response<CafeListingData> response) {
                if (response != null) {
                    cafeListingData = response.body();
                    if (cafeListingData != null) {
                        if (cafeListingData.getError().equals("true")) {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(context,cafeListingData.getTitle(), Toast.LENGTH_SHORT).show();
                        } else {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            ArrayList<Cafes> cafeLIstingHelperseslist = new ArrayList<Cafes>(Arrays.asList(cafeListingData.getCafes()));
                            CafeListAdapter = new CafeListAdapter(context, cafeLIstingHelperseslist, new OnItemClickListener() {

                                @Override
                                public void OnItemClickListener(Cafes item) {
                                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                                    intent.putExtra("image",item.getImageurl());
                                    intent.putExtra("tittle",item.getCafe_name());
                                    intent.putExtra("sid",item.get_id());
                                    startActivity(intent);
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


                            });

                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recycleView.setLayoutManager(mLayoutManager);
                            recycleView.setAdapter(CafeListAdapter);
                            recycleView.setNestedScrollingEnabled(false);


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
            public void onFailure(Call<CafeListingData> call, Throwable t) {
                progressBarCyclic.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(context,R.string.Something_went_wrong,Toast.LENGTH_SHORT);

            }
        });


        //recycleView.getLayoutManager().smoothScrollToPosition(recycleView, null, 0);
        logo.setFocusable(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email);
        username.setText(sharedPreferences.getString("name",null)+" "+sharedPreferences.getString("surname",null));
        email.setText(sharedPreferences.getString("email",null));
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();  // optional depending on your needs
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
        } else if (id == R.id.nav_reward) {
            Intent intent = new Intent(MainActivity.this, RewardActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_cart) {
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_order) {
            Intent intent = new Intent(MainActivity.this, order_history.class);
            startActivity(intent);


        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_logout) {
            sessionManager.clear();
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_card) {
            Intent intent = new Intent(MainActivity.this, Card_Activity.class);
            startActivity(intent);

        } else if (id == R.id.nav_notification) {
            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(intent);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
