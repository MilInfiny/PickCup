package com.example.infiny.pickup.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.Adapters.Sub_Menu_Adapter;
import com.example.infiny.pickup.Adapters.TimeviewAdapter;
import com.example.infiny.pickup.Helpers.MenuItem;
import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Interfaces.OnItemClickListener;
import com.example.infiny.pickup.Model.Cafes;
import com.example.infiny.pickup.Model.CardDetails;
import com.example.infiny.pickup.Model.CreateOrderData;
import com.example.infiny.pickup.Model.FooRequest;
import com.example.infiny.pickup.Model.ItemData;
import com.example.infiny.pickup.Model.OrderData;
import com.example.infiny.pickup.Model.OrderListData;
import com.example.infiny.pickup.Model.Ordered;
import com.example.infiny.pickup.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.infiny.pickup.R.drawable.cofeecup;
import static com.example.infiny.pickup.R.drawable.ic_person_black_48dp;

public class OrderActivity extends AppCompatActivity implements OnItemClickListener {


    TimeviewAdapter timeviewAdapter;
    ArrayList<OrderData> orderDatas;
    Context context;
    AlertDialog.Builder builder;
    OrderListData orderListData;
    Retrofit retroFitClient;
    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    CreateOrderData createOrderData;
    public static String dateString = null, parcel = "false", note;
    String total;
    ;
    OnItemClickListener onItemClickListener;
    FooRequest fooRequest;


