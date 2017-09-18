package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.infiny.pickup.Adapters.MenuAdapter;
import com.example.infiny.pickup.Adapters.NormalMenuAdapter;
import com.example.infiny.pickup.Helpers.CafeLIstingHelpers;
import com.example.infiny.pickup.Helpers.MenuItem;
import com.example.infiny.pickup.Helpers.MenuItemData;
import com.example.infiny.pickup.Interfaces.OnItemClickListener;
import com.example.infiny.pickup.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.tittle)
    TextView tittle;
    @BindView(R.id.rating1)
    ImageView rating1;
    @BindView(R.id.rating2)
    ImageView rating2;
    @BindView(R.id.rating3)
    ImageView rating3;

    @BindView(R.id.toplayout)
    RelativeLayout toplayout;
    @BindView(R.id.drinksmenuview)
    RecyclerView drinksmenuview;


    MenuAdapter drinksadapter;
    @BindView(R.id.tw_tittledrinks)
    TextView twTittledrinks;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.drinklayout)
    RelativeLayout drinklayout;
    @BindView(R.id.tw_tittledessert)
    TextView twTittledessert;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.dessertsmenuview)
    RecyclerView dessertsmenuview;
    @BindView(R.id.dessertlayout)
    RelativeLayout dessertlayout;
    @BindView(R.id.tw_tittlesandwitch)
    TextView twTittlesandwitch;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.sandwitchesmenuview)
    RecyclerView sandwitchesmenuview;
    @BindView(R.id.sandwitchlayout)
    RelativeLayout sandwitchlayout;
    @BindView(R.id.scrollview)
    ScrollView scrollview;

    NormalMenuAdapter dessertadapter, sandwitchadapter;
    @BindView(R.id.status_button)
    Button statusButton;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.tittleimage)
    ImageView tittleimage;
    @BindView(R.id.order)
    RelativeLayout order;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appbar.setOutlineProvider(null);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        tittle.setText(intent.getStringExtra("tittle"));
        tittleimage.setImageResource(intent.getIntExtra("image", 0));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MenuActivity.this, OrderActivity.class);
                startActivity(intent1);
            }
        });
        drinksadapter = new MenuAdapter(MenuItem.makeGenres(), new OnItemClickListener() {
            @Override
            public void OnItemClickListener(CafeLIstingHelpers item) {

            }

            @Override
            public void onimageclickLister() {
                order.setVisibility(View.VISIBLE);


            }
        }, this);
        drinksmenuview.setLayoutManager(layoutManager);
        drinksmenuview.setAdapter(drinksadapter);
        drinksmenuview.setNestedScrollingEnabled(false);

        ArrayList<MenuItemData> tittles = new ArrayList<MenuItemData>();

        tittles.add(new MenuItemData("Brownie", "£ 3.00"));
        tittles.add(new MenuItemData("Cheesecake", "£ 3.10"));
        tittles.add(new MenuItemData("Carrot Cake", "£ 3.10"));
        dessertadapter = new NormalMenuAdapter(this, tittles, new OnItemClickListener() {
            @Override
            public void OnItemClickListener(CafeLIstingHelpers item) {

            }

            @Override
            public void onimageclickLister() {
                order.setVisibility(View.VISIBLE);
            }
        });
        dessertsmenuview.setLayoutManager(layoutManager1);
        dessertsmenuview.setAdapter(dessertadapter);
        dessertsmenuview.setNestedScrollingEnabled(false);

        ArrayList<MenuItemData> tittles1 = new ArrayList<MenuItemData>();
        tittles1.add(new MenuItemData("Tuna Sandwich", "£ 5.20"));
        tittles1.add(new MenuItemData("Avocado Sandwich", "£ 5.50"));
        tittles1.add(new MenuItemData("Egg Sandwich", "£ 5.20"));
        tittles1.add(new MenuItemData("Ham Sandwich", "£ 5.50"));
        sandwitchadapter = new NormalMenuAdapter(this, tittles1, new OnItemClickListener() {
            @Override
            public void OnItemClickListener(CafeLIstingHelpers item) {

            }

            @Override
            public void onimageclickLister() {
                order.setVisibility(View.VISIBLE);
            }
        });
        sandwitchesmenuview.setLayoutManager(layoutManager2);
        sandwitchesmenuview.setAdapter(sandwitchadapter);
        sandwitchesmenuview.setNestedScrollingEnabled(false);

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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        drinksadapter.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        drinksadapter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
