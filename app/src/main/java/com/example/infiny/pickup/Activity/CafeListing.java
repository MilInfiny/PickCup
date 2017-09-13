package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.infiny.pickup.Adapters.CafeListAdapter;
import com.example.infiny.pickup.Helpers.CafeLIstingHelpers;
import com.example.infiny.pickup.Interfaces.OnItemClickListener;
import com.example.infiny.pickup.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CafeListing extends AppCompatActivity {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    CafeListAdapter CafeListAdapter;
    CafeLIstingHelpers cafeLIstingHelpers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_listing);
        ButterKnife.bind(this);
        ArrayList<CafeLIstingHelpers> cafeLIstingHelperseslist=new ArrayList<>();
       CafeLIstingHelpers cafeLIstingHelpers=new CafeLIstingHelpers();
        CafeLIstingHelpers cafeLIstingHelpers1=new CafeLIstingHelpers();
        CafeLIstingHelpers cafeLIstingHelpers2=new CafeLIstingHelpers();
        CafeLIstingHelpers cafeLIstingHelpers3=new CafeLIstingHelpers();
        CafeLIstingHelpers cafeLIstingHelpers4=new CafeLIstingHelpers();
        cafeLIstingHelpers.setStatus("await");
        cafeLIstingHelpers.setPartyname("urban cafe");
        cafeLIstingHelpers.setRating("2");
       cafeLIstingHelpers.setImage(R.drawable.cofee3);
        cafeLIstingHelperseslist.add(cafeLIstingHelpers);
        cafeLIstingHelpers1.setStatus("online");
        cafeLIstingHelpers1.setPartyname("Yorks");
        cafeLIstingHelpers1.setRating("4");
//
//        cafeLIstingHelpers1.setImage(R.drawable.cofee2);
//        cafeLIstingHelperseslist.add(cafeLIstingHelpers1);
//        cafeLIstingHelpers2.setStatus("online");
//        cafeLIstingHelpers2.setPartyname("7/8 kafe");
//        cafeLIstingHelpers2.setRating("1");
//
//        cafeLIstingHelpers2.setImage(R.drawable.cofee3);
//        cafeLIstingHelperseslist.add(cafeLIstingHelpers2);
//        cafeLIstingHelpers3.setStatus("offline");
//        cafeLIstingHelpers3.setPartyname("snack adda");
//        cafeLIstingHelpers3.setRating("2");
//
//        cafeLIstingHelpers3.setImage(R.drawable.cofee4);
//        cafeLIstingHelperseslist.add(cafeLIstingHelpers3);
//        cafeLIstingHelpers4.setStatus("online");
//        cafeLIstingHelpers4.setPartyname("urban adda");
//        cafeLIstingHelpers4.setRating("5");
//        cafeLIstingHelpers4.setImage(R.drawable.cofee5);
//        cafeLIstingHelperseslist.add(cafeLIstingHelpers4);

        CafeListAdapter=new CafeListAdapter(this,cafeLIstingHelperseslist, new OnItemClickListener() {

            @Override
            public void OnItemClickListener(CafeLIstingHelpers item) {
                Intent intent=new Intent(CafeListing.this,MenuActivity.class);
                intent.putExtra("tittle",item.getPartyname());
                intent.putExtra("image",item.getImage());
                startActivity(intent);
            }

            @Override
            public void onimageclickLister() {

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.setAdapter(CafeListAdapter);
        recycleView.getLayoutManager().smoothScrollToPosition(recycleView, null, 0);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