    OrderData orderData;
    int image;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.progressBar_cyclic)
    ProgressBar progressBarCyclic;
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
    @BindView(R.id.tw_order)
    TextView twOrder;
    @BindView(R.id.arrow)
    ImageView arrow;
    @BindView(R.id.relativeLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.et_notehint)
    TextView etNotehint;
    @BindView(R.id.et_note)
    EditText etNote;
    @BindView(R.id.bt_sitin)
    TextView btSitin;
    @BindView(R.id.bt_pickcup)
    TextView btPickcup;
    @BindView(R.id.buttonview)
    RelativeLayout buttonview;
    @BindView(R.id.bottomview)
    View bottomview;
    @BindView(R.id.totalprice)
    TextView totalprice;
    @BindView(R.id.totalview)
    RelativeLayout totalview;
    @BindView(R.id.sub_layout)
    RelativeLayout subLayout;
    @BindView(R.id.card_view1)
    CardView cardView1;
    @BindView(R.id.timerecycleview)
    RecyclerView timerecycleview;
    @BindView(R.id.rel_time)
    RelativeLayout relTime;
    @BindView(R.id.card_view)
    CardView cardView;
    Sub_Menu_Adapter subMenuAdapter;
    Ordered[] ordered;
    ArrayList<Ordered> ordereds;
    Ordered orderedObject;
    String sid, fromMenu, cafeName, rewardcompleted, rewardQuantity;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.layoutyscrollview)
    ScrollView layoutyscrollview;
    @BindView(R.id.logo1)
    ImageView logo1;
    @BindView(R.id.simpleSwitch)
    Switch simpleSwitch;
    @BindView(R.id.claimlayout)
    RelativeLayout claimlayout;
    Float tax = 0f;
    Float totalpercentage;
    @BindView(R.id.taxprice)
    TextView taxprice;
    @BindView(R.id.taxview)
    RelativeLayout taxview;
    @BindView(R.id.texbelowview)
    View texbelowview;
    @BindView(R.id.belowSwitch)
    View belowSwitch;
    @BindView(R.id.paylayout)
    Button paylayout;
    String rating;
    @BindView(R.id.rewarddetails)
    TextView rewarddetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appbar.setOutlineProvider(null);
        context = this;
        onItemClickListener = this;
        sessionManager = new SessionManager(this);
        sharedPreferences = getSharedPreferences(sessionManager.PREF_NAME, 0);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        ordereds = new ArrayList<>();
        Intent intent = getIntent();
        sid = intent.getStringExtra("sid");
        fromMenu = intent.getStringExtra("fromPage");
        cafeName = intent.getStringExtra("tittle");
        rewardcompleted = intent.getStringExtra("rewardcompleted");
        rewardQuantity = intent.getStringExtra("rewardQuantity");
        tittle.setText(cafeName);
        progressBarCyclic.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        retroFitClient = new RetroFitClient(context).getBlankRetrofit();
        Call<OrderListData> call = retroFitClient
                .create(ApiIntegration.class)
                .getOrderListing(sharedPreferences.getString("token", null),
                        sid);
        call.enqueue(new Callback<OrderListData>() {

            @Override
            public void onResponse(Call<OrderListData> call, Response<OrderListData> response) {
                if (response != null) {
                    orderListData = response.body();
                    if (orderListData != null) {
                        if (orderListData.getError().equals("true")) {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (!fromMenu.equals("rewardActivity")) {
                                nodata.setVisibility(View.VISIBLE);
                                layoutyscrollview.setVisibility(View.GONE);
                                logo1.setVisibility(View.VISIBLE);

                            } else {
                                layoutyscrollview.setVisibility(View.VISIBLE);
                                progressBarCyclic.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                simpleSwitch.setChecked(true);
                                claimlayout.setVisibility(View.VISIBLE);
                                belowSwitch.setVisibility(View.VISIBLE);
                                taxprice.setText("0");
                                totalprice.setText("0");
                                mainLayout.setVisibility(View.VISIBLE);

                                if (subLayout.getVisibility() == View.VISIBLE) {
                                    subLayout.setVisibility(View.GONE);
                                    arrow.setRotation(360);
                                } else {
                                    subLayout.setVisibility(View.VISIBLE);
                                    arrow.setRotation(180);
                                }
                            }

                        } else {
                            layoutyscrollview.setVisibility(View.VISIBLE);

                            if (isTablet(context)) {

                                Picasso.with(context)
                                        .load(orderListData.getData().getShopDetail().getImageurl() + "_large.jpg")
                                        .placeholder(cofeecup)
                                        .into(tittleimage);
                            } else {

                                Picasso.with(context)
                                        .load(orderListData.getData().getShopDetail().getImageurl() + "_small.jpg")
                                        .placeholder(cofeecup)
                                        .into(tittleimage);
                            }
                            ;
                            tittle.setText(orderListData.getData().getShopDetail().getCafe_name());
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if (orderListData.getCanClaimedReward().equals("false")) {
                                simpleSwitch.setChecked(false);
                                claimlayout.setVisibility(View.GONE);
                                belowSwitch.setVisibility(View.GONE);
                            } else {
                                simpleSwitch.setChecked(true);
                                claimlayout.setVisibility(View.VISIBLE);
                                belowSwitch.setVisibility(View.VISIBLE);
                            }
                            totalpercentage = Float.valueOf(orderListData.getStripeCharge().getPercentCharge()) + Float.valueOf(orderListData.getAdminTax());


                            orderDatas = new ArrayList<>(Arrays.asList(orderListData.getData()));
                            for (int i = 0; i < orderDatas.size(); i++) {
                                orderData = orderDatas.get(i);

                                Sub_Menu_Adapter.totalprize = 0f;
                                image = R.drawable.graycup1;
                                ordered = orderData.getOrdered();
                                sid = orderListData.getData().getShopDetail().get_id();
                                for (int j = 0; j < ordered.length; j++) {
                                    orderedObject = ordered[j];
                                    orderedObject.setImage(image);
                                    ordereds.add(orderedObject);

                                }
                                subMenuAdapter = new Sub_Menu_Adapter(context, ordereds, onItemClickListener, sid);
                                recycleView.setLayoutManager(layoutManager);
                                recycleView.setAdapter(subMenuAdapter);
                                recycleView.setNestedScrollingEnabled(false);


                                mainLayout.setVisibility(View.VISIBLE);
                                note = etNote.getText().toString().trim();
                                if (subLayout.getVisibility() == View.VISIBLE) {
                                    subLayout.setVisibility(View.GONE);
                                    arrow.setRotation(360);
                                } else {
                                    subLayout.setVisibility(View.VISIBLE);
                                    arrow.setRotation(180);
                                }
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
            public void onFailure(Call<OrderListData> call, Throwable t) {
                progressBarCyclic.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();

            }
        });


        timeviewAdapter = new TimeviewAdapter(MenuItem.makeorder(), this);
        timerecycleview.setLayoutManager(layoutManager1);
        timerecycleview.setAdapter(timeviewAdapter);
        timerecycleview.setNestedScrollingEnabled(false);
        paylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fromMenu.equals("rewardActivity")) {
                    if (ordered.length == 0) {
                        Toast.makeText(context, "Cart is Empty ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (dateString == null) {
                    Toast.makeText(context, R.string.selectTime, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!fromMenu.equals("rewardActivity")) {
                    if (orderListData.getData().getUserDetail().getCardDetails().length == 0) {
                        Toast.makeText(context, R.string.Add_Card, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, Add_Card_Activity.class);
                        intent.putExtra("fromOrder", true);
                        startActivity(intent);
                        finish();

                    } else {
                        getData();
                    }
                } else {

                    getData();

                }
            }


        });
        btPickcup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = R.drawable.cuptake;
                parcel = "true";
                Sub_Menu_Adapter.totalprize = 0f;
                ordered = orderData.getOrdered();
                for (int j = 0; j < ordered.length; j++) {
                    orderedObject = ordered[j];
                    orderedObject.setImage(image);


                }
                subMenuAdapter.notifyDataSetChanged();
                if (subMenuAdapter.getItemCount() > 1) {
                    recycleView.getLayoutManager().smoothScrollToPosition(recycleView, null, 0);
                }

            }
        });
        btSitin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = R.drawable.graycup1;
                Sub_Menu_Adapter.totalprize = 0f;
                ordered = orderData.getOrdered();
                for (int j = 0; j < ordered.length; j++) {
                    orderedObject = ordered[j];
                    orderedObject.setImage(image);

                }
                subMenuAdapter.notifyDataSetChanged();
                if (subMenuAdapter.getItemCount() > 1) {
                    recycleView.getLayoutManager().smoothScrollToPosition(recycleView, null, 0);
                }

            }
        });
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subLayout.getVisibility() == View.VISIBLE) {
                    subLayout.setVisibility(View.GONE);
                    arrow.setRotation(360);
                } else {
                    subLayout.setVisibility(View.VISIBLE);
                    arrow.setRotation(180);

                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public String getCorrectValue(String price) {
        String[] priceSpl = price.split("\\.");
        if (priceSpl.length > 1)
            if (priceSpl[1].equals("00") || priceSpl[1].equals("0")) {
                price = priceSpl[0];
            }
        return price;
    }

    public void getData() {
        progressBarCyclic.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        retroFitClient = new RetroFitClient(context).getBlankRetrofit();
        fooRequest = new FooRequest();
        fooRequest.setUserToken(sharedPreferences.getString("token", null));
        fooRequest.setParcel(parcel);
        if (ordered == null && simpleSwitch.isChecked()) {
            fooRequest.setOrderType("0");
        } else if (ordered.length >= 0 && simpleSwitch.isChecked()) {
            fooRequest.setOrderType("2");
        } else if (ordered.length >= 0 && simpleSwitch.isChecked() == false) {
            fooRequest.setOrderType("1");
        }

        fooRequest.setOrder(ordered);
        fooRequest.setNote(etNote.getText().toString().trim());
        fooRequest.setTimezone(TimeZone.getDefault().getID());
        fooRequest.setTimeForPickcup(dateString);
        if (orderListData.getData() == null) {
            fooRequest.setShopDetail(sid);
        } else {
            fooRequest.setShopDetail(orderListData.getData().getShopDetail().get_id());
        }

        fooRequest.setTotalPrice(total);
        Gson gson = new Gson();
        JSONObject s = null;
        try {
            s = new JSONObject(gson.toJson(fooRequest));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call<CreateOrderData> call = retroFitClient
                .create(ApiIntegration.class)
                .getCreateOrder("application/json", fooRequest);
        call.enqueue(new Callback<CreateOrderData>() {

            @Override
            public void onResponse(Call<CreateOrderData> call, Response<CreateOrderData> response) {
                if (response != null) {
                    createOrderData = response.body();
                    if (createOrderData != null) {
                        if (createOrderData.getError().equals("true")) {
                            dateString = null;
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        } else {
                            dateString = null;
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    } else {
                        dateString = null;
                        if (response.code() == 404 || response.code() == 500) {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }

            @Override
            public void onFailure(Call<CreateOrderData> call, Throwable t) {
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
        if (Float.valueOf(total) == 0) {
            arrow.setRotation(360);
            taxprice.setText("£ " + "0");
            totalprice.setText("£ " + "0");
        } else {
            tax = (Float.valueOf(total) * totalpercentage / 100) + Float.valueOf(orderListData.getStripeCharge().getAdditional());
            this.total = getCorrectValue(String.format("%.2f", Float.valueOf(total) + tax));
            taxprice.setText("£ " + getCorrectValue(String.format("%.2f", tax)));
            totalprice.setText("£ " + getCorrectValue(String.format("%.2f", Float.valueOf(this.total))));
        }
    }

    public void onBackpresss() {
        if (fromMenu.equals("menuActivity")) {
            Intent intent = new Intent(context, MenuActivity.class);
            intent.putExtra("sid", sid);
            intent.putExtra("tittle", cafeName);
            intent.putExtra("image", orderListData.getData().getShopDetail().getImageurl());
            intent.putExtra("rewardcompleted", rewardcompleted);
            if(rewardQuantity==null)
            {
                rewardQuantity="0";
            }
                intent.putExtra("rewardQuantity", rewardQuantity);


            startActivity(intent);
            finish();
        } else if (fromMenu.equals("rewardActivity")) {
            Intent intent = new Intent(context, RewardActivity.class);
            startActivity(intent);
            finish();
        } else if (fromMenu.equals("mainActivity")) {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    @Override
    public void ordereddata(Ordered[] ordered) {
        this.ordered = ordered;
    }

    @Override
    public void cardSet(ArrayList<CardDetails> cardDetailses) {


    }


}
