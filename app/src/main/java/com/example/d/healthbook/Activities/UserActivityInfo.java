package com.example.d.healthbook.Activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.Adapters.PagerAdapterUserInfo;
import com.example.d.healthbook.API.App;
import com.example.d.healthbook.CalendarBekarysa.CalendarActivity;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.Controller.UploadService;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.ImageLoadResponse;
import com.example.d.healthbook.Models.ResponseAllSubscriptionsToDoctor;
import com.example.d.healthbook.Models.ResponseEditUserProfile;
import com.example.d.healthbook.Models.ResponseGetUserData;
import com.example.d.healthbook.Models.ResponseMyFamilyMembers;
import com.example.d.healthbook.Models.ResponseProgressUser;
import com.example.d.healthbook.R;
import com.example.d.healthbook.UI.MyToolbar;
import com.example.d.healthbook.View.UserInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Request;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.d.healthbook.Controller.MainController.getPath;
import static com.example.d.healthbook.FragmentsTab.TabUserEditProfile.clickBtnSaveProfileEdit;
import static com.example.d.healthbook.GlobalVariables.GlobalVariables.responseGetUserData;

//import com.example.d.healthbook.Fragments.DialogFragmentEditUserProfile;

public class UserActivityInfo extends BaseActivity implements UserInterface {

    private static ResponseMyFamilyMembers registeredUserMyFamily;
    public static PagerAdapterUserInfo adapter = null;
    private Intent intent;
    private ResponseGetUserData registeredUser;

    @BindView(R.id.profile_imageINFO)
    CircleImageView profile_imageINFO;
    @BindView(R.id.name_surnameTVINFO)
    TextView name_surnameTVINFO;
    @BindView(R.id.edit_TV_UserActivity)
    TextView edit_TV_UserActivity;
    @BindView((R.id.pager))
    ViewPager viewPager;
    @BindView((R.id.cancelBtnEdit))
    LinearLayout cancelBtnEdit;
    @BindView((R.id.cancelBtnEditIV))
    ImageView cancelBtnEditIV;
    @BindView((R.id.main_appbar))
    AppBarLayout main_appbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;


