package com.example.d.healthbook.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.API.App;
import com.example.d.healthbook.CalendarBekarysa.CalendarActivity;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.Fragments.MainPageFragment;
import com.example.d.healthbook.Fragments.VisitFragment;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.ResponseAllSubscriptionsToDoctor;
import com.example.d.healthbook.Models.ResponseProgressUser;
import com.example.d.healthbook.R;
import com.example.d.healthbook.UI.BlockLayoutFabrique;
import com.example.d.healthbook.UI.CompositeBlock;
import com.example.d.healthbook.UI.CoverBlock;
import com.example.d.healthbook.UI.ICBlock;
import com.example.d.healthbook.UI.ICTBlock;
import com.example.d.healthbook.UI.ImageBlock;
import com.example.d.healthbook.UI.MyToolbar;
import com.example.d.healthbook.UI.Size;
import com.example.d.healthbook.UI.TextBlock;
import com.liulishuo.magicprogresswidget.MagicProgressCircle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DiaryActivity extends BaseActivity {




//    @BindView(R.id.toolbar_title)
//    public TextView toolbar_title;
    @BindView((R.id.main_appbar))
    AppBarLayout main_appbar;

//    private MyToolbar toolbar;
//    private NavigationView navigation;
//    private DrawerLayout drawerLayout;
//    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        ButterKnife.bind(this);
        App.getInstance().setCurrentActivity(this);
        onViewSet();
        setToolbarTitleText("Дневник");
        App.getInstance().setCurrentContext(this);
       // toolbar = (MyToolbar) findViewById(R.id.main_page_toolbar);
       // setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       //
      //  drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
      //  mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
      //  drawerLayout.addDrawerListener(mToggle);
      //  getSupportActionBar().setDisplayShowTitleEnabled(false);
      //  mToggle.syncState();
      //  setMenuData();
      //  navigation = (NavigationView) findViewById(R.id.navigationView);
        navigationClickItemListener(DiaryActivity.this,DialogsActivity.class);

        setInside();
    }

    void setInside(){
        float percentage = (float) 0.82;

        MagicProgressCircle activity_progress_bar_indicator = (MagicProgressCircle) findViewById(R.id.activity_progress_bar_text);
        activity_progress_bar_indicator.setSmoothPercent(percentage);
        TextView activity_progress_bar_text_indicator = (TextView) findViewById(R.id.activity_progress_bar_text_indicator);
        activity_progress_bar_text_indicator.setText(String.valueOf((int) (percentage * 100)) + "%");
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.metrics);

        CompositeBlock block0 = (CompositeBlock) BlockLayoutFabrique.getBlock(CompositeBlock.class);
        if(block0!=null) {
            block0.addGroup("g0", ViewGroup.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT, LinearLayout.VERTICAL);
            block0.addTextField("g0", "Двигаться: 30 мин. за день", R.color.colorBlack, 14, Typeface.DEFAULT_BOLD);
            block0.addGroup("g0", "g1", RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, LinearLayout.HORIZONTAL);
            block0.addMultilineCheckbox("g1", "П");
            block0.addMultilineCheckbox("g1", "В");
            block0.addMultilineCheckbox("g1", "С");
            block0.addMultilineCheckbox("g1", "Ч");
            block0.addMultilineCheckbox("g1", "П");
            block0.addMultilineCheckbox("g1", "С");
            block0.addMultilineCheckbox("g1", "В");
            block0.getGroup("g1").setPadding(0, 10, 0, 0);
            block0.addGroup("g2", RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            block0.addCircularProgressBar("g2", "prg", 100, 100, 6, R.color.colorDarkGray);
            block0.setAlignment("g2", RelativeLayout.ALIGN_PARENT_RIGHT);
            block0.getGroup("g2").setPadding(0, 10, 10, 0);
            block0.setMargin(10, 10, 10, 10);
            block0.setPadding(20, 20, 20, 20);
            block0.adjustToView(viewGroup, new Size(1, 0));
        }
        ImageBlock block1 = (ImageBlock) BlockLayoutFabrique.getBlock(ImageBlock.class);
        if(block1!=null) {
            block1.setLabelText("Вода");
            block1.setImageResourse(R.drawable.icon_water);
            block1.setTextValue("9");
            block1.setTextValueColor(R.color.colorWhite);
            block1.setMargin(10, 10, 10, 10);
        }
        TextBlock block2 = (TextBlock) BlockLayoutFabrique.getBlock(TextBlock.class);
        if(block1!=null) {
            block2.setLabelText("Вес");
            block2.setValueText("55");
            block2.setValueColor(R.color.coral_blue);
            block2.setMargin(10, 10, 10, 10);
        }
        TextBlock block3 = (TextBlock) BlockLayoutFabrique.getBlock(TextBlock.class);
        if(block3!=null) {
            block3.setLabelText("Сон");
            block3.setValueText("55");
            block3.setValueColor(R.color.md_red_400);
            block3.setMargin(10, 10, 10, 10);
        }
        TextBlock block4 = (TextBlock) BlockLayoutFabrique.getBlock(TextBlock.class);
        if(block4!=null) {
            block4.setLabelText("Давление");
            block4.setValueText("120/80");
            block4.setMargin(10, 10, 10, 10);
        }
        TextBlock block5 = (TextBlock) BlockLayoutFabrique.getBlock(TextBlock.class);
        if(block5!=null) {
            block5.setLabelText("Сахар");
            block5.setValueText("4,5");
            block5.setMargin(10, 10, 10, 10);
        }
        CoverBlock block6 = (CoverBlock) BlockLayoutFabrique.getBlock(CoverBlock.class);
        if(block6!=null) {
            block6.setHeadingText("+");
            block6.setDescriptionText("Управление данными");
            block6.setMargin(10, 10, 10, 10);
        }
        ICTBlock block7 = (ICTBlock) BlockLayoutFabrique.getBlock(ICTBlock.class);
        if(block7!=null){
        block7.setImageResourse(R.drawable.ic_menu_camera);
        block7.setContentText("Креон");
        block7.setTime("18:00");
        block7.setMargin(10,10,10,5);}

        ICBlock block8 = (ICBlock) BlockLayoutFabrique.getBlock(ICBlock.class);
        if(block8!=null) {
            block8.setImageResourse(R.drawable.ic_menu_camera);
            block8.setContentText("Добавить напоминание о графике приема лекарств");
            block8.setMargin(10, 5, 10, 10);
        }

        if(block1!=null)
            block1.adjustToView(viewGroup, new Size(0.33, 1));
        if(block2!=null)
            block2.adjustToView(viewGroup, new Size(0.33, 1));
        if(block3!=null)
            block3.adjustToView(viewGroup, new Size(0.33, 1));
        if(block4!=null)
            block4.adjustToView(viewGroup, new Size(0.33, 1));
        if(block5!=null)
            block5.adjustToView(viewGroup, new Size(0.33, 1));
        if(block6!=null)
            block6.adjustToView(viewGroup, new Size(0.33, 1));
        if(block7!=null)
            block7.adjustToView(viewGroup, new Size(1, 0));
        if(block8!=null)
            block8.adjustToView(viewGroup, new Size(1, 0));

        ViewCompat.setTranslationZ(findViewById(R.id.scroll_view), -1000);

    }

    void setMenuData(){
        NavigationView mView = ( NavigationView ) findViewById( R.id.navigationView );
        if( mView != null ){
            RelativeLayout mParent = (RelativeLayout) mView.getHeaderView( 0 );

            if( mParent != null ){
                // Set your values to the image and text view by declaring and setting as you need to here.
                MainController.setImageToViewById(DiaryActivity.this,  GlobalVariables.responseGetUserData.getPhoto(),  ((CircleImageView)mParent.findViewById(R.id.profile_imageMenu)) );
                ((TextView)mParent.findViewById(R.id.last_name)).setText(MainController.stringChecker( GlobalVariables.responseGetUserData.getSurname()));
                ((TextView)mParent.findViewById(R.id.first_name)).setText(MainController.stringChecker( GlobalVariables.responseGetUserData.getName()));
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


//    public void navigationClickItemListener() {
//        navigation.getMenu().getItem(5).setChecked(true);
//        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                item.setCheckable(true);
////                item.setChecked(true);
//                navigation.setCheckedItem(item.getItemId());
//                int id = item.getItemId();
//                Intent intent;
//                switch (id) {
////                    case R.id.nav_diary_indicator:
////                        Toast.makeText(getApplicationContext(), "Дневник", Toast.LENGTH_SHORT).show();
////                        break;
//                    case R.id.nav_search_doctors:
//                        intent = new Intent(DiaryActivity.this, MainPageActivity.class);
//                        intent.putExtra("fragment", "MainPageFragment");
//                        startActivity(intent);
//                        finish();
//                        drawerLayout.closeDrawer(Gravity.LEFT);
//                        break;
//                    case R.id.nav_diary:
//                        drawerLayout.closeDrawer(Gravity.LEFT);
//                        break;
//                    case R.id.nav_profile:
//                        Intent intent2 = new Intent(DiaryActivity.this, UserActivityInfo.class);
//                        startActivity(intent2);
//                        finish();
//                        drawerLayout.closeDrawer(Gravity.LEFT);
//                        break;
//                    case R.id.nav_visit:
//                        intent = new Intent(DiaryActivity.this, MainPageActivity.class);
//                        intent.putExtra("fragment", "VisitFragment");
//                        startActivity(intent);
//                        finish();
//                        drawerLayout.closeDrawer(Gravity.LEFT);
//
//                        break;
//                    case R.id.nav_calendar:
//                        drawerLayout.closeDrawer(Gravity.LEFT);
//                        intent = new Intent(DiaryActivity.this, CalendarActivity.class);
//                        startActivity(intent);
//                        finish();
//
//                        break;
//                    case R.id.nav_message:
//                        Intent intentMess = new Intent(DiaryActivity.this, ChatActivityShowAllChat.class);
//                        startActivity(intentMess);
//                        finish();
//                        break;
//                    case R.id.nav_settings:
//                        startActivity( new Intent(DiaryActivity.this, SettingsActivity.class));
//                        finish();
//                        break;
//                }
//                return false;
//            }
//        });
//    }
}
