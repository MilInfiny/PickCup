

package com.example.infiny.pickup.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
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
                    .load(dataNotification.getShopDetail().getImageurl()+"_large.png")
                    .placeholder(R.drawable.ic_person_black_48dp)
                    .into(holder.imageView);

        }
        else
        {

            Picasso.with(context)
                    .load(dataNotification.getShopDetail().getImageurl()+"_small.png")
                    .placeholder(R.drawable.ic_person_black_48dp)
                    .into(holder.imageView);


        };

        holder.textView.setText(dataNotification.getMessage());
        holder.posteddate.setText(dataNotification.getCreatedAt());
        String date[] = dataNotification.getCreatedAt().split("T");
        String ogdate = date[0];
        holder.posteddate.setText(ogdate);
        holder.topview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        public boolean isTablet(Context context) {
            boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
            boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
            return (xlarge || large);
        }
    }
}
