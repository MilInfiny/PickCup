package com.example.infiny.pickup.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.infiny.pickup.Helpers.Drinks;
import com.example.infiny.pickup.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.ArrayList;
import java.util.List;

public class Sub_Menu_Adapter extends RecyclerView.Adapter<Sub_Menu_Adapter.MyViewHolder> {
    Context context;
  ArrayList<String> drinkses;

    public Sub_Menu_Adapter(Context context, ArrayList<String> drinkses) {
        this.context = context;
        this.drinkses = drinkses;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_manu_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
      String s=drinkses.get(position);
        holder.tw_small.setText(s);

    }

    @Override
    public int getItemCount() {
        return drinkses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tw_small;

        public MyViewHolder(View itemView) {
            super(itemView);
            tw_small=(TextView)itemView.findViewById(R.id.tw_small);
        }
    }
}
