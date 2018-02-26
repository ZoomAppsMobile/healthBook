package com.example.d.healthbook.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.d.healthbook.Adapters.RecyclerAdapterHorizontalChat;
import com.example.d.healthbook.Adapters.RecyclerChatAdapterShowPeople;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.Models.ChatManIMG;
import com.example.d.healthbook.Models.ChatPeopleAddGroup;
import com.example.d.healthbook.R;
import com.example.d.healthbook.UI.MyToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivityChooseManForGroup extends AppCompatActivity {
    private MyToolbar toolbar;
    private RecyclerView mRecyclerViewChat;
    public RecyclerView recylcerShowPeople;
    public List<ChatPeopleAddGroup> chatPeopleAddGroups;
    public RecyclerChatAdapterShowPeople adapterShowPeople;
    public RecyclerAdapterHorizontalChat adapterHorizontalChat;
    public static int positionItem = -1;
    public static boolean checkClick = false;
    private List<ChatManIMG> chatManIMGs = new ArrayList<>();
    private List<String> chatNameMan = new ArrayList<>();
    private boolean addOne = false;

    @BindView(R.id.quantityPeopleGroup)
    TextView quantityPeopleGroup;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_choose_man_for_group);

        ButterKnife.bind(this);

        toolbar = (MyToolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        addOne = getIntent().getBooleanExtra("addOne", false);
        if (addOne) {
            setShowPeopleAddroup();
            setPeopleList(true);
        } else {
            setShowPeopleAddroup();
            setPeopleList(false);
            quantityPeopleGroup.setText("Вы никого не выбрали");
        }

        floatingActionButtonClick();


    }

    private void floatingActionButtonClick() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addOne) {
                    String peopleGroup = "";
                    String devider = "";
                    for (int i = 0; i < chatManIMGs.size(); i++) {
                        if (i + 1 != chatManIMGs.size()) {
                            devider = ",";
                        }
                        peopleGroup += chatManIMGs.get(i).getName() + devider;
                        devider = "";
                    }

                    if (!peopleGroup.equals("")) {
                        Intent intent = new Intent(ChatActivityChooseManForGroup.this, ChatActivityShowAllChat.class);
                        intent.putExtra("group", peopleGroup);
                        startActivity(intent);
                    } else
                        MainController.showToast(ChatActivityChooseManForGroup.this, "Добавьте в группу хотя бы одного человека");
                } else {
                    String people = "";
                    people = chatManIMGs.get(0).getName();
                    if (!people.equals("")) {
                        Intent intent = new Intent(ChatActivityChooseManForGroup.this, ChatActivityShowMessage.class);
                        intent.putExtra("people", people);
                        intent.putExtra("img",chatManIMGs.get(0).getDrawableIMG());
                        startActivity(intent);
                        finish();
                    } else
                        MainController.showToast(ChatActivityChooseManForGroup.this, "Добавьте хоть бы одного человека");
                }
            }
        });
    }

    public void checkIfAlreadyAdd(List<ChatPeopleAddGroup> list, int positionItem) {
        boolean alreadyAdd = false;
        int deletePosition = 0;

        if (!chatManIMGs.isEmpty()) {
            for (int i = 0; i < chatManIMGs.size(); i++) {
                if (chatManIMGs.get(i).getDrawableIMG() == list.get(positionItem).getImage()) {
                    deletePosition = i;
                    chatManIMGs.remove(i);
                    list.get(positionItem).setSelected(false);
                    alreadyAdd = true;
                }
            }
        }
        if (!alreadyAdd) {
            chatManIMGs.add(chatManIMGs.size(), new ChatManIMG(list.get(positionItem).getImage(),
                    list.get(positionItem).getName()));

            quantityPeopleGroup.setText("Выбрано: " + chatManIMGs.size());
            adapterHorizontalChat.notifyItemInserted(chatManIMGs.size());
        } else {
            quantityPeopleGroup.setText("Выбрано: " + chatManIMGs.size());
            adapterHorizontalChat.notifyItemRemoved(deletePosition);

        }

    }

    public void checkIfAlreadyAddOneMan(List<ChatPeopleAddGroup> list, int positionItem) {
        boolean alreadyAdd = false;
        if (!chatManIMGs.isEmpty()) {
            if (chatManIMGs.get(0).getDrawableIMG() == list.get(positionItem).getImage()) {
                chatManIMGs.remove(0);
                list.get(positionItem).setSelected(false);
                adapterHorizontalChat.notifyItemRemoved(0);
                alreadyAdd = true;
                quantityPeopleGroup.setText("Вы никого не выбрали");
            }
        }
        if (!alreadyAdd) {
            if (!chatManIMGs.isEmpty())
                chatManIMGs.remove(0);
            chatManIMGs.add(chatManIMGs.size(), new ChatManIMG(list.get(positionItem).getImage(),
                    list.get(positionItem).getName()));


            adapterHorizontalChat.notifyItemRemoved(0);
            adapterHorizontalChat.notifyItemInserted(chatManIMGs.size());
            quantityPeopleGroup.setText("Выбрано");
        }
    }


    private void setShowPeopleAddroup() {
        mRecyclerViewChat = (RecyclerView) findViewById(R.id.recyclerShowAddManGroup);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewChat.setLayoutManager(layoutManager);
        adapterHorizontalChat = new RecyclerAdapterHorizontalChat(chatManIMGs, this);
        mRecyclerViewChat.setAdapter(adapterHorizontalChat);


    }


    private void setPeopleList(boolean addOne) {
        recylcerShowPeople = (RecyclerView) findViewById(R.id.recylcerShowPeople);
        recylcerShowPeople.setLayoutManager(new LinearLayoutManager(this));

        ChatPeopleAddGroup chatPeopleAddGroup = new ChatPeopleAddGroup("Антонио Куанышев", false, false,
                R.drawable.placeholder);
        ChatPeopleAddGroup chatPeopleAddGroup1 = new ChatPeopleAddGroup("FFF", false, false, R.drawable.placeholder);
        ChatPeopleAddGroup chatPeopleAddGroup2 = new ChatPeopleAddGroup("Жандарбек Траволта", false, false,
                R.drawable.placeholder);
        ChatPeopleAddGroup chatPeopleAddGroup3 = new ChatPeopleAddGroup("Нарко Зависич", false, false, R.drawable.placeholder);
        ChatPeopleAddGroup chatPeopleAddGroup4 = new ChatPeopleAddGroup("Айайай Ыыыыыев", false, false, R.drawable.placeholder);
        ChatPeopleAddGroup chatPeopleAddGroup5 = new ChatPeopleAddGroup("Стивен Сигал", false, false, R.drawable.placeholder);
        ChatPeopleAddGroup chatPeopleAddGroup6 = new ChatPeopleAddGroup("FFFF", false, false, R.drawable.placeholder);
        ChatPeopleAddGroup chatPeopleAddGroup7 = new ChatPeopleAddGroup("KKKKK", false, false, R.drawable.placeholder);
        ChatPeopleAddGroup chatPeopleAddGroup8 = new ChatPeopleAddGroup("АЛОХА", false, false, R.drawable.placeholder);
        ChatPeopleAddGroup chatPeopleAddGroup9 = new ChatPeopleAddGroup("МАКАЛАЙ", false, false, R.drawable.placeholder);
        ChatPeopleAddGroup chatPeopleAddGroup10 = new ChatPeopleAddGroup("БАЛАЛАЙ", false, false, R.drawable.placeholder);


        chatPeopleAddGroups = new ArrayList<ChatPeopleAddGroup>();
        chatPeopleAddGroups.add(chatPeopleAddGroup);
        chatPeopleAddGroups.add(chatPeopleAddGroup1);
        chatPeopleAddGroups.add(chatPeopleAddGroup2);
        chatPeopleAddGroups.add(chatPeopleAddGroup3);
        chatPeopleAddGroups.add(chatPeopleAddGroup4);
        chatPeopleAddGroups.add(chatPeopleAddGroup5);
        chatPeopleAddGroups.add(chatPeopleAddGroup6);
        chatPeopleAddGroups.add(chatPeopleAddGroup7);

        chatPeopleAddGroups.add(chatPeopleAddGroup8);
        chatPeopleAddGroups.add(chatPeopleAddGroup9);
        chatPeopleAddGroups.add(chatPeopleAddGroup10);


        adapterShowPeople = new RecyclerChatAdapterShowPeople(chatPeopleAddGroups, this, addOne);

        RecyclerView.ItemAnimator animator = recylcerShowPeople.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }


        recylcerShowPeople.setAdapter(adapterShowPeople);


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
}
