package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.infiny.pickup.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appbar.setOutlineProvider(null);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etPassword.setVisibility(View.GONE);
        etConfirmpassword.setVisibility(View.GONE);
        btSignup.setText("Save");
        etEmail.getEditText().setText("johnnysmith@gmail.com");
        etName.getEditText().setText("johnny");
        etSurname.getEditText().setText("smith");
        etDob.getEditText().setText("29/03/1991");
        etAdd.getEditText().setText("701,Affairs");
        etPostcode.getEditText().setText("Navi mumbai");
        etCity.getEditText().setText("400604");
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


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
