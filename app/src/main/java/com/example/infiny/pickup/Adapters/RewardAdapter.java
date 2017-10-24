package com.example.infiny.pickup.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.Activity.MenuActivity;
import com.example.infiny.pickup.Activity.OrderActivity;
import com.example.infiny.pickup.Activity.RewardActivity;
import com.example.infiny.pickup.Helpers.CafeLIstingHelpers;
import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Model.AddToCartData;
import com.example.infiny.pickup.Model.CafeListingData;
import com.example.infiny.pickup.Model.ClaimToCartData;
import com.example.infiny.pickup.Model.Data;
import com.example.infiny.pickup.Model.DataRewards;
import com.example.infiny.pickup.Model.Ordered;
import com.example.infiny.pickup.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by infiny on 9/9/17.
 */

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.MyViewHolder> {
    Context context;
    ArrayList<DataRewards> partyName;
    ArrayList<Integer> quantities;
    RewardsQuantityAdapter rewardsQuantityAdapter;
    Retrofit retroFitClient;
    SharedPreferences sharedPreferences;
    ClaimToCartData claimToCartData;
    SessionManager sessionManager;

    public RewardAdapter(Context context, ArrayList<DataRewards> partyName) {
        this.context = context;
        this.partyName = partyName;
        sessionManager=new SessionManager(this.context);
        sharedPreferences=this.context.getSharedPreferences(sessionManager.PREF_NAME,0);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rewardlistlayout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final DataRewards f1=partyName.get(position);
        holder.cafeNAme.setText(f1.getCafe_name());

        if(holder.isTablet(context))
        {
            Picasso.with(context)
                    .invalidate(f1.getImageurl()+"_large.png");
            Picasso.with(context)
                    .load(f1.getImageurl()+"_large.png")
                    .placeholder(R.drawable.cofeecup)
                    .into(holder.imageView);

        }
        else
        {
            Picasso.with(context)
                    .invalidate(f1.getImageurl()+"_small.png");
            Picasso.with(context)
                    .load(f1.getImageurl()+"_small.png")
                    .placeholder(R.drawable.ic_person_black_48dp)
                    .into(holder.imageView);


        };

        if(Integer.parseInt(f1.getRewardCompleted())>=Integer.parseInt(f1.getQuantity()))
        {
            holder.claim.setVisibility(View.VISIBLE);
            holder.recyclerView.setVisibility(View.GONE);
            holder.tv_rewardsdetail.setText(f1.getQuantity()+"/"+f1.getQuantity());
            holder.tv_info.setText("You are ready to cliam ");
        }
        else {
            int leftrewards=Integer.parseInt(f1.getQuantity())-Integer.parseInt(f1.getRewardCompleted());
            holder.tv_info.setText("You are "+String.valueOf(leftrewards)+" order away from Reward");
            holder.tv_rewardsdetail.setText(f1.getRewardCompleted()+"/"+f1.getQuantity());
            int numberOfColumns = 6;
            rewardsQuantityAdapter = new RewardsQuantityAdapter(context, Integer.parseInt(f1.getQuantity()), f1.getRewardCompleted());
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, numberOfColumns);
            holder.recyclerView.setLayoutManager(mLayoutManager);
            holder.recyclerView.setAdapter(rewardsQuantityAdapter);
            holder.recyclerView.setNestedScrollingEnabled(false);

        }

        holder.claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RewardActivity.progressBarCyclic.setVisibility(View.VISIBLE);
                ((Activity)context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                retroFitClient = new RetroFitClient(context).getBlankRetrofit();
                String token =sharedPreferences.getString("token",null);

                Call<ClaimToCartData> call = retroFitClient
                        .create(ApiIntegration.class)
                        .rewardtocart(sharedPreferences.getString("token",null),
                               f1.getCafe_id());
                call.enqueue(new Callback<ClaimToCartData>() {

                    @Override
                    public void onResponse(Call<ClaimToCartData> call, Response<ClaimToCartData> response) {
                        if (response != null) {
                            claimToCartData = response.body();
                            if (claimToCartData != null) {
                                if (claimToCartData.getError().equals("true")) {

                                    AlertDialog.Builder builder;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                                    } else {
                                        builder = new AlertDialog.Builder(context);
                                    }

                                    builder.setTitle("Delete Cart")
                                            .setMessage("Some items from another cafe already exist in your cart. Adding this item will remove those items, are you sure you want to proceed?")
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    RewardActivity.progressBarCyclic.setVisibility(View.VISIBLE);
                                                    ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                    retroFitClient = new RetroFitClient(context).getBlankRetrofit();
                                                    Call<ClaimToCartData> call = retroFitClient
                                                            .create(ApiIntegration.class)
                                                            .deleteCart(sharedPreferences.getString("token", null));

                                                    call.enqueue(new Callback<ClaimToCartData>() {
                                                        @Override
                                                        public void onResponse(Call<ClaimToCartData> call, Response<ClaimToCartData> response) {
                                                            if (response != null) {
                                                                claimToCartData = response.body();
                                                                if (claimToCartData != null) {
                                                                    if (claimToCartData.getError().equals("true")) {
                                                                        RewardActivity.progressBarCyclic.setVisibility(View.GONE);
                                                                        ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                                                                    } else {
                                                                        RewardActivity.progressBarCyclic.setVisibility(View.GONE);
                                                                        ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                                        Intent intent=new Intent(context, OrderActivity.class);
                                                                        intent.putExtra("fromPage","rewardActivity");
                                                                        intent.putExtra("sid",f1.getCafe_id());
                                                                        intent.putExtra("rewardcompleted",f1.getRewardCompleted());
                                                                        intent.putExtra("rewardQuantity",f1.getQuantity());
                                                                        context.startActivity(intent);
                                                                        ((Activity)context).finish();



                                                                    }
                                                                }
                                                                else {

                                                                    RewardActivity.progressBarCyclic.setVisibility(View.GONE);
                                                                        ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                                        Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();

                                                                }
                                                            }

                                                        }

                                                        @Override
                                                        public void onFailure(Call<ClaimToCartData> call, Throwable t) {
                                                            RewardActivity.progressBarCyclic.setVisibility(View.GONE);
                                                            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                            Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();


                                                        }
                                                    });
                                                }}).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();;



                                } else {
                                    RewardActivity.progressBarCyclic.setVisibility(View.GONE);
                                    ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    Intent intent=new Intent(context, OrderActivity.class);
                                    intent.putExtra("fromPage","rewardActivity");
                                    intent.putExtra("sid",f1.getCafe_id());
                                    intent.putExtra("rewardcompleted",f1.getRewardCompleted());
                                    intent.putExtra("rewardQuantity",f1.getQuantity());
                                    context.startActivity(intent);
                                    ((Activity)context).finish();

                                }

                            }

                        } else {
                            RewardActivity.progressBarCyclic.setVisibility(View.GONE);
                            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ClaimToCartData> call, Throwable t) {
                        RewardActivity.progressBarCyclic.setVisibility(View.GONE);
                        ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Toast.makeText(context,R.string.Something_went_wrong,Toast.LENGTH_SHORT);

                    }
                });



            }
        });

    }

    @Override
    public int getItemCount() {
        return partyName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView cafeNAme,tv_rewardsdetail,tv_info;
        ImageView imageView,r1,r2,r3,r4,r5;
        RecyclerView recyclerView;
        Button claim;
        public MyViewHolder(View itemView) {
            super(itemView);
            cafeNAme=(TextView)itemView.findViewById(R.id.tittle);
            imageView=(ImageView)itemView.findViewById(R.id.tittleimage);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.toplayout);
            recyclerView=(RecyclerView)itemView.findViewById(R.id.quantityRecycleView);
            tv_rewardsdetail=(TextView)itemView.findViewById(R.id.rewarddetails);
            tv_info=(TextView)itemView.findViewById(R.id.tv_info);
            claim=(Button)itemView.findViewById(R.id.bt_claim);
        }
        public boolean isTablet(Context context) {
            boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
            boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
            return (xlarge || large);
        }
    }
}
