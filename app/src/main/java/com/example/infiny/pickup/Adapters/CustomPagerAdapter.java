package com.example.infiny.pickup.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.Activity.Card_Activity;
import com.example.infiny.pickup.Activity.MenuActivity;
import com.example.infiny.pickup.Helpers.ModelObject;
import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Interfaces.OnItemClickListener;
import com.example.infiny.pickup.Model.AddToCartData;
import com.example.infiny.pickup.Model.CardDetails;
import com.example.infiny.pickup.Model.CardListingData;
import com.example.infiny.pickup.R;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by infiny on 9/13/17.
 */

public class CustomPagerAdapter  extends PagerAdapter {
    Context mContext;
    ArrayList<CardDetails> cardDetailses;
    Retrofit retroFitClient;
    SharedPreferences sharedPreferences;
    CardListingData cardListingData;
    SessionManager sessionManager;
    OnItemClickListener onItemClickListener;
    Boolean status;
    public CustomPagerAdapter(Context context,ArrayList<CardDetails> cardDetailses, OnItemClickListener onItemClickListener) {
        this.mContext = context;
        this.cardDetailses=cardDetailses;
        this.onItemClickListener=onItemClickListener;
    }
    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        ViewGroup layout = null;
        final CardDetails cardDetails = cardDetailses.get(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);

        if(cardDetails.getBrand().equals("Visa"))
        {
             layout = (ViewGroup) inflater.inflate(R.layout.view_visa_card, collection, false);
        }
        else {
            layout = (ViewGroup) inflater.inflate(R.layout.view_master_card, collection, false);
        }
        TextView card1,card2,card3,card4,holdername,expires,tv_Name,tv_cardnumbar,tv_card_no;
        Button bt_SetPrimary,bt_DeleteCard;
        final ProgressBar progressBarCyclic;
         card1=(TextView)layout.findViewById(R.id.card_1);
         card2=(TextView)layout.findViewById(R.id.card_2);
         card3=(TextView)layout.findViewById(R.id.card_3);
         card4=(TextView)layout.findViewById(R.id.card_4);
         card4.setText(cardDetails.getCard_number());
         holdername=(TextView)layout.findViewById(R.id.cardholdername);
         expires=(TextView)layout.findViewById(R.id.expires);
         tv_Name=(TextView)layout.findViewById(R.id.tv_Name);
        tv_cardnumbar=(TextView)layout.findViewById(R.id.tv_cardnumbar);
        tv_card_no=(TextView)layout.findViewById(R.id.tv_card_no);

        progressBarCyclic=(ProgressBar)layout.findViewById(R.id.progressBar_cyclic);
        tv_Name.setText(cardDetails.getCard_name());
        tv_cardnumbar.setText("**** **** **** "+cardDetails.getCard_number());
        holdername.setText(cardDetails.getCard_name());


