package com.example.d.healthbook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.d.healthbook.Activities.DoctorActivityInfo;
import com.example.d.healthbook.Models.ChatManIMG;
import com.example.d.healthbook.Models.Document;
import com.example.d.healthbook.R;

import java.util.List;

/**
 * Created by D on 20.07.2017.
 */

public class RecyclerAdapterHorizontalChat extends RecyclerView.Adapter<RecyclerAdapterHorizontalChat.ViewHolder> {
    private static final String TAG1 = "MY LIST ADAPTER";
    private Context context;
    private List<ChatManIMG> documents;

    public RecyclerAdapterHorizontalChat(List<ChatManIMG> documentsUrl, Context context) {
        documents = documentsUrl;
        this.context = context;

    }


    @Override
    public RecyclerAdapterHorizontalChat.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_man_add_group_horizontal, parent, false);

        return new RecyclerAdapterHorizontalChat.ViewHolder((View) view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterHorizontalChat.ViewHolder holder, final int position) {

        holder.imageChatMan.setImageBitmap(
                decodeSampledBitmapFromResource(context.getResources(), documents.get(position).getDrawableIMG(),
                        100, 100));





    }

    @Override
    public int getItemCount() {
        return documents.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageChatMan;


        public ViewHolder(final View v) {
            super(v);
            imageChatMan = (ImageView) v.findViewById(R.id.imageChatMan);


        }
    }

    private static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
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

    private static int calculateInSampleSize(
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

}


