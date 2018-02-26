package com.example.d.healthbook.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.d.healthbook.Adapters.RecyclerChangebaleRowAllChatGroup;
import com.example.d.healthbook.CalendarBekarysa.CalendarActivity;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.ChatModel;
import com.example.d.healthbook.Models.ChatRealmListModel;
import com.example.d.healthbook.Models.GroupModel;
import com.example.d.healthbook.R;
import com.example.d.healthbook.UI.MyToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ChatActivityShowAllChat extends AppCompatActivity {
    private MyToolbar toolbar;
    private NavigationView navigation;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.add_one_man_chat)
    ImageView add_one_man_chat;
    @BindView(R.id.add_group_man_chat)
    ImageView add_group_man_chat;
    @BindView(R.id.createGroupBtn)
    LinearLayout createGroupBtn;
    @BindView(R.id.containerETLL)
    LinearLayout containerETLL;
    @BindView(R.id.titleET)
    EditText titleET;
    private Intent intent;
    private RealmList<ChatModel> chatModels;
    private Realm mRealm;
    private RecyclerView mRecyclerViewChat;
    private RecyclerChangebaleRowAllChatGroup adapter;
    private static String titleGroup = "";
    public static List<ChatModel> chatModelsAddOneMan;
    private String groupPeople = "";
    private String addpeople;
    private int IMG;

//    @BindView(R.id.profile_imageMenu)
//    CircleImageView profile_imageMenu;
//    @BindView(R.id.first_name)
//    TextView first_name;
//    @BindView(R.id.last_name)
//    TextView last_name;

    void setMenuData(){
        NavigationView mView = ( NavigationView ) findViewById( R.id.navigationView );
        if( mView != null ){
            RelativeLayout mParent = (RelativeLayout) mView.getHeaderView( 0 );

            if( mParent != null ){
                // Set your values to the image and text view by declaring and setting as you need to here.
                MainController.setImageToViewById(ChatActivityShowAllChat.this,  GlobalVariables.responseGetUserData.getPhoto(),  ((CircleImageView)mParent.findViewById(R.id.profile_imageMenu)) );
                ((TextView)mParent.findViewById(R.id.last_name)).setText(MainController.stringChecker( GlobalVariables.responseGetUserData.getSurname()));
                ((TextView)mParent.findViewById(R.id.first_name)).setText(MainController.stringChecker( GlobalVariables.responseGetUserData.getName()));
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_show_all_chat);
        ButterKnife.bind(this);

        mRealm = Realm.getInstance(this);

        toolbar = (MyToolbar) findViewById(R.id.main_page_toolbar);
        navigation = (NavigationView) findViewById(R.id.navigationView);
        setMenuData();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mRecyclerViewChat = (RecyclerView) findViewById(R.id.reccyclerChat);
        mRecyclerViewChat.setLayoutManager(new LinearLayoutManager(this));


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToggle.setDrawerIndicatorEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_black_24x24);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar_title.setText("Добавить группу для чата");
        navigationClickItemListener();
        navigation.getMenu().getItem(2).setChecked(true);


        addItemRecycler();
        setRealmChatToRecycler();

        groupPeople = getIntent().getStringExtra("group");
        addpeople = getIntent().getStringExtra("addpeople");
        IMG = getIntent().getIntExtra("IMG", -1);

        if (groupPeople != null) {
            setGroupNewGroupORChatMethod(false, "", 0, chatModels);
        } else if (addpeople != null) {
            setGroupNewGroupORChatMethod(true, addpeople, IMG, chatModelsAddOneMan);
        }


        add_one_man_chat.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ChatActivityShowAllChat.this, ChatActivityShowMessage.class);
