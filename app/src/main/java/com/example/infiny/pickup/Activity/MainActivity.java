package com.example.infiny.pickup.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.infiny.pickup.Adapters.CafeListAdapter;
import com.example.infiny.pickup.Helpers.CafeLIstingHelpers;
import com.example.infiny.pickup.Interfaces.OnItemClickListener;
import com.example.infiny.pickup.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        ArrayList<CafeLIstingHelpers> cafeLIstingHelperseslist = new ArrayList<>();
        CafeLIstingHelpers cafeLIstingHelpers = new CafeLIstingHelpers();
        CafeLIstingHelpers cafeLIstingHelpers1 = new CafeLIstingHelpers();
        CafeLIstingHelpers cafeLIstingHelpers2 = new CafeLIstingHelpers();
        CafeLIstingHelpers cafeLIstingHelpers3 = new CafeLIstingHelpers();
        CafeLIstingHelpers cafeLIstingHelpers4 = new CafeLIstingHelpers();
        cafeLIstingHelpers.setStatus("await");
        cafeLIstingHelpers.setPartyname("urban cafe");
        cafeLIstingHelpers.setImage(R.drawable.cofee3);
        cafeLIstingHelpers.setRating("2");
        cafeLIstingHelperseslist.add(cafeLIstingHelpers);
        cafeLIstingHelpers1.setStatus("online");
        cafeLIstingHelpers1.setPartyname("Yorks");
        cafeLIstingHelpers1.setRating("4");

        cafeLIstingHelpers1.setImage(R.drawable.cofee2);
        cafeLIstingHelperseslist.add(cafeLIstingHelpers1);
        cafeLIstingHelpers2.setStatus("online");
        cafeLIstingHelpers2.setRating("1");
        cafeLIstingHelpers2.setPartyname("7/8 kafe");
        cafeLIstingHelpers2.setImage(R.drawable.cofee3);
        cafeLIstingHelperseslist.add(cafeLIstingHelpers2);
        cafeLIstingHelpers3.setRating("2");
        cafeLIstingHelpers3.setStatus("offline");
        cafeLIstingHelpers3.setPartyname("snack adda");
        cafeLIstingHelpers3.setImage(R.drawable.cofee4);
        cafeLIstingHelperseslist.add(cafeLIstingHelpers3);
        cafeLIstingHelpers4.setStatus("online");
        cafeLIstingHelpers4.setRating("3");
        cafeLIstingHelpers4.setPartyname("urban adda");
        cafeLIstingHelpers4.setImage(R.drawable.cofee5);
        cafeLIstingHelperseslist.add(cafeLIstingHelpers4);

        CafeListAdapter = new CafeListAdapter(this, cafeLIstingHelperseslist, new OnItemClickListener() {

            @Override
            public void OnItemClickListener(CafeLIstingHelpers item) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                intent.putExtra("tittle", item.getPartyname());
                intent.putExtra("image", item.getImage());
                startActivity(intent);
            }

            @Override
            public void onimageclickLister() {

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setAdapter(CafeListAdapter);
        //recycleView.getLayoutManager().smoothScrollToPosition(recycleView, null, 0);
        logo.setFocusable(true) ;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            Intent intent=new Intent(MainActivity.this,RewardActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_cart) {

        } else if (id == R.id.nav_order) {
            Intent intent=new Intent(MainActivity.this,order_history.class);
            startActivity(intent);


        } else if (id == R.id.nav_profile) {
            Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_logout) {

        }
        else if (id == R.id.nav_card) {
            Intent intent=new Intent(MainActivity.this,Card_Activity.class);
            startActivity(intent);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
