package com.example.infiny.pickup.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.infiny.pickup.Helpers.Order_History_Helpers;
import com.example.infiny.pickup.R;

import java.util.ArrayList;

/**
 * Created by infiny on 9/12/17.
 */

public class Order_History_Adapter  extends RecyclerView.Adapter<Order_History_Adapter.MyViewHolder>  {
    Context context;
    ArrayList<Order_History_Helpers> partyName;



    public Order_History_Adapter(Context  context, ArrayList<Order_History_Helpers> partyName) {
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

        Order_History_Helpers f1=partyName.get(position);
        holder.partyName.setText(f1.getPartyname());
        holder.imageView.setImageResource(f1.getImage());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OrderDetailsActivity.class);
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
        TextView partyName;
        ImageView imageView;


        public MyViewHolder(View itemView) {
            super(itemView);
            partyName=(TextView)itemView.findViewById(R.id.tittle);
            imageView=(ImageView)itemView.findViewById(R.id.tittleimage);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}
