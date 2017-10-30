package com.example.infiny.pickup.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
    Bitmap profile_image;
    Context context;
    Intent intent;
    Uri imageUri;
    Boolean dobstatus=true;
    @BindView(R.id.tie_contactNumber)
    EditText tieContactNumber;
    @BindView(R.id.et_contactNumber)
    TextInputLayout etContactNumber;
    private boolean status, removephoto = false;
    File file;
    Boolean tablet;
    SharedPreferences sharedPreferences;
    SessionManager sessionManager;
    Retrofit retroFitClient;
    EditProfileData editProfileData;
    String imageurl;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int SELECT_PICTURE = 1;
    private static final int CAMERA_REQUEST = 1888;
    String mCurrentPhotoPath;
    @BindView(R.id.progressBar_cyclic)
    ProgressBar progressBarCyclic;
    String imageexist =null;

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
        if (!sharedPreferences.getString("image", null).equals("noImage")) {
            if (isTablet(context)) {
                new DownloadImage().execute(sharedPreferences.getString("image", null) + "_large.jpg");
            } else {
                new DownloadImage().execute(sharedPreferences.getString("image", null) + "_small.jpg");
            }
            ;

        }


        etEmail.getEditText().setText(sharedPreferences.getString(sessionManager.email, null));
        etName.getEditText().setText(sharedPreferences.getString(sessionManager.name, null));
        etSurname.getEditText().setText(sharedPreferences.getString(sessionManager.surname, null));
        etDob.getEditText().setText(sharedPreferences.getString(sessionManager.dob, null));
        etAdd.getEditText().setText(sharedPreferences.getString(sessionManager.address, null));
        etCity.getEditText().setText(sharedPreferences.getString(sessionManager.city, null));
        etPostcode.getEditText().setText(sharedPreferences.getString(sessionManager.postalcode, null));
        etContactNumber.getEditText().setText(sharedPreferences.getString(sessionManager.contactNumber,null));
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
                if (submitForm() && dobstatus) {
                    RequestBody fbody = null;

                    if (file != null) {
                        fbody = RequestBody.create(MediaType.parse("image/*"), file);
                        imageexist = "true";
                        sessionManager.setImageUpload("true");
                    } else {
                        imageexist = "false";
                    }
                    RequestBody imageUpload = RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString(sessionManager.imageUpload, null));
                    RequestBody token = RequestBody.create(MediaType.parse("text/plain"), sharedPreferences.getString("token", null));
                    RequestBody email = RequestBody.create(MediaType.parse("text/plain"), etEmail.getEditText().getText().toString().trim());
                    RequestBody name = RequestBody.create(MediaType.parse("text/plain"), etName.getEditText().getText().toString().trim());
                    RequestBody surname = RequestBody.create(MediaType.parse("text/plain"), etSurname.getEditText().getText().toString().trim());
                    RequestBody contact = RequestBody.create(MediaType.parse("text/plain"), etContactNumber.getEditText().getText().toString().trim());

                    RequestBody dob = RequestBody.create(MediaType.parse("text/plain"), etDob.getEditText().getText().toString().trim());
                    RequestBody address = RequestBody.create(MediaType.parse("text/plain"), etAdd.getEditText().getText().toString().trim());
                    RequestBody city = RequestBody.create(MediaType.parse("text/plain"), etCity.getEditText().getText().toString().trim());
                    RequestBody postcode = RequestBody.create(MediaType.parse("text/plain"), etPostcode.getEditText().getText().toString().trim());
                    progressBarCyclic.setVisibility(View.VISIBLE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    retroFitClient = new RetroFitClient(context).getBlankRetrofit();

                    Call<EditProfileData> call = retroFitClient
                            .create(ApiIntegration.class)
                            .editProfile(sharedPreferences.getString("token", null), imageexist,
                                    fbody, token, email, name, surname, imageUpload, dob,contact, address, city, postcode);
                    call.enqueue(new Callback<EditProfileData>() {

                        @Override
                        public void onResponse(Call<EditProfileData> call, Response<EditProfileData> response) {
                            if (response != null) {
                                editProfileData = response.body();
                                if (editProfileData != null) {
                                    if (editProfileData.getError().equals("true")) {
                                        progressBarCyclic.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    } else {

                                        sessionManager.createLoginSession(editProfileData.getUser().getFirstname(), editProfileData.getUser().getEmail(), editProfileData.getUser().getLastname(), sharedPreferences.getString("token", null), editProfileData.getUser().getDob(), editProfileData.getUser().getAddress().getPostalCode(), editProfileData.getUser().getAddress().getCity(), editProfileData.getUser().getAddress().getAddress(), editProfileData.getUser().getImageUrl(),editProfileData.getUser().getContact());
                                        if (isTablet(context)) {
                                            new DownloadImage().execute(editProfileData.getUser().getImageUrl() + "_large.jpg");
                                        } else {
                                            new DownloadImage().execute(editProfileData.getUser().getImageUrl() + "_small.jpg");
                                        }
                                        ;

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
                        public void onFailure(Call<EditProfileData> call, Throwable t) {
                            progressBarCyclic.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(context, R.string.Something_went_wrong, Toast.LENGTH_SHORT);

                        }
                    });

                }
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();

            }
        });


    }
    private void startCropActivity(@NonNull Uri uri) {
        Intent intent=new Intent(context,CropImageActivity.class);
        intent.putExtra("sourceUri",uri.toString());
        startActivityForResult(intent,2);
    }


    public Bitmap loadImageBitmap(Context context, String imageName) {
        Bitmap bitmap = null;
        FileInputStream fiStream;
        try {
            fiStream = context.openFileInput(imageName);
            bitmap = BitmapFactory.decodeStream(fiStream);
            fiStream.close();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 3, Something went wrong!");
            e.printStackTrace();
        }
        return bitmap;
    }

    public void saveImage(Context context, Bitmap b, String imageName) {
        FileOutputStream foStream;
        try {
            foStream = this.openFileOutput(imageName, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, foStream);
            foStream.close();
            progressBarCyclic.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Log.d("saveImage", "Exception 2, Something went wrong!" + e);
            e.printStackTrace();
        }
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        private String TAG = "DownloadImage";

        private Bitmap downloadImageBitmap(String sUrl) {
            Log.d("bitmap url", sUrl);
            Bitmap bitmap = null;

            okhttp3.Response response;
            try {
                OkHttpClient client = RetroFitClient.getOkHTTPClient();
                Request request = new Request.Builder()
                        .url(sUrl)
                        .build();

                response = client.newCall(request).execute();

                FileOutputStream foStream;
                try {
                    foStream = getApplicationContext().openFileOutput("user_Image.jpg", Context.MODE_PRIVATE);
                    foStream.write(response.body().bytes());
                    foStream.close();
                } catch (Exception e) {
                    Log.d("saveImage", "Exception 2, Something went wrong!" + e);
                    e.printStackTrace();
                }

            } catch (Exception e) {
                Log.d("Error decodeing", e.getMessage());
                e.printStackTrace();
            }


            return bitmap;


        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImageBitmap(params[0]);
        }

        protected void onPostExecute(Bitmap result) {
            //saveImage(getApplicationContext(), result, "user_Image.png");
            progressBarCyclic.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            if (imageexist !=null || removephoto) {

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                profile_image = loadImageBitmap(getApplicationContext(), "user_Image.jpg");
                profile.setImageBitmap(profile_image);
            }
        }
    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose From Library", "Remove Photo"};
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

                } else if (items[item].equals("Choose From Library")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                } else if (items[item].equals("Remove Photo")) {
                    removephoto = true;
                    imageexist = "false";
                    sessionManager.setImageUpload("false");
                    profile.setImageResource(R.drawable.ic_person_black_48dp);
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
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        Date todayDate = new Date();
        if (myCalendar.getTime().after(todayDate)) {
            etDob.getEditText().setError("Please enter valid date of birth");
            dobstatus = false;

        } else {
            etDob.getEditText().setError(null);
            etDob.getEditText().setText(sdf.format(myCalendar.getTime()));
            dobstatus=true;
        }


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                try {
                    Uri contentURI = data.getData();
                    startCropActivity(data.getData());
                   /* final int takeFlags = data.getFlags()
                            & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                            | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    String id = contentURI.getLastPathSegment().split(":")[1];
                    final String[] imageColumns = {MediaStore.Images.Media.DATA};
                    final String imageOrderBy = null;
                    Uri uri = getUri();
                    String selectedImagePath = "path";
                    Cursor imageCursor = managedQuery(uri, imageColumns,
                            MediaStore.Images.Media._ID + "=" + id, null, imageOrderBy);
                    if (imageCursor.moveToFirst()) {
                        selectedImagePath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));

                    }
                    Log.e("path", selectedImagePath);
                    file = new File(selectedImagePath);
                 *//*   file=new File(getRealPathFromURI(contentURI,((Activity) context)));*//*
                    profile.setImageBitmap(fromGallaryMultiple(contentURI));*/

                } catch (NullPointerException e) {
                }
            }
        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    startCropActivity(imageUri);
                   /* fromGallaryNog(Uri.parse(mCurrentPhotoPath));
                    final int takeFlags = data.getFlags()
                            & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                            | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    String id = imageUri.getLastPathSegment().split(":")[1];
                    final String[] imageColumns = {MediaStore.Images.Media.DATA};
                    final String imageOrderBy = null;
                    Uri uri = getUri();
                    String selectedImagePath = "path";
                    Cursor imageCursor = managedQuery(uri, imageColumns,
                            MediaStore.Images.Media._ID + "=" + id, null, imageOrderBy);
                    if (imageCursor.moveToFirst()) {
                        selectedImagePath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));

                    }
                    Log.e("path", selectedImagePath);
                    file = new File(selectedImagePath);*/
                } else {
                    startCropActivity(imageUri);
                    /*file = new File(imageUri.getPath());
                    profile.setImageBitmap(fromGallaryMultiple(imageUri));*/
                }
            } catch (NullPointerException e) {
            }
        }
        if(requestCode ==2){
            String sourceuri=data.getStringExtra("sourceuri");
            String path=data.getStringExtra("path");
            if(sourceuri != null) {
                file = new File(path);
                profile.setImageBitmap(fromGallaryMultiple(Uri.parse(sourceuri)));

            }
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
        if (TextUtils.isEmpty(etName.getEditText().getText().toString().trim())) {
            etName.getEditText().setError("Please Enter Name");
            status = false;
        }
        if (TextUtils.isEmpty(etSurname.getEditText().getText().toString().trim())) {
            etSurname.getEditText().setError("Please Enter Surname");
            status = false;
        }
        if (TextUtils.isEmpty(etContactNumber.getEditText().getText().toString().trim())) {
            etContactNumber.getEditText().setError("Please Enter Contact Number");
            status = false;
        }



        return status;
    }

    public byte[] fromGallaryNog(Uri data) {
        Bitmap photo11 = null;
        Uri filePath = data;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inSampleSize = 2;
            photo11 = BitmapFactory.decodeStream(getContentResolver().openInputStream(filePath), null, options);
            Bitmap scaled1 = ExifUtils.rotateBitmap(ExifUtils.getPath(context, filePath), photo11);
            profile.setImageBitmap(scaled1);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            scaled1.compress(Bitmap.CompressFormat.JPEG, 90, bao);
            byte[] ba = bao.toByteArray();
            return ba;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public String getRealPathFromURI(Uri contentURI, Activity context) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = context.managedQuery(contentURI, projection, null,
                null, null);
        if (cursor == null)
            return null;
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        if (cursor.moveToFirst()) {
            String s = cursor.getString(column_index);
            // cursor.close();
            return s;
        }
        // cursor.close();
        return null;
    }

    private Uri getUri() {
        String state = Environment.getExternalStorageState();
        if (!state.equalsIgnoreCase(Environment.MEDIA_MOUNTED))
            return MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackpresss();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackpresss();

        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackpresss() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);

    }

    public byte[] fromCamera() {
        try {
            Bitmap photo;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inSampleSize = 2;
            photo = BitmapFactory.decodeFile(imageUri.getPath(), options);
//            Bitmap scaled = Bitmap.createScaledBitmap(photo, 350, 350, false);
            Bitmap scaled1 = ExifUtils.rotateBitmap(ExifUtils.getPath(context, imageUri), photo);
            profile.setImageBitmap(scaled1);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            scaled1.compress(Bitmap.CompressFormat.JPEG, 70, bao);
            byte[] ba = bao.toByteArray();

            return ba;
        } catch (NullPointerException e) {
            return null;
        }
    }


}
