package com.example.d.healthbook.Controller;

import android.util.Log;

import com.example.d.healthbook.GlobalVariables.GlobalVariables;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

/**
 * Created by User on 07.09.2017.
 */

public class UploadService {
        public okhttp3.Call uploadImage(File image, String imageName, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
        .addFormDataPart("file[0]", imageName, RequestBody.create(MEDIA_TYPE_IMAGE, image))
        .build();
        //
//            Request request = new Request.Builder().url("http://139.59.136.183:8001/v1/ru/" + "place_photos")
        Request request = new Request.Builder().url("https://healthbook.kz/api/files/image")
                .post(requestBody)
                .addHeader("AUTH-TOKEN", GlobalVariables.user_auth_token)
                .addHeader("USER-ID",GlobalVariables.user_id.toString())
                .addHeader("Content-Type","multipart/form-data")
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
        }

        MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/*");
}