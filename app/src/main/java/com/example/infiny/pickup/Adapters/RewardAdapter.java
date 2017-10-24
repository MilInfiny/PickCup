package com.example.infiny.pickup.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.infiny.pickup.Activity.OrderActivity;
import com.example.infiny.pickup.Helpers.CafeLIstingHelpers;
import com.example.infiny.pickup.Model.Data;
import com.example.infiny.pickup.Model.DataRewards;
import com.example.infiny.pickup.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by infiny on 9/9/17.
 */

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.MyViewHolder> {
    Context context;
    ArrayList<DataRewards> partyName;
    ArrayList<Integer> quantities;
    RewardsQuantityAdapter rewardsQuantityAdapter;

    public RewardAdapter(Context context, ArrayList<DataRewards> partyName) {
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

        final DataRewards f1=partyName.get(position);

        if(holder.isTablet(context))
        {
            Picasso.with(context)
                    .invalidate(f1.getImageurl()+"_large.png");
            Picasso.with(context)
                    .load(f1.getImageurl()+"_large.png")
                    .placeholder(R.drawable.ic_person_black_48dp)
                    .into(holder.imageView);

        }
        else
        {
            Picasso.with(context)
                    .invalidate(f1.getImageurl()+"_small.png");
            Picasso.with(context)
                    .load(f1.getImageurl()+"_small.png")
                    .placeholder(R.drawable.ic_person_black_48dp)
                    .into(holder.imageView);


        };

        if(Integer.parseInt(f1.getRewardCompleted())>=Integer.parseInt(f1.getQuantity()))
        {
            holder.claim.setVisibility(View.VISIBLE);
            holder.recyclerView.setVisibility(View.GONE);
            holder.tv_rewardsdetail.setText(f1.getQuantity()+"/"+f1.getQuantity());
            holder.tv_info.setText("You are ready to cliam ");
        }
        else {
            int leftrewards=Integer.parseInt(f1.getQuantity())-Integer.parseInt(f1.getRewardCompleted());
            holder.tv_info.setText("You are "+String.valueOf(leftrewards)+" order away from Reward");
            holder.tv_rewardsdetail.setText(f1.getRewardCompleted()+"/"+f1.getQuantity());
            int numberOfColumns = 6;
            rewardsQuantityAdapter = new RewardsQuantityAdapter(context, Integer.parseInt(f1.getQuantity()), f1.getRewardCompleted());
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, numberOfColumns);
            holder.recyclerView.setLayoutManager(mLayoutManager);
            holder.recyclerView.setAdapter(rewardsQuantityAdapter);
            holder.recyclerView.setNestedScrollingEnabled(false);

        }

        holder.claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, OrderActivity.class);
                intent.putExtra("fromPage","rewardActivity");
                intent.putExtra("sid",f1.getCafe_id());
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return partyName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView partyName,tv_rewardsdetail,tv_info;
        ImageView imageView,r1,r2,r3,r4,r5;
        RecyclerView recyclerView;
        Button claim;
        public MyViewHolder(View itemView) {
            super(itemView);
            partyName=(TextView)itemView.findViewById(R.id.tittle);
            imageView=(ImageView)itemView.findViewById(R.id.tittleimage);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.toplayout);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.quantityRecycleView);
            tv_rewardsdetail=(TextView)itemView.findViewById(R.id.rewarddetails);
            tv_info=(TextView)itemView.findViewById(R.id.tv_info);
            claim=(Button)itemView.findViewById(R.id.bt_claim);
        }
        public boolean isTablet(Context context) {
            boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
            boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
            return (xlarge || large);
        }
    }
}