    private Fragment currentFragment;
    private List<ResponseAllSubscriptionsToDoctor> registeredUserSubscriptions;
    private ResponseProgressUser registeredUserProgress;
    //    private DialogFragmentEditUserProfile dialogFragmentFilterFeed;
    private int MY_REQUEST_CODE = 1;
    public static boolean clickEditUserProfile = false;
    private ResponseEditUserProfile registeredUserEditUser;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode==210) {
                Uri selectedImageUri = data.getData();

                if (selectedImageUri == null)
                    return;
                uploadAvatar(selectedImageUri);
            }
        }
    }
    void uploadAvatar(Uri imageUri){
        try {
            String path = getPath(this,imageUri);
            Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
            if(path!=null) {
                File file = new File(path);
                new UploadService().uploadImage(file, file.getName(), new okhttp3.Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        String test ="";
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                        if (response.body() != null) {
                            ImageLoadResponse resp_obj = new Gson().fromJson(response.body().string(),ImageLoadResponse.class);
                            updateImage(resp_obj.getId().get$id());
                        }
                    }
                });
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    void updateImage(String photo_id){
        JsonObject editPhoto = new JsonObject();
        editPhoto.addProperty("photo", photo_id);
        App.getApi().editUserProfile(GlobalVariables.user_id,
                GlobalVariables.user_auth_token,
                editPhoto ).enqueue(new Callback<ResponseEditUserProfile>() {
            @Override
            public void onResponse(Call<ResponseEditUserProfile> call, Response<ResponseEditUserProfile> response) {
                if(response.body()!=null && response.body().getPhoto()!=null) {
                    registeredUser.setPhoto(response.body().getPhoto());
                    MainController.setImageToViewById(UserActivityInfo.this, registeredUser.getPhoto(), profile_imageINFO);
                }
                else{
                    Toast.makeText(UserActivityInfo.this,"Не удалось обновить аватар",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditUserProfile> call, Throwable t) {
                Toast.makeText(UserActivityInfo.this,"Не удалось обновить аватар",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void _setOn(final int color_value) {
        runOnUiThread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
            Integer setColorValue = MainController.getColor(UserActivityInfo.this, color_value);
            toolbar_title.setTextColor(setColorValue);
            edit_TV_UserActivity.setTextColor(setColorValue);
            ColorStateList csl = AppCompatResources.getColorStateList(UserActivityInfo.this, color_value);
            Drawable drawable = DrawableCompat.wrap(cancelBtnEditIV.getBackground());
            if(csl!=null && drawable!=null) {
                try {
                    DrawableCompat.setTintList(drawable, csl);
                    cancelBtnEditIV.setImageDrawable(drawable);
                }
                catch (NullPointerException ex){
                    Log.e("{USER ACTIVITY INFO}",ex.getMessage());
                }
            }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info2);
        ButterKnife.bind(this);
        onViewSet();
     //   onViewSet(getWindow().getDecorView().getRootView());
//        navigation = (NavigationView) findViewById(R.id.navigationView);
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        setToolbarTitleText("Аккаунт");

        final int main_appbar_max_height = (int) MainController.convertDpToPixel(UserActivityInfo.this, 100);
        main_appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0 || Math.abs(verticalOffset) <= main_appbar_max_height) {
                    _setOn(R.color.white);
                } else {
                    _setOn(R.color.bpGreen);
                }

            }
        });

        navigation.getMenu().getItem(4).setChecked(true);

        seeUserInfo();
        seeUserFamilyMembers();
        seeSubscriptions();
        seeUserProgress();

        navigationClickItemListener(UserActivityInfo.this , UserActivityInfo.class);
        cancelAndEditBtnClickListener();

        ((CircleImageView)findViewById(R.id.profile_imageINFO)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(210);
            }
        });

/*
        View headerView = navigation.getHeaderView(3);
        CircleImageView img = (CircleImageView) headerView.findViewById(R.id.profile_imageINFO);
        img.setClickable(true);
        img.setOnClickListener( new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(UserActivityInfo.this, "zxczx", Toast.LENGTH_SHORT).show();

            }
        });*/
    }

    public void init_tab(){
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().
                setText("Мои данные"));

        tabLayout.addTab(tabLayout.newTab().

                setText("Моя семья"));
        tabLayout.addTab(tabLayout.newTab().

                setText("Подписки"));
        tabLayout.addTab(tabLayout.newTab().

                setText("Прогресc"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(MainController.getColor(UserActivityInfo.this,R.color.black  ),MainController.getColor(UserActivityInfo.this,R.color.bpGreen));
        tabLayout.setSelectedTabIndicatorColor(MainController.getColor(UserActivityInfo.this,R.color.bpGreen));


        adapter = new PagerAdapterUserInfo
                (this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        if(getIntent().getStringExtra("dswitch")!=null){
                viewPager.setCurrentItem(1);

        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tabLayout.getSelectedTabPosition() == 0) edit_TV_UserActivity.setVisibility(View.VISIBLE);
                else edit_TV_UserActivity.setVisibility(View.GONE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }





        });



    }

    public void cancelAndEditBtnClickListener() {
        cancelBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickEditUserProfile = false;
                hideKeyboard(UserActivityInfo.this);
                cancelBtnEdit.setVisibility(View.GONE);
                edit_TV_UserActivity.setText("Редактировать");
                mToggle.setDrawerIndicatorEnabled(true);
                toolbar_title.setPadding(0, 0, 74, 0);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                adapter.notifyDataSetChanged();

            }
        });
        edit_TV_UserActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickEditUserProfile) {

                    try {
                        clickEditUserProfile = false;
                        hideKeyboard(UserActivityInfo.this);
                        clickBtnSaveProfileEdit.performClick();
                        cancelBtnEdit.setVisibility(View.GONE);
                        edit_TV_UserActivity.setText("Редактировать");
                        mToggle.setDrawerIndicatorEnabled(true);
                        toolbar_title.setPadding(0, 0, 74, 0);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setHomeButtonEnabled(true);

                    }
                    catch (Exception e){



                    }

                } else {
                    clickEditUserProfile = true;
                    adapter.notifyDataSetChanged();
                    edit_TV_UserActivity.setText("Сохранить");
                    toolbar_title.setPadding(0, 0, 133, 0);
                    mToggle.setDrawerIndicatorEnabled(false);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getSupportActionBar().setHomeButtonEnabled(false);
                    cancelBtnEdit.setVisibility(View.VISIBLE);
                }




            }
        });

    }

    public void seeSubscriptions() {
        App.getApi().allUserSubscpriptionsToDoc(GlobalVariables.user_auth_token).enqueue(new Callback<List<ResponseAllSubscriptionsToDoctor>>() {
            @Override
            public void onResponse(Call<List<ResponseAllSubscriptionsToDoctor>> call, Response<List<ResponseAllSubscriptionsToDoctor>> response) {
                int s = response.code();
                registeredUserSubscriptions = response.body();
                if (adapter != null) {
                    adapter.OnResponseSuccessAllSubscription(registeredUserSubscriptions);
                }
            }

            @Override
            public void onFailure(Call<List<ResponseAllSubscriptionsToDoctor>> call, Throwable t) {
                Toast.makeText(UserActivityInfo.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void seeUserProgress() {
        App.getApi().seeUserProgress(GlobalVariables.user_auth_token).enqueue(new Callback<ResponseProgressUser>() {
            @Override
            public void onResponse(Call<ResponseProgressUser> call, Response<ResponseProgressUser> response) {
                int s = response.code();
                registeredUserProgress = response.body();
                if (adapter != null) {
                    adapter.OnResponseSuccessProgress(registeredUserProgress);
                }
            }

            @Override
            public void onFailure(Call<ResponseProgressUser> call, Throwable t) {
                Toast.makeText(UserActivityInfo.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void seeUserFamilyMembers() {
        App.getApi().seeFamilyMembers(GlobalVariables.user_auth_token).enqueue(new Callback<ResponseMyFamilyMembers>() {
            @Override
            public void onResponse(Call<ResponseMyFamilyMembers> call, Response<ResponseMyFamilyMembers> response) {
                int s = response.code();
                registeredUserMyFamily = response.body();
                if (adapter != null) {
                    adapter.OnResponseSuccesMyfamily(registeredUserMyFamily);
                }
            }

            @Override
            public void onFailure(Call<ResponseMyFamilyMembers> call, Throwable t) {
//                Toast.makeText(UserActivityInfo.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void seeUserInfo() {
        App.getApi().seedUserInfo(GlobalVariables.user_auth_token, GlobalVariables.user_id).enqueue(new Callback<ResponseGetUserData>() {
            @Override
            public void onResponse(Call<ResponseGetUserData> call, Response<ResponseGetUserData> response) {
                int s = response.code();
                if (response.errorBody() != null) {
                    try {
                        Log.e("LOG", "Retrofit Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                registeredUser = response.body();

                if (registeredUser != null) {
                    responseGetUserData = registeredUser;
                    setMenuData();
                    init_tab();

                    if (registeredUser.getPhoto() != null) {
                        MainController.setImageToViewById(UserActivityInfo.this, registeredUser.getPhoto(), profile_imageINFO);
                        profile_imageINFO.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MainController.setImageToViewById(UserActivityInfo.this, registeredUser.getPhoto(), profile_imageINFO);
                            }
                        });
                    }

                    name_surnameTVINFO.setText(registeredUser.getName() + " " + registeredUser.getSurname());
                    name_surnameTVINFO.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openGallery(210);
                            Toast.makeText(UserActivityInfo.this, "ZXCZCZ", Toast.LENGTH_SHORT).show();

                        }
                    });

                    if (adapter != null) {
                        adapter.onResponseSuccess(registeredUser);
                        adapter.notifyDataSetChanged();
                    }
                    else{
                        throw new NullPointerException("The variable 'adapter' is not set");
                    }

                   // Toast.makeText(UserActivityInfo.this, "Загрузка прошла успешно ", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(UserActivityInfo.this, "Ошибка при загрузке ", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseGetUserData> call, Throwable t) {
                Toast.makeText(UserActivityInfo.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });


    }

    void openGallery(Integer req_code){
        if (checkAndRequestPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent();
            intent.setType( "image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Выберите картинку"), req_code);
        }
        else{

        }
    }
    Boolean checkAndRequestPermissions(String permissionID) {
        Integer storage = ContextCompat.checkSelfPermission(this, permissionID);
        if (storage != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permissionID},1);
            return false;
        }
        else{
            return true;
        }
    }



    @Override
    public ResponseGetUserData getData() {
        if (registeredUser != null)
            return registeredUser;
        return null;
    }

    @Override
    public List<ResponseAllSubscriptionsToDoctor> getDataSub() {
        if (registeredUserSubscriptions != null)
            return registeredUserSubscriptions;
        return null;
    }

    @Override
    public ResponseProgressUser getDataProgress() {
        if (registeredUserProgress != null)
            return registeredUserProgress;
        return null;
    }

    @Override
    public ResponseMyFamilyMembers getDataFamily() {
        if (registeredUserMyFamily != null)
            return registeredUserMyFamily;
        return null;
    }

    public void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}





