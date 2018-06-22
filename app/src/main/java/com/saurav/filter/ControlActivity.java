package com.saurav.filter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.saurav.filter.utility.Helper;
import com.saurav.filter.utility.TransformImage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ControlActivity extends AppCompatActivity {

    static
    {
        System.loadLibrary("NativeImageProcessor");
    }

    Toolbar mControlToolbar;

    ImageView mTickImageView;

    ImageView mCentreView;

    TransformImage mTransformImage;

    int mScreenHeight,mScreenWidth;

    SeekBar mSeekbar;
    ImageView mCancelImageView;

    Uri mSelectImageUri;

    int mCurrentFilter;

    final static int PICK_IMAGE= 2;
    final static int MY_PERSONAL_REQUEST_STORAGE_INFORMATION = 3;

    Target mApplySingleFilter = new Target() {
        @Override


        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            int currentFilterValue = mSeekbar.getProgress();

            if (mCurrentFilter == TransformImage.FILTER_BRIGHTNESS) {
                mTransformImage.applyBrightnessSubFilter(currentFilterValue);

                Helper.writeDataIntoExternalStorage(ControlActivity.this, mTransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS), mTransformImage.getBitmap(TransformImage.FILTER_BRIGHTNESS));

                Picasso.with(ControlActivity.this).invalidate(Helper.getFileFromExternalStorage(ControlActivity.this, mTransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS)));
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this, mTransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS))).resize(0, mScreenHeight / 2).into(mCentreView);
            } else if (mCurrentFilter == TransformImage.FILTER_CONTRAST) {
                mTransformImage.applyContrastSubFilter(currentFilterValue);

                Helper.writeDataIntoExternalStorage(ControlActivity.this, mTransformImage.getFilename(TransformImage.FILTER_CONTRAST), mTransformImage.getBitmap(TransformImage.FILTER_CONTRAST));

                Picasso.with(ControlActivity.this).invalidate(Helper.getFileFromExternalStorage(ControlActivity.this, mTransformImage.getFilename(TransformImage.FILTER_CONTRAST)));
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this, mTransformImage.getFilename(TransformImage.FILTER_CONTRAST))).resize(0, mScreenHeight / 2).into(mCentreView);
            } else if (mCurrentFilter == TransformImage.FILTER_VIGNETTE) {
                mTransformImage.applyVignetteSubFilter(currentFilterValue);

                Helper.writeDataIntoExternalStorage(ControlActivity.this, mTransformImage.getFilename(TransformImage.FILTER_VIGNETTE), mTransformImage.getBitmap(TransformImage.FILTER_VIGNETTE));

                Picasso.with(ControlActivity.this).invalidate(Helper.getFileFromExternalStorage(ControlActivity.this, mTransformImage.getFilename(TransformImage.FILTER_VIGNETTE)));
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this, mTransformImage.getFilename(TransformImage.FILTER_VIGNETTE))).resize(0, mScreenHeight / 2).into(mCentreView);
            } else if (mCurrentFilter == TransformImage.FILTER_SATURATION) {
                mTransformImage.applySaturationSubFilter(currentFilterValue);

                Helper.writeDataIntoExternalStorage(ControlActivity.this, mTransformImage.getFilename(TransformImage.FILTER_SATURATION), mTransformImage.getBitmap(TransformImage.FILTER_SATURATION));

                Picasso.with(ControlActivity.this).invalidate(Helper.getFileFromExternalStorage(ControlActivity.this, mTransformImage.getFilename(TransformImage.FILTER_SATURATION)));
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this, mTransformImage.getFilename(TransformImage.FILTER_SATURATION))).resize(0, mScreenHeight / 2).into(mCentreView);
            }
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    Target mSmallTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

             mTransformImage = new TransformImage(ControlActivity.this,bitmap);

            mTransformImage.applyBrightnessSubFilter(TransformImage.DEFAULT_BRIGHTNESS);

            Helper.writeDataIntoExternalStorage(ControlActivity.this, mTransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS),mTransformImage.getBitmap(TransformImage.FILTER_BRIGHTNESS));
            Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,mTransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS))).fit().centerInside().into(mFirstFilterImageView);


            mTransformImage.applySaturationSubFilter(TransformImage.DEFAULT_SATURATION);

            Helper.writeDataIntoExternalStorage(ControlActivity.this, mTransformImage.getFilename(TransformImage.FILTER_SATURATION),mTransformImage.getBitmap(TransformImage.FILTER_SATURATION));
            Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,mTransformImage.getFilename(TransformImage.FILTER_SATURATION))).fit().centerInside().into(mSecondFilterImageView);


            mTransformImage.applyVignetteSubFilter(TransformImage.DEFAULT_VIGNETTE);

            Helper.writeDataIntoExternalStorage(ControlActivity.this, mTransformImage.getFilename(TransformImage.FILTER_VIGNETTE),mTransformImage.getBitmap(TransformImage.FILTER_VIGNETTE));
            Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,mTransformImage.getFilename(TransformImage.FILTER_VIGNETTE))).fit().centerInside().into(mThirdFilterImageView);


            mTransformImage.applyContrastSubFilter(TransformImage.DEFAULT_CONTRAST);

            Helper.writeDataIntoExternalStorage(ControlActivity.this, mTransformImage.getFilename(TransformImage.FILTER_CONTRAST),mTransformImage.getBitmap(TransformImage.FILTER_CONTRAST));
            Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this,mTransformImage.getFilename(TransformImage.FILTER_CONTRAST))).fit().centerInside().into(mFourthFilterImageView);

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    ImageView mFirstFilterImageView;
    ImageView mSecondFilterImageView;
    ImageView mThirdFilterImageView;
    ImageView mFourthFilterImageView;

    private static final String TAG = ControlActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        mControlToolbar = (Toolbar) findViewById(R.id.toolbar);

        mTickImageView = (ImageView) findViewById(R.id.imageView3);

        mCentreView = (ImageView) findViewById(R.id.CentreImageView);

        mSeekbar = (SeekBar) findViewById(R.id.seekBar);

        mFirstFilterImageView = (ImageView) findViewById(R.id.imageView5);
        mSecondFilterImageView = (ImageView) findViewById(R.id.imageView4);
        mThirdFilterImageView = (ImageView) findViewById(R.id.imageView6);
        mFourthFilterImageView = (ImageView) findViewById(R.id.imageView7);

        mControlToolbar.setTitle(getString(R.string.app_name));
        mControlToolbar.setNavigationIcon(R.drawable.icon);
        mControlToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        mTickImageView = (ImageView) findViewById(R.id.imageView3);
        mTickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ControlActivity.this, ImagePreviewActivity.class);
                startActivity(intent);
            }
        });

        mCentreView = (ImageView) findViewById(R.id.CentreImageView);

        mCentreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requeststoragePermission();

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        mFirstFilterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSeekbar.setMax(TransformImage.MAX_BRIGHTNESS);
                mSeekbar.setProgress(TransformImage.DEFAULT_BRIGHTNESS);

                mCurrentFilter = TransformImage.FILTER_BRIGHTNESS;


                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this ,mTransformImage.getFilename(TransformImage.FILTER_BRIGHTNESS))).resize(0, mScreenHeight/2).into(mCentreView);
            }
        });

        mSecondFilterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSeekbar.setMax(TransformImage.MAX_SATURATION);
                mSeekbar.setProgress(TransformImage.DEFAULT_SATURATION);

                mCurrentFilter = TransformImage.FILTER_SATURATION;

                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this ,mTransformImage.getFilename(TransformImage.FILTER_SATURATION))).resize(0, mScreenHeight/2).into(mCentreView);
            }
        });


        mThirdFilterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSeekbar.setMax(TransformImage.MAX_VIGNETTE);
                mSeekbar.setProgress(TransformImage.DEFAULT_VIGNETTE);

                mCurrentFilter = TransformImage.FILTER_VIGNETTE;

                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this ,mTransformImage.getFilename(TransformImage.FILTER_VIGNETTE))).resize(0, mScreenHeight/2).into(mCentreView);
            }
        });


        mFourthFilterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSeekbar.setMax(TransformImage.MAX_CONTRAST);
                mSeekbar.setProgress(TransformImage.DEFAULT_CONTRAST);

                mCurrentFilter = TransformImage.FILTER_CONTRAST;
                Picasso.with(ControlActivity.this).load(Helper.getFileFromExternalStorage(ControlActivity.this ,mTransformImage.getFilename(TransformImage.FILTER_CONTRAST))).resize(0, mScreenHeight/2).into(mCentreView);
            }
        });

        mTickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(ControlActivity.this).load(mSelectImageUri).into(mApplySingleFilter);
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case MY_PERSONAL_REQUEST_STORAGE_INFORMATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    new MaterialDialog.Builder(ControlActivity.this).title(R.string.permission_title)
                            .content(R.string.permission_content)
                            .negativeText(R.string.permission_cancel)
                            .positiveText(R.string.permission_agree_settings).canceledOnTouchOutside(true).show();
                } else {
                    Log.d(TAG,"Pemission denied");
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK){
             mSelectImageUri = data.getData();

            Picasso.with(ControlActivity.this).load(mSelectImageUri).fit().centerInside().into(mCentreView);

            Picasso.with(ControlActivity.this).load(mSelectImageUri).into(mSmallTarget);


           // Picasso.with(ControlActivity.this).load(mSelectImageUri).fit().centerInside().into(mFirstFilterImageView);
          //  Picasso.with(ControlActivity.this).load(mSelectImageUri).fit().centerInside().into(mSecondFilterImageView);
          //  Picasso.with(ControlActivity.this).load(mSelectImageUri).fit().centerInside().into(mThirdFilterImageView);
          //  Picasso.with(ControlActivity.this).load(mSelectImageUri).fit().centerInside().into(mFourthFilterImageView);


        }
    }

    public  void requeststoragePermission(){
        if(ContextCompat.checkSelfPermission(ControlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(ControlActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                new MaterialDialog.Builder(ControlActivity.this).title(R.string.permission_title)
                        .content(R.string.permission_content)
                        .negativeText(R.string.permission_cancel)
                        .positiveText(R.string.permission_agree_settings)
                        .canceledOnTouchOutside(true)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
                            }
                        }) .show();
            } else{
                ActivityCompat.requestPermissions(ControlActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERSONAL_REQUEST_STORAGE_INFORMATION);
            }
            return;
        }
    }
}
