package com.example.infiny.pickup.Activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infiny.pickup.R;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.isseiaoki.simplecropview.util.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CropImageActivity extends AppCompatActivity {

    @BindView(R.id.cropImageView)
    CropImageView mCropView;
    @BindView(R.id.buttonCencel)
    TextView buttonCencel;
    public static  String path;
    @BindView(R.id.buttonDone)
    TextView buttonDone;
    File file;
    private RectF mFrameRect = null;
    private static final String KEY_FRAME_RECT = "FrameRect";
    Context context;
    Uri mSourceUri;
    private Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.JPEG;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        ButterKnife.bind(this);
        context=this;
        if (savedInstanceState != null) {
            // restore data
            mFrameRect = savedInstanceState.getParcelable(KEY_FRAME_RECT);

        }

        Intent intent = getIntent();
        mSourceUri = Uri.parse(intent.getStringExtra("sourceUri"));
        mCropView.setCropMode(CropImageView.CropMode.SQUARE);
        mCropView.load(mSourceUri)
                .initialFrameRect(mFrameRect)
                .useThumbnail(true)
                .execute(mLoadCallback);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCropView.crop(mSourceUri).execute(mCropCallback);
            }
        });
        buttonCencel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent();
                setResult(2,intent);
                finish();

            }
        });

    }
    private final LoadCallback mLoadCallback = new LoadCallback() {
        @Override
        public void onSuccess() {
            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();

        }
    };
    private final CropCallback mCropCallback = new CropCallback() {
        @Override public void onSuccess(Bitmap cropped) {
            mCropView.save(cropped)
                    .compressFormat(mCompressFormat)
                    .execute(createSaveUri(), mSaveCallback);
        }

        @Override public void onError(Throwable e) {
        }
    };
    public Uri createSaveUri() {
        return createNewUri(context, mCompressFormat);
    }
    public static Uri createNewUri(Context context, Bitmap.CompressFormat format) {
        long currentTimeMillis = System.currentTimeMillis();
        Date today = new Date(currentTimeMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String title = dateFormat.format(today);
        String dirPath = getDirPath();
        String fileName = "scv" + title + "." + getMimeType(format);
         path = dirPath + "/" + fileName;
        File file = new File(path);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/" + getMimeType(format));
        values.put(MediaStore.Images.Media.DATA, path);
        long time = currentTimeMillis / 1000;
        values.put(MediaStore.MediaColumns.DATE_ADDED, time);
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, time);
        if (file.exists()) {
            values.put(MediaStore.Images.Media.SIZE, file.length());
        }

        ContentResolver resolver = context.getContentResolver();
        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Logger.i("SaveUri = " + uri);
        return uri;
    }
    public static String getDirPath() {
        String dirPath = "";
        File imageDir = null;
        File extStorageDir = Environment.getExternalStorageDirectory();
        if (extStorageDir.canWrite()) {
            imageDir = new File(extStorageDir.getPath() + "/simplecropview");
        }
        if (imageDir != null) {
            if (!imageDir.exists()) {
                imageDir.mkdirs();
            }
            if (imageDir.canWrite()) {
                dirPath = imageDir.getPath();
            }
        }
        return dirPath;
    }
    public static String getMimeType(Bitmap.CompressFormat format) {
        Logger.i("getMimeType CompressFormat = " + format);
        switch (format) {
            case JPEG:
                return "jpeg";
            case PNG:
                return "png";
        }
        return "png";
    }
    private final SaveCallback mSaveCallback = new SaveCallback() {
        @Override public void onSuccess(Uri outputUri) {
            Intent  intent=new Intent();
            intent.putExtra("sourceuri",String.valueOf(outputUri));
            intent.putExtra("path",path);

            setResult(2,intent);
            finish();


        }

        @Override public void onError(Throwable e) {
            Toast.makeText(getApplicationContext(),"errror",Toast.LENGTH_SHORT).show();

        }
    };

}
