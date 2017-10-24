package com.example.infiny.pickup.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.infiny.pickup.Activity.OrderDetailsActivity;
import com.example.infiny.pickup.Activity.order_history;
import com.example.infiny.pickup.Helpers.OrderHIstoryEvent;
import com.example.infiny.pickup.Helpers.Order_History_Helpers;
import com.example.infiny.pickup.Model.DataOrderHistory;
import com.example.infiny.pickup.Model.Order_History_Data;
import com.example.infiny.pickup.Model.Ordered;
import com.example.infiny.pickup.R;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * Created by infiny on 9/12/17.
 */

public class Order_History_Adapter  extends RecyclerView.Adapter<Order_History_Adapter.MyViewHolder>  {
    Context context;
    ArrayList<DataOrderHistory> partyName;
    ArrayList<Ordered> ordereds;



    public Order_History_Adapter(Context  context, ArrayList<DataOrderHistory> partyName) {
        this.context = context;
        this.partyName = partyName;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_history_adapter, parent, false);
        return new Order_History_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final DataOrderHistory f1=partyName.get(position);

        holder.partyName.setText(f1.getShopDetail().getCafe_name());
        holder.tw_orderno.setText(f1.getOrderId());
        holder.tw_total.setText("£ "+f1.getTotalPrice());
        holder.tw_otp.setText("OTP: "+f1.getOtp());
        if(f1.getParcel().equals("false"))
        {
            holder.type.setText("Order Type: Sit In");
        }
        else
        {
            holder.type.setText("Order Type: Pick Cup");
        }
        String date[]=f1.getCreatedAt().split("T");
        String ogdate=date[0];
        holder.tw_date.setText(ogdate);
        ordereds=new ArrayList<>(Arrays.asList(f1.getOrdered()));

        if(holder.isTablet(context))
        {

            Picasso.with(context)
                    .load(f1.getShopDetail().getImageurl()+"_large.png")
                    .placeholder(R.drawable.cofeecup)
                    .into(holder.imageView);

        }
        else
        {

            Picasso.with(context)
                    .load(f1.getShopDetail().getImageurl()+"_small.png")
                    .placeholder(R.drawable.cofeecup)
                    .into(holder.imageView);


        };

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OrderDetailsActivity.class);
                intent.putExtra("myClass", (Parcelable) f1);
                intent.putExtra("cafename",f1.getShopDetail().getCafe_name());
                intent.putExtra("orderId",f1.get_id());
                intent.putExtra("TotalPrice",f1.getTotalPrice());
                intent.putExtra("otp",f1.getOtp());
                intent.putExtra("parcel",f1.getParcel());
                intent.putExtra("date",f1.getCreatedAt());
                intent.putExtra("ordered",ordereds);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return partyName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView partyName,tw_orderno,tw_total,tw_otp,type,tw_date;
        ImageView imageView;


        public MyViewHolder(View itemView) {
            super(itemView);
            partyName=(TextView)itemView.findViewById(R.id.tittle);
            imageView=(ImageView)itemView.findViewById(R.id.tittleimage);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.relativeLayout);
            tw_orderno=(TextView)itemView.findViewById(R.id.tw_orderno);
            tw_total=(TextView)itemView.findViewById(R.id.tw_total);
            tw_otp=(TextView)itemView.findViewById(R.id.tw_otp);
            type=(TextView)itemView.findViewById(R.id.type);
            tw_date=(TextView)itemView.findViewById(R.id.tw_date);

        }
        public boolean isTablet(Context context) {
            boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
            boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
            return (xlarge || large);
        }

    }
}
