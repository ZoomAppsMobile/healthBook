package com.example.d.healthbook.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.d.healthbook.API.App;
import com.example.d.healthbook.CalendarBekarysa.CalendarActivity;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.Fragments.CalendarFragment;
import com.example.d.healthbook.Fragments.ChatFragment;
import com.example.d.healthbook.Fragments.DialogFragmentFeedFilter;
import com.example.d.healthbook.Fragments.FilterFragment;
import com.example.d.healthbook.Fragments.MainPageFragment;
import com.example.d.healthbook.Fragments.UserProfileFragment;
import com.example.d.healthbook.Fragments.VisitFragment;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.ResponseGetUserData;
import com.example.d.healthbook.R;
import com.example.d.healthbook.UI.MyToolbar;
import com.example.d.healthbook.View.ActivityView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainPageActivity extends AppCompatActivity implements ActivityView {

    private static final int REQUEST_CODE = 1;
    private MyToolbar toolbar;
    private DrawerLayout drawerLayout;
    public static ActionBarDrawerToggle mToggle;
    private NavigationView navigation;
    private Fragment currentFragment;

    private DialogFragment dialogFragmentFilterFeed;



    @BindView(R.id.toolbar_title)
    public TextView toolbar_title;


    private ResponseGetUserData registeredUser;
    public static boolean mKeyboardShown;
    private RelativeLayout imageToolbarChat;
    private String checkFromUserActivity = "";


    RelativeLayout relativeLayout;

   String default_name_filter_edit="Введите имя специалиста";



    void setMenuData(){
        NavigationView mView = ( NavigationView ) findViewById( R.id.navigationView );
        if( mView != null ){
            RelativeLayout mParent = (RelativeLayout) mView.getHeaderView( 0 );

            if( mParent != null ){
                // Set your values to the image and text view by declaring and setting as you need to here.
                MainController.setImageToViewById(MainPageActivity.this,  GlobalVariables.responseGetUserData.getPhoto(),  ((CircleImageView)mParent.findViewById(R.id.profile_imageMenu)) );
                ((TextView)mParent.findViewById(R.id.last_name)).setText(MainController.stringChecker( GlobalVariables.responseGetUserData.getSurname()));
                ((TextView)mParent.findViewById(R.id.first_name)).setText(MainController.stringChecker( GlobalVariables.responseGetUserData.getName()));
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ButterKnife.bind(this);
        toolbar = (MyToolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        App.getInstance().setCurrentContext(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        relativeLayout = (RelativeLayout) findViewById(R.id.deleteToolbar);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSerach();
                App.getInstance().closeFragment(MainPageActivity.this);
                mToggle.setDrawerIndicatorEnabled(true);

                changeMenuItems(1);
            }
        });
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        setMenuData();
        navigation = (NavigationView) findViewById(R.id.navigationView);

        View header = navigation.getHeaderView(0);
//        View view = navigation.inflateHeaderView(R.layout.drawer_header);


        imageToolbarChat = (RelativeLayout) findViewById(R.id.profileToolbar);


        App.getInstance().setCurrentActivity(this);


//        App.getInstance().setFragment(MainPageActivity.this, UserProfileFragment.class.getName(), android.R.anim.fade_in, android.R.anim.fade_out, true);

        checkWhatFragmentSet();


        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);
//                item.setChecked(true);
                navigation.setCheckedItem(item.getItemId());
//                String cFragment = App.getInstance().getCurrentFragment().getClass().getName();

                currentFragment = MainPageActivity.this.getSupportFragmentManager().findFragmentById(R.id.fragment_container);


                int id = item.getItemId();
                switch (id) {
//                    case R.id.nav_diary_indicator:
//                        Toast.makeText(getApplicationContext(), "Дневник", Toast.LENGTH_SHORT).show();
//                        break;
                    case R.id.nav_search_doctors:
                        if (currentFragment instanceof MainPageFragment) {
                            drawerLayout.closeDrawer(Gravity.LEFT);

                        } else {
                            hideKeyboard2(MainPageActivity.this);
                            _startFragnment(MainPageFragment.class.getName());
                            drawerLayout.closeDrawer(Gravity.LEFT);

                        }

                        break;
//                    case R.id.nav_diary:
//                        hideKeyboard2(MainPageActivity.this);
//                        startActivity(new Intent(MainPageActivity.this, DiaryActivity.class));
//                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;
//
//                        finish();
//                        break;
                    case R.id.nav_profile:
                        hideKeyboard2(MainPageActivity.this);
                        Intent intent = new Intent(MainPageActivity.this, UserActivityInfo.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;
                        finish();

                        break;
                    case R.id.nav_visit:
                        if (currentFragment instanceof VisitFragment) {
                            Toast.makeText(getApplicationContext(), "currentFragment", Toast.LENGTH_SHORT).show();
                            drawerLayout.closeDrawer(Gravity.LEFT);
                        } else {
                            hideKeyboard2(MainPageActivity.this);
                            _startFragnment(VisitFragment.class.getName());
                            drawerLayout.closeDrawer(Gravity.LEFT);
                            Toast.makeText(getApplicationContext(), "No currentFragment", Toast.LENGTH_SHORT).show();

                        }

                        break;
                    case R.id.nav_calendar:

                        drawerLayout.closeDrawer(Gravity.LEFT);
                        hideKeyboard2(MainPageActivity.this);
                        Intent intentCal = new Intent(MainPageActivity.this, CalendarActivity.class);
                        startActivity(intentCal);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;
                        finish();

                        break;
                    case R.id.nav_settings:
                        startActivity( new Intent(MainPageActivity.this, SettingsActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;
                        finish();
                        break;
                    case R.id.nav_message:
                        Intent intentMess = new Intent(MainPageActivity.this, DialogsActivity.class);
                        startActivity(intentMess);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;
                        finish();

                        break;


                    case R.id.nav_folders:{
                        startActivity( new Intent(MainPageActivity.this, FoldersActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;
                        finish();
                        break;
                    }









                }
                return false;
            }
        });


    }

    private void checkWhatFragmentSet() {
        checkFromUserActivity = getIntent().getStringExtra("fragment");
        if (checkFromUserActivity.equals("MainPageFragment")) {
            _startFragnmentFromUserAct(MainPageFragment.class.getName());
            navigation.getMenu().getItem(0).setChecked(true);
        }
        if (checkFromUserActivity.equals("VisitFragment")) {
            _startFragnmentFromUserAct(VisitFragment.class.getName());
            navigation.getMenu().getItem(3).setChecked(true);

        }
        if (checkFromUserActivity.equals("CalendarFragment")) {
//            _startFragnmentFromUserAct(CalendarFragment.class.getName());
            Bundle bundle = new Bundle();
            bundle.putBoolean("true", true);
            App.getInstance().setFragment(MainPageActivity.this, CalendarFragment.class.getName(),
                    android.R.anim.fade_in, android.R.anim.fade_out, true, bundle, false);
        }
        if (checkFromUserActivity.equals("ChatFragment")) {
            _startFragnmentFromUserAct(ChatFragment.class.getName());
            navigation.getMenu().getItem(2).setChecked(true);

        }
    }

    public void   setDefaultName(String name){
        this.default_name_filter_edit=name;


    };


    public String   getDefaultName(){
        return default_name_filter_edit;


    };


    public void _addFragment(String fragmentName){
        App.getInstance().addFragment(MainPageActivity.this ,fragmentName);
    }

    public void _startFragnment(String fragmentName) {
        MainPageActivity.this.getSupportFragmentManager().beginTransaction().remove(App.getInstance().getCurrentFragment()).commit();
        App.getInstance().setFragment(MainPageActivity.this, fragmentName, android.R.anim.fade_in, android.R.anim.fade_out, true);

    }

    public void _startFragnmentFromUserAct(String fragmentName) {
        App.getInstance().setFragment(MainPageActivity.this, fragmentName, android.R.anim.fade_in, android.R.anim.fade_out, true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (App.getInstance().getCurrentFragment() instanceof FilterFragment) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    onBackPressed();

                    return true;
            }
        }
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            switch (item.getItemId()) {
                case R.id.action_edit_profile: {
                    if (!GlobalVariables.checkMenuBtn) {

                        _addFragment(FilterFragment.class.getName());
                        relativeLayout.setVisibility(View.VISIBLE);
                        changeMenuItems(0);

                        mToggle.setDrawerIndicatorEnabled(false);
                        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_black_24x24);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    } else {
                        dialogFragmentFilterFeed = new DialogFragmentFeedFilter();
                        dialogFragmentFilterFeed.show(getSupportFragmentManager(), "dialogFragmentFilterFeed");
                        dialogFragmentFilterFeed.setCancelable(true);
                    }

                    break;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setActivityTitle(String titlename) {
        if (toolbar_title != null) {
            toolbar_title.setText(titlename);
        }
    }

    @Override
    public void setActivityToolbarIcon(int menu_id) {
        if (toolbar != null) {
            toolbar.getMenu().clear();
            toolbar.inflateMenu(menu_id);
        }
    }

    @Override
    public void changeMenuItems(int type) {
        toolbarmenu_type = type;
    }

    @Override
    public void changeImageVisibility(int type) {
        if (type == 1) {
            imageToolbarChat.setVisibility(View.VISIBLE);
        } else {
            imageToolbarChat.setVisibility(View.GONE);
        }
    }


    public  void  closeSerach(){
        relativeLayout.setVisibility(View.GONE);

    }
    void changeMenuItemType(int type) {
        switch (type) {
            case 1: {
                if (mFilter_Item != null) {
                    mFilter_Item.setVisible(true);
                }
                break;
            }
            case 2: {
                if (mFilter_Item != null) {
                    mFilter_Item.setVisible(false);
                }
                break;
            }
            default: {
                mFilter_Item.setVisible(false);
                break;
            }
        }
    }

//    @Override
//    public Menu getMenu() {
//        if(mainMenu!=null)
//            return mainMenu;
//        return null;
//    }

    Menu mainMenu = null;
    MenuItem mFilter_Item, mFilter_feed_item;
    Integer toolbarmenu_type = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile_layout, menu);
        mFilter_Item = menu.findItem(R.id.action_edit_profile);
        changeMenuItemType(toolbarmenu_type);

        return true;
    }


    Boolean backBtnClicked = false;

    @Override
    public void onBackPressed() {

        Toast.makeText(this, "zxczzcxz", Toast.LENGTH_LONG).show();
        if(App.getInstance().tmp_fragment!=null){
            App.getInstance().closeFragment(MainPageActivity.this);


            return;
        }
        if (backBtnClicked || App.getInstance().getCurrentFragment() instanceof FilterFragment) {
            if (!(App.getInstance().getCurrentFragment() instanceof FilterFragment)) {

                super.onBackPressed();
                finish();
            } else {
                super.onBackPressed();
            }
        } else {
            backBtnClicked = true;
            Toast.makeText(getApplicationContext(), "Нажмите еще раз если хотите выйти", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backBtnClicked = false;
                }
            }, App.getInstance().BackPressTreshHold);
        }

    }

    public static void hideKeyboard2(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void hideKeyboard(View view) {
        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
