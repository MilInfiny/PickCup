package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infiny.pickup.Adapters.RewardAdapter;
import com.example.infiny.pickup.Helpers.CafeLIstingHelpers;
import com.example.infiny.pickup.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RewardActivity extends AppCompatActivity {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    RewardAdapter rewardAdapter;
    @BindView(R.id.tw_reward)
    TextView twReward;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.view)
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appbar.setOutlineProvider(null);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        logo.requestFocus();

        ArrayList<String> partyname = new ArrayList<>();
        icon.setImageDrawable(getResources().getDrawable(R.drawable.rewards));
        ArrayList<CafeLIstingHelpers> cafeLIstingHelperseslist = new ArrayList<>();
        CafeLIstingHelpers cafeLIstingHelpers = new CafeLIstingHelpers();
        CafeLIstingHelpers cafeLIstingHelpers1 = new CafeLIstingHelpers();
        CafeLIstingHelpers cafeLIstingHelpers2 = new CafeLIstingHelpers();
        CafeLIstingHelpers cafeLIstingHelpers3 = new CafeLIstingHelpers();
        CafeLIstingHelpers cafeLIstingHelpers4 = new CafeLIstingHelpers();
        cafeLIstingHelpers.setStatus("5");
        cafeLIstingHelpers.setPartyname("urban cafe");
        cafeLIstingHelpers.setImage(R.drawable.cofee3);
        cafeLIstingHelperseslist.add(cafeLIstingHelpers);
        cafeLIstingHelpers1.setStatus("3");
        cafeLIstingHelpers1.setPartyname("Yorks");
        cafeLIstingHelpers1.setImage(R.drawable.cofee2);
        cafeLIstingHelperseslist.add(cafeLIstingHelpers1);
        cafeLIstingHelpers2.setStatus("2");
        cafeLIstingHelpers2.setPartyname("7/8 kafe");
        cafeLIstingHelpers2.setImage(R.drawable.cofee3);
        cafeLIstingHelperseslist.add(cafeLIstingHelpers2);
        cafeLIstingHelpers3.setStatus("3");
        cafeLIstingHelpers3.setPartyname("snack adda");
        cafeLIstingHelpers3.setImage(R.drawable.cofee4);
        cafeLIstingHelperseslist.add(cafeLIstingHelpers3);
        cafeLIstingHelpers4.setStatus("2");
        cafeLIstingHelpers4.setPartyname("urban adda");
        cafeLIstingHelpers4.setImage(R.drawable.cofee5);
        cafeLIstingHelperseslist.add(cafeLIstingHelpers4);

        rewardAdapter = new RewardAdapter(this, cafeLIstingHelperseslist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setAdapter(rewardAdapter);
        recycleView.setNestedScrollingEnabled(false);
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
