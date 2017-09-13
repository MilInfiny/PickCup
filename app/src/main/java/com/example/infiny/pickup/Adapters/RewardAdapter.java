package com.example.infiny.pickup.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.infiny.pickup.Helpers.CafeLIstingHelpers;
import com.example.infiny.pickup.R;

import java.util.ArrayList;

/**
 * Created by infiny on 9/9/17.
 */

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.MyViewHolder> {
    Context context;
    ArrayList<CafeLIstingHelpers> partyName;

    public RewardAdapter(Context context, ArrayList<CafeLIstingHelpers> partyName) {
        this.context = context;
        this.partyName = partyName;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rewardlistlayout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        CafeLIstingHelpers f1=partyName.get(position);
        holder.partyName.setText(f1.getPartyname());
        holder.imageView.setImageResource(f1.getImage());
        if(f1.getStatus()=="1")
        {
         holder.r1.setVisibility(View.VISIBLE);
        }
        if(f1.getStatus()=="2")
        {
            holder.r1.setVisibility(View.VISIBLE);
            holder.r2.setVisibility(View.VISIBLE);
        }
        if(f1.getStatus()=="3")
        {
            holder.r1.setVisibility(View.VISIBLE);
            holder.r2.setVisibility(View.VISIBLE);
            holder.r3.setVisibility(View.VISIBLE);
        }
        if(f1.getStatus()=="4")
        {
            holder.r1.setVisibility(View.VISIBLE);
            holder.r2.setVisibility(View.VISIBLE);
            holder.r3.setVisibility(View.VISIBLE);
            holder.r4.setVisibility(View.VISIBLE);
        }
        if(f1.getStatus()=="5")
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
        return partyName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView partyName;
        ImageView imageView,r1,r2,r3,r4,r5;
        RecyclerView recyclerView;
        public MyViewHolder(View itemView) {
            super(itemView);
            partyName=(TextView)itemView.findViewById(R.id.tittle);
            imageView=(ImageView)itemView.findViewById(R.id.tittleimage);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.toplayout);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.recycleView);
            r1=(ImageView)itemView.findViewById(R.id.rating1);
            r2=(ImageView)itemView.findViewById(R.id.rating2);
            r3=(ImageView)itemView.findViewById(R.id.rating3);
            r4=(ImageView)itemView.findViewById(R.id.rating4);
            r5=(ImageView)itemView.findViewById(R.id.rating5);
        }
    }
}
