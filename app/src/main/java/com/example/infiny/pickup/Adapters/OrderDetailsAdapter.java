package com.example.infiny.pickup.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.infiny.pickup.Helpers.MenuItemData;
import com.example.infiny.pickup.R;

import java.util.ArrayList;

/**
 * Created by infiny on 9/13/17.
 */

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailHolder>{

    Context mContext;
    ArrayList<MenuItemData> menuItemDataArrayList;

    public OrderDetailsAdapter(Context mContext, ArrayList<MenuItemData> menuItemDataArrayList) {
        this.mContext=mContext;
        this.menuItemDataArrayList=menuItemDataArrayList;
    }

    @Override
    public OrderDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_order_details, parent, false);

        return new OrderDetailsAdapter.OrderDetailHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderDetailHolder holder, int position) {

        if (position==0){
            holder.tv_price.setText("£ 16.00");
        }else if(position==1){
            holder.tv_item_name.setText("Latte (Small)");
            holder.tv_cost_per_item.setText("£ 2.00");
            holder.tv_quantity.setText("1");
            holder.tv_price.setText("£ 2.00");
        }else if (position==2){
            holder.tv_item_name.setText("Brownie");
            holder.tv_cost_per_item.setText("£ 3.00");
            holder.tv_quantity.setText("3");
            holder.tv_price.setText("£ 9.00");
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class OrderDetailHolder extends RecyclerView.ViewHolder {

        TextView tv_cost_per_item,tv_item_name,tv_quantity,tv_price;
        public OrderDetailHolder(View itemView) {
            super(itemView);
            tv_cost_per_item= (TextView) itemView.findViewById(R.id.tv_cost_per_item);
            tv_item_name= (TextView) itemView.findViewById(R.id.tv_item_name);
            tv_quantity= (TextView) itemView.findViewById(R.id.tv_quantity);
            tv_price= (TextView) itemView.findViewById(R.id.tv_price);
        }
    }
}
