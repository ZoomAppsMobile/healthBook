package com.example.d.healthbook.Controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by User on 11.09.2017.
 */

public class ImageUtils {

    public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap convertByteArrayToBitmap(byte[] bitmapArray) {
        return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
    }
}