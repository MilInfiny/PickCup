package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.Adapters.MenuNormalAdapter;
import com.example.infiny.pickup.Adapters.NormalMenuAdapter;
import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Interfaces.OnItemClickListener;
import com.example.infiny.pickup.Model.AddToCartData;
import com.example.infiny.pickup.Model.Cafes;
import com.example.infiny.pickup.Model.CardDetails;
import com.example.infiny.pickup.Model.Data;
import com.example.infiny.pickup.Model.ItemData;
import com.example.infiny.pickup.Model.MenuListData;
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

import static com.example.infiny.pickup.R.drawable.cofeecup;
import static com.example.infiny.pickup.R.drawable.ic_person_black_48dp;

public class MenuActivity extends AppCompatActivity implements OnItemClickListener {


    MenuNormalAdapter menuNormalAdapter;
    Context context;
    String sid;
    public static String rewardcompleted,rewardQuantity,status;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.tittleimage)
    ImageView tittleimage;
    @BindView(R.id.tittle)
    TextView tittle;
    @BindView(R.id.status_button)
    Button statusButton;

    @BindView(R.id.toplayout)
    RelativeLayout toplayout;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    @BindView(R.id.card_view)
    CardView cardView;

    public static RelativeLayout order;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.progressBar_cyclic)
    ProgressBar progressBarCyclic;
    Retrofit retroFitClient;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    MenuListData menuItemData;
    AddToCartData addToCartData;
    ItemData itemData;
    public static TextView btOrder;
    OnItemClickListener onItemClickListener;
Typeface font;
    public static TextView orderPrice;
    @BindView(R.id.cartimage)
    ImageView cartimage;
    public static String cart_count_String;
    public static TextView cartCount;
    @BindView(R.id.rewarddetails)
    TextView rewarddetails;


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
        final Intent intent = getIntent();
        context = this;
        font = Typeface.createFromAsset(context.getAssets(), "fonts/opensansbold.ttf");
        onItemClickListener = this;
        sessionManager = new SessionManager(context);
        sharedPreferences = getSharedPreferences(sessionManager.PREF_NAME, 0);
        tittle.setTypeface(font);
        tittle.setText(intent.getStringExtra("tittle"));
        cartCount = (TextView) findViewById(R.id.cart_count);
        sid = intent.getStringExtra("sid");
        rewardcompleted=intent.getStringExtra("rewardcompleted");
        rewardQuantity=intent.getStringExtra("rewardQuantity");
        status=intent.getStringExtra("status");


        if (isTablet(context)) {

            Picasso.with(context)
                    .load(intent.getStringExtra("image") + "_large.jpg")
                    .placeholder(cofeecup)
                    .into(tittleimage);

        } else {

            Picasso.with(context)
                    .load(intent.getStringExtra("image") + "_small.jpg")
                    .placeholder(cofeecup)
                    .into(tittleimage);
        };

        if(status.equals("ready"))
        {
            statusButton.setBackground(context.getResources().getDrawable(R.drawable.button_bg_round_green));
        }
        if(status.equals("closed"))
        {
            statusButton.setBackground(context.getResources().getDrawable(R.drawable.button_bg_round_red));
        }
        if(status.equals("busy"))
        {
            statusButton.setBackground(context.getResources().getDrawable(R.drawable.button_bg_round_yellow));

        };


            rewarddetails.setText(rewardcompleted+"/"+rewardQuantity);

        if (rewardcompleted == null) {
            rewarddetails.setText("0" + "/" +rewardQuantity);
        } else {
            rewarddetails.setText(rewardcompleted + "/" + rewardQuantity);
        }


        order = (RelativeLayout) findViewById(R.id.order);
        btOrder = (TextView) findViewById(R.id.bt_order);
        orderPrice = (TextView) findViewById(R.id.order_price);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, OrderActivity.class);
                intent1.putExtra("sid", sid);
                intent1.putExtra("fromPage", "menuActivity");
                intent1.putExtra("tittle", intent.getStringExtra("tittle"));
                intent1.putExtra("image", intent.getStringExtra("image"));

                startActivity(intent1);
                finish();

            }
        });
        Log.d("sdas", sharedPreferences.getString("token", null));

        progressBarCyclic.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        retroFitClient = new RetroFitClient(context).getBlankRetrofit();
        Call<MenuListData> call = retroFitClient
                .create(ApiIntegration.class)
                .getMenuListing(sharedPreferences.getString("token", null),
                        sid);
        call.enqueue(new Callback<MenuListData>() {

            @Override
            public void onResponse(Call<MenuListData> call, Response<MenuListData> response) {
                if (response != null) {
                    menuItemData = response.body();
                    if (menuItemData != null) {
                        if (menuItemData.getError().equals("true")) {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(context, menuItemData.getTitle(), Toast.LENGTH_SHORT).show();
                            order.setVisibility(View.GONE);
                        } else {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            NormalMenuAdapter.total = Float.valueOf(menuItemData.getTotalPrice());

                            if (menuItemData.getTotalCount().equals("0") && menuItemData.getTotalPrice().equals("0")) {
                                MenuActivity.cart_count_String = "0";
                                order.setVisibility(View.GONE);
                            } else {
                                order.setVisibility(View.VISIBLE);
                                cartCount.setText(menuItemData.getTotalCount());
                                cart_count_String = menuItemData.getTotalCount();
                                orderPrice.setText("£ " + getCorrectValue(String.format("%.2f", Float.valueOf(menuItemData.getTotalPrice()))));
                            }

                            ArrayList<Data> datas = new ArrayList<>(Arrays.asList(menuItemData.getData()));
                            menuNormalAdapter = new MenuNormalAdapter(context, datas, onItemClickListener, sid);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recycleView.setLayoutManager(mLayoutManager);
                            recycleView.setAdapter(menuNormalAdapter);
                            recycleView.setNestedScrollingEnabled(false);

                        }

                    } else {
                        if (response.code() == 404 || response.code() == 500) {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                            order.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MenuListData> call, Throwable t) {
                progressBarCyclic.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(context, R.string.Something_went_wrong, Toast.LENGTH_SHORT);

            }
        });


    }

    public String getCorrectValue(String price) {
        String[] priceSpl = price.split("\\.");
        if (priceSpl.length > 1)
            if (priceSpl[1].equals("00") || priceSpl[1].equals("0")) {
                price = priceSpl[0];
            }
        return price;
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

    @Override
    public void OnItemClickListener(Cafes item) {

    }

    @Override
    public void voidOnAddCart(ItemData itemData) {
        this.itemData = itemData;

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

    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }


}
