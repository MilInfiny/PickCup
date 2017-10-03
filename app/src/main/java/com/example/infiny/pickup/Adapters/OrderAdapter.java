package com.example.infiny.pickup.Adapters;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.infiny.pickup.Activity.OrderActivity;
import com.example.infiny.pickup.Helpers.Drinks;
import com.example.infiny.pickup.Helpers.Menu;
import com.example.infiny.pickup.Interfaces.OnItemClickListener;
import com.example.infiny.pickup.Model.Cafes;
import com.example.infiny.pickup.Model.ItemData;
import com.example.infiny.pickup.Model.OrderData;
import com.example.infiny.pickup.Model.Ordered;
import com.example.infiny.pickup.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by infiny on 9/8/17.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    Context context;
    ArrayList<OrderData> orderDatas;
    int image ;
    Sub_Menu_Adapter subMenuAdapter;
    OnItemClickListener onItemClickListener;


    public OrderAdapter(Context context, ArrayList<OrderData> orderDatas,OnItemClickListener onItemClickListener) {
        this.context = context;
        this.orderDatas = orderDatas;
        this.onItemClickListener=onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderlyout, parent, false);
        return new OrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final OrderData orderData=orderDatas.get(position);
        image = R.drawable.graycup1;
        holder.mailnlayout.setVisibility(View.VISIBLE);
        OrderActivity.note=holder.et_note.getText().toString().trim();
        if(holder.subLayout.getVisibility()==View.VISIBLE) {
            holder.subLayout.setVisibility(View.GONE);
            holder.arrow.setRotation(360);
        }

        else{
            holder.subLayout.setVisibility(View.VISIBLE);
            holder.arrow.setRotation(180);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());
            ArrayList<Ordered> ordereds = new ArrayList<Ordered>(Arrays.asList(orderData.getOrdered()));
            Ordered [] ordered = orderData.getOrdered();
            onItemClickListener.ordereddata(ordered);
            subMenuAdapter=new Sub_Menu_Adapter(context,ordereds,image,new OnItemClickListener(){

                @Override
                public void OnItemClickListener(Cafes item) {

                }

                @Override
                public void voidOnAddCart(ItemData itemData) {

                }

                @Override
                public void totalPrice(String total) {
                    holder.totalprice.setText(holder.getCorrectValue(String.format("%.2f", Float.valueOf(total))));
                    onItemClickListener.totalPrice(total);
                }

                @Override
                public void ordereddata(Ordered[] ordered) {


                }


            },orderData.getShopDetail().get_id());
            holder.recyclerView.setLayoutManager(mLayoutManager);
            holder.recyclerView.setAdapter(subMenuAdapter);

            holder.recyclerView.setNestedScrollingEnabled(false);
        }

        holder.mailnlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.subLayout.getVisibility()==View.VISIBLE) {
                    holder.subLayout.setVisibility(View.GONE);
                    holder.arrow.setRotation(360);
                }

                else{
                    holder.subLayout.setVisibility(View.VISIBLE);
                    holder.arrow.setRotation(180);

                }

            }
        });
        holder.sitin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image = R.drawable.graycup1;
                OrderActivity.parcel="false";
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());

                ArrayList<Ordered> ordereds = new ArrayList<Ordered>(Arrays.asList(orderData.getOrdered()));

                 subMenuAdapter=new Sub_Menu_Adapter(context,ordereds,image,new OnItemClickListener(){

                    @Override
                    public void OnItemClickListener(Cafes item) {

                    }

                    @Override
                    public void voidOnAddCart(ItemData itemData) {

                    }

                    @Override
                    public void totalPrice(String total) {
                        holder.totalprice.setText(holder.getCorrectValue(String.format("%.2f", Float.valueOf(total))));
                        onItemClickListener.totalPrice(total);

                    }

                     @Override
                     public void ordereddata(Ordered[] ordered) {

                     }


                 },orderData.getShopDetail().get_id());
                holder.recyclerView.setLayoutManager(mLayoutManager);
                holder.recyclerView.setAdapter(subMenuAdapter);
                mLayoutManager.scrollToPosition(getItemCount());
                holder.recyclerView.setNestedScrollingEnabled(false);

            }
        });

        holder.pickcup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image=R.drawable.cuptake;
                OrderActivity.parcel="true";
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());
                ArrayList<Ordered> ordereds = new ArrayList<Ordered>(Arrays.asList(orderData.getOrdered()));

                 subMenuAdapter=new Sub_Menu_Adapter(context,ordereds,image,new OnItemClickListener(){

                    @Override
                    public void OnItemClickListener(Cafes item) {

                    }

                    @Override
                    public void voidOnAddCart(ItemData itemData) {

                    }

                    @Override
                    public void totalPrice(String total) {
                        holder.totalprice.setText(holder.getCorrectValue(String.format("%.2f", Float.valueOf(total))));
                        onItemClickListener.totalPrice(total);
                    }

                     @Override
                     public void ordereddata(Ordered[] ordered) {

                     }


                 },orderData.getShopDetail().get_id());
                holder.recyclerView.setLayoutManager(mLayoutManager);
                holder.recyclerView.setAdapter(subMenuAdapter);
                holder.recyclerView.setNestedScrollingEnabled(false);
            }
        });


    }

    @Override
    public int getItemCount() {
        return orderDatas.size();
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder   {
        RelativeLayout subLayout,mailnlayout;
        RecyclerView recyclerView;
        TextView sitin,pickcup;
        public  TextView totalprice;
        ImageView arrow;
        EditText et_note;
        public MyViewHolder(View itemView) {
            super(itemView);
            subLayout=(RelativeLayout)itemView.findViewById(R.id.sub_layout);
            mailnlayout=(RelativeLayout)itemView.findViewById(R.id.relativeLayout);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.recycleView);
            sitin=(TextView)itemView.findViewById(R.id.bt_sitin);
            pickcup=(TextView)itemView.findViewById(R.id.bt_pickcup);
            et_note=(EditText)itemView.findViewById(R.id.et_note);
            arrow=(ImageView)itemView.findViewById(R.id.list_item_genre_arrow);
            totalprice=(TextView)itemView.findViewById(R.id.totalprice);


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
}
