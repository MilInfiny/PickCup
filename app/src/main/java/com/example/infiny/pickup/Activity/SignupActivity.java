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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Model.SignUpData;
import com.example.infiny.pickup.R;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.tie_email)
    EditText tieEmail;
    @BindView(R.id.et_email)
    TextInputLayout etEmail;
    @BindView(R.id.tie_password)
    EditText tiePassword;
    @BindView(R.id.et_password)
    TextInputLayout etPassword;
    @BindView(R.id.tie_confipassword)
    EditText tieConfipassword;
    @BindView(R.id.et_confirmpassword)
    TextInputLayout etConfirmpassword;
    @BindView(R.id.tie_name)
    EditText tieName;
    @BindView(R.id.et_name)
    TextInputLayout etName;
    @BindView(R.id.tie_surname)
    EditText tieSurname;
    @BindView(R.id.et_surname)
    TextInputLayout etSurname;
    @BindView(R.id.tie_dob)
    EditText tieDob;
    @BindView(R.id.et_dob)
    TextInputLayout etDob;
    @BindView(R.id.tie_add)
    EditText tieAdd;
    @BindView(R.id.et_add)
    TextInputLayout etAdd;
    @BindView(R.id.tie_postcode)
    EditText tiePostcode;
    @BindView(R.id.et_postcode)
    TextInputLayout etPostcode;
    @BindView(R.id.tie_city)
    EditText tieCity;
    @BindView(R.id.et_city)
    TextInputLayout etCity;
    @BindView(R.id.bt_signup)
    Button btSignup;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.progressBar_cyclic)
    ProgressBar progressBarCyclic;
    private boolean status;
    Retrofit retroFitClient;
    private Context context;
    SignUpData signUpData;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appbar.setOutlineProvider(null);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        sessionManager = new SessionManager(context);
        tiePassword.setTransformationMethod(new PasswordTransformationMethod());
        tieConfipassword.setTransformationMethod(new PasswordTransformationMethod());
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (submitForm()) {
                    progressBarCyclic.setVisibility(View.VISIBLE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    retroFitClient = new RetroFitClient(context).getBlankRetrofit();
                    Call<SignUpData> call = retroFitClient
                            .create(ApiIntegration.class)
                            .getsignup(etName.getEditText().getText().toString(),
                                    etSurname.getEditText().getText().toString(),
                                    etEmail.getEditText().getText().toString(),
                                    etPassword.getEditText().getText().toString(),
                                    etDob.getEditText().getText().toString(),
                                    etPostcode.getEditText().getText().toString(),
                                    etAdd.getEditText().getText().toString(),
                                    etCity.getEditText().getText().toString());
                    call.enqueue(new Callback<SignUpData>() {
                        @Override
                        public void onResponse(Call<SignUpData> call, Response<SignUpData> response) {
                            if (response != null) {
                                signUpData = response.body();
                                if (signUpData != null) {
                                    if (response.code() == 404 || response.code() == 500) {
                                        progressBarCyclic.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show();
                                        sessionManager.createLoginSession(signUpData.getUser().getFirstname(),signUpData.getUser().getEmail(),signUpData.getUser().getLastname());
                                        progressBarCyclic.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        Intent intent = new Intent(context, MainActivity.class);
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
                        public void onFailure(Call<SignUpData> call, Throwable t) {
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
        Log.d("password", etPassword.getEditText().getText().toString());
        Log.d("samepasword", String.valueOf(!etPassword.getEditText().getText().equals(etConfirmpassword.getEditText().getText())));
        if (TextUtils.isEmpty(etEmail.getEditText().getText().toString())) {
            etEmail.setError("Please enter your User ID");
            status = false;
        }
        if (TextUtils.isEmpty(etPassword.getEditText().getText().toString())) {
            etPassword.setError("Please enter your Pin");
            status = false;
        }
        if (TextUtils.isEmpty(etConfirmpassword.getEditText().getText().toString())) {
            etConfirmpassword.setError("Please enter your Org ID");
            status = false;
        }
        if (TextUtils.isEmpty(etName.getEditText().getText().toString())) {
            etName.setError("Please enter your Org ID");
            status = false;
        }
        if (TextUtils.isEmpty(etSurname.getEditText().getText().toString())) {
            etSurname.setError("Please enter your Org ID");
            status = false;
        }
        if (TextUtils.isEmpty(etDob.getEditText().getText().toString())) {
            etDob.setError("Please enter your Org ID");
            status = false;
        }
        if (TextUtils.isEmpty(etAdd.getEditText().getText().toString())) {
            etAdd.setError("Please enter your Org ID");
            status = false;
        }
        if (TextUtils.isEmpty(etCity.getEditText().getText().toString())) {
            etCity.setError("Please enter your Org ID");
            status = false;
        }
        if (TextUtils.isEmpty(etPostcode.getEditText().getText().toString())) {
            etPostcode.setError("Please enter your Org ID");
            status = false;
        }
        if (!etPassword.getEditText().getText().toString().equals(etConfirmpassword.getEditText().getText().toString())) {
            etPassword.setError("password & confirm password should be same");
            etConfirmpassword.setError("password & confirm password should be same");
            status = false;
        }


        return status;
    }

    public void getData() {


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
    @Override
    public void onBackPressed()
    {
        Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
        startActivity(intent);
    }

}
