package com.example.d.healthbook.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Activities.MainPageActivity;
import com.example.d.healthbook.Adapters.RecyclerDoctorListAdapter;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.ResponseDoctorList;
import com.example.d.healthbook.R;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by D on 31.05.2017.
 */


public class SearchDoctorsFragment2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nameDoctor = getArguments().getString(GlobalVariables.titleClinic_key);
            cityName = getArguments().getString(GlobalVariables.cityName_key);
            speciality = getArguments().getString(GlobalVariables.speciality_key);
            setHasOptionsMenu(true);
            String doctorName = null;
            if(nameDoctor!=null && !nameDoctor.isEmpty()){
                doctorName = nameDoctor;
            }
            String doctorSpeciality = (speciality==null || speciality.isEmpty() )? null : speciality;
            seeDoctorFilterMethod("1", doctorSpeciality, doctorName);
        } else {
            setHasOptionsMenu(true);
            seeDoctorMethod(getActivity().getIntent().getStringExtra("idClinic"));
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //App.getInstance().setCurrentFragment(this);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.search_doctors_fragment, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        App.getInstance().changeToolbarTitle((AppCompatActivity) getActivity(), "Список врачей");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.doctorlist_recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

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
                                Toast.makeText(getActivity(), "Загрузка списка докторов", Toast.LENGTH_SHORT).show();
                                addDoctorList();
                            }
                        }
                    }
                }
            }
        });
    }

    class PaginationHelper{
        public Call<ResponseDoctorList> callHolder;
        public Integer page  = 1;
        public String city_id  = "1";
        public String speciality = null;
        public String full_name  = null;
        public PaginationHelper(String city_id,  String speciality,String full_name){
            this.city_id = city_id;
            this.speciality = speciality;
            this.full_name = full_name;
            initCall();
        }

        public PaginationHelper(String city_id){
            this.city_id = city_id;
            initCall();
        }

        void initCall(){
            this.page =page+1;
            this.callHolder = App.getApi().seeDoctorListFilter(city_id,speciality,full_name,page);
        }
    }

    PaginationHelper paginationHelper;
    public void addDoctorList() {
        paginationHelper.callHolder.enqueue(new Callback<ResponseDoctorList>() {
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
                    paginationHelper.initCall();
                    Log.e("CURRENT_PAGE_NUM", "" + CURRENT_PAGE_NUM);
                    //Toast.makeText(getActivity(), "Загрузка прошла успешно ", Toast.LENGTH_SHORT).show();
                    Log.e("Data size ", "" + registeredUser.getDocuments().size());
                    for (int i = 0; i < registeredUser.getDocuments().size(); i++) {
                        GlobalVariables.documentList.add(registeredUser.getDocuments().get(i));
                    }
                    recyclerDoctorListAdapter.notifyDataSetChanged();
                    if (recyclerDoctorListAdapter == null)
                        return;
                    Log.e("GlobalVariables SIZE ", "" + GlobalVariables.documentList.size());
                } else {
                    Toast.makeText(getActivity(), "Ошибочка", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorList> call, Throwable t) {
                Toast.makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void seeDoctorMethod(String company_id) {
        App.getApi().seeDoctorListFilter2(company_id,"1").enqueue(new Callback<ResponseDoctorList>() {
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
                    paginationHelper = new PaginationHelper("1");
                    CURRENT_PAGE_NUM = 1;
                    loading = false;
                //    Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_LONG).show();

                    GlobalVariables.documentList = registeredUser.getDocuments();
                    recyclerDoctorListAdapter = new RecyclerDoctorListAdapter(GlobalVariables.documentList, getActivity());

                    mRecyclerView.setLayoutManager(mLayoutManager);
                    pages = registeredUser.getPages();
                    addNewDataintoRecycler();
//                  mRecyclerViewChat.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(recyclerDoctorListAdapter);

                } else {
                    Toast.makeText(getActivity(), "Ошибочка", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorList> call, Throwable t) {
                Toast.makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void seeDoctorFilterMethod(String id, final String speciality,final String full_name) {
        App.getApi().seeDoctorListFilter2("4","1").enqueue(new Callback<ResponseDoctorList>() {
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
                    paginationHelper = new PaginationHelper("1",speciality,full_name);
                   // paginationHelper.initCall();
                    CURRENT_PAGE_NUM = 1;
                    paginationHelper.initCall();
                    loading = false;
               //     Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_LONG).show();

                    GlobalVariables.documentList = registeredUser.getDocuments();
                    recyclerDoctorListAdapter = new RecyclerDoctorListAdapter(GlobalVariables.documentList, getActivity());

                    mRecyclerView.setLayoutManager(mLayoutManager);
                    pages = registeredUser.getPages();
                    addNewDataintoRecycler();

//                    mRecyclerViewChat.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(recyclerDoctorListAdapter);


                } else {
                    Toast.makeText(getActivity(), "Ошибка поиска попробуйте еще раз", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDoctorList> call, Throwable t) {
                Toast.makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
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

}