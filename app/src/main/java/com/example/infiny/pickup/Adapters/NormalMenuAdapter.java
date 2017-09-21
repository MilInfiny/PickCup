package com.example.infiny.pickup.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.infiny.pickup.Helpers.CafeLIstingHelpers;

import com.example.infiny.pickup.Helpers.MenuItemData;
import com.example.infiny.pickup.Interfaces.OnItemClickListener;
import com.example.infiny.pickup.Model.ItemData;
import com.example.infiny.pickup.R;

import java.util.ArrayList;

/**
 * Created by infiny on 9/7/17.
 */

public class NormalMenuAdapter extends RecyclerView.Adapter<NormalMenuAdapter.MyViewHolder> {
    Context context;
    ArrayList<ItemData> tittles;

    public NormalMenuAdapter(Context context, ArrayList<ItemData> tittles) {
        this.context = context;
        this.tittles = tittles;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menulistlayout, parent, false);
        return new NormalMenuAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ItemData f1=tittles.get(position);
        holder.menuname.setText(f1.getItemName());
        holder.price.setText("£ "+f1.getItemPrice());

            String k = f1.getItemSmallPrice();
            if (k == null) {
                holder.view2.setVisibility(View.GONE);
            } else {
                holder.small.setText("£ "+f1.getItemSmallPrice());
                holder.large.setText("£ "+f1.getItemLargePrice());
                holder.medium.setText("£ "+f1.getItemMediumPrice());
                holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.sublayout.getVisibility()==View.VISIBLE) {
                            holder.sublayout.setVisibility(View.GONE);
                            holder.view2.setRotation(360);
                        }

                       else{
                            holder.sublayout.setVisibility(View.VISIBLE);
                            holder.view2.setRotation(180);
                        }

                    }
                });
            }
    }

    @Override
    public int getItemCount() {
        return tittles.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView menuname,price;
        TextView small,medium,large;
        RelativeLayout relativeLayout,sublayout;
        ImageView view2,iv_Small,iv_Mediuam,iv_Large;
        public MyViewHolder(View itemView) {
            super(itemView);
            menuname=(TextView)itemView.findViewById(R.id.tw_manuname);
            view2=(ImageView) itemView.findViewById(R.id.list_item_genre_arrow);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.relativeLayout);
            sublayout=(RelativeLayout)itemView.findViewById(R.id.sublayout);
            price=(TextView)itemView.findViewById(R.id.price);
            small=(TextView)itemView.findViewById(R.id.tw_smallprice);
            medium=(TextView)itemView.findViewById(R.id.tw_mediumprice);
            large=(TextView)itemView.findViewById(R.id.tw_largeprice);
            iv_Small=(ImageView) itemView.findViewById(R.id.iv_smallsize);
            iv_Mediuam=(ImageView) itemView.findViewById(R.id.iv_mediumsize);
            iv_Large=(ImageView) itemView.findViewById(R.id.iv_largesize);
        }


    }
}
