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
import com.example.infiny.pickup.Helpers.MenuItem;
import com.example.infiny.pickup.Helpers.MenuItemData;
import com.example.infiny.pickup.Interfaces.OnItemClickListener;
import com.example.infiny.pickup.R;

import java.util.ArrayList;

/**
 * Created by infiny on 9/7/17.
 */

public class NormalMenuAdapter extends RecyclerView.Adapter<NormalMenuAdapter.MyViewHolder> {
    Context context;
    ArrayList<MenuItemData> tittles;
    private final OnItemClickListener listener;

    public NormalMenuAdapter(Context context, ArrayList<MenuItemData> tittles, OnItemClickListener listener) {
        this.context = context;
        this.tittles = tittles;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menulistlayout, parent, false);
        return new NormalMenuAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MenuItemData f1=tittles.get(position);
        holder.bind(context,listener);
        holder.view2.setVisibility(View.INVISIBLE);
        holder.menuname.setText(f1.getMenuItem());
        holder.price.setText(f1.getPrice());
    }

    @Override
    public int getItemCount() {
        return tittles.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView menuname,price;
        RelativeLayout relativeLayout;
        ImageView view2;
        public MyViewHolder(View itemView) {
            super(itemView);
            menuname=(TextView)itemView.findViewById(R.id.tw_manuname);
            view2=(ImageView) itemView.findViewById(R.id.list_item_genre_arrow);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.relativeLayout);
            price=(TextView)itemView.findViewById(R.id.price);
        }
        public void bind(final Context item, final OnItemClickListener listener) {
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onimageclickLister();
                }
            });

        }

    }
}