        tv_card_no.setText(cardDetails.getExpiryMonth()+"/"+cardDetails.getExpiryYear());
        expires.setText(cardDetails.getExpiryMonth()+"/"+cardDetails.getExpiryYear());
        Typeface typeFace=Typeface.createFromAsset(mContext.getAssets(),"fonts/CREDC___.ttf");
        card1.setTypeface(typeFace);
        card2.setTypeface(typeFace);
        card3.setTypeface(typeFace);
        card4.setTypeface(typeFace);
        holdername.setTypeface(typeFace);
        expires.setTypeface(typeFace);
        bt_SetPrimary=(Button)layout.findViewById(R.id.bt_SetPrimary);
        bt_DeleteCard=(Button)layout.findViewById(R.id.bt_DeleteCard);
        sessionManager=new SessionManager(mContext);
        sharedPreferences = ((Activity)mContext).getSharedPreferences(sessionManager.PREF_NAME, 0);
        if(cardDetails.getIsPrimary().equals("true"))
        {
            bt_SetPrimary.setText("Primary");
        }
        bt_SetPrimary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cardDetails.getIsPrimary().equals("true"))
                {
                   Toast.makeText(mContext,R.string.already_Primary,Toast.LENGTH_LONG).show();
                }
                else {


                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(mContext);
                    }


                    builder.setTitle("Set Primary")
                            .setMessage("Want to set primary?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    progressBarCyclic.setVisibility(View.VISIBLE);
                                    ((Activity) mContext).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    retroFitClient = new RetroFitClient(mContext).getBlankRetrofit();
                                    Call<CardListingData> call = retroFitClient
                                            .create(ApiIntegration.class)
                                            .setPrimaryCard(sharedPreferences.getString("token", null),
                                                    cardDetails.getCardId());
                                    call.enqueue(new Callback<CardListingData>() {

                                        @Override
                                        public void onResponse(Call<CardListingData> call, Response<CardListingData> response) {
                                            if (response != null) {
                                                cardListingData = response.body();
                                                if (cardListingData != null) {
                                                    if (cardListingData.getError().equals("true")) {
                                                        progressBarCyclic.setVisibility(View.GONE);
                                                        ((Activity) mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                        Toast.makeText(mContext, cardListingData.getTitle(), Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        progressBarCyclic.setVisibility(View.GONE);
                                                        ((Activity) mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                        Toast.makeText(mContext, "Marked As Primary", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(mContext, Card_Activity.class);
                                                        mContext.startActivity(intent);
                                                        ((Activity) mContext).finish();


                                                    }

                                                } else {
                                                    if (response.code() == 404 || response.code() == 500) {
                                                        progressBarCyclic.setVisibility(View.GONE);
                                                        ((Activity) mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                        Toast.makeText(mContext, R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }

                                        }


                                        @Override
                                        public void onFailure(Call<CardListingData> call, Throwable t) {
                                            progressBarCyclic.setVisibility(View.GONE);
                                            ((Activity) mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                            Toast.makeText(mContext, R.string.server_not_responding, Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }
                            }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    ;

                }

            }
        });

        bt_DeleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(mContext);
                }


                builder.setTitle("Delete")
                        .setMessage("Delete card?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                progressBarCyclic.setVisibility(View.VISIBLE);
                                ((Activity)mContext).getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                retroFitClient = new RetroFitClient(mContext).getBlankRetrofit();
                                Call<CardListingData> call = retroFitClient
                                        .create(ApiIntegration.class)
                                        .deleteCard(sharedPreferences.getString("token",null),
                                                cardDetails.getCardId() );
                                call.enqueue(new Callback<CardListingData>() {

                                    @Override
                                    public void onResponse(Call<CardListingData> call, Response<CardListingData> response) {
                                        if (response != null) {
                                            cardListingData = response.body();
                                            if (cardListingData != null) {
                                                if (cardListingData.getError().equals("true")) {
                                                    progressBarCyclic.setVisibility(View.GONE);
                                                    ((Activity)mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                    Toast.makeText(mContext,cardListingData.getTitle(), Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(mContext,"Card deleted successfully", Toast.LENGTH_SHORT).show();
                                                    Intent intent=new Intent(mContext,Card_Activity.class);
                                                    mContext.startActivity(intent);
                                                }

                                            }else {
                                                if (response.code() == 404 || response.code() == 500) {
                                                    progressBarCyclic.setVisibility(View.GONE);
                                                    ((Activity)mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                    Toast.makeText(mContext, R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<CardListingData> call, Throwable t) {
                                        progressBarCyclic.setVisibility(View.GONE);
                                        ((Activity)mContext).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        Toast.makeText(mContext, R.string.server_not_responding, Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }}).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();;







            }
        });

        collection.addView(layout);
        return layout;
    }




    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return cardDetailses.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        ModelObject customPagerEnum = ModelObject.values()[position];
        return mContext.getString(customPagerEnum.getTitleResId());
    }

}
