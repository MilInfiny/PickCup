package com.example.infiny.pickup.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.infiny.pickup.Model.Data;
import com.example.infiny.pickup.Model.ItemData;
import com.example.infiny.pickup.Model.MenuListData;
import com.example.infiny.pickup.R;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Callback;

/**
 * Created by infiny on 9/21/17.
 */

public class MenuNormalAdapter extends RecyclerView.Adapter<MenuNormalAdapter.MyViewHolder> {
    Context context;
    ArrayList<Data> datas;
    public MenuNormalAdapter(Context context, ArrayList<Data> datas) {
        this.context = context;
        this.datas = datas;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_menu_layout, parent, false);
        return new MenuNormalAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Data menuListData=datas.get(position);
        holder.menutype.setText(menuListData.getItemCategory());

      ItemData [] itemdata=menuListData.getItemData();
        ArrayList<ItemData> itemDatas=new ArrayList<>(Arrays.asList(itemdata));
        NormalMenuAdapter normalMenuAdapter=new NormalMenuAdapter(context,itemDatas);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context.getApplicationContext());
        holder.recyclerView.setLayoutManager(mLayoutManager);
        holder.recyclerView.setAdapter(normalMenuAdapter);
        holder.recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView menutype;
        RecyclerView recyclerView;
        public MyViewHolder(View itemView) {
            super(itemView);

            recyclerView=(RecyclerView)itemView.findViewById(R.id.recycleView);
            menutype=(TextView)itemView.findViewById(R.id.tw_tittle);

        }
    }
}
