package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infiny.pickup.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FpotpActivity extends AppCompatActivity {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.tw_header)
    TextView twHeader;
    @BindView(R.id.tie_otp)
    EditText tieOtp;
    @BindView(R.id.et_otp)
    TextInputLayout etOtp;
    @BindView(R.id.bt_otp)
    Button btOtp;
    @BindView(R.id.tw_recent)
    TextView twRecent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpotp);
        ButterKnife.bind(this);
        btOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FpotpActivity.this,fpresetpassword.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
