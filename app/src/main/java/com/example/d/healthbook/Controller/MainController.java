package com.example.d.healthbook.Controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.StringLoader;
import com.example.d.healthbook.Activities.UserActivityInfo;
import com.example.d.healthbook.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by D on 26.05.2017.
 */

public class MainController {

    public static String stringChecker(String value){
        if(value == null || value.isEmpty()){
            return "";
        }
        else
            return value;
    }


    public static int getScreenWidth(Activity activity) {
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        return width;
    }

    public static Integer getColor(Context ctx , Integer colorId){
        if(ctx == null)
            return 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.getColor(ctx, colorId);
        }
        else{
            return ctx.getResources().getColor(colorId);
        }
    }

    public static float convertDpToPixel(Context ctx ,float dp) {
        DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static void showToast(final Context _context, final String message_text) {
        ((Activity) _context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(_context, message_text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static long getCurrent_UNIXTIME(){
        return System.currentTimeMillis() / 1000L;
    }

    public static void showPreparedToast(final Context _context, final String message_type) {
        switch (message_type) {
            case "Success": {
                showToast(_context, "Успешно");
                break;
            }
            case "Failed": {
                showToast(_context, "Ошибка");
                break;
            }
            default: {
                showToast(_context, message_type);
                break;
            }
        }

    }

    public static <T>String serializeToJson(T myClass) {
        Gson gson = new Gson();
        String string_json = gson.toJson(myClass);
        return string_json;
    }

    public static <T> T deSerializeToJson(String myJson ,Class<T>  modelType) {
        Gson gson = new Gson();
        return gson.fromJson(myJson,modelType);
    }

    static HashMap<String, String> loadedImagePath;

    static void saveToKnownPath(String image_id, String image_path) {
        if (loadedImagePath == null) {
            loadedImagePath = new HashMap<String, String>();
        }
        if (!loadedImagePath.containsKey(image_id))
            loadedImagePath.put(image_id, image_path);
    }
    public static String checkLastChar(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
    static String checkSavedPath(String image_id) {
        if (loadedImagePath != null) {
//            String image_url = loadedImagePath.get(image_id);
//            if(image_url.isEmpty())
//                return null;
//            return image_url;
            if (loadedImagePath.containsKey(image_id))
                return loadedImagePath.get(image_id);
            return null;
        }
        return null;
    }

    public static void setImageToViewById(final Context _context, final String image_id, final ImageView image_view) {
        if(image_id==null || image_id.isEmpty()){
            loadImageToView(_context, R.drawable.placeholder,image_view);
            return;
        }
        String save_img_path;
        if ((save_img_path = checkSavedPath(image_id)) != null) {
            loadImageToView(_context, save_img_path, image_view);
            return;
        }
        OkHttpClient client = new OkHttpClient();
        String url_to_image_path = "https://healthbook.kz/api/files/image/path/" + image_id;
        Request request = new Request.Builder()
                .url(url_to_image_path)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadImageToView(_context, R.drawable.placeholder,image_view);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String raw_response = response.body().string();
                raw_response = raw_response.replace("\\", "");
                raw_response = raw_response.replace("\"", "");
                saveToKnownPath(image_id, raw_response);
                loadImageToView(_context, raw_response, image_view);
            }

        });
    }
    public static Calendar getCalendarByDate(String date , String format) {
        SimpleDateFormat format1 = new SimpleDateFormat(format);
        boolean isSuccessfull = false;
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(format1.parse(date));
            isSuccessfull = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (isSuccessfull)
            return cal;
        return null;
    }

    public static void loadImageToView(final Context _ctx, final String image_url, final ImageView image_view) {
        ((AppCompatActivity) _ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(_ctx).load(image_url).into(image_view);
            }
        });
    }
    public static void loadImageToView(final Context _ctx, final Integer res_id , final ImageView image_view) {
        ((AppCompatActivity) _ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(_ctx).load(res_id)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(image_view);
            }
        });
    }

    public static String getNormalTime(String raw_date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS",
                Locale.ENGLISH);
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss",
                    Locale.ENGLISH);
            Date date = sdf.parse(raw_date);
            return  df.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return raw_date;
    }


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                          int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    @SuppressLint("NewApi")
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{ split[1] };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
