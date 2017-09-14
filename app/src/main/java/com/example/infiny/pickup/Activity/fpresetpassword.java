package com.example.infiny.pickup.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infiny.pickup.R;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpresetpassword);
        ButterKnife.bind(this);
        tieNewpassword.setTransformationMethod(new PasswordTransformationMethod());
        tieConfirmpassword.setTransformationMethod(new PasswordTransformationMethod());
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(fpresetpassword.this,MainActivity.class);
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
