package com.example.rcksuporte05.rcksistemas.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by RCK 03 on 30/01/2018.
 */

public class FotoUtil {

    public static Bitmap rotateBitmap(Bitmap bitmap, Uri uri) {

        int rotate = 0;
        int w = 0;
        int h = 0;
        Matrix mtx = new Matrix();

        try {

            w = bitmap.getWidth();
            h = bitmap.getHeight();
            mtx = new Matrix();

            ExifInterface exif = new ExifInterface(uri.getPath());

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            mtx.postRotate(rotate);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

}
