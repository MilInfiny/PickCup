package com.example.infiny.pickup.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.Activity.MenuActivity;
import com.example.infiny.pickup.Helpers.CafeLIstingHelpers;

import com.example.infiny.pickup.Helpers.MenuItemData;
import com.example.infiny.pickup.Helpers.RetroFitClient;
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
    OnItemClickListener onItemClickListener;
    String category;
    int quantity=1;
    public NormalMenuAdapter(Context context, ArrayList<ItemData> tittles,OnItemClickListener onItemClickListener,String category) {
        this.context = context;
        this.tittles = tittles;
        this.onItemClickListener=onItemClickListener;
        this.category=category;

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
        holder.menuname.setText(f1.getItemName());
        holder.price.setText("£ "+f1.getItemPrice());
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
            } else {
                holder.small.setText("£ "+f1.getItemSmallPrice());
                holder.large.setText("£ "+f1.getItemLargePrice());
                holder.medium.setText("£ "+f1.getItemMediumPrice());
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
        ImageView view2,iv_Small,iv_medium,iv_Large;
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
        }
        public void openDiolog(final ItemData f1)
        {

            LayoutInflater inflater = LayoutInflater.from(context);
            View alertLayout = inflater.inflate(R.layout.quantity, null);
            final ImageView increment=(ImageView) alertLayout.findViewById(R.id.increment);
            final ImageView decrement=(ImageView)alertLayout.findViewById(R.id.decrement);
            final TextView  display=(TextView)alertLayout.findViewById(R.id.display);
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Quantity");
            // this is set the view from XML inside AlertDialog
            alert.setView(alertLayout);
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
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    quantity=1;
                }
            });

            alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MenuActivity.order.setVisibility(View.VISIBLE);
                    int total=Integer.parseInt(f1.getItemPrice())*quantity;
                    f1.setItemQuantity(String.valueOf(quantity));
                    f1.setItemTotalamount(String.valueOf(total));
                    MenuActivity.orderPrice.setText("£ "+String.valueOf(total));
                    onItemClickListener.voidOnAddCart(f1);

                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();



        }
        public void openDiolog(final ItemData f1, final String amount)
        {

            LayoutInflater inflater = LayoutInflater.from(context);
            View alertLayout = inflater.inflate(R.layout.quantity, null);
            final ImageView increment=(ImageView) alertLayout.findViewById(R.id.increment);
            final ImageView decrement=(ImageView)alertLayout.findViewById(R.id.decrement);
            final TextView  display=(TextView)alertLayout.findViewById(R.id.display);
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Quantity");
            // this is set the view from XML inside AlertDialog
            alert.setView(alertLayout);
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
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    quantity=1;
                }
            });

            alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MenuActivity.order.setVisibility(View.VISIBLE);
                    int total=Integer.parseInt(amount)*quantity;
                    f1.setItemQuantity(String.valueOf(quantity));
                    f1.setItemTotalamount(String.valueOf(total));
                    MenuActivity.orderPrice.setText("£ "+String.valueOf(total));
                    onItemClickListener.voidOnAddCart(f1);

                }
            });
            AlertDialog dialog = alert.create();
            dialog.show();



        }





    }
}
