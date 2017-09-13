/*
package com.example.infiny.pickup.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.infiny.pickup.Helpers.Order_History_Helpers;
import com.example.infiny.pickup.Helpers.Orders;
import com.example.infiny.pickup.R;

import java.util.ArrayList;

*/
/**
 * Created by infiny on 9/12/17.
 *//*


public class Order_History_Details_Adapter extends RecyclerView.Adapter<Order_History_Details_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Orders> Order_History_Helpers ;

    public Order_History_Details_Adapter(Context context, ArrayList<Orders> Order_History_Helpers ) {
        this.context = context;
        this.Order_History_Helpers = Order_History_Helpers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_history_details, parent, false);
        return new Order_History_Details_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Orders orders=Order_History_Helpers.get(position);
        holder.itemname.setText(orders.getOrdername());
        holder.price.setText(orders.getPrice());
        holder.qty.setText(orders.getQuantity());

    }

    @Override
    public int getItemCount() {
        return Order_History_Helpers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemname,price,qty;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemname=(TextView)itemView.findViewById(R.id.tw_item);
            price=(TextView)itemView.findViewById(R.id.tw_prize);
            qty=(TextView)itemView.findViewById(R.id.tw_quantity);


        }
    }
}
*/
