package com.example.infiny.pickup.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Model.SignUpData;
import com.example.infiny.pickup.R;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    Calendar myCalendar;
    SessionManager sessionManager;
    SharedPreferences  sharedPreferences;
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
        sharedPreferences=getSharedPreferences("Messaging",Context.MODE_PRIVATE);
        tiePassword.setTransformationMethod(new PasswordTransformationMethod());
        tieConfipassword.setTransformationMethod(new PasswordTransformationMethod());
         myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        etDob.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


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
                            .getsignup(etName.getEditText().getText().toString().trim(),
                                    etSurname.getEditText().getText().toString().trim(),
                                    etEmail.getEditText().getText().toString().trim(),
                                    etPassword.getEditText().getText().toString().trim(),
                                    etDob.getEditText().getText().toString().trim(),
                                    etPostcode.getEditText().getText().toString().trim(),
                                    etAdd.getEditText().getText().toString().trim(),
                                    etCity.getEditText().getText().toString().trim(),
                                    sharedPreferences.getString("FcmId",null));
                    call.enqueue(new Callback<SignUpData>() {
                        @Override
                        public void onResponse(Call<SignUpData> call, Response<SignUpData> response) {
                            if (response != null) {
                                signUpData = response.body();
                                if (signUpData != null) {
                                    if (signUpData.getError().equals("true")) {
                                        progressBarCyclic.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        Toast.makeText(context, signUpData.getTitle(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        sessionManager.createLoginSession(signUpData.getUser().getFirstname(),signUpData.getUser().getEmail(),signUpData.getUser().getLastname(),signUpData.getToken(),signUpData.getUser().getDob(),signUpData.getUser().getAddress().getPostalCode(),signUpData.getUser().getAddress().getCity(),signUpData.getUser().getAddress().getAddress(),signUpData.getUser().getImageUrl());
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
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        Date todayDate = new Date();
        if(myCalendar.getTime().after(todayDate))
        {
            etDob.getEditText().setError("Please Enter Valid Date Of Birth");

        }
        else
        {
            etDob.getEditText().setError(null);
            etDob.getEditText().setText(sdf.format(myCalendar.getTime()));
        }
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
        if (!isValidEmail(etEmail.getEditText().getText().toString())) {
            etEmail.getEditText().setError("Email Is Not Valid ");
            status = false;
        }
        if (TextUtils.isEmpty(etEmail.getEditText().getText().toString().trim())) {
            etEmail.getEditText().setError("Please Enter Email");
            status = false;
        }
        if (TextUtils.isEmpty(etPassword.getEditText().getText().toString().trim())) {
            etPassword.getEditText().setError("Please Enter Password");
            status = false;
        }
        if (TextUtils.isEmpty(etConfirmpassword.getEditText().getText().toString().trim())) {
            etConfirmpassword.getEditText().setError("Please Enter Confirm Password");
            status = false;
        }
        if (TextUtils.isEmpty(etName.getEditText().getText().toString().trim())) {
            etName.getEditText().setError("Please Enter Name");
            status = false;
        }
        if (TextUtils.isEmpty(etSurname.getEditText().getText().toString().trim())) {
            etSurname.getEditText().setError("Please Enter Surname");
            status = false;
        }

        if (!etPassword.getEditText().getText().toString().trim().equals(etConfirmpassword.getEditText().getText().toString().trim())) {
            etConfirmpassword.getEditText().setError("Password & Confirm Password Should Be Same");
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
                onBackPressed();
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
