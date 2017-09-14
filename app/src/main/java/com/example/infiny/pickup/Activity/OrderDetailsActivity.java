package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.infiny.pickup.Adapters.OrderDetailsAdapter;
import com.example.infiny.pickup.Helpers.MenuItemData;
import com.example.infiny.pickup.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private Typeface font;
    private Context mContext;

    OrderDetailsAdapter orderDetailsAdapter;

    ArrayList<MenuItemData> menuItemDataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        font = Typeface.createFromAsset(getAssets(), "fonts/opensansbold.ttf");
        menuItemDataArrayList = new ArrayList<MenuItemData>();
//        tvOrderDetails.setTypeface(font);
//        tvOrderSummary.setTypeface(font);
//        tvSpecialNotes.setTypeface(font);
        tvGrandtotal.setTypeface(font);
        tvTotalCost.setTypeface(font);

        Verifybutton.setTypeface(font);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerviewMenuListing.setLayoutManager(layoutManager);
        recylerviewMenuListing.setItemAnimator(new DefaultItemAnimator());


        orderDetailsAdapter = new OrderDetailsAdapter(mContext, menuItemDataArrayList);

        recylerviewMenuListing.setAdapter(orderDetailsAdapter);

    }

}
