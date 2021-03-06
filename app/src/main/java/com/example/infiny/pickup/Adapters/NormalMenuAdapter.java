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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.Activity.MenuActivity;
import com.example.infiny.pickup.Activity.OrderActivity;
import com.example.infiny.pickup.Helpers.CafeLIstingHelpers;

import com.example.infiny.pickup.Helpers.MenuItemData;
import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Interfaces.OnItemClickListener;
import com.example.infiny.pickup.Model.AddToCartData;
import com.example.infiny.pickup.Model.ItemData;
import com.example.infiny.pickup.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by infiny on 9/7/17.
 */

public class NormalMenuAdapter extends RecyclerView.Adapter<NormalMenuAdapter.MyViewHolder> {
    Context context;
    ArrayList<ItemData> tittles;
    OnItemClickListener onItemClickListener;
    String category;
    int quantity=1;
    Retrofit retroFitClient;
    public static String cartRewardsCompleted,cartRewatdQuantity;
    AddToCartData addToCartData;
    public static Float total=0.0f;
    String sid;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    public NormalMenuAdapter(Context context, ArrayList<ItemData> tittles,OnItemClickListener onItemClickListener,String category,String sid) {
        this.context = context;
        this.tittles = tittles;
        this.onItemClickListener=onItemClickListener;
        this.category=category;
        this.sid=sid;
        sessionManager=new SessionManager(context);
        sharedPreferences = context.getSharedPreferences(sessionManager.PREF_NAME, 0);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menulistlayout, parent, false);
        return new NormalMenuAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final ItemData f1=tittles.get(position);
        Log.d("tittle",String.valueOf(tittles.size()));
        Log.d("position",String.valueOf(position));
           if(f1.getEligibleForRewards().equals("true"))
           {
               holder.iv_Eligable_For_Reward.setVisibility(View.VISIBLE);
               holder.iv_Eligable_For_Reward.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Toast.makeText(view.getContext(), "Applicable For Reward Points", Toast.LENGTH_SHORT).show();

                   }
               });

           }

        holder.menuname.setText(f1.getItemName());
        holder.price.setText("£ "+holder.getCorrectValue(String.format("%.2f",Float.valueOf(f1.getItemPrice()))));
            f1.setCategoty(category);
            String k = f1.getItemSmallPrice();
            if (k == null || k == "") {
                holder.view2.setVisibility(View.GONE);
                holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        holder.openDiolog(f1);

                }
                });
                onItemClickListener.voidOnAddCart(f1);
            } else {
                if(f1.getItemSmallPrice()=="")
                {
                    holder.labelsmall.setVisibility(View.GONE);
                    holder.small.setVisibility(View.GONE);
                    holder.iv_Small.setVisibility(View.GONE);
                }
                else {
                    holder.small.setText("£ " + holder.getCorrectValue(String.format("%.2f", Float.valueOf(f1.getItemSmallPrice()))));
                }
                if(f1.getItemMediumPrice()=="")
                {
                    holder.labelmedium.setVisibility(View.GONE);
                    holder.medium.setVisibility(View.GONE);
                    holder.iv_medium.setVisibility(View.GONE);
                }
                else {
                    holder.medium.setText("£ " + holder.getCorrectValue(String.format("%.2f", Float.valueOf(f1.getItemMediumPrice()))));
                }
                if(f1.getItemLargePrice()=="")
                {
                    holder.labellarge.setVisibility(View.GONE);
                    holder.large.setVisibility(View.GONE);
                    holder.iv_Large.setVisibility(View.GONE);
                }
                else {
                    holder.large.setText("£ " + holder.getCorrectValue(String.format("%.2f", Float.valueOf(f1.getItemLargePrice()))));
                }

                holder.small.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.openDiolog(f1,f1.getItemSmallPrice());
                        f1.setSize("small");
                        f1.setItemPrice(f1.getItemSmallPrice());
                        onItemClickListener.voidOnAddCart(f1);

                    }
                });
                holder.labellarge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.openDiolog(f1,f1.getItemLargePrice());
                        f1.setSize("large");
                        f1.setItemPrice(f1.getItemLargePrice());
                        onItemClickListener.voidOnAddCart(f1);
                    }
                });
                holder.labelmedium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.openDiolog(f1,f1.getItemMediumPrice());
                        f1.setSize("medium");
                        f1.setItemPrice(f1.getItemMediumPrice());
                        onItemClickListener.voidOnAddCart(f1);
                    }
                });
                holder.labelsmall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.openDiolog(f1,f1.getItemSmallPrice());
                        f1.setSize("small");
                        f1.setItemPrice(f1.getItemSmallPrice());
                        onItemClickListener.voidOnAddCart(f1);

                    }
                });
               holder.large.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       holder.openDiolog(f1,f1.getItemLargePrice());
                       f1.setSize("large");
                       f1.setItemPrice(f1.getItemLargePrice());
                       onItemClickListener.voidOnAddCart(f1);
                   }
               });
                holder.medium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.openDiolog(f1,f1.getItemMediumPrice());
                        f1.setSize("medium");
                        f1.setItemPrice(f1.getItemMediumPrice());
                        onItemClickListener.voidOnAddCart(f1);

                    }
                });
                holder.small.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.openDiolog(f1,f1.getItemSmallPrice());
                        f1.setSize("small");
                        f1.setItemPrice(f1.getItemSmallPrice());
                        onItemClickListener.voidOnAddCart(f1);

                    }
                });
                holder.iv_Small.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.openDiolog(f1,f1.getItemSmallPrice());
                        f1.setSize("small");
                        f1.setItemPrice(f1.getItemSmallPrice());
                        onItemClickListener.voidOnAddCart(f1);


                    }
                });
                holder.iv_medium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.openDiolog(f1,f1.getItemMediumPrice());
                        f1.setSize("medium");
                        f1.setItemPrice(f1.getItemMediumPrice());
                        onItemClickListener.voidOnAddCart(f1);


                    }
                });
                holder.iv_Large.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.openDiolog(f1,f1.getItemLargePrice());
                        f1.setSize("large");
                        f1.setItemPrice(f1.getItemLargePrice());
                        onItemClickListener.voidOnAddCart(f1);

                    }
                });

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
        TextView small,medium,large,labelsmall,labelmedium,labellarge;
        RelativeLayout relativeLayout,sublayout;
        ImageView view2,iv_Small,iv_medium,iv_Large,iv_Eligable_For_Reward;
        ProgressBar progressBarCyclic;
        View bottom_light_View;
        public MyViewHolder(View itemView) {
            super(itemView);
            menuname=(TextView)itemView.findViewById(R.id.tw_manuname);
            view2=(ImageView) itemView.findViewById(R.id.list_item_genre_arrow);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.relativeLayout);
            sublayout=(RelativeLayout)itemView.findViewById(R.id.sublayout);
            labelsmall=(TextView)itemView.findViewById(R.id.tw_small);
            labelmedium=(TextView)itemView.findViewById(R.id.tw_medium);
            labellarge=(TextView)itemView.findViewById(R.id.tw_large);
            price=(TextView)itemView.findViewById(R.id.price);
            small=(TextView)itemView.findViewById(R.id.tw_smallprice);
            medium=(TextView)itemView.findViewById(R.id.tw_mediumprice);
            large=(TextView)itemView.findViewById(R.id.tw_largeprice);
            iv_Small=(ImageView) itemView.findViewById(R.id.iv_smallsize);
            iv_medium=(ImageView) itemView.findViewById(R.id.iv_mediumsize);
            iv_Large=(ImageView) itemView.findViewById(R.id.iv_largesize);
            progressBarCyclic=(ProgressBar)itemView.findViewById(R.id.progressBar_cyclic);
            bottom_light_View=(View)itemView.findViewById(R.id.bottom_light_View);
            iv_Eligable_For_Reward=(ImageView)itemView.findViewById(R.id.iv_Eligable_For_Reward);
        }
        public void openDiolog(final ItemData itemData)
        {
            quantity=1;

            LayoutInflater inflater = LayoutInflater.from(context);
            View alertLayout = inflater.inflate(R.layout.quantity, null);
            final ImageView increment=(ImageView) alertLayout.findViewById(R.id.increment);
            final ImageView decrement=(ImageView)alertLayout.findViewById(R.id.decrement);
            final TextView  display=(TextView)alertLayout.findViewById(R.id.display);
            Button btCancel=(Button)alertLayout.findViewById(R.id.btnCancel);
            Button btOk=(Button)alertLayout.findViewById(R.id.btnOk);
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            final AlertDialog alertDialog=alert.create();
            alertDialog.setTitle("Quantity");
            // this is set the view from XML inside AlertDialog
            alertDialog.setView(alertLayout);
            // disallow cancel of AlertDialog on click of back button and outside touch
            if(quantity ==1)
            {
                decrement.setImageResource(R.drawable.ic_remove_circle_grey_400_24dp);
            }
            else{
                decrement.setImageResource(R.drawable.ic_remove_circle_black_24dp);
            }

            decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(quantity == 1)
                    {
                        quantity--;
                    }
                    else  if(quantity ==2){
                        decrement.setImageResource(R.drawable.ic_remove_circle_grey_400_24dp);
                        quantity--;
                        display.setText(String.valueOf(quantity));
                    }
                    else{
                        quantity--;
                        display.setText(String.valueOf(quantity));
                    }

                }
            });
            increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantity++;
                    decrement.setImageResource(R.drawable.ic_remove_circle_black_24dp);
                    display.setText(String.valueOf(quantity));

                }
            });

            display.setText(String.valueOf(quantity));
            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    quantity=1;
                }
            });
            btOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(MenuActivity.status.equals("ready"))
                    {
                        alertDialog.dismiss();
                        getData(itemData);
                    }
                    if((MenuActivity.status.equals("closed")))
                    {
                        alertDialog.dismiss();
                        Toast.makeText(context, R.string.Cafe_Closed, Toast.LENGTH_SHORT).show();
                    }
                    if((MenuActivity.status.equals("busy")))
                    {
                        alertDialog.dismiss();
                        AlertDialog.Builder builder;
                        ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(context);
                        }
                        builder.setMessage(R.string.busyPopup)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        getData(itemData);

                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    };



                }

            });
            alertDialog.show();


        }
        public void openDiolog(final ItemData itemData, final String amount)
        {
            quantity=1;

            LayoutInflater inflater = LayoutInflater.from(context);
            View alertLayout = inflater.inflate(R.layout.quantity, null);
            final ImageView increment=(ImageView) alertLayout.findViewById(R.id.increment);
            final ImageView decrement=(ImageView)alertLayout.findViewById(R.id.decrement);
            final TextView  display=(TextView)alertLayout.findViewById(R.id.display);
            Button btCancel=(Button)alertLayout.findViewById(R.id.btnCancel);
            Button btOk=(Button)alertLayout.findViewById(R.id.btnOk);
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            final AlertDialog alertDialog=alert.create();
            alertDialog.setTitle("Quantity");
            // this is set the view from XML inside AlertDialog
            alertDialog.setView(alertLayout);
            // disallow cancel of AlertDialog on click of back button and outside touch
            if(quantity ==1)
            {
                decrement.setImageResource(R.drawable.ic_remove_circle_grey_400_24dp);
            }
            else{
                decrement.setImageResource(R.drawable.ic_remove_circle_black_24dp);
            }

            decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(quantity == 1)
                    {
                        quantity--;
                    }
                    else  if(quantity ==2){
                        decrement.setImageResource(R.drawable.ic_remove_circle_grey_400_24dp);
                        quantity--;
                        display.setText(String.valueOf(quantity));
                    }
                    else{
                        quantity--;
                        display.setText(String.valueOf(quantity));
                    }

                }
            });
            increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantity++;
                    decrement.setImageResource(R.drawable.ic_remove_circle_black_24dp);
                    display.setText(String.valueOf(quantity));

                }
            });

            display.setText(String.valueOf(quantity));
            btCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    quantity=1;
                }
            });
            btOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(MenuActivity.status.equals("ready"))
                     {
                         alertDialog.dismiss();
                         getData(itemData);
                     }
                    if((MenuActivity.status.equals("closed")))
                     {
                         alertDialog.dismiss();
                         Toast.makeText(context, R.string.Cafe_Closed, Toast.LENGTH_SHORT).show();
                     }
                    if((MenuActivity.status.equals("busy")))
                    {
                        alertDialog.dismiss();
                        AlertDialog.Builder builder;
                        ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(context);
                        }
                        builder.setMessage(R.string.busyPopup)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        getData(itemData);

                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    };



        }

            });
            alertDialog.show();

        }

        public void getData(final ItemData itemData)
        {

            total=total + Float.valueOf(itemData.getItemPrice())*quantity;
            itemData.setItemQuantity(String.valueOf(quantity));
            itemData.setItemTotalamount(String.valueOf(total));
            retroFitClient = new RetroFitClient(context).getBlankRetrofit();
            Call<AddToCartData> call = retroFitClient
                    .create(ApiIntegration.class)
                    .getAddtocart(sharedPreferences.getString("token", null),
                            itemData.get_id(),
                            itemData.getSize(),
                            itemData.getItemName(),
                            itemData.getItemPrice(),
                            String.valueOf(quantity),
                            category,
                            itemData.getEligibleForRewards(),
                            sid);
            call.enqueue(new Callback<AddToCartData>() {

                @Override
                public void onResponse(Call<AddToCartData> call, Response<AddToCartData> response) {
                    if (response != null) {
                        addToCartData = response.body();
                        if (addToCartData != null) {
                            if (addToCartData.getError().equals("true") && addToCartData.getTitle().equals("multiple shopDetail")) {

                                progressBarCyclic.setVisibility(View.GONE);
                                AlertDialog.Builder builder;
                                ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                                } else {
                                    builder = new AlertDialog.Builder(context);
                                }
                                builder.setTitle("Delete Cart")
                                        .setMessage("Some items from another cafe already exist in your cart. Adding this item will remove those items, are you sure you want to proceed?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                progressBarCyclic.setVisibility(View.VISIBLE);
                                                ((Activity)context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                retroFitClient = new RetroFitClient(context).getBlankRetrofit();
                                                Call<AddToCartData> call = retroFitClient
                                                        .create(ApiIntegration.class)
                                                        .getDeleteCart(sharedPreferences.getString("token", null),
                                                                itemData.get_id(),
                                                                itemData.getSize(),
                                                                itemData.getItemName(),
                                                                itemData.getItemPrice(),
                                                                String.valueOf(quantity),
                                                                category,
                                                                itemData.getEligibleForRewards(),
                                                                sid);
                                                call.enqueue(new Callback<AddToCartData>() {

                                                    @Override
                                                    public void onResponse(Call<AddToCartData> call, Response<AddToCartData> response) {
                                                        if (response != null) {
                                                            quantity=1;
                                                            addToCartData = response.body();
                                                            if (addToCartData != null) {
                                                                if (addToCartData.getError().equals("true")) {

                                                                    progressBarCyclic.setVisibility(View.GONE);
                                                                    AlertDialog.Builder builder;
                                                                    ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                                } else {
                                                                    sessionManager.createRewardsSession(MenuActivity.rewardcompleted,MenuActivity.rewardQuantity,MenuActivity.status);

                                                                    cartRewardsCompleted =MenuActivity.rewardcompleted;
                                                                    cartRewatdQuantity=MenuActivity.rewardQuantity;
                                                                    Toast.makeText(context, R.string.Item_Add_To_Cart, Toast.LENGTH_SHORT).show();
                                                                    progressBarCyclic.setVisibility(View.GONE);
                                                                    ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                                    MenuActivity.order.setVisibility(View.VISIBLE);
                                                                    total=Float.valueOf(itemData.getItemPrice())*Float.valueOf(addToCartData.getData().getOrdered()[0].getItemQuantity());
                                                                    MenuActivity.cart_count_String=String.valueOf(quantity);
                                                                    itemData.setItemQuantity(String.valueOf(Float.valueOf(addToCartData.getData().getOrdered()[0].getItemQuantity())));
                                                                    itemData.setItemTotalamount(String.valueOf(total));
                                                                    MenuActivity.orderPrice.setText("£ "+getCorrectValue(String.format("%.2f", total)));
                                                                    MenuActivity.cartCount.setText(MenuActivity.cart_count_String);

                                                                }
                                                            }
                                                            else {
                                                                if (response.code() == 404 || response.code() == 500) {
                                                                    progressBarCyclic.setVisibility(View.GONE);
                                                                    ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                                    Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<AddToCartData> call, Throwable t) {
                                                        progressBarCyclic.setVisibility(View.GONE);
                                                        ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                        Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();

                                                    }
                                                });


                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                            else if(addToCartData.getError().equals("true"))
                            {
                                sessionManager.createRewardsSession(MenuActivity.rewardcompleted,MenuActivity.rewardQuantity,MenuActivity.status);

                                cartRewardsCompleted =MenuActivity.rewardcompleted;
                                cartRewatdQuantity=MenuActivity.rewardQuantity;
                                Toast.makeText(context, R.string.Item_Add_To_Cart, Toast.LENGTH_SHORT).show();

                                quantity=1;
                                progressBarCyclic.setVisibility(View.GONE);
                                ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                MenuActivity.order.setVisibility(View.VISIBLE);
                                MenuActivity.orderPrice.setText("£ "+getCorrectValue(String.format("%.2f", total)));

                            }

                            else {
                                sessionManager.createRewardsSession(MenuActivity.rewardcompleted,MenuActivity.rewardQuantity,MenuActivity.status);

                                cartRewardsCompleted =MenuActivity.rewardcompleted;
                                cartRewatdQuantity=MenuActivity.rewardQuantity;
                                Toast.makeText(context, R.string.Item_Add_To_Cart, Toast.LENGTH_SHORT).show();

                                quantity=1;
                                progressBarCyclic.setVisibility(View.GONE);
                                ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                MenuActivity.order.setVisibility(View.VISIBLE);
                                MenuActivity.cart_count_String=String.valueOf(Integer.parseInt(MenuActivity.cart_count_String)+1);
                                MenuActivity.cartCount.setText(MenuActivity.cart_count_String);
                                MenuActivity.orderPrice.setText("£ "+getCorrectValue(String.format("%.2f", total)));

                            }

                        } else {
                            if (response.code() == 404 || response.code() == 500) {
                                progressBarCyclic.setVisibility(View.GONE);
                                ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddToCartData> call, Throwable t) {
                    progressBarCyclic.setVisibility(View.GONE);
                    ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                }
            });

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
