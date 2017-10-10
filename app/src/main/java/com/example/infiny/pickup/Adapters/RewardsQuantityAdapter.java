package com.example.infiny.pickup.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.infiny.pickup.R;

import java.util.ArrayList;

/**
 * Created by infiny on 10/10/17.
 */

public class RewardsQuantityAdapter extends RecyclerView.Adapter<RewardsQuantityAdapter.MyViewHolder> {
    Context context;
   Integer quantity;
    String completed;

    public RewardsQuantityAdapter(Context context, Integer quantity, String completed) {
        this.context = context;
        this.quantity = quantity;
        this.completed = completed;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rewardquantity, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(Integer.parseInt(completed)>position)
        {
            holder.r1.setImageResource(R.drawable.filledcup);
        }

    }

    @Override
    public int getItemCount() {
        return quantity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView r1,r2,r3,r4,r5;
        public MyViewHolder(View itemView) {
            super(itemView);
            r1=(ImageView)itemView.findViewById(R.id.rating1);
        }
    }
}
