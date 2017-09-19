package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
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
import com.example.infiny.pickup.Model.FpResetPasswordData;
import com.example.infiny.pickup.Model.LoginData;
import com.example.infiny.pickup.Model.VerifyFpOtp;
import com.example.infiny.pickup.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class fpresetpassword extends AppCompatActivity {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.tw_header)
    TextView twHeader;
    @BindView(R.id.tw_subheader)
    TextView twSubheader;
    @BindView(R.id.tie_newpassword)
    EditText tieNewpassword;
    @BindView(R.id.et_newpassword)
    TextInputLayout etNewpassword;
    @BindView(R.id.tie_confirmpassword)
    EditText tieConfirmpassword;
    @BindView(R.id.et_confirmpassword)
    TextInputLayout etConfirmpassword;
    @BindView(R.id.bt_submit)
    Button btSubmit;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    Retrofit retroFitClient;
    FpResetPasswordData verifyFpOtp;
    SessionManager sessionManager;
    @BindView(R.id.progressBar_cyclic)
    ProgressBar progressBarCyclic;
    Boolean status;
    Context context;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpresetpassword);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appbar.setOutlineProvider(null);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context=this;
        Intent intent=getIntent();
         token=intent.getStringExtra("token");
        tieNewpassword.setTransformationMethod(new PasswordTransformationMethod());
        tieConfirmpassword.setTransformationMethod(new PasswordTransformationMethod());
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (submitForm()) {
                    progressBarCyclic.setVisibility(View.VISIBLE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    retroFitClient = new RetroFitClient(context).getBlankRetrofit();
                    Call<FpResetPasswordData> call = retroFitClient
                            .create(ApiIntegration.class)
                            .getResetpassword(token,
                                    etConfirmpassword.getEditText().getText().toString());
                    call.enqueue(new Callback<FpResetPasswordData>() {

                        @Override
                        public void onResponse(Call<FpResetPasswordData> call, Response<FpResetPasswordData> response) {
                            if (response != null) {
                                verifyFpOtp = response.body();
                                if (verifyFpOtp != null) {
                                    if (verifyFpOtp.getError().equals("true")) {
                                        progressBarCyclic.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        Toast.makeText(context,verifyFpOtp.getTitle(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, R.string.Password_set, Toast.LENGTH_SHORT).show();
                                        progressBarCyclic.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        Intent intent = new Intent(fpresetpassword.this, LoginActivity.class);
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
                        public void onFailure(Call<FpResetPasswordData> call, Throwable t) {

                        }
                    });

                }

            }
        });

    }
    public boolean submitForm() {
        status = true;
        if (TextUtils.isEmpty(etNewpassword.getEditText().getText().toString())) {
            etNewpassword.getEditText().setError("Please enter your email");
            status = false;
        }
        if (TextUtils.isEmpty(etConfirmpassword.getEditText().getText().toString())) {
            etConfirmpassword.getEditText().setError("Please enter password");
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
