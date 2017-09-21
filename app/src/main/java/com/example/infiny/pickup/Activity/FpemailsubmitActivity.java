package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Model.ForgotPasswordData;
import com.example.infiny.pickup.Model.LoginData;
import com.example.infiny.pickup.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FpemailsubmitActivity extends AppCompatActivity {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.tw_header)
    TextView twHeader;
    @BindView(R.id.tie_email)
    EditText tieEmail;
    @BindView(R.id.et_email)
    TextInputLayout etEmail;
    @BindView(R.id.bt_submit)
    Button bt_submit;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    Boolean status;
    Context context;
    @BindView(R.id.progressBar_cyclic)
    ProgressBar progressBarCyclic;
    Retrofit retroFitClient;
    ForgotPasswordData forgotPasswordData;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpemailsubmit);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appbar.setOutlineProvider(null);
        getSupportActionBar().setTitle("");
        sessionManager=new SessionManager(this);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (submitForm()) {
                    progressBarCyclic.setVisibility(View.VISIBLE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    retroFitClient = new RetroFitClient(context).getBlankRetrofit();
                    Call<ForgotPasswordData> call = retroFitClient
                            .create(ApiIntegration.class)
                            .getForgotPassword(etEmail.getEditText().getText().toString());
                    call.enqueue(new Callback<ForgotPasswordData>() {

                        @Override
                        public void onResponse(Call<ForgotPasswordData> call, Response<ForgotPasswordData> response) {
                            if (response != null) {
                                forgotPasswordData = response.body();
                                if (forgotPasswordData != null) {
                                    if (forgotPasswordData.getError().equals("true")) {
                                        progressBarCyclic.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        Toast.makeText(context,forgotPasswordData.getTitle(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, R.string.email_sent_please_check, Toast.LENGTH_SHORT).show();
                                        progressBarCyclic.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        Intent intent = new Intent(FpemailsubmitActivity.this, FpotpActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                }else {
                                    if (response.code() == 404 || response.code() == 500) {
                                        progressBarCyclic.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ForgotPasswordData> call, Throwable t) {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(context,R.string.Something_went_wrong,Toast.LENGTH_SHORT);

                        }
                    });

                }


            }
        });

    }

    public boolean submitForm() {
        status = true;
        if (TextUtils.isEmpty(etEmail.getEditText().getText().toString())) {
            etEmail.getEditText().setError("Please enter your email");
            status = false;
        }

        return status;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();  // optional depending on your needs
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
