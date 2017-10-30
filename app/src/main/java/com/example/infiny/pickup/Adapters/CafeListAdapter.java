package com.example.infiny.pickup.Adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.infiny.pickup.Helpers.CafeLIstingHelpers;
import com.example.infiny.pickup.Interfaces.OnItemClickListener;
import com.example.infiny.pickup.Model.Cafes;
import com.example.infiny.pickup.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;

import static com.example.infiny.pickup.R.drawable.ic_person_black_48dp;

/**
 * Created by infiny on 9/4/17.
 */

public class CafeListAdapter extends RecyclerView.Adapter<CafeListAdapter.MyViewHolder> {
    Context context;
    ArrayList<Cafes> list;
    private final OnItemClickListener listener;
    private Typeface font;


    public CafeListAdapter(Context context, ArrayList<Cafes> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cafelistinglayout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        font = Typeface.createFromAsset(context.getAssets(), "fonts/opensansbold.ttf");
        Cafes cafes=list.get(position);
        holder.tittle.setTypeface(font);
        holder.tittle.setText(cafes.getCafe_name());
        holder.bind(cafes, listener);
        if(cafes.getStatus().equals("ready"))
        {
            holder.status.setBackground(context.getResources().getDrawable(R.drawable.button_bg_round_green));
        }
        if(cafes.getStatus().equals("closed"))
        {
            holder.status.setBackground(context.getResources().getDrawable(R.drawable.button_bg_round_red));
        }
        if(cafes.getStatus().equals("busy"))
        {
            holder.status.setBackground(context.getResources().getDrawable(R.drawable.button_bg_round_yellow));

        };

        if(holder.isTablet(context))
        {

            Picasso.with(context)
                    .load(cafes.getImageurl()+"_large.jpg")
                    .placeholder(R.drawable.cofeecup)
                    .into(holder.bgimage);


        }
        else
        {

            Picasso.with(context)
                    .load(cafes.getImageurl()+"_small.jpg")
                    .placeholder(R.drawable.cofeecup)
                    .into(holder.bgimage);



        };


            if (cafes.getRewardCompleted() == null) {
                holder.rewarddetails.setText("0" + "/" + cafes.getRewardQuan());
            } else {
                holder.rewarddetails.setText(cafes.getRewardCompleted() + "/" + cafes.getRewardQuan());
            }
        }





    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tittle,rewarddetails;
        ImageView bgimage;
        Button status;
        FrameLayout framelayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            tittle=(TextView) itemView.findViewById(R.id.tittle);
            framelayout=(FrameLayout)itemView.findViewById(R.id.Framelayout);
            bgimage=(ImageView)itemView.findViewById(R.id.bgimage);
            status=(Button)itemView.findViewById(R.id.status_button);
            rewarddetails=(TextView)itemView.findViewById(R.id.rewarddetails);




        }
        public void bind(final Cafes item, final OnItemClickListener listener) {
            framelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClickListener(item);
                }
            });

        }
        public boolean isTablet(Context context) {
            boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
            boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
            return (xlarge || large);
        }

    }
}
