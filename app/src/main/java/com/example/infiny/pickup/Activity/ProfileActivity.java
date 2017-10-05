package com.example.infiny.pickup.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.infiny.pickup.BuildConfig;
import com.example.infiny.pickup.Helpers.RetroFitClient;
import com.example.infiny.pickup.Helpers.SessionManager;
import com.example.infiny.pickup.Interfaces.ApiIntegration;
import com.example.infiny.pickup.Model.EditProfileData;
import com.example.infiny.pickup.Model.ExifUtils;
import com.example.infiny.pickup.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.logo)
    ImageView profile;
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
    Button btsave;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    Calendar myCalendar;
    Context context;
    Intent intent;
    Uri imageUri;
    File file;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    Retrofit retroFitClient;
    EditProfileData editProfileData;

    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int SELECT_PICTURE = 1;
    private static final int CAMERA_REQUEST = 1888;
    String mCurrentPhotoPath;
    @BindView(R.id.progressBar_cyclic)
    ProgressBar progressBarCyclic;

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
        btsave.setText("Save");
        context = this;
        sessionManager = new SessionManager(context);
        sharedPreferences = getSharedPreferences(sessionManager.PREF_NAME, 0);
        Picasso.with(context)
                .load(sharedPreferences.getString(sessionManager.image, null))
                .placeholder(R.drawable.ic_person_black_48dp)
                .into(profile);
        etEmail.getEditText().setText(sharedPreferences.getString(sessionManager.email, null));
        etName.getEditText().setText(sharedPreferences.getString(sessionManager.name, null));
        etSurname.getEditText().setText(sharedPreferences.getString(sessionManager.surname,null));
        etDob.getEditText().setText(sharedPreferences.getString(sessionManager.dob, null));
        etAdd.getEditText().setText(sharedPreferences.getString(sessionManager.address, null));
        etCity.getEditText().setText(sharedPreferences.getString(sessionManager.city, null));
        etPostcode.getEditText().setText(sharedPreferences.getString(sessionManager.postalcode, null));
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


        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody fbody = null;
                if(file!=null) {
                     fbody = RequestBody.create(MediaType.parse("image/*"), file);
                }
                RequestBody token = RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("token",null));
                RequestBody email = RequestBody.create(MediaType.parse("text/plain"), etEmail.getEditText().getText().toString());
                RequestBody name = RequestBody.create(MediaType.parse("text/plain"), etName.getEditText().getText().toString());
                RequestBody surname = RequestBody.create(MediaType.parse("text/plain"), etSurname.getEditText().getText().toString());
                RequestBody dob = RequestBody.create(MediaType.parse("text/plain"), etDob.getEditText().getText().toString());
                RequestBody address = RequestBody.create(MediaType.parse("text/plain"), etAdd.getEditText().getText().toString());
                RequestBody city = RequestBody.create(MediaType.parse("text/plain"), etCity.getEditText().getText().toString());
                RequestBody postcode = RequestBody.create(MediaType.parse("text/plain"), etPostcode.getEditText().getText().toString());
                progressBarCyclic.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                retroFitClient = new RetroFitClient(context).getBlankRetrofit();

                Call<EditProfileData> call = retroFitClient
                        .create(ApiIntegration.class)
                        .editProfile(sharedPreferences.getString("token", null),
                                     fbody,token,email,name,surname,dob,address,city,postcode);
                call.enqueue(new Callback<EditProfileData>() {

                    @Override
                    public void onResponse(Call<EditProfileData> call, Response<EditProfileData> response) {
                        if (response != null) {
                            editProfileData = response.body();
                            if (editProfileData != null) {
                                if (editProfileData.getError().equals("true")) {
                                    progressBarCyclic.setVisibility(View.GONE);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    Toast.makeText(context, "trueeeeeee", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show();
                                    sessionManager.createLoginSession(editProfileData.getUser().getFirstname(),editProfileData.getUser().getEmail(),editProfileData.getUser().getLastname(),sharedPreferences.getString("token", null),editProfileData.getUser().getDob(),editProfileData.getUser().getAddress().getPostalCode(),editProfileData.getUser().getAddress().getCity(),editProfileData.getUser().getAddress().getAddress(),editProfileData.getUser().getImageUrl());
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
                    public void onFailure(Call<EditProfileData> call, Throwable t) {
                        progressBarCyclic.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Toast.makeText(context,R.string.Something_went_wrong,Toast.LENGTH_SHORT);

                    }
                });

            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });


    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
//                        File imageFile = new File(imageFilePath);
                        try {

                            imageUri = FileProvider.getUriForFile(context,
                                    BuildConfig.APPLICATION_ID + ".provider",
                                    createImageFile());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        try {

                            imageUri = Uri.fromFile(createImageFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
//                    imageUri =Uri.fromFile(imageFile);
//                    imageUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CAMERA_REQUEST);

                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                }
            }
        });

        builder.show();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    public Bitmap fromGallaryMultiple(Uri data) {
        Bitmap photo11 = null;
        Uri imagepath = data;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inSampleSize = 2;
            photo11 = BitmapFactory.decodeStream(getContentResolver().openInputStream(imagepath), null, null);

            Bitmap scaled1 = ExifUtils.rotateBitmap(ExifUtils.getPath(context, imagepath), photo11);

            return scaled1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDob.getEditText().setText(sdf.format(myCalendar.getTime()));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                try {
                    Uri contentURI = data.getData();
                    file = new File(contentURI.getPath());
                    profile.setImageBitmap(fromGallaryMultiple(contentURI));

                } catch (NullPointerException e) {
                }
            }
        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    file = new File(imageUri.getPath());
                    profile.setImageBitmap(fromGallaryMultiple((imageUri)));
                } else {
                    file = new File(imageUri.getPath());
                    profile.setImageBitmap(fromGallaryMultiple((imageUri)));
                }
            } catch (NullPointerException e) {
            }
        }
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
