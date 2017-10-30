

package com.example.infiny.pickup.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.infiny.pickup.Activity.OrderDetailsActivity;
import com.example.infiny.pickup.Helpers.CafeLIstingHelpers;
import com.example.infiny.pickup.Model.DataNotification;
import com.example.infiny.pickup.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by infiny on 9/13/17.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    Context context;
    ArrayList<DataNotification> dataNotifications;

    public NotificationAdapter(Context context, ArrayList<DataNotification> dataNotifications) {
        this.context = context;
        this.dataNotifications = dataNotifications;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_layout, parent, false);
        return new NotificationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DataNotification dataNotification=dataNotifications.get(position);

        if(holder.isTablet(context))
        {

            Picasso.with(context)
                    .load(dataNotification.getShopDetail().getImageurl()+"_large.jpg")
                    .placeholder(R.drawable.cofeecup)
                    .into(holder.imageView);

        }
        else
        {

            Picasso.with(context)
                    .load(dataNotification.getShopDetail().getImageurl()+"_small.jpg")
                    .placeholder(R.drawable.cofeecup)
                    .into(holder.imageView);


        };







        holder.textView.setText(dataNotification.getMessage());
        final SpannableStringBuilder sb = new SpannableStringBuilder(dataNotification.getMessage());
        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        int  startstringPosition =dataNotification.getMessage().indexOf(dataNotification.getShopDetail().getCafe_name());
        int  stringPositionlenght =dataNotification.getShopDetail().getCafe_name().length();
        int endstringPosition=startstringPosition+stringPositionlenght;

        sb.setSpan(bss, startstringPosition, endstringPosition, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold

        holder.textView.setText(sb);



        holder.posteddate.setText(dataNotification.getCreatedAt());
        String date[] = dataNotification.getCreatedAt().split("T");
        String ogdate = date[0];
        holder.posteddate.setText(ogdate);
        holder.topview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,OrderDetailsActivity.class);
                intent.putExtra("orderId",dataNotification.getOrderId());
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return dataNotifications.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView,posteddate;
        RelativeLayout img;
        ImageView imageView;
        RelativeLayout topview;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.notificationHeading);
            img=(RelativeLayout) itemView.findViewById(R.id.img);
            imageView=(ImageView)itemView.findViewById(R.id.img1);
            posteddate=(TextView)itemView.findViewById(R.id.posteddate);
            topview=(RelativeLayout)itemView.findViewById(R.id.topview);

        }
        int getIndex(String str, String substring)
        {
            return Arrays.asList(str.split("\\s+")).indexOf(substring)+1;
        }
        public boolean isTablet(Context context) {
            boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
            boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
            return (xlarge || large);
        }
    }
}
