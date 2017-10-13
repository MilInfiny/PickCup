package com.example.infiny.pickup.Activity;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.Adapters.CafeListAdapter;
import com.example.infiny.pickup.Helpers.BadgeDrawerArrowDrawable;
import com.example.infiny.pickup.Helpers.BadgeDrawerToggle;
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
    private boolean loading = true;
    Retrofit retroFitClient;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int current_page = 1;
    private BadgeDrawerToggle badgeToggle;
    ArrayList<Cafes> cafeLIstingHelperseslist;

    TextView username,email,Notifications,Rewards;
    ImageView profileView;
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
        Log.e("userToken",sharedPreferences.getString("token",null));
        Notifications=(TextView) MenuItemCompat.getActionView(navView.getMenu().
                findItem(R.id.nav_notification));
        Rewards=(TextView) MenuItemCompat.getActionView(navView.getMenu().
                findItem(R.id.nav_reward));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        badgeToggle = new BadgeDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(badgeToggle);
        badgeToggle.setBadgeEnabled(false);
        badgeToggle.syncState();
      cafeLIstingHelperseslist = new ArrayList<Cafes>();

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


        //recycleView.getLayoutManager().smoothScrollToPosition(recycleView, null, 0);
        logo.setFocusable(true);






        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        username = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email);
        profileView=(ImageView)navigationView.getHeaderView(0).findViewById(R.id.profileView);




        if(isTablet(context))
        {
            Picasso.with(context)
                    .invalidate(sharedPreferences.getString(sessionManager.image, null)+"_large.png");
            Picasso.with(context)
                    .load(sharedPreferences.getString(sessionManager.image, null)+"_large.png")
                    .placeholder(R.drawable.ic_person_black_48dp)
                    .into(profileView);

        }
        else
        {
            Picasso.with(context)
                    .invalidate(sharedPreferences.getString(sessionManager.image, null)+"_small.png");
            Picasso.with(context)
                    .load(sharedPreferences.getString(sessionManager.image, null)+"_small.png")
                    .placeholder(R.drawable.ic_person_black_48dp)
                    .into(profileView);
        };
        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

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
    public void getData(int currentPage)
    {
        progressBarCyclic.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        retroFitClient = new RetroFitClient(context).getBlankRetrofit();

        Call<CafeListingData> call = retroFitClient
                .create(ApiIntegration.class)
                .getcafelisting(sharedPreferences.getString("token",null),
                        "134.05839","73.00754",String.valueOf(currentPage),String.valueOf(currentPage-1));
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
                            if(cafeListingData.getClaimedReward()!=0)
                            {


                            }
                            if(cafeListingData.getUnreadNotification() != 0)
                            {

                            }
                            else {
                                badgeToggle.setBadgeEnabled(true);
                                badgeToggle.setBadgeText(String.valueOf(cafeListingData.getUnreadNotification()+cafeListingData.getClaimedReward()));
                                Notifications.setText(String.valueOf(cafeListingData.getUnreadNotification()));
                                Notifications.setGravity(Gravity.CENTER_VERTICAL);
                                Rewards.setText(String.valueOf(cafeListingData.getClaimedReward()));
                                Rewards.setGravity(Gravity.CENTER_VERTICAL);

                            }




                            cafeLIstingHelperseslist.addAll(Arrays.asList(cafeListingData.getCafes()));
                            CafeListAdapter.notifyDataSetChanged();


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
            finish();

        } else if (id == R.id.nav_cart) {
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            intent.putExtra("fromMenu",false);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_order) {
            Intent intent = new Intent(MainActivity.this, order_history.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_logout) {

            Intent intent=new Intent(MainActivity.this,LoginActivity.class);

            startActivity(intent);
            finish();

        } else if (id == R.id.nav_card) {
            Intent intent = new Intent(MainActivity.this, Card_Activity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_notification) {
            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(intent);
            finish();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

}
