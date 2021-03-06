package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.Helpers.ConnectivityReceiver;
import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Model.LoginData;
import com.example.infiny.pickup.Model.SignUpData;
import com.example.infiny.pickup.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tw_email)
    TextView twEmail;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.tw_password)
    TextView twPassword;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.rel_edit_text)
    RelativeLayout relEditText;
    @BindView(R.id.bt_signup)
    Button btSignup;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.button_layout)
    RelativeLayout buttonLayout;
    @BindView(R.id.tw_forgot_password)
    TextView twForgotPassword;
    @BindView(R.id.progressBar_cyclic)
    ProgressBar progressBarCyclic;
    private boolean status;
    Context context;
    Retrofit retroFitClient;
    LoginData loginData;
    SessionManager sessionManager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        context=this;
        sessionManager=new SessionManager(this);
        sharedPreferences = getSharedPreferences("Messaging", Context.MODE_PRIVATE);
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
        twForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FpemailsubmitActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (submitForm()) {
                    progressBarCyclic.setVisibility(View.VISIBLE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    retroFitClient = new RetroFitClient(context).getBlankRetrofit();
                    Call<LoginData> call = retroFitClient
                            .create(ApiIntegration.class)
                            .getsignin(etEmail.getText().toString(),
                                    password.getText().toString(),
                                    sharedPreferences.getString("FcmId", null));
                    call.enqueue(new Callback<LoginData>() {

                        @Override
                        public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                            if (response != null) {
                                loginData = response.body();
                                if (loginData != null) {
                                    if (loginData.getError().equals("true")) {
                                        progressBarCyclic.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        Toast.makeText(context, loginData.getTitle(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        sessionManager.createLoginSession(loginData.getUser().getFirstname(), loginData.getUser().getEmail(), loginData.getUser().getLastname(), loginData.getToken(), loginData.getUser().getDob(), loginData.getUser().getAddress().getPostalCode(), loginData.getUser().getAddress().getCity(), loginData.getUser().getAddress().getAddress(), loginData.getUser().getImageUrl(),loginData.getUser().getContact());
                                        progressBarCyclic.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                } else {
                                    if (response.code() == 404 || response.code() == 500) {
                                        progressBarCyclic.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {

                                progressBarCyclic.setVisibility(View.GONE);
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                Toast.makeText(context, R.string.server_not_responding, Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<LoginData> call, Throwable t) {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(context, R.string.Something_went_wrong, Toast.LENGTH_SHORT);
                        }
                    });

                }
            }


        });

    }
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Connected to Internet.";
            color = Color.WHITE;
        } else {
            message = "Please check your internet connection & try again";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    public boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean submitForm() {
        status = true;
        if (!isValidEmail(etEmail.getText().toString())) {
            etEmail.setError("Email Is Not Valid ");
            status = false;
        }
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Please Enter Your Email");
            status = false;
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            password.setError("Please Enter Password");
            status = false;
        }

        return status;
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
