package com.saurav.filter.utility;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.saurav.filter.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Helper {

    public static Boolean writeDataIntoExternalStorage(Context contex, String fileName, Bitmap bitmap){
        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/" + contex.getString(R.string.app_name));

        if(!directory.exists() && directory.mkdir()){
            return false;
        }

        File file = new File(directory.getAbsolutePath()+ "/" + fileName);
        if(file.exists() && !(file.canWrite())){
            return false;
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            return bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

        } catch (FileNotFoundException e){
            e.printStackTrace();
            return false;
        }
    }

      public static File getFileFromExternalStorage(Context context, String filename){
          File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ "/" + context.getString(R.string.app_name));

          File file = new File(directory.getAbsolutePath()+ "/" + filename);

          if(!file.exists() || !file.canRead()){
              return null;
          }
            return file;

    }

}




