package com.example.infiny.pickup.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.Adapters.Sub_Menu_Adapter;
import com.example.infiny.pickup.Adapters.TimeviewAdapter;
import com.example.infiny.pickup.Helpers.MenuItem;
import com.example.infiny.pickup.Helpers.Orders;
import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Interfaces.OnItemClickListener;
import com.example.infiny.pickup.Model.Cafes;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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
    public static String dateString, parcel = "false", note;
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
    @BindView(R.id.rating1)
    ImageView rating1;
    @BindView(R.id.rating2)
    ImageView rating2;
    @BindView(R.id.rating3)
    ImageView rating3;
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
    @BindView(R.id.tw_pay)
    TextView twPay;
    @BindView(R.id.list_item_genre_arrow)
    ImageView listItemGenreArrow;
    @BindView(R.id.paylayout)
    RelativeLayout paylayout;
    @BindView(R.id.card_pay)
    CardView cardPay;
    Sub_Menu_Adapter subMenuAdapter;
     Ordered [] ordered;
    ArrayList<Ordered> ordereds;
    Ordered orderedObject;
    String sid;
    Boolean fromMenu;

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
        ordereds=new ArrayList<>();
        Intent intent=getIntent();
        sid=intent.getStringExtra("sid");
        fromMenu=intent.getBooleanExtra("fromMenu",false);
        subMenuAdapter=new Sub_Menu_Adapter(context,ordereds,onItemClickListener,sid);
        recycleView.setLayoutManager(layoutManager);
        recycleView.setAdapter(subMenuAdapter);
       recycleView.setNestedScrollingEnabled(false);
        progressBarCyclic.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        retroFitClient = new RetroFitClient(context).getBlankRetrofit();
        Call<OrderListData> call = retroFitClient
                .create(ApiIntegration.class)
                .getOrderListing(sharedPreferences.getString("token", null));
        call.enqueue(new Callback<OrderListData>() {

            @Override
            public void onResponse(Call<OrderListData> call, Response<OrderListData> response) {
                if (response != null) {
                    orderListData = response.body();
                    if (orderListData != null) {
                        if (orderListData.getError().equals("true")) {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        } else {
                            Picasso.with(context)
                                    .load(orderListData.getData().getShopDetail().getImageurl())
                                    .placeholder(R.drawable.cofeecup)
                                    .into(tittleimage);
                            tittle.setText(orderListData.getData().getShopDetail().getCafe_name());
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                            orderDatas = new ArrayList<>(Arrays.asList(orderListData.getData()));
                            for (int i = 0; i < orderDatas.size(); i++) {
                                orderData = orderDatas.get(i);

                                Sub_Menu_Adapter.totalprize=0f;
                                image = R.drawable.graycup1;
                                ordered =orderData.getOrdered();
                                for(int j=0;j<ordered.length;j++)
                                {
                                    orderedObject=ordered[j];
                                    orderedObject.setImage(image);
                                    ordereds.add(orderedObject);

                                }



                                mainLayout.setVisibility(View.VISIBLE);
                              note=etNote.getText().toString().trim();
                                if(subLayout.getVisibility()==View.VISIBLE) {
                                    subLayout.setVisibility(View.GONE);
                                    arrow.setRotation(360);
                                }
                                else {
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

            }
        });


        timeviewAdapter = new TimeviewAdapter(MenuItem.makeorder(), this);
        timerecycleview.setLayoutManager(layoutManager1);
        timerecycleview.setAdapter(timeviewAdapter);
        timerecycleview.setNestedScrollingEnabled(false);
        paylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarCyclic.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                retroFitClient = new RetroFitClient(context).getBlankRetrofit();
                fooRequest = new FooRequest();
                fooRequest.setUserToken(sharedPreferences.getString("token", null));
                fooRequest.setParcel(parcel);
                fooRequest.setOrder(ordered);
                fooRequest.setNote(note);
                fooRequest.setTimeForPickcup(dateString);
                fooRequest.setShopDetail(orderListData.getData().getShopDetail().get_id());
                fooRequest.setTotalPrice(totalprice.getText().toString());
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
                                    progressBarCyclic.setVisibility(View.GONE);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                } else {
                                    Intent intent = new Intent(context, MainActivity.class);
                                    startActivity(intent);

                                }

                            } else {
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
        });
        btPickcup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image=R.drawable.cuptake;
                Sub_Menu_Adapter.totalprize=0f;
                ordered =orderData.getOrdered();
                for(int j=0;j<ordered.length;j++)
                {
                    orderedObject=ordered[j];
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
                Sub_Menu_Adapter.totalprize=0f;
                ordered =orderData.getOrdered();
                for(int j=0;j<ordered.length;j++)
                {
                    orderedObject=ordered[j];
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
                if(subLayout.getVisibility()==View.VISIBLE) {
                    subLayout.setVisibility(View.GONE);
                    arrow.setRotation(360);
                }

                else{
                   subLayout.setVisibility(View.VISIBLE);
                    arrow.setRotation(180);

                }
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
        totalprice.setText(getCorrectValue(String.format("%.2f", Float.valueOf(total))));
    }
   public void onBackpresss()
    {
        if(fromMenu)
        {
            Intent intent=new Intent(OrderActivity.this,MenuActivity.class);
            intent.putExtra("sid",sid);
            startActivity(intent);
            finish();
        }
        else
        {
            Intent intent=new Intent(context,MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void ordereddata(Ordered[] ordered) {
        this.ordered = ordered;
    }


}
