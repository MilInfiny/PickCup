package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.Adapters.OrderDetailsAdapter;
import com.example.infiny.pickup.Helpers.MenuItemData;
import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Model.DataFindpartiOrder;
import com.example.infiny.pickup.Model.DataOrderHistory;
import com.example.infiny.pickup.Model.FindpartiOrder;
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

public class OrderDetailsActivity extends AppCompatActivity {


    @BindView(R.id.tv_order_details)
    TextView tvOrderDetails;
    @BindView(R.id.tv_CustomerName)
    TextView tvCustomerName;
    @BindView(R.id.tv_TotalCostOrder)
    TextView tvTotalCostOrder;
    @BindView(R.id.tv_OrderNumber)
    TextView tvOrderNumber;
    @BindView(R.id.tv_CustomerNametv_CustomerName)
    TextView tvCustomerNametvCustomerName;
    @BindView(R.id.tv_otp)
    TextView tvOtp;
    @BindView(R.id.tv_VerifyBtn)
    TextView tvVerifyBtn;
    @BindView(R.id.rel_detailCard)
    RelativeLayout relDetailCard;
    @BindView(R.id.card_view)
    RelativeLayout cardView;
    @BindView(R.id.tv_order_summary)
    TextView tvOrderSummary;
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
    @BindView(R.id.tv_special_notes)
    TextView tvSpecialNotes;
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
    private Typeface font;
    ArrayList<Ordered> ordereds;
    Retrofit retroFitClient;
    String orderId;
    FindpartiOrder findpartiOrder;
    SessionManager sessionManager;
    SharedPreferences sharedPreferences;

    private Context mContext;
    DataOrderHistory dataOrderHistory;

    OrderDetailsAdapter orderDetailsAdapter;

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
        sessionManager = new SessionManager(context);
        sharedPreferences = getSharedPreferences(sessionManager.PREF_NAME, 0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        font = Typeface.createFromAsset(getAssets(), "fonts/opensansbold.ttf");
        menuItemDataArrayList = new ArrayList<MenuItemData>();
//        tvOrderDetails.setTypeface(font);
//        tvOrderSummary.setTypeface(font);
//        tvSpecialNotes.setTypeface(font);

        tvGrandtotal.setTypeface(font);
        tvTotalCost.setTypeface(font);
        Verifybutton.setTypeface(font);
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
                            tvCustomerName.setText(findpartiOrder.getData()[0].getShopDetail().getCafe_name());
                            tvOrderNumber.setText("Order#: " + findpartiOrder.getData()[0].getOrderId());
                            tvOtp.setText("OTP: " + findpartiOrder.getData()[0].getOtp());
                            DataFindpartiOrder dataFindpartiOrder = findpartiOrder.getData()[0];
                            if (findpartiOrder.getData()[0].getParcel().equals("false")) {
                                tvCustomerNametvCustomerName.setText("Order Type: Sit In");
                            } else {
                                tvCustomerNametvCustomerName.setText("Order Type: Pick Cup");
                            }
                            String date[] = findpartiOrder.getData()[0].getCreatedAt().split("T");
                            String ogdate = date[0];
                            time.setText(ogdate);
                            tvSpecialNotes.setText(findpartiOrder.getData()[0].getNote());

                            tvTotalCost.setText("£ " + findpartiOrder.getData()[0].getTotalPrice());
                            tvNotes.setText(findpartiOrder.getData()[0].getNote());
                            ordereds = new ArrayList<Ordered>(Arrays.asList(dataFindpartiOrder.getOrdered()));
                            orderDetailsAdapter = new OrderDetailsAdapter(mContext, ordereds);
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
    protected void onStop() {
        super.onStop();
    }
}
