package com.example.d.healthbook.new_code.choose_service;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.Activities.FoldersActivity;
import com.example.d.healthbook.R;
import com.example.d.healthbook.Utils.RealPathUtil;
import com.example.d.healthbook.Utils.UserPicture;
import com.example.d.healthbook.new_code.choose_service.model.FolderResponse;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by User on 14.08.2017.
 */

public class AddFileDialogFragment extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.imageView1)
    ImageView imageView1;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.imageView3)
    TextView imageView3;
    @BindView(R.id.imageView4)
    TextView imageView4;
    private FolderResponse registeredUser;
   private String folder_name;
    @Override
    public void onClick(View view) {

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_add_file, null);
        ButterKnife.bind(this, v);
      folder_name=getArguments().getString("folder_name");




        return v;
    }
//clickBtnCancelEvent

    @OnClick(R.id.clickBtnSaveEvent)
    public void onAddField() {

        dismiss();


    }


    @OnClick(R.id.clickBtnCancelEvent)
    public void onCancleield() {

        dismiss();

    }




    @OnClick(R.id.imageView1)
    void open3() {
        openGallery(230);
    }

    @OnClick(R.id.videoLayout)
    void openVideo() {
        openGallery(240);
    }
    @OnClick(R.id.fileLayout)
    void open4() {
        openGallery(240);
    }

    @OnClick(R.id.anaLisLayout)
    void openVideo4() {
        openGallery(240);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {


            if (requestCode == 230) {
                Uri selectedImageUri = data.getData();

                if (selectedImageUri == null)
                    return;
                uploadAvatar(selectedImageUri, imageView1);




            } else if (requestCode == 240) {
                Uri selectedImageUri = data.getData();
                Toast.makeText(getContext(), "Видео загружено", Toast.LENGTH_SHORT).show();
                dismiss();


            }

        }


    }

    void openGallery(Integer req_code) {
        if (checkAndRequestPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)) {




               if (req_code==230) {

                   Intent intent = new Intent();
                   intent.setType("image/*");
                   intent.setAction(Intent.ACTION_GET_CONTENT);
                   startActivityForResult(Intent.createChooser(intent, "Выберите картинку"), req_code);


               }
               else if(req_code==240){

                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Video"),req_code);



            }




        }


    }

    Boolean checkAndRequestPermissions(String permissionID) {
        Integer storage = ContextCompat.checkSelfPermission(getContext(), permissionID);
        if (storage != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{permissionID}, 1);
            return false;
        } else {
            return true;
        }
    }


    void uploadAvatar(Uri imageUri, ImageView imageView) {

        try {

            String path = uriToFilename(imageUri);
            //  listImagesSrings.add(path);
            Toast.makeText(getActivity(), path, Toast.LENGTH_SHORT).show();

            Bitmap bitmap = new UserPicture(imageUri, getActivity().getContentResolver()).getBitmap();
            if (bitmap.getWidth() >= bitmap.getHeight()) {

                bitmap = Bitmap.createBitmap(
                        bitmap,
                        bitmap.getWidth() / 2 - bitmap.getHeight() / 2,
                        0,
                        bitmap.getHeight(),
                        bitmap.getHeight()
                );


            } else {

                bitmap = Bitmap.createBitmap(
                        bitmap,
                        0,
                        bitmap.getHeight() / 2 - bitmap.getWidth() / 2,
                        bitmap.getWidth(),
                        bitmap.getWidth()
                );


            }
            imageView.setImageBitmap(bitmap);
            //   String image = getStringImage(bitmap);
            //  uploadAvaImage(activity,image);



            Toast.makeText(getContext(), "Фото загружено", Toast.LENGTH_SHORT).show();
            dismiss();

        } catch (IOException e) {

        }
        //  ic_profile.setImageURI(imageUri);

    }

    private String uriToFilename(Uri uri) {
        String path = null;

        if (Build.VERSION.SDK_INT < 11) {
            path = RealPathUtil.getRealPathFromURI_BelowAPI11(getActivity(), uri);
        } else if (Build.VERSION.SDK_INT < 19) {
            path = RealPathUtil.getRealPathFromURI_API11to18(getActivity(), uri);
        } else {
            path = RealPathUtil.getRealPathFromURI_API19(getActivity(), uri);
        }

        return path;
    }












}
