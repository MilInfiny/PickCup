package com.example.infiny.pickup.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;


import com.example.infiny.pickup.Helpers.Drinks;
import com.example.infiny.pickup.Model.Ordered;
import com.example.infiny.pickup.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class Sub_Menu_Adapter extends RecyclerView.Adapter<Sub_Menu_Adapter.MyViewHolder> {
    Context context;
  ArrayList<Ordered> ordereds;
    int pickcupimage;
    int quantity=1;

    public Sub_Menu_Adapter(Context context, ArrayList<Ordered> ordereds,int pickcupimage ) {
        this.context = context;
        this.ordereds = ordereds;
        this.pickcupimage=pickcupimage;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_manu_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Ordered s=ordereds.get(position);
        holder.tw_itemName.setText(s.getItemName());
        holder.tw_size.setText(s.getItemSize());
        holder.itemimg.setImageResource(pickcupimage);
        quantity=Integer.parseInt(s.getItemQuantity());
        if(quantity ==1)
        {
            holder.decrement.setImageResource(R.drawable.ic_remove_circle_grey_400_24dp);
        }
        else{
            holder.decrement.setImageResource(R.drawable.ic_remove_circle_black_24dp);
        }

        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity ==1)
                {

                }
                else  if(quantity ==2){
                    holder.decrement.setImageResource(R.drawable.ic_remove_circle_grey_400_24dp);
                    quantity--;
                    holder.display.setText(String.valueOf(quantity));
                }
                else{
                    quantity--;
                    holder.display.setText(String.valueOf(quantity));
                }

            }
        });
        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                holder.decrement.setImageResource(R.drawable.ic_remove_circle_black_24dp);
                holder.display.setText(String.valueOf(quantity));

            }
        });

        holder.display.setText(String.valueOf(quantity));

    }


    @Override
    public int getItemCount() {
        return ordereds.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tw_size,tw_itemName,display;
        ImageView itemimg,increment,decrement;


        public MyViewHolder(View itemView) {
            super(itemView);
            tw_size=(TextView)itemView.findViewById(R.id.tw_small);
            tw_itemName=(TextView)itemView.findViewById(R.id.tw_itemname);
            increment=(ImageView) itemView.findViewById(R.id.increment);
            decrement=(ImageView)itemView.findViewById(R.id.decrement);
            itemimg=(ImageView)itemView.findViewById(R.id.iv_itemimage);
            display=(TextView)itemView.findViewById(R.id.display);

        }
    }
}
