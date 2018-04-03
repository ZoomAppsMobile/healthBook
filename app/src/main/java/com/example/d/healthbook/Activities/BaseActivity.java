package com.example.d.healthbook.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.d.healthbook.CalendarBekarysa.CalendarActivity;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.R;
import com.example.d.healthbook.UI.MyToolbar;
import com.example.d.healthbook.View.BaseActivityView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by User on 28.08.2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseActivityView {
    @Override
    public void onViewSet() {
        navigation = (NavigationView) findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        toolbar = (MyToolbar) findViewById(R.id.main_page_toolbar);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_black_24x24);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setMenuData();
    }
    void setMenuData(){
        NavigationView mView = ( NavigationView ) findViewById( R.id.navigationView );
        if( mView != null && GlobalVariables.responseGetUserData!=null){
            RelativeLayout mParent = (RelativeLayout) mView.getHeaderView( 0 );

            if( mParent != null ){
                // Set your values to the image and text view by declaring and setting as you need to here.
                MainController.setImageToViewById(this,  GlobalVariables.responseGetUserData.getPhoto(),  ((CircleImageView)mParent.findViewById(R.id.profile_imageMenu)) );
                ((TextView)mParent.findViewById(R.id.last_name)).setText(MainController.stringChecker( GlobalVariables.responseGetUserData.getSurname()));
                ((TextView)mParent.findViewById(R.id.first_name)).setText(MainController.stringChecker( GlobalVariables.responseGetUserData.getName()));
            }
        }

    }
    protected void setToolbarTitleText(String title){
        ((TextView)findViewById(R.id.toolbar_title)).setText(title);
    }

    protected NavigationView navigation;
    protected DrawerLayout drawerLayout;
    protected ActionBarDrawerToggle mToggle;
    protected MyToolbar toolbar;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private boolean checkCurrentActivity(Class currentActivity , Class toActivity){
        if(currentActivity != toActivity){
            return true;
        }
        return  false;
    }
    public void navigationClickItemListener(final Context context,final Class currentActivity) {
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);
//                item.setChecked(true);
                navigation.setCheckedItem(item.getItemId());
                Intent intent;
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_search_doctors:
                        if(checkCurrentActivity(currentActivity, MainPageActivity.class)) {
                            intent = new Intent(context, MainPageActivity.class);
                            intent.putExtra("fragment", "MainPageFragment");
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;
                            finish();
                           // drawerLayout.closeDrawer(Gravity.LEFT);
                        }
                        break;
//                    case R.id.nav_diary:
//                        if(checkCurrentActivity(currentActivity, DiaryActivity.class)) {
//                            intent = new Intent(context, DiaryActivity.class);
//                            startActivity(intent);
//                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;
//                            finish();
//                        }
//                        break;
                    case R.id.nav_folders:{
                        if(checkCurrentActivity(currentActivity,FoldersActivity.class)){
                            startActivity(new Intent(context,FoldersActivity.class));
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;
                            finish();
                        }
                    }
                    case R.id.nav_profile:
                        if(checkCurrentActivity(currentActivity, UserActivityInfo.class)) {
                            intent = new Intent(context, UserActivityInfo.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;
                            finish();
                        }
                        break;
                    case R.id.nav_visit:
                        if(checkCurrentActivity(currentActivity, MainPageActivity.class)) {
                            intent = new Intent(context, MainPageActivity.class);
                            intent.putExtra("fragment", "VisitFragment");
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;
                            finish();
                        }
                        break;
                    case R.id.nav_calendar:
                        if(checkCurrentActivity(currentActivity, CalendarActivity.class)) {
                            intent = new Intent(context, CalendarActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;
                            finish();
                        }
                        break;
                    case R.id.nav_settings:
                        if(checkCurrentActivity(currentActivity, SettingsActivity.class)) {
                            startActivity(new Intent(context, SettingsActivity.class));
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;
                            finish();
                        }
                        break;
                    case R.id.nav_message:

                            Intent intentMess = new Intent(context, DialogsActivity.class); //ChatActivityShowAllChat.class);
                            startActivity(intentMess);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;
                            finish();

                        break;
                }
                drawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        });
    }
}
