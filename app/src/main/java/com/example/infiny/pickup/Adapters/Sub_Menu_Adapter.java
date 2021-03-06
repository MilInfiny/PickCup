package com.example.infiny.pickup.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;


import com.example.infiny.pickup.Activity.MenuActivity;
import com.example.infiny.pickup.Activity.OrderActivity;
import com.example.infiny.pickup.Helpers.Drinks;
import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Interfaces.OnItemClickListener;
import com.example.infiny.pickup.Model.AddToCartData;
import com.example.infiny.pickup.Model.Ordered;
import com.example.infiny.pickup.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Sub_Menu_Adapter extends RecyclerView.Adapter<Sub_Menu_Adapter.MyViewHolder> {
    Context context;
  ArrayList<Ordered> ordereds;
    int pickcupimage;
    Retrofit retroFitClient;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
   public static Float totalprize=0.0f;
    OnItemClickListener onItemClickListener;
    String sid;
    AddToCartData addToCartData;


    public Sub_Menu_Adapter(Context context, ArrayList<Ordered> ordereds,OnItemClickListener onItemClickListener,String sid ) {
        this.context = context;
        this.ordereds = ordereds;
        this.onItemClickListener=onItemClickListener;
        this.sid=sid;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_manu_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Ordered s=ordereds.get(position);
        holder.tw_itemName.setText(s.getItemName());
        if(s.getItemSize()!=null)
        {
            String upperString = s.getItemSize().substring(0,1).toUpperCase() + s.getItemSize().substring(1);
            holder.tw_size.setText(upperString);


        }
        holder.itemimg.setImageResource(s.getImage());
        sessionManager=new SessionManager(context);
        sharedPreferences = context.getSharedPreferences(sessionManager.PREF_NAME, 0);
        holder.singleprice.setText("£" +" "+holder.getCorrectValue(String.format("%.2f",Float.valueOf(s.getItemPrice()))));
        holder.totalprice.setText("£" +" "+ holder.getCorrectValue(String.format("%.2f",Float.valueOf(s.getItemPrice())*Integer.parseInt(s.getItemQuantity()))));

       totalprize=totalprize + Float.valueOf(s.getItemPrice())*Float.valueOf(s.getItemQuantity());
       onItemClickListener.totalPrice(String.valueOf(totalprize));
        holder.display.setText(String.valueOf(s.getItemQuantity()));



        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(s.getItemQuantity())==1)
                {

                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(context);
                    }


                    builder.setTitle("Delete")
                            .setMessage("Delete Item?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    holder.progressBar_cyclic.setVisibility(View.VISIBLE);
                                    retroFitClient = new RetroFitClient(context).getBlankRetrofit();
                                    Call<AddToCartData> call = retroFitClient
                                            .create(ApiIntegration.class)
                                            .getDeleteCartItem(sharedPreferences.getString("token", null),
                                                    s.getItemId(),
                                                    s.getItemSize(),
                                                    sid
                                            );
                                    call.enqueue(new Callback<AddToCartData>() {
                                        @Override
                                        public void onResponse(Call<AddToCartData> call, Response<AddToCartData> response) {
                                            if (response != null) {
                                                addToCartData = response.body();
                                                if (addToCartData != null) {
                                                    if (addToCartData.getError().equals("true")) {
                                                        holder.progressBar_cyclic.setVisibility(View.GONE);

                                                        Toast.makeText(context, addToCartData.getTitle(), Toast.LENGTH_SHORT).show();

                                                    } else {
                                                        holder.progressBar_cyclic.setVisibility(View.GONE);
                                                        if( MenuActivity.cart_count_String!=null)
                                                        {
                                                            MenuActivity.cart_count_String=String.valueOf(Integer.parseInt(MenuActivity.cart_count_String)-1);

                                                        }
                                                        ordereds.remove(s);

                                                        if(ordereds.size()==0)
                                                        {
                                                            totalprize=0f;
                                                            Ordered [] ordered={};
                                                            onItemClickListener.ordereddata(ordered);
                                                            onItemClickListener.totalPrice(String.valueOf(totalprize));
                                                        }
                                                        else {

                                                            totalprize = totalprize - Float.valueOf(s.getItemPrice()) * Integer.parseInt(s.getItemQuantity());
                                                            Float total=Float.valueOf(s.getItemPrice())*Integer.parseInt(s.getItemQuantity());
                                                            totalprize = totalprize + total;
                                                            onItemClickListener.totalPrice(String.valueOf(totalprize));
                                                            holder.totalprice.setText("£" + " " + holder.getCorrectValue(String.format("%.2f", Float.valueOf(s.getItemPrice()) * Integer.parseInt(s.getItemQuantity()))));
                                                        }
                                                        notifyDataSetChanged();


                                                    }
                                                }
                                                else {
                                                    if (response.code() == 404 || response.code() == 500) {
                                                        holder.progressBar_cyclic.setVisibility(View.GONE);
                                                        Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<AddToCartData> call, Throwable t) {
                                            holder.progressBar_cyclic.setVisibility(View.GONE);
                                            Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();


                                        }
                                    });
                                }}).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();;

                }
                else {
                  totalprize = totalprize - Float.valueOf(s.getItemPrice()) * Integer.parseInt(s.getItemQuantity());
                    s.setItemQuantity(String.valueOf(Integer.parseInt(s.getItemQuantity()) - 1));
                    Float total = Float.valueOf(s.getItemPrice()) * Integer.parseInt(s.getItemQuantity());
                    holder.totalprice.setText("£" +" "+holder.getCorrectValue(String.format("%.2f", total)));
                    totalprize = totalprize + total;
                    onItemClickListener.totalPrice(String.valueOf(totalprize));
                    holder.display.setText(String.valueOf(s.getItemQuantity()));

                }


            }
        });
        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalprize=totalprize-Float.valueOf(s.getItemPrice())*Integer.parseInt(s.getItemQuantity());
                s.setItemQuantity(String.valueOf(Integer.parseInt(s.getItemQuantity())+1));
                Float total=Float.valueOf(s.getItemPrice())*Integer.parseInt(s.getItemQuantity());

                holder.totalprice.setText("£" +" "+holder.getCorrectValue(String.format("%.2f", total)));

                holder.decrement.setImageResource(R.drawable.ic_remove_circle_black_24dp);
                totalprize=totalprize +total;
                onItemClickListener.totalPrice(String.valueOf(totalprize));

                holder.display.setText(String.valueOf(Integer.parseInt(s.getItemQuantity())));



            }
        });


    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getItemCount() {
        return ordereds.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tw_size,tw_itemName,display,singleprice,totalprice;
        ImageView itemimg,increment,decrement;
        ProgressBar progressBar_cyclic;


        public MyViewHolder(View itemView) {
            super(itemView);
            tw_size=(TextView)itemView.findViewById(R.id.tw_small);
            tw_itemName=(TextView)itemView.findViewById(R.id.tw_itemname);
            increment=(ImageView) itemView.findViewById(R.id.increment);
            decrement=(ImageView)itemView.findViewById(R.id.decrement);
            itemimg=(ImageView)itemView.findViewById(R.id.iv_itemimage);
            display=(TextView)itemView.findViewById(R.id.display);
            singleprice=(TextView)itemView.findViewById(R.id.singleprice);
            totalprice=(TextView)itemView.findViewById(R.id.totalprice);
            progressBar_cyclic=(ProgressBar)itemView.findViewById(R.id.progressBar_cyclic);

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
