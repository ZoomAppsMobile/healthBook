package com.example.d.healthbook.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.d.healthbook.Adapters.DialogsAdapter;
import com.example.d.healthbook.CalendarBekarysa.CalendarActivity;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.Chat.Chat;
import com.example.d.healthbook.Models.Chat.DialogModel;
import com.example.d.healthbook.Models.ResponseGetUserData;
import com.example.d.healthbook.Presenter.DialogsPresenter;
import com.example.d.healthbook.R;
import com.example.d.healthbook.UI.MyToolbar;
import com.example.d.healthbook.View.DialogsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DialogsActivity extends BaseActivity implements DialogsView{


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 172 && data!=null) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("expert_id");
                presenter.addChat(Integer.parseInt(result));
                Toast.makeText(DialogsActivity.this,result,Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
        else if (requestCode == 173 && data!=null) {
            if(resultCode == Activity.RESULT_OK){
                String[] result=data.getStringArrayExtra("experts_id");
                String group_name = data.getStringExtra("group_name");
                presenter.addGroup(group_name,result);
               // Toast.makeText(DialogsActivity.this,result,Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    @BindView(R.id.dialogs_RV)
    RecyclerView dialogs_RV;
//    private MyToolbar toolbar;
//    private NavigationView navigation;
//    private DrawerLayout drawerLayout;
//    private ActionBarDrawerToggle mToggle;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.add_one_man_chat)
    ImageView add_one_man_chat;
    @BindView(R.id.add_group_man_chat)
    ImageView add_group_man_chat;
    @BindView(R.id.createGroupBtn)
    LinearLayout createGroupBtn;

    @BindView(R.id.loader_container_LL)
    LinearLayout loader_container_LL;
    @BindView(R.id.progressMessage_TV)
    TextView progressMessage_TV;

    @BindView(R.id.loader_RB)
    ProgressBar loader_RB;

    private Intent intent;

    private DialogsAdapter mAdapter;
//    @BindView(R.id.containerETLL)
//    LinearLayout containerETLL;
//    @BindView(R.id.titleET)
//    EditText titleET;

    List<DialogModel> adapterData = new ArrayList<DialogModel>();

    private DialogsPresenter presenter = new DialogsPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs);
        ButterKnife.bind(this);
        onViewSet();
//        toolbar = (MyToolbar) findViewById(R.id.main_page_toolbar);
//        navigation = (NavigationView) findViewById(R.id.navigationView);
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
//        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
    //    drawerLayout.addDrawerListener(mToggle);
      //  mToggle.syncState();
       // setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // mToggle.setDrawerIndicatorEnabled(true);
       // getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_black_24x24);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar_title.setText("Добавить группу для чата");
        setToolbarTitleText("Сообщение");

        navigationClickItemListener(DialogsActivity.this , DialogsActivity.class);
        navigation.getMenu().getItem(2).setChecked(true);
        mAdapter = new DialogsAdapter(adapterData,DialogsActivity.this);
        dialogs_RV.setLayoutManager(new LinearLayoutManager(DialogsActivity.this));
        dialogs_RV.setAdapter(mAdapter);
        presenter.getDialogs();
        add_one_man_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(DialogsActivity.this , SearchDoctorActivity2.class).putExtra("type","dialog"),172);
            }
        });
        add_group_man_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(DialogsActivity.this , SearchDoctorActivity2.class).putExtra("type","group"),173);
            }
        });
    }


//    public void navigationClickItemListener() {
//        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                item.setCheckable(true);
////                item.setChecked(true);
//                navigation.setCheckedItem(item.getItemId());
//
//                int id = item.getItemId();
//                switch (id) {
////                    case R.id.nav_diary_indicator:
////                        Toast.makeText(getApplicationContext(), "Дневник", Toast.LENGTH_SHORT).show();
////                        break;
//                    case R.id.nav_search_doctors:
//                        intent = new Intent(DialogsActivity.this, MainPageActivity.class);
//                        intent.putExtra("fragment", "MainPageFragment");
//                        startActivity(intent);
//                        finish();
//                        drawerLayout.closeDrawer(Gravity.LEFT);
//
//                        break;
//                    case R.id.nav_diary:
//                        Intent intent = new Intent(DialogsActivity.this, DiaryActivity.class);
//                        startActivity(intent);
//                        finish();
//                        break;
//                    case R.id.nav_profile:
//                        Intent intent2 = new Intent(DialogsActivity.this, UserActivityInfo.class);
//                        startActivity(intent2);
//                        finish();
//                        drawerLayout.closeDrawer(Gravity.LEFT);
//                        break;
//                    case R.id.nav_visit:
//                        intent = new Intent(DialogsActivity.this, MainPageActivity.class);
//                        intent.putExtra("fragment", "VisitFragment");
//                        startActivity(intent);
//                        finish();
//                        drawerLayout.closeDrawer(Gravity.LEFT);
//
//                        break;
//                    case R.id.nav_calendar:
//                        drawerLayout.closeDrawer(Gravity.LEFT);
//                        intent = new Intent(DialogsActivity.this, CalendarActivity.class);
//                        startActivity(intent);
//                        finish();
//
//                        break;
//                    case R.id.nav_message:
//
//                        drawerLayout.closeDrawer(Gravity.LEFT);
//                        break;
//                    case R.id.nav_settings:
//                        startActivity( new Intent(DialogsActivity.this, SettingsActivity.class));
//                        finish();
//                        break;
//                }
//                return false;
//            }
//        });
//    }

    @Override
    public void onDialogsListLoaded(List<DialogModel> data) {
        loader_container_LL.setVisibility(View.GONE);
        adapterData.clear();
        adapterData.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogsListLoadError(String reason) {
        loader_RB.setVisibility(View.GONE);
        Toast.makeText(DialogsActivity.this,reason,Toast.LENGTH_SHORT).show();
        progressMessage_TV.setText(reason);
    }

    @Override
    public void onNewMessage() {

    }

    @Override
    public void onProgressChanged(String message) {
        loader_container_LL.setVisibility(View.VISIBLE);
        loader_RB.setVisibility(View.VISIBLE);
        progressMessage_TV.setText(message);
    }

    @Override
    public void onDialogClicked(Integer position) {
        DialogModel sendData = adapterData.get(position);
        Intent chat_intent = new Intent(DialogsActivity.this,ChatActivity.class);
        chat_intent.putExtra("chat_data",MainController.serializeToJson(sendData));
        startActivity(chat_intent);
    }

    @Override
    public void onChatCreated(String chat_id) {
        loader_container_LL.setVisibility(View.GONE);
        Intent chat_intent = new Intent(DialogsActivity.this,ChatActivity.class);
        chat_intent.putExtra("chat_id",chat_id);
        startActivity(chat_intent);
    }


    @Override
    public void onNewChatDataLoaded(DialogModel data){
        Intent chat_intent = new Intent(DialogsActivity.this,ChatActivity.class);
        chat_intent.putExtra("chat_data",MainController.serializeToJson(data));
        startActivity(chat_intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
