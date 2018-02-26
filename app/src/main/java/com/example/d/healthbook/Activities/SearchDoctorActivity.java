package com.example.d.healthbook.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Adapters.RecyclerDoctorListAdapter;
import com.example.d.healthbook.Fragments.DialogFragmentFeedFilter;
import com.example.d.healthbook.Fragments.FilterFragment;
import com.example.d.healthbook.Fragments.FilterFragment2;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.ResponseDoctorList;
import com.example.d.healthbook.R;
import com.example.d.healthbook.UI.CustomDialogBuilder;
import com.example.d.healthbook.View.AdapterSelection;
import com.example.d.healthbook.View.FilterInterface;

import java.io.IOException;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchDoctorActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener ,FilterInterface,AdapterSelection {
    private ResponseDoctorList registeredUser;
    private RecyclerView mRecyclerView;
    private RecyclerDoctorListAdapter recyclerDoctorListAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager mLayoutManager;
    private int pages = 0;
    private int countPages = 1;
    private String nameDoctor;
    private String cityName;
    private String speciality;
    private FrameLayout fragment_container;
    private String groupName="";

    private LinearLayout additional_layout_for_group;
    private EditText group_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        App.getInstance().setCurrentContext(this);
        App.getInstance().setCurrentActivity(this);
        fragment_container = (FrameLayout)findViewById(R.id.fragment_container);
        mRecyclerView = (RecyclerView) findViewById(R.id.doctorlist_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        additional_layout_for_group = (LinearLayout)findViewById(R.id.additional_layout_for_group);
        group_name = (EditText)findViewById(R.id.group_name);
        seeDoctorMethod("1");
        currentType = getIntent().getStringExtra("type");
        if(currentType.equals("group")){
            FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!checkedList.isEmpty()){
                        String text = String.format("Вы точно хотите выбрать пользователей ");
                        String[] doc_id = new String[checkedList.size()];
                        doc_id = checkedList.toArray(doc_id);
                        final String[] finalDoc_id = doc_id;
                        CustomDialogBuilder builder = new CustomDialogBuilder(SearchDoctorActivity.this)
                                .setTitle("Выбор пользователя")
                                .setMessage(text).setPositiveClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        setUsers(finalDoc_id);
                                    }
                                }).build();
                        builder.showDialog();
                    }
                    else{
                        Toast.makeText(SearchDoctorActivity.this , "Вы не бырали пользователей", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            if(getIntent().getBooleanExtra("is_edit",false)){
                String group_name = getIntent().getStringExtra("group_name");
                String[] checkedUsers = getIntent().getStringArrayExtra("checked_users");
                for(String tmp_user : checkedUsers) {
                    checkedList.add(tmp_user);
                }
                groupName= group_name;
            }
            additional_layout_for_group.setVisibility(View.VISIBLE);
            group_name.setText(groupName);
            group_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    groupName =charSequence.toString();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
    String currentType="";
    int CURRENT_PAGE_NUM = 1;
    public void addNewDataintoRecycler() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    if (!loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            if (CURRENT_PAGE_NUM >= 1 && CURRENT_PAGE_NUM < pages) {
                                loading = true;
                             //   Toast.makeText(SearchDoctorActivity.this, "Загрузка списка докторов", Toast.LENGTH_SHORT).show();
                                addDoctorList(CURRENT_PAGE_NUM + 1);
                            }
                        }
                    }
                }
            }
        });
    }
    public void addDoctorList(Integer page_num) {
        App.getApi().addDoctorList(page_num, 1).enqueue(new Callback<ResponseDoctorList>() {
            @Override
            public void onResponse(Call<ResponseDoctorList> call, Response<ResponseDoctorList> response) {
                int s = response.code();
                if (response.errorBody() != null) {
                    try {
                        Log.e("LOG", "Retrofit Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                registeredUser = response.body();
                if (registeredUser.getDocuments() != null) {
                    loading = false;
                    CURRENT_PAGE_NUM++;
                    Log.e("CURRENT_PAGE_NUM", "" + CURRENT_PAGE_NUM);
                  //  Toast.makeText(SearchDoctorActivity.this, "Загрузка прошла успешно ", Toast.LENGTH_SHORT).show();
                    Log.e("Data size ", "" + registeredUser.getDocuments().size());
                    for (int i = 0; i < registeredUser.getDocuments().size(); i++) {
                        Log.e("Iteration", "" + i);
                        GlobalVariables.documentList.add(registeredUser.getDocuments().get(i));
                    }
                    recyclerDoctorListAdapter.notifyDataSetChanged();
                    if (recyclerDoctorListAdapter == null)
                        return;
                    Log.e("GlobalVariables SIZE ", "" + GlobalVariables.documentList.size());

                } else {
                    Toast.makeText(SearchDoctorActivity.this, "Ошибочка", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorList> call, Throwable t) {
                Toast.makeText(SearchDoctorActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void seeDoctorMethod(String id) {
        App.getApi().seeDoctorList(id).enqueue(new Callback<ResponseDoctorList>() {
            @Override
            public void onResponse(Call<ResponseDoctorList> call, Response<ResponseDoctorList> response) {
                int s = response.code();
                if (response.errorBody() != null) {
                    try {
                        Log.e("LOG", "Retrofit Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                registeredUser = response.body();
                if (registeredUser.getDocuments() != null) {
                    CURRENT_PAGE_NUM = 1;
                    loading = false;
                 //   Toast.makeText(SearchDoctorActivity.this, "SUCCESS", Toast.LENGTH_LONG).show();

                    GlobalVariables.documentList = registeredUser.getDocuments();
                    recyclerDoctorListAdapter =
                            new RecyclerDoctorListAdapter(GlobalVariables.documentList, SearchDoctorActivity.this, true,currentType);

                    mRecyclerView.setLayoutManager(mLayoutManager);
                    pages = registeredUser.getPages();
                    addNewDataintoRecycler();

//                    mRecyclerViewChat.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(recyclerDoctorListAdapter);


                } else {
                    Toast.makeText(SearchDoctorActivity.this, "Ошибочка", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorList> call, Throwable t) {
                Toast.makeText(SearchDoctorActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void seeDoctorFilterMethod(String id, String speciality, String full_name) {
        App.getApi().seeDoctorListFilter(id, speciality, full_name).enqueue(new Callback<ResponseDoctorList>() {
            @Override
            public void onResponse(Call<ResponseDoctorList> call, Response<ResponseDoctorList> response) {
                int s = response.code();
                if (response.errorBody() != null) {
                    try {
                        Log.e("LOG", "Retrofit Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                registeredUser = response.body();
//                if (registeredUser.getDocuments() != null)
                if (registeredUser != null) {
                    CURRENT_PAGE_NUM = 1;
                    loading = false;
                 //   Toast.makeText(SearchDoctorActivity.this, "SUCCESS", Toast.LENGTH_LONG).show();
//
                    GlobalVariables.documentList = registeredUser.getDocuments();
                    recyclerDoctorListAdapter = new RecyclerDoctorListAdapter(GlobalVariables.documentList, SearchDoctorActivity.this);

                    mRecyclerView.setLayoutManager(mLayoutManager);
                    pages = registeredUser.getPages();
                    addNewDataintoRecycler();

//                    mRecyclerViewChat.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(recyclerDoctorListAdapter);


                } else {
                    Toast.makeText(SearchDoctorActivity.this, "Ошибка поиска попробуйте еще раз", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorList> call, Throwable t) {
                Toast.makeText(SearchDoctorActivity.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loading = true;
                // Отменяем анимацию обновления
                mSwipeRefreshLayout.setRefreshing(false);
                seeDoctorMethod("1");
                CURRENT_PAGE_NUM = 1;
            }
        }, 1000);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile_layout, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:{
                if(isFilterActive){
                    App.getInstance().closeFragment(this);
                    isFilterActive = false;
                }
                else {
                    onBackPressed();
                }
                return true;
            }
            case R.id.action_edit_profile: {
                _addFragment(FilterFragment2.class.getName());
            }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    boolean isFilterActive = false;
    public void _addFragment(String fragmentName){
        isFilterActive = true;
        App.getInstance().addFragment(SearchDoctorActivity.this ,fragmentName);
    }

    @Override
    public void onFilterSelected(Bundle out) {
        isFilterActive = false;
        String titleClinic = getValueIfIsNotEmpty(GlobalVariables.titleClinic_key,out);
        String cityName = getValueIfIsNotEmpty(GlobalVariables.cityName_key,out);
        String speciality = getValueIfIsNotEmpty(GlobalVariables.speciality_key,out);
        String doctorName = null;
        if(nameDoctor!=null && !nameDoctor.isEmpty()){
            doctorName = nameDoctor;
        }
        seeDoctorFilterMethod("1",speciality,doctorName);
    }

    String getValueIfIsNotEmpty(String key , Bundle out){
        String value = out.getString(key);
        if(value == null || value.isEmpty() || value.equals(""))
            return null;
        else
            return value;
    }

    @Override
    public void onSelected(HashMap<String, String> out) {

        String text = String.format("Вы точно хотите выбрать пользователя: \n%s ",out.get("name"));

        final String doc_id = out.get("id_doc");
        CustomDialogBuilder builder = new CustomDialogBuilder(this)
                .setTitle("Выбор пользователя")
                .setMessage(text).setPositiveClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setUser(doc_id);
                    }
                }).build();
        builder.showDialog();
    }


    public Boolean isChecked(String id) {
        return checkedList.contains(id);
    }

    List<String> checkedList = new ArrayList<String>();

    @Override
    public void onChecked(String out) {
        checkedList.add(out);
    }

    @Override
    public void onUnChecked(String out) {
        checkedList.remove(out);
    }

    @Override
    public void onSelect(HashMap<String, String> out) {
        CustomDialogBuilder builder = new CustomDialogBuilder(this)
                .setTitle("Вы пользователей")
                .setMessage("Вы хотите продолжить?").setPositiveClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        setUser(doc_id);
                    }
                }).build();
        builder.showDialog();
    }

    void setUser(String id){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("expert_id",id);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
    void setUsers(String[] ids){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("experts_id",ids);
        returnIntent.putExtra("group_name",groupName);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
//    void setUsers(List<String> id){
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("expert_id",id);
//        setResult(Activity.RESULT_OK,returnIntent);
//        finish();
//    }
}
