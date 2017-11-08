package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.Adapters.OrderDetailsAdapter;
import com.example.infiny.pickup.Helpers.MenuItemData;
import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Interfaces.OnItemClickListener;
import com.example.infiny.pickup.Model.Cafes;
import com.example.infiny.pickup.Model.CardDetails;
import com.example.infiny.pickup.Model.DataFindpartiOrder;
import com.example.infiny.pickup.Model.DataOrderHistory;
import com.example.infiny.pickup.Model.FindpartiOrder;
import com.example.infiny.pickup.Model.ItemData;
import com.example.infiny.pickup.Model.Ordered;
import com.example.infiny.pickup.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OrderDetailsActivity extends AppCompatActivity implements OnItemClickListener {


    @BindView(R.id.tv_CustomerName)
    TextView tvCustomerName;
    @BindView(R.id.tv_TotalCostOrder)
    TextView tvTotalCostOrder;
    @BindView(R.id.tv_OrderNumber)
    TextView tvOrderNumber;
    @BindView(R.id.tv_CustomerNametv_CustomerName)
    TextView tvCustomerNametvCustomerName;

    @BindView(R.id.tv_VerifyBtn)
    TextView tvVerifyBtn;
    @BindView(R.id.rel_detailCard)
    RelativeLayout relDetailCard;
    @BindView(R.id.card_view)
    RelativeLayout cardView;
    @BindView(R.id.recylerview_menu_listing)
    RecyclerView recylerviewMenuListing;
    @BindView(R.id.rel_SummaryCard)
    RelativeLayout relSummaryCard;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.tv_grandtotal)
    TextView tvGrandtotal;
    @BindView(R.id.tv_total_cost)
    TextView tvTotalCost;
    @BindView(R.id.card_view_summary)
    RelativeLayout cardViewSummary;
    @BindView(R.id.Verifybutton)
    Button Verifybutton;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    Context context;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.progressBar_cyclic)
    ProgressBar progressBarCyclic;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.tv_notes)
    EditText tvNotes;
    @BindView(R.id.tv_Tax)
    TextView tvTax;
    @BindView(R.id.tv_tax_cost)
    TextView tvTaxCost;
    @BindView(R.id.tv_OrderNumber_unbold)
    TextView tvOrderNumberUnbold;
    @BindView(R.id.tv_CustomerNametv_CustomerName_unBold)
    TextView tvCustomerNametvCustomerNameUnBold;

    @BindView(R.id.tv_otp_unBold)
    TextView tvOtpUnBold;
    @BindView(R.id.date_unBold)
    TextView dateUnBold;
    @BindView(R.id.tv_otp)
    TextView tvOtp;
    @BindView(R.id.tv_Date)
    TextView tvDate;

    @BindView(R.id.tv_CustomerName_unBold)
    TextView tvCustomerNameUnBold;
    @BindView(R.id.tittleimage)
    ImageView tittleimage;
    @BindView(R.id.tittle)
    TextView tittle;
    @BindView(R.id.toplayout)
    RelativeLayout toplayout;
    @BindView(R.id.middleimage)
    ImageView middleimage;
    @BindView(R.id.middletittle)
    TextView middletittle;
    @BindView(R.id.toplayout1)
    RelativeLayout toplayout1;
    @BindView(R.id.middlelayout)
    RelativeLayout middlelayout;
    @BindView(R.id.Bottomtittleimage)
    ImageView Bottomtittleimage;
    @BindView(R.id.bottomtittle)
    TextView bottomtittle;
    @BindView(R.id.bottomlayout)
    RelativeLayout bottomlayout;
    private Typeface font;
    ArrayList<Ordered> ordereds;
    Retrofit retroFitClient;
    String orderId;
    FindpartiOrder findpartiOrder;
    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    Float total = 0f;
    private Context mContext;
    DataOrderHistory dataOrderHistory;

    OrderDetailsAdapter orderDetailsAdapter;
    OnItemClickListener onItemClickListener;

    ArrayList<MenuItemData> menuItemDataArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appbar.setOutlineProvider(null);
        getSupportActionBar().setTitle("");
        context = this;
        onItemClickListener = this;
        sessionManager = new SessionManager(context);
        sharedPreferences = getSharedPreferences(sessionManager.PREF_NAME, 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        font = Typeface.createFromAsset(getAssets(), "fonts/opensansbold.ttf");
        menuItemDataArrayList = new ArrayList<MenuItemData>();
//        tvOrderDetails.setTypeface(font);
//        tvOrderSummary.setTypeface(font);
//        tvSpecialNotes.setTypeface(font);
        tvGrandtotal.setTypeface(font);
        tvCustomerName.setTypeface(font);
        tvTotalCost.setTypeface(font);
        Verifybutton.setTypeface(font);
        tvCustomerName.setTypeface(font);
        tvOrderNumber.setTypeface(font);
        tvOtp.setTypeface(font);
        tvCustomerNametvCustomerName.setTypeface(font);
        tvDate.setTypeface(font);

        Intent intent = getIntent();
        DataOrderHistory f1 = (DataOrderHistory) intent.getSerializableExtra("myClass");
        orderId = intent.getStringExtra("orderId");


        progressBarCyclic.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        retroFitClient = new RetroFitClient(context).getBlankRetrofit();
        Call<FindpartiOrder> call = retroFitClient
                .create(ApiIntegration.class)
                .getsingalOrder(sharedPreferences.getString("token", null), orderId);
        call.enqueue(new Callback<FindpartiOrder>() {

            @Override
            public void onResponse(Call<FindpartiOrder> call, Response<FindpartiOrder> response) {
                if (response != null) {
                    findpartiOrder = response.body();
                    if (findpartiOrder != null) {
                        if (findpartiOrder.getError().equals("true")) {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(context, findpartiOrder.getTitle(), Toast.LENGTH_SHORT).show();
                        } else {
                            mainLayout.setVisibility(View.VISIBLE);
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            if(isTablet(context))
                            {

                                Picasso.with(context)
                                        .load(findpartiOrder.getData()[0].getShopDetail().getImageurl()+"_large.jpg")
                                        .placeholder(R.drawable.cofeecup)
                                        .into(tittleimage);

                                Picasso.with(context)
                                        .load(findpartiOrder.getData()[0].getShopDetail().getImageurl()+"_large.jpg")
                                        .placeholder(R.drawable.cofeecup)
                                        .into(middleimage);

                                Picasso.with(context)
                                        .load(findpartiOrder.getData()[0].getShopDetail().getImageurl()+"_large.jpg")
                                        .placeholder(R.drawable.cofeecup)
                                        .into(Bottomtittleimage);


                            }
                            else
                            {

                                Picasso.with(context)
                                        .load(findpartiOrder.getData()[0].getShopDetail().getImageurl()+"_small.jpg")
                                        .placeholder(R.drawable.cofeecup)
                                        .into(tittleimage);

                                Picasso.with(context)
                                        .load(findpartiOrder.getData()[0].getShopDetail().getImageurl()+"_small.jpg")
                                        .placeholder(R.drawable.cofeecup)
                                        .into(middleimage);

                                Picasso.with(context)
                                        .load(findpartiOrder.getData()[0].getShopDetail().getImageurl()+"_small.jpg")
                                        .placeholder(R.drawable.cofeecup)
                                        .into(Bottomtittleimage);
                            };
                            tvCustomerName.setText(findpartiOrder.getData()[0].getShopDetail().getCafe_name());
                            tvOrderNumber.setText(findpartiOrder.getData()[0].getOrderId());
                            tvOtp.setText(findpartiOrder.getData()[0].getOtp());
                            DataFindpartiOrder dataFindpartiOrder = findpartiOrder.getData()[0];
                            if (findpartiOrder.getData()[0].getParcel().equals("false")) {
                                tvCustomerNametvCustomerName.setText("Sit In");
                            } else {
                                tvCustomerNametvCustomerName.setText("Pick Cup");
                            }
                            String date[] = findpartiOrder.getData()[0].getTimeForPickcup().split(" ");
                            String ogdate = date[0];
                            String ogTime = date[1];
                            tvDate.setText(ogdate);
                            Log.d("Time fs", findpartiOrder.getData()[0].getTimeForPickcup());

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

                             try {
                                 Date date2  = sdf.parse(findpartiOrder.getData()[0].getTimeForPickcup());
                                 sdf.setTimeZone(TimeZone.getDefault());
                                 String formattedDate = sdf.format(date2);
                                 Log.d("fmd", formattedDate);
                                 SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                SimpleDateFormat timesdf = new SimpleDateFormat("HH:mm:ss");
                                 Date myDate=sdf1.parse(formattedDate);
                                String timeString = timesdf.format(myDate);
                                String timeformate[] = timeString.split(":");
                                int hour = Integer.parseInt(timeformate[0]);
                                int minutes = Integer.parseInt(timeformate[1]);
                                String timeSet = "";
                                if (hour > 12) {
                                    hour -= 12;
                                    timeSet = "PM";
                                } else if (hour == 0) {
                                    hour += 12;
                                    timeSet = "AM";
                                } else if (hour == 12) {
                                    timeSet = "PM";
                                } else {
                                    timeSet = "AM";
                                }
                                if (minutes < 10) {

                                    time.setText(hour + ":0" + minutes + " " + timeSet);

                                } else {

                                    time.setText(hour + ":" + minutes + " " + timeSet);

                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


/*

                           String timeformate[]=ogTime.split(":");
                            int hour=Integer.parseInt(timeformate[0]);
                            int minutes=Integer.parseInt(timeformate[1]);
                            String timeSet = "";
                            if (hour > 12) {
                                hour -= 12;
                                timeSet = "PM";
                            } else if (hour == 0) {
                                hour += 12;
                                timeSet = "AM";
                            } else if (hour == 12){
                                timeSet = "PM";
                            }else{
                                timeSet = "AM";
                            }
                            if(minutes<10)
                            {

                                time.setText(hour + ":0" + minutes+" "+timeSet);

                            }
                            else {

                                time.setText(hour + ":" + minutes+" "+timeSet);

                            }
*/


                            tvTotalCost.setText("£ " + findpartiOrder.getData()[0].getTotalPrice());
                            tvNotes.setText(findpartiOrder.getData()[0].getNote());
                            ordereds = new ArrayList<Ordered>(Arrays.asList(dataFindpartiOrder.getOrdered()));
                            orderDetailsAdapter = new OrderDetailsAdapter(mContext, ordereds, onItemClickListener);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recylerviewMenuListing.setLayoutManager(mLayoutManager);
                            recylerviewMenuListing.setAdapter(orderDetailsAdapter);
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
            public void onFailure(Call<FindpartiOrder> call, Throwable t) {
                progressBarCyclic.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();

            }
        });


    }
    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void OnItemClickListener(Cafes item) {

    }

    @Override
    public void voidOnAddCart(ItemData itemData) {

    }

    @Override
    public void totalPrice(String total) {
        this.total = this.total + Float.valueOf(total);
        tvTaxCost.setText("£" + " " + getCorrectValue(String.format("%.2f", Float.valueOf(findpartiOrder.getData()[0].getTotalPrice()) - this.total)));

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

    public String getCorrectValue(String price) {
        String[] priceSpl = price.split("\\.");
        if (priceSpl.length > 1)
            if (priceSpl[1].equals("00") || priceSpl[1].equals("0")) {
                price = priceSpl[0];
            }
        return price;
    }
}
