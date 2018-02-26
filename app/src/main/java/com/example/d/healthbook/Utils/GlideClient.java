package com.example.d.healthbook.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.d.healthbook.R;


import java.io.File;

public class GlideClient {



    public static void downloadImage(Context c, String url, ImageView img)
    {
        Glide
                .with(c)
                .load(url)
                .centerCrop()
                .thumbnail( 0.1f )
                //.fitCenter() // другой вариант
                .into(img);
    }

    private static final String PICASSO_CACHE = "picasso-cache";

    public static void clearCache(Context context) {

        final File cache = new File(
                context.getApplicationContext().getCacheDir(),
                PICASSO_CACHE);
        if (cache.exists()) {
            deleteFolder(cache);
        }
    }

    private static void deleteFolder(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles())
                deleteFolder(child);
        }
        fileOrDirectory.delete();
    }


}