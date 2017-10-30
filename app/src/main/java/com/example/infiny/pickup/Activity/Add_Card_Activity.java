package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.infiny.pickup.Helpers.CreditCardFormatTextWatcher;
import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Model.AddCardData;
import com.example.infiny.pickup.Model.CafeListingData;
import com.example.infiny.pickup.R;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.security.AccessController.getContext;

public class Add_Card_Activity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBar_cyclic)
    ProgressBar progressBarCyclic;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.tie_email)
    EditText tieEmail;
    @BindView(R.id.et_cardholder)
    TextInputLayout etCardholder;
    @BindView(R.id.tie_password)
    EditText tiePassword;
    @BindView(R.id.et_cardNUmber)
    TextInputLayout etCardNUmber;
    @BindView(R.id.tie_confipassword)
    EditText tieConfipassword;
    @BindView(R.id.et_exprireDate)
    TextInputLayout etExprireDate;
    @BindView(R.id.tie_name)
    EditText tieName;
    @BindView(R.id.et_cvv)
    TextInputLayout etCvv;
    @BindView(R.id.btAdd)
    Button btAdd;
    Context context;
    Boolean  fromOrder=false;
    Retrofit retroFitClient;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    AddCardData addCardData;
    private CreditCardFormatTextWatcher tv;
    Boolean status;
    String sid,tittle,image;
    String a;
    int keyDel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__card_);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add New Card");
        context=this;
        sessionManager=new SessionManager(context);
        sharedPreferences=getSharedPreferences(sessionManager.PREF_NAME,0);
        tv = new CreditCardFormatTextWatcher(etCardNUmber.getEditText());
        etCardNUmber.getEditText().addTextChangedListener(new TextWatcher() {
            int pos;
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

                boolean flag = true;
                String eachBlock[] = etCardNUmber.getEditText().getText().toString().split("-");
                for (int i = 0; i < eachBlock.length; i++) {
                    if (eachBlock[i].length() > 4) {
                        flag = false;
                    }
                }
                if (flag) {

                    etCardNUmber.getEditText().setOnKeyListener(new View.OnKeyListener() {

                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {

                            if (keyCode == KeyEvent.KEYCODE_DEL)
                                keyDel = 1;
                            return false;
                        }
                    });

                    if (keyDel == 0) {

                        if (((etCardNUmber.getEditText().getText().length() + 1) % 5) == 0) {

                            if (etCardNUmber.getEditText().getText().toString().split("-").length <= 3) {
                                etCardNUmber.getEditText().setText(etCardNUmber.getEditText().getText() + "-");
                                etCardNUmber.getEditText().setSelection(etCardNUmber.getEditText().getText().length());
                            }
                        }
                        a = etCardNUmber.getEditText().getText().toString();
                    } else {
                        a = etCardNUmber.getEditText().getText().toString();
                        keyDel = 0;
                    }

                } else {
                    etCardNUmber.getEditText().setText(a);
                }

                }



            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
                pos=etCardNUmber.getEditText().getText().length();

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
        etExprireDate.getEditText().addTextChangedListener(new TextWatcher() {
     int pos;
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

                if(etExprireDate.getEditText().getText().length()==2 && pos!=3)
                {   etExprireDate.getEditText().setText(etExprireDate.getEditText().getText().toString()+"/");
                    etExprireDate.getEditText().setSelection(3);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
                pos=etExprireDate.getEditText().getText().length();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
        Intent intent=getIntent();
        fromOrder=intent.getBooleanExtra("fromOrder",false);
        sid=intent.getStringExtra("sid");
        tittle=intent.getStringExtra("tittle");
        image=intent.getStringExtra("image");



        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(submitForm()) {

                    String[] expiredate = etExprireDate.getEditText().getText().toString().split("/");
                    final int month = Integer.parseInt(expiredate[0]);
                    final int year = Integer.parseInt(expiredate[1]);
                    if(month>12)
                    {
                        etExprireDate.getEditText().setError("Please Enter Valid Expire Date");
                    }
                    else {
                        progressBarCyclic.setVisibility(View.VISIBLE);
                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    Card card = new Card(
                            etCardNUmber.getEditText().getText().toString().trim(),
                            month, year,
                            etCvv.getEditText().getText().toString());

                    card.validateNumber();
                    card.validateCVC();
                    card.setName(etCardholder.getEditText().getText().toString().trim());
                        Stripe stripe = new Stripe(context, "pk_test_YITKZXIVRXcx9u5iIHj3fV56");
                        stripe.createToken(
                                card,
                                new TokenCallback() {
                                    public void onSuccess(Token token) {

                                        retroFitClient = new RetroFitClient(context).getBlankRetrofit();
                                        Call<AddCardData> call = retroFitClient
                                                .create(ApiIntegration.class)
                                                .addCard(sharedPreferences.getString("token", null),
                                                        token.getId());
                                        call.enqueue(new Callback<AddCardData>() {

                                            @Override
                                            public void onResponse(Call<AddCardData> call, Response<AddCardData> response) {
                                                if (response != null) {
                                                    addCardData = response.body();
                                                    if (addCardData != null) {
                                                        if (addCardData.getError().equals("true") )  {
                                                            if(addCardData.getTitle().equals("Duplicate Card"))
                                                            {
                                                                progressBarCyclic.setVisibility(View.GONE);
                                                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                                Toast.makeText(context, "A card with the same number already exists" +
                                                                        "", Toast.LENGTH_SHORT).show();
                                                            }
                                                            else{
                                                                progressBarCyclic.setVisibility(View.GONE);
                                                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                                Toast.makeText(context, "Card added successfully", Toast.LENGTH_SHORT).show();
                                                            }

                                                        } else {
                                                            progressBarCyclic.setVisibility(View.GONE);
                                                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                            if (fromOrder) {
                                                                Intent intent = new Intent(context, OrderActivity.class);
                                                                intent.putExtra("fromPage","add_card_activity");
                                                                intent.putExtra("sid", sid);
                                                                intent.putExtra("tittle", tittle);
                                                                intent.putExtra("image", image);
                                                               startActivity(intent);
                                                                finish();
                                                            } else {
                                                                Intent intent = new Intent(context, Card_Activity.class);

                                                                startActivity(intent);
                                                                finish();

                                                            }

                                                        }

                                                    } else {
                                                        if (response.code() == 404 || response.code() == 500) {
                                                            progressBarCyclic.setVisibility(View.GONE);
                                                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                            Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<AddCardData> call, Throwable t) {

                                            }
                                        });

                                    }

                                    public void onError(Exception error) {
                                        progressBarCyclic.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        String erroe = error.getLocalizedMessage();
                                        if(error.getLocalizedMessage().equals("Your card number is incorrect."))
                                        {
                                            etCardNUmber.getEditText().setError("Please Enter Valid Credit Card Number");
                                        }
                                        else if(error.getLocalizedMessage().equals("Your card's security code is invalid."))
                                        {
                                            etCvv.getEditText().setError("Please Enter Valid CVV Code");
                                        }
                                        else if(error.getLocalizedMessage().equals("Your card's expiration year is invalid."))
                                        {
                                            etExprireDate.getEditText().setError("Please Enter Valid Expiration Date");

                                        }

                                    }
                                }
                        );

                }}
            }
        });

    }


    public static class FourDigitCardFormatWatcher implements TextWatcher {

        // Change this to what you want... ' ', '-' etc..
        private static final char space = ' ';

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(count%4==0)
            {

            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Remove spacing char
            if (s.length() > 0 && (s.length() % 5) == 0) {
                final char c = s.charAt(s.length() - 1);
                if (space == c) {
                    s.delete(s.length() - 1, s.length());
                }
            }
            // Insert char where needed.
            if (s.length() > 0 && (s.length() % 5) == 0) {
                char c = s.charAt(s.length() - 1);
                // Only if its a digit where there should be a space we insert a space
                if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                    s.insert(s.length() - 1, String.valueOf(space));
                }
            }
        }
    }
    public boolean submitForm() {
        status = true;

        if (TextUtils.isEmpty(etCardNUmber.getEditText().getText().toString().trim())) {
            etCardNUmber.getEditText().setError("Please Enter Credit Card Number");
            status = false;
        }
        if (etCardNUmber.getEditText().getText().toString().trim().length()<19) {
            etCardNUmber.getEditText().setError("Credit Card Number Must Be 16 Digits");
            status = false;
        }
        if (TextUtils.isEmpty(etCardholder.getEditText().getText().toString().trim())) {
            etCardholder.getEditText().setError("Please Enter Cardholder Name");
            status = false;
        }
        if (TextUtils.isEmpty(etExprireDate.getEditText().getText().toString().trim())) {
            etExprireDate.getEditText().setError("Please Enter Expiration Date");
            status = false;
        }
        if (TextUtils.isEmpty(etCvv.getEditText().getText().toString().trim())) {
            etCvv.getEditText().setError("Please Enter CVV Code");
            status = false;
        }




        return status;
    }

}
