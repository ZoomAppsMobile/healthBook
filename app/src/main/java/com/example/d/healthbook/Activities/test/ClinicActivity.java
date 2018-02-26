package com.example.d.healthbook.Activities.test;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.d.healthbook.API.API_Controller;
import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Activities.ChatActivity;
import com.example.d.healthbook.Activities.ChooseVisitDataActivity;
import com.example.d.healthbook.Activities.ZoomActivity;
import com.example.d.healthbook.Adapters.PagerAdapterDoctorInfo;
import com.example.d.healthbook.Controller.DateController;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.ResponseAllSubscriptionsToDoctor;
import com.example.d.healthbook.Models.ResponseDoctorInfo;
import com.example.d.healthbook.Models.ResponseSubscribeToDoctor;
import com.example.d.healthbook.Models.Schedule;
import com.example.d.healthbook.Presenter.DoctorInfoPresenter;
import com.example.d.healthbook.R;
import com.example.d.healthbook.UI.MyToolbar;
import com.example.d.healthbook.View.DoctorInterface;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClinicActivity extends AppCompatActivity implements DoctorInterface {
    private boolean checkSubscription = false;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward, fab_open_tv, fab_close_tv;
    private MyToolbar toolbar;
    private List<Schedule> schedules;
    private ResponseDoctorInfo registeredUser;
    private String doctorID = "";
    @BindView(R.id.profile_imageINFO)
    CircleImageView profile_imageINFO;
    @BindView(R.id.name_surnameTVINFO)
    TextView name_surnameTVINFO;
    @BindView(R.id.specialityTVINFO)
    TextView specialityTVINFO;
    @BindView(R.id.experienceTVINFO)
    TextView experienceTVINFO;


    @BindView(R.id.jobName)
    TextView jobName;
//jobName

    @BindView((R.id.pager))
    ViewPager viewPager;

//    @BindView((R.id.fabMakeAnAppointment))
//    com.github.clans.fab.FloatingActionButton fabVisitToDoctor;


    @BindView((R.id.main_appbar))
    AppBarLayout main_appbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    PagerAdapterDoctorInfo adapter = null;
    private String expert_Room_ID = " ";
    private ProgressDialog progressDialog = null;
    private FloatingActionButton fabSetting;
    private FloatingActionButton fabSub1;
    private FloatingActionButton fabSub2;
    private boolean fabStatus = false;
    private ResponseSubscribeToDoctor registeredUserSubscribeToDoctor;
    private List<ResponseAllSubscriptionsToDoctor> registeredUserSubscriptions;


    void _setOn(final int color_value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    toolbar_title.setTextColor(ContextCompat.getColor(ClinicActivity.this, color_value));
                } else {
                    toolbar_title.setTextColor(getResources().getColor(color_value));
                }
            }
        });

    }

    public float convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);
        ButterKnife.bind(this);
        init_anims();
