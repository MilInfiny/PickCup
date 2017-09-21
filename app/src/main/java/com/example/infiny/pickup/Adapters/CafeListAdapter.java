package com.example.infiny.pickup.Adapters;

import android.content.Context;
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

/**
 * Created by infiny on 9/4/17.
 */

public class CafeListAdapter extends RecyclerView.Adapter<CafeListAdapter.MyViewHolder> {
    Context context;
    ArrayList<Cafes> list;
    private final OnItemClickListener listener;


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
        Cafes cafes=list.get(position);

        holder.bind(cafes, listener);
        if(cafes.getStatus().equals("ready"))
        {
            holder.status.setBackground(context.getResources().getDrawable(R.drawable.button_bg_round_green));
        }
        if(cafes.getStatus().equals("offline"))
        {
            holder.status.setBackground(context.getResources().getDrawable(R.drawable.button_bg_round_red));
        }
        if(cafes.getStatus().equals("await"))
        {
            holder.status.setBackground(context.getResources().getDrawable(R.drawable.button_bg_round_yellow));

        }
        Picasso.with(context)
                .load(cafes.getImageurl())
                .placeholder(R.drawable.cofeecup)
                .into(holder.bgimage);
        holder.tittle.setText(cafes.getCafe_name());
        if(cafes.getRating().equals("1"))
        {
            holder.r1.setVisibility(View.VISIBLE);
        }
        if(cafes.getRating().equals("2"))
        {
            holder.r1.setVisibility(View.VISIBLE);
            holder.r2.setVisibility(View.VISIBLE);
        }
        if(cafes.getRating().equals("3"))
        {
            holder.r1.setVisibility(View.VISIBLE);
            holder.r2.setVisibility(View.VISIBLE);
            holder.r3.setVisibility(View.VISIBLE);
        }
        if(cafes.getRating().equals("4"))
        {
            holder.r1.setVisibility(View.VISIBLE);
            holder.r2.setVisibility(View.VISIBLE);
            holder.r3.setVisibility(View.VISIBLE);
            holder.r4.setVisibility(View.VISIBLE);
        }
        if(cafes.getRating().equals("5"))
        {
            holder.r1.setVisibility(View.VISIBLE);
            holder.r2.setVisibility(View.VISIBLE);
            holder.r3.setVisibility(View.VISIBLE);
            holder.r4.setVisibility(View.VISIBLE);
            holder.r5.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tittle;
        ImageView bgimage,r1,r2,r3,r4,r5;
        Button status;
        FrameLayout framelayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            tittle=(TextView) itemView.findViewById(R.id.tittle);
            framelayout=(FrameLayout)itemView.findViewById(R.id.Framelayout);
            bgimage=(ImageView)itemView.findViewById(R.id.bgimage);
            status=(Button)itemView.findViewById(R.id.status_button);
            r5=(ImageView)itemView.findViewById(R.id.rating1);
            r4=(ImageView)itemView.findViewById(R.id.rating2);
            r3=(ImageView)itemView.findViewById(R.id.rating3);
            r2=(ImageView)itemView.findViewById(R.id.rating4);
            r1=(ImageView)itemView.findViewById(R.id.rating5);



        }
        public void bind(final Cafes item, final OnItemClickListener listener) {
            framelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClickListener(item);
                }
            });

        }
    }
}
