package com.saurav.filter.utility;

import android.content.Context;
import android.graphics.Bitmap;

import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubfilter;

public class TransformImage {

    public static final int MAX_BRIGHTNESS = 100;
    public static final int MAX_CONTRAST = 100;
    public static final int MAX_VIGNETTE = 255;
    public static final int MAX_SATURATION = 5;

    public static final int DEFAULT_BRIGHTNESS = 70;
    public static final int DEFAULT_VIGNETTE = 100;
    public static final int DEFAULT_SATURATION = 5;
    public static final int DEFAULT_CONTRAST = 60;

    private  String mFilename;
    private Bitmap mBitmap;
    private Context mContext;


    private Bitmap BrightnessFilterBitmap;
    private Bitmap VignetteFilterBitmap;
    private Bitmap SaturationFilterBitmap;
    private Bitmap ContrastFilterBitmap;

    public static int FILTER_BRIGHTNESS = 0;
    public static int FILTER_VIGNETTE = 1;
    public static int FILTER_SATURATION = 2;
    public static int FILTER_CONTRAST = 3;

    public String getFilename(int filter){
        if(filter == FILTER_BRIGHTNESS){
            return mFilename+"_brightness";
        }else if(filter == FILTER_CONTRAST){
            return mFilename+"_contrast";
        }else if (filter == FILTER_SATURATION){
            return mFilename+"_saturation";
        }else if(filter == FILTER_VIGNETTE){
            return mFilename+"vignette";
        }
        return mFilename;
    }

    public Bitmap getBitmap(int filter){
        if(filter == FILTER_BRIGHTNESS){
            return BrightnessFilterBitmap;
        }else if(filter == FILTER_CONTRAST){
            return ContrastFilterBitmap;
        }else if(filter == FILTER_SATURATION){
            return SaturationFilterBitmap;
        }else if(filter == FILTER_VIGNETTE){
            return VignetteFilterBitmap;
        }
        return mBitmap;
    }

    public TransformImage(Context context, Bitmap bitmap){

        mContext = context;
        mBitmap = bitmap;
        mFilename  = System.currentTimeMillis()+"";
    }

    public Bitmap applyBrightnessSubFilter(int brightness){

        Filter myFilter = new Filter();

        Bitmap workingBitmap = Bitmap.createBitmap(mBitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888,true);



        myFilter.addSubFilter(new BrightnessSubfilter(brightness));
        Bitmap outputImage = myFilter.processFilter(mutableBitmap);

        BrightnessFilterBitmap = outputImage;

        return outputImage;



    }

    public Bitmap applyVignetteSubFilter(int vignette){

        Filter myFilter = new Filter();

        Bitmap workingBitmap = Bitmap.createBitmap(mBitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888,true);



        myFilter.addSubFilter(new VignetteSubfilter(mContext, vignette));
        Bitmap outputImage = myFilter.processFilter(mutableBitmap);

        VignetteFilterBitmap = outputImage;

        return outputImage;
    }

    public Bitmap applySaturationSubFilter(int saturation){

        Filter myFilter = new Filter();

        Bitmap workingBitmap = Bitmap.createBitmap(mBitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888,true);



        myFilter.addSubFilter(new SaturationSubfilter(saturation));
        Bitmap outputImage = myFilter.processFilter(mutableBitmap);

        SaturationFilterBitmap = outputImage;

        return outputImage;

    }

    public Bitmap applyContrastSubFilter(int contrast){

        Filter myFilter = new Filter();

        Bitmap workingBitmap = Bitmap.createBitmap(mBitmap);
        Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888,true);



        myFilter.addSubFilter(new ContrastSubfilter(contrast));
        Bitmap outputImage = myFilter.processFilter(mutableBitmap);

        ContrastFilterBitmap = outputImage;

        return outputImage;

    }

}