//        App.getInstance().setFragment(DoctorActivityInfo.this, DoctorActivityInfoFragment.class.getName(), android.R.anim.fade_in, android.R.anim.fade_out, true);
        toolbar = (MyToolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final int main_appbar_max_height = (int) convertDpToPixel(150);
        final int main_toolbar_max_height = toolbar.getHeight();
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

        fabSetting = (FloatingActionButton) this.findViewById(R.id.fabSetting);
        fabSub1 = (FloatingActionButton) this.findViewById(R.id.fabSub1);
        fabSub2 = (FloatingActionButton) this.findViewById(R.id.fabSub2);


        /* when fab Setting (fab main) clicked */
        fabSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
            }
        });

        /* enable clik on FrameLayout fraToolFloat */




        fabSub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkSubscription) {
                    subscribeToDoctor(doctorID);
                } else {
                    unSubscribeToDoctor(doctorID);
                }

            }
        });

        fabSub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(registeredUser!=null && registeredUser.getUserId()!=null)

                    doctorInfoPresenter.getChat(Integer.parseInt(registeredUser.getUserId()));
                    fabSub1.setClickable(false);

            }
        });

        String id = getIntent().getStringExtra("idDoctor");
        final String imageString = getIntent().getStringExtra("imageDoc");
        String name = getIntent().getStringExtra("nameDoc");
        String surname = getIntent().getStringExtra("surNameDoc");
        String company_name = getIntent().getStringExtra("company_name");

        jobName.setText(  "Место работы: " + company_name);

        name_surnameTVINFO.setText(name + " " + surname);
        Glide.with(
                getApplicationContext())
                .load(imageString)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(profile_imageINFO);

        profile_imageINFO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // openImage(view,imageString);
            }
        });
        seeDoctorInfoMethod(id);
       // seeSubscriptions();


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().

                setText("Информация"));
        tabLayout.addTab(tabLayout.newTab().

                setText("График работы"));
        tabLayout.addTab(tabLayout.newTab().

                setText("Отзывы"));
        tabLayout.addTab(tabLayout.newTab().

                setText("Статус"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        adapter = new

                PagerAdapterDoctorInfo
                (this, getSupportFragmentManager(), tabLayout.

                        getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()

        {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        fabSub1.setClickable(true);
    }

    void openImage(View v, String image_src){
        Intent intent = new Intent(this, ZoomActivity.class);
        intent.putExtra(ZoomActivity.IMAGE_KEY,image_src);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, v, "cakeImageAnimation");
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    private void unSubscribeToDoctor(String doctorID) {
        App.getApi().unSubscribeToDoctor(GlobalVariables.user_auth_token, API_Controller.subscribeToDoctorJson(doctorID)).enqueue(new Callback<ResponseSubscribeToDoctor>() {
            @Override
            public void onResponse(Call<ResponseSubscribeToDoctor> call, Response<ResponseSubscribeToDoctor> response) {

                if (response.code() == 200) {
                    registeredUserSubscribeToDoctor = response.body();
                    MainController.showPreparedToast(ClinicActivity.this, "Вы успешно отписались");
                    fabSub2.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#59b538")));
                    checkSubscription=false;
                } else {
                    MainController.showPreparedToast(ClinicActivity.this, "Failed");
                }

            }

            @Override
            public void onFailure(Call<ResponseSubscribeToDoctor> call, Throwable t) {
                Toast.makeText(ClinicActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }


    void init_anims() {
        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);
        fab_open_tv = AnimationUtils.loadAnimation(this, R.anim.fab_open_tv);
        fab_close_tv = AnimationUtils.loadAnimation(this, R.anim.fab_close_tv);
    }

    public void seeDoctorInfoMethod(String id) {
        App.getApi().seedoctorInfo(id).enqueue(new Callback<ResponseDoctorInfo>() {
            @Override
            public void onResponse(Call<ResponseDoctorInfo> call, Response<ResponseDoctorInfo> response) {
                int s = response.code();
                if (response.errorBody() != null) {
                    try {
                        Log.e("LOG", "Retrofit Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                registeredUser = response.body();
                if (response.body().getRooms().size() != 0) {
                    expert_Room_ID = response.body().getRooms().get(0).getId();
                }


                schedules = registeredUser.getSchedule();
              //  Toast.makeText(getApplication(), "SUCCESS", Toast.LENGTH_LONG).show();
                doctorID = registeredUser.getId();
                specialityTVINFO.setText(registeredUser.getSpecialityName());
                String experianceOfDoctor = "Не указано";
                String exp = DateController.getDateDifferenceWithCondition(registeredUser.getExperience(),
                        DateController.YEAR);
                if(exp!=null){
                     experianceOfDoctor = String.format("Опыт работы: %s %s" , exp, "лет");
                }
                experienceTVINFO.setText(experianceOfDoctor);

                if (adapter != null)
                    adapter.onResponseSuccess(registeredUser);


            }

            @Override
            public void onFailure(Call<ResponseDoctorInfo> call, Throwable t) {
                Toast.makeText(getApplication(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void subscribeToDoctor(String doctorID) {

        App.getApi().subscribeToDoctor(GlobalVariables.user_auth_token, API_Controller.subscribeToDoctorJson(doctorID)).enqueue(new Callback<ResponseSubscribeToDoctor>() {
            @Override
            public void onResponse(Call<ResponseSubscribeToDoctor> call, Response<ResponseSubscribeToDoctor> response) {

                if (response.code() == 200) {
                    registeredUserSubscribeToDoctor = response.body();
                    MainController.showPreparedToast(ClinicActivity.this, "Вы успешно подписались на врача");
                    fabSub2.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    checkSubscription=true;
                } else {
                    MainController.showPreparedToast(ClinicActivity.this, "Failed");
                }

            }

            @Override
            public void onFailure(Call<ResponseSubscribeToDoctor> call, Throwable t) {
                Toast.makeText(ClinicActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void seeSubscriptions() {
        App.getApi().allUserSubscpriptionsToDoc(GlobalVariables.user_auth_token).enqueue(new Callback<List<ResponseAllSubscriptionsToDoctor>>() {
            @Override
            public void onResponse(Call<List<ResponseAllSubscriptionsToDoctor>> call, Response<List<ResponseAllSubscriptionsToDoctor>> response) {
                int s = response.code();
                registeredUserSubscriptions = response.body();
                for (int i = 0; i < registeredUserSubscriptions.size(); i++) {
                    if (doctorID.equals(registeredUserSubscriptions.get(i).getId())) {
//                        fabSub2.s(Color.RED);
                        fabSub2.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                        checkSubscription = true;

                    }


                }
            }

            @Override
            public void onFailure(Call<List<ResponseAllSubscriptionsToDoctor>> call, Throwable t) {
                Toast.makeText(ClinicActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public ResponseDoctorInfo getData() {
        if (registeredUser != null)
            return registeredUser;
        return null;
    }

    DoctorInfoPresenter doctorInfoPresenter = new DoctorInfoPresenter(this);

    @Override
    public void onChatCreated(String chat_id) {
        openChat(chat_id);
    }

    void openChat(String chat_id){
        Intent chat_intent = new Intent(ClinicActivity.this,ChatActivity.class);
        chat_intent.putExtra("chat_id",chat_id);
        startActivity(chat_intent);
    }

    @Override
    public void onDialogsListLoadError(String reason) {
        Toast.makeText(this,reason,Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.button)
    public  void addVisit(){

        Intent intent = new Intent(ClinicActivity.this, ChooseVisitDataActivity.class);
        Bundle extra = new Bundle();
        extra.putSerializable("schedules", (Serializable) schedules);
        intent.putExtra("extra", extra);
        intent.putExtra("roomID", expert_Room_ID);
        intent.putExtra("id", doctorID);

        if (!expert_Room_ID.equals(" ")) {
            startActivity(intent);
        } else {
            Toast.makeText(ClinicActivity.this, "К сожалению нет адреса работы врача", Toast.LENGTH_LONG).show();
        }


    }

    public void animateFAB() {
        if (fabStatus) {
            fabSetting.startAnimation(rotate_backward);
            fabSub1.startAnimation(fab_close);
            fabSub2.startAnimation(fab_close);
            fabSub1.setClickable(false);
            fabSub2.setClickable(false);

//            tvFabSubscription.setVisibility(View.INVISIBLE);
            fabStatus = false;
        } else {
            fabSetting.startAnimation(rotate_forward);
            fabSub1.startAnimation(fab_open);
            fabSub2.startAnimation(fab_open);


            fabSub1.setClickable(true);
            fabSub2.setClickable(true);

            fabStatus = true;
        }
    }


}