//                startActivity(intent);
                Intent intent = new Intent(ChatActivityShowAllChat.this, ChatActivityChooseManForGroup.class);
                intent.putExtra("addOne", true);
                startActivity(intent);
            }
        });

        add_group_man_chat.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                toolbar_title.setText("Название чата");
                add_one_man_chat.setVisibility(View.GONE);
                add_group_man_chat.setVisibility(View.GONE);
                mToggle.setDrawerIndicatorEnabled(false);
                createGroupBtn.setVisibility(View.VISIBLE);
                containerETLL.setVisibility(View.VISIBLE);

                titleET.setText("");
                titleET.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(titleET, InputMethodManager.SHOW_IMPLICIT);

                titleET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_NEXT) {
                            createGroupBtn.performClick();
                            return true;
                        }
                        return false;
                    }
                });
                createGroupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        titleGroup = titleET.getText().toString();
                        if (!titleGroup.equals("")) {
                            Intent intent = new Intent(ChatActivityShowAllChat.this, ChatActivityChooseManForGroup.class);
                            startActivity(intent);
                        } else {
                            MainController.showToast(ChatActivityShowAllChat.this,
                                    "Введите название группы");
                        }

                    }
                });

            }
        });
    }


    public void setGroupNewGroupORChatMethod(boolean addOne, String addpeople, Integer IMG, List<ChatModel> chatModels) {

        final RealmResults<ChatRealmListModel> chatRealmListModels = mRealm.allObjects(ChatRealmListModel.class);
        final List<ChatRealmListModel> chatRealmListModelList = mRealm.copyFromRealm(chatRealmListModels);

        mRealm.beginTransaction();
        chatRealmListModels.clear();
        mRealm.commitTransaction();


        if (!addOne) {
            GroupModel groupModel = new GroupModel(groupPeople, titleGroup);
            mRealm.beginTransaction();
            ChatRealmListModel model2 = mRealm.createObject(ChatRealmListModel.class);
            model2.setType(2);
            model2.getGroupModel().add(groupModel);
            mRealm.commitTransaction();
        } else {
            mRealm.beginTransaction();
            ChatRealmListModel model2 = mRealm.createObject(ChatRealmListModel.class);
            for (int i = 0; i < chatModels.size(); i++) {
                model2.getChatModels().add(chatModels.get(i));
            }
            model2.setNameSurname(addpeople);
            model2.setImageMan(IMG);
            model2.setType(1);
            mRealm.commitTransaction();
        }


        for (int i = 0; i <= chatRealmListModelList.size() - 1; i++) {
            mRealm.beginTransaction();

            ChatRealmListModel model3 = mRealm.createObject(ChatRealmListModel.class);
            model3.setType(chatRealmListModelList.get(i).getType());
            model3.setNameSurname(chatRealmListModelList.get(i).getNameSurname());
            model3.setImageMan(chatRealmListModelList.get(i).getImageMan());
            if (chatRealmListModelList.get(i).getChatModels().size() != 0) {
                for (int j = 0; j < chatRealmListModelList.get(i).getChatModels().size(); j++) {
                    model3.getChatModels().add(chatRealmListModelList.get(i).getChatModels().get(j));
                }
            } else {
                for (int j = 0; j < chatRealmListModelList.get(i).getGroupModel().size(); j++) {
                    model3.getGroupModel().add(chatRealmListModelList.get(i).getGroupModel().get(j));
                }

            }
            mRealm.commitTransaction();
        }


        adapter.notifyDataSetChanged();
    }

    private void addItemRecycler() {

        chatModels = new RealmList<>();
        ChatModel chatModel = new ChatModel(1, "Привет как дела?", true);
        ChatModel chatModel1 = new ChatModel(2, "Нормально у тебя?", true, R.drawable.placeholder);
        ChatModel chatModel2 = new ChatModel(1, "замечательно", true);
        ChatModel chatModel3 = new ChatModel(2, "Че пропала?", false);
        ChatModel chatModel4 = new ChatModel(2, "Давай увидимся?", false);

        ChatModel chatMode01 = new ChatModel(1, "Верните мне деньги?", true);
        ChatModel chatMode02 = new ChatModel(2, "чего какие деньги?", true, R.drawable.placeholder);
        ChatModel chatMode03 = new ChatModel(1, "доллары", true);
        ChatModel chatMode04 = new ChatModel(2, "кто это?", false);
        ChatModel chatMode05 = new ChatModel(2, "не пишите сюда больше?", false);
        ChatModel chatMode06 = new ChatModel(2, "понятно?", false);

        RealmResults<ChatRealmListModel> listModels = mRealm.allObjects(ChatRealmListModel.class);
        if (listModels.isEmpty()) {
            mRealm.beginTransaction();
            ChatRealmListModel model = mRealm.createObject(ChatRealmListModel.class);
            model.setNameSurname("Петруха");
            model.setType(1);
            model.setImageMan(R.drawable.placeholder);
            model.getChatModels().add(chatModel);
            model.getChatModels().add(chatModel1);
            model.getChatModels().add(chatModel2);
            model.getChatModels().add(chatModel3);
            model.getChatModels().add(chatModel4);


            mRealm.commitTransaction();

            mRealm.beginTransaction();
            ChatRealmListModel model2 = mRealm.createObject(ChatRealmListModel.class);
            model2.setNameSurname("Братушка");
            model2.setType(1);
            model2.setImageMan(R.drawable.placeholder);
            model2.getChatModels().add(chatMode01);
            model2.getChatModels().add(chatMode02);
            model2.getChatModels().add(chatMode03);
            model2.getChatModels().add(chatMode04);
            model2.getChatModels().add(chatMode05);
            model2.getChatModels().add(chatMode06);


            mRealm.commitTransaction();
        }
    }

    private void setRealmChatToRecycler() {
        RealmResults<ChatRealmListModel> listModels = mRealm.allObjects(ChatRealmListModel.class);
        adapter = new RecyclerChangebaleRowAllChatGroup(listModels, ChatActivityShowAllChat.this);
        mRecyclerViewChat.setAdapter(adapter);

    }

    public void navigationClickItemListener() {
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);
//                item.setChecked(true);
                navigation.setCheckedItem(item.getItemId());

                int id = item.getItemId();
                switch (id) {
//                    case R.id.nav_diary_indicator:
//                        Toast.makeText(getApplicationContext(), "Дневник", Toast.LENGTH_SHORT).show();
//                        break;
                    case R.id.nav_search_doctors:
                        intent = new Intent(ChatActivityShowAllChat.this, MainPageActivity.class);
                        intent.putExtra("fragment", "MainPageFragment");
                        startActivity(intent);
                        finish();
                        drawerLayout.closeDrawer(Gravity.LEFT);

                        break;
                    case R.id.nav_diary:
                        Intent intent = new Intent(ChatActivityShowAllChat.this, DiaryActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.nav_profile:
                        Intent intent2 = new Intent(ChatActivityShowAllChat.this, UserActivityInfo.class);
                        startActivity(intent2);
                        finish();
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.nav_visit:
                        intent = new Intent(ChatActivityShowAllChat.this, MainPageActivity.class);
                        intent.putExtra("fragment", "VisitFragment");
                        startActivity(intent);
                        finish();
                        drawerLayout.closeDrawer(Gravity.LEFT);

                        break;
                    case R.id.nav_calendar:
                        drawerLayout.closeDrawer(Gravity.LEFT);
                        intent = new Intent(ChatActivityShowAllChat.this, CalendarActivity.class);
                        startActivity(intent);
                        finish();

                        break;
                    case R.id.nav_message:

                        drawerLayout.closeDrawer(Gravity.LEFT);
                        break;
                    case R.id.nav_settings:
                        startActivity( new Intent(ChatActivityShowAllChat.this, SettingsActivity.class));
                        finish();
                        break;
                }
                return false;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            switch (item.getItemId()) {
                case android.R.id.home:
                    toolbar_title.setText("Health book");
                    add_one_man_chat.setVisibility(View.VISIBLE);
                    add_group_man_chat.setVisibility(View.VISIBLE);
                    createGroupBtn.setVisibility(View.GONE);
                    containerETLL.setVisibility(View.GONE);


                    mToggle.setDrawerIndicatorEnabled(true);
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_black_24x24);
                    hideKeyboard2(ChatActivityShowAllChat.this);
                    return true;
            }
        }

        return super.onOptionsItemSelected(item);
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
}
