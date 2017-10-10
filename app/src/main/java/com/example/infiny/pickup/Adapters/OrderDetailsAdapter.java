package com.example.infiny.pickup.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.infiny.pickup.Helpers.MenuItemData;
import com.example.infiny.pickup.Model.Data;
import com.example.infiny.pickup.Model.DataFindpartiOrder;
import com.example.infiny.pickup.Model.Ordered;
import com.example.infiny.pickup.R;

import java.util.ArrayList;

/**
 * Created by infiny on 9/13/17.
 */

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailHolder>{

    Context mContext;
    ArrayList<Ordered> menuItemDataArrayList;

    public OrderDetailsAdapter(Context mContext,  ArrayList<Ordered> menuItemDataArrayList) {
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
            Ordered data=menuItemDataArrayList.get(position);
            holder.tv_item_name.setText(data.getItemName());
            holder.tv_cost_per_item.setText("£ " +data.getItemPrice());
            holder.tv_quantity.setText(data.getItemQuantity());
            Float total=Float.valueOf(data.getItemPrice())* Float.valueOf(data.getItemQuantity());
            holder.tv_price.setText("£ " +String.valueOf(total));

    }

    @Override
    public int getItemCount() {
        return menuItemDataArrayList.size();
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
    public String getCorrectValue(String price) {
        String[] priceSpl = price.split("\\.");
        if (priceSpl.length > 1)
            if (priceSpl[1].equals("00") || priceSpl[1].equals("0")) {
                price = priceSpl[0];
            }
        return price;
    }
}
