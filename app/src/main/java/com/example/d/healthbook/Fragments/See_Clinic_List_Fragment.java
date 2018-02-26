package com.example.d.healthbook.Fragments;

/**
 * Created by D on 07.06.2017.
 */

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.Activities.MainPageActivity;
import com.example.d.healthbook.Adapters.RecyclerСlinicListAdapter;
import com.example.d.healthbook.API.App;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.ResponseClinicList;
import com.example.d.healthbook.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class See_Clinic_List_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    Toolbar toolbar;


    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private boolean loading = true;
    private int pages = 0;
    private RecyclerСlinicListAdapter recyclerСlinicListAdapter;
    private ResponseClinicList registeredUser;
    @BindView(R.id.resultSearchClinic)
    TextView resultSearchClinic;


//    @BindView(R.id.bottomBar)
//    BottomBar bottomBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setHasOptionsMenu(true);
        //   parseJson();


        if (getArguments() != null) {
            String nameDoctor = getArguments().getString(GlobalVariables.titleClinic_key);
            if (nameDoctor == null) {
                seeClinicList();
            } else {
                if (nameDoctor.equals("")) {
                    seeClinicList();
                } else {
                    seeClinicListByName(nameDoctor);
                }

            }


            //Toast.makeText(getContext(), nameDoctor, Toast.LENGTH_SHORT).show();
        } else {
            seeClinicList();


        }


    }

    public void parseJson() {

        // Your JOSON string
        String jsonStr = "{\"place\": [\n" +
                "            {\n" +
                "                \"address\": {\n" +
                "                    \"country_code\": \"fr\",\n" +
                "                    \"country\": \"France\",\n" +
                "                    \"state\": \"Normandie\",\n" +
                "                    \"county\": \"Calvados\"\n" +
                "                },\n" +
                "                \"icon\": \"http://nominatim.openstreetmap.org/images/mapicons/poi_boundary_administrative.p.20.png\",\n" +
                "                \"importance\": 0.74963706049207,\n" +
                "                \"type\": \"administrative\",\n" +
                "                \"class\": \"boundary\",\n" +
                "                \"display_name\": \"Calvados, Normandie, France\",\n" +
                "                \"lon\": \"-0.24139500722798\",\n" +
                "                \"lat\": \"49.09076485\",\n" +
                "                \"boundingbox\": [\n" +
                "                    \"48.7516623\",\n" +
                "                    \"49.4298653\",\n" +
                "                    \"-1.1597713\",\n" +
                "                    \"0.4466332\"\n" +
                "                ],\n" +
                "                \"osm_id\": \"7453\",\n" +
                "                \"osm_type\": \"relation\",\n" +
                "                \"licence\": \"Data © OpenStreetMap contributors, ODbL 1.0. http://www.openstreetmap.org/copyright\",\n" +
                "                \"place_id\": \"158910871\"\n" +
                "            }\n" +
                "        ]}";


        String jsonStr2 = "{\"data\":[{\"type\":\"text\",\"data\":{\"text\":\"**Адрес клиники:** Мусабаева, дом 8\\n\\n**Адрес филиала:** Макатаева, 127/1\\n\\nHealthCity – это медицинская сеть, основанная в г. Алматы в 2014 году и объединяющая в себе инновационную Диагностическую клинику и Центры персональной медицины в г. Алматы. Основой проекта HealthCity является персональная медицина, которая направлена на точную диагностику, индивидуальный подход к предупреждению, ранней диагностике и лечению заболеваний на основе международных стандартов.\\n\\nHealthCity обеспечивает внедрение высоких стандартов медицинского обслуживания, с фокусом на профилактику и раннее предупреждение болезней.\\n\\nHealthCity предлагает своим пациентам:\\n \\\\- Комплекс клинической диагностики по международным стандартам Stanford25\\n \\\\- Надежная лабораторная диагностика с широким спектром исследований\\n \\\\- Радиологические исследования на современном оборудовании Phillips\\n \\\\- Второе чтение \\\\(second opinion\\\\) снимков магнитно\\\\-резонансной и компьютерной томографии \\\\(МРТ и КТ\\\\) – заключения американских специалистов\\n \\\\- Исследования УЗИ экспертного класса, включая эхокардиографию\\n \\\\- Функциональная диагностика\\n \\\\- Пять программных диагностических пакетов Check\\\\-Up для взрослых от базового до углубленного – базовый, бронзовый, серебряный, золотой, платиновый\\n \\\\- Четыре программных диагностических пакетов Check\\\\-Up для оценки здоровья детей \\\\(от новорожденных до подростков\\\\)\\n \\\\- Пятнадцать фокусных \\\\(специализированных\\\\) диагностических пакетов для диагностики болезней сердца, печени, суставов, эндокринных заболеваний, оценки женского и мужского здоровья, онкологического профиля и др.\\n \\\\- Индивидуальные пакеты для корпоративных клиентов\\n \\\\- Консультации зарубежных врачей – педиатра, гинеколога и других специалистов из США, Германии, Кореи и других стран\\n \\\\- Консультации опытных кардиологов, хирургов и других казахстанских узких специалистов\\n \\\\- Услуги скорой и неотложной медицинской помощи \\\\(24 часа/7 дней в неделю\\\\)\\n \\\\- Услуги стационарозамещающей медицинской помощи \\\\(дневной стационар, стационар на дому\\\\)\\n \\\\- Центр семейной медицины с опытными педиатрами\\n \\\\- Центр женского здоровья с опытными акушер\\\\-гинекологами, возможностями маммографии, денситометрии\\n\"}}]}";

        if (jsonStr != null) {

            try {
                JSONObject jsonObj = new JSONObject(jsonStr2);

                // Place
                JSONArray place = jsonObj.getJSONArray("data");

                // Address
                JSONObject address = place.getJSONObject(0).getJSONObject("data");

                String countryCode = address.getString("text");
                // String country = address.getString("country");
                // String state = address.getString("state");
                // String county = address.getString("county");

                Log.d("SUCCESS", " County: " + countryCode);

            } catch (final JSONException e) {
                Log.e("FAILED", "Json parsing error: " + e.getMessage());
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //App.getInstance().setCurrentFragment(this);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.see_clinic_list_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ((MainPageActivity) getActivity()).changeMenuItems(0);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.cliniclist_recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());


        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container_clinic_list);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
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
                                //   Toast.makeText(getActivity(), "Загрузка списка клиник", Toast.LENGTH_SHORT).show();
//                                addNewsList(CURRENT_PAGE_NUM + 1);
                            }
                        }
                    }
                }
            }
        });

    }


    public void seeClinicList() {
        App.getApi().seeClinicList().enqueue(new Callback<ResponseClinicList>() {
            @Override
            public void onResponse(Call<ResponseClinicList> call, Response<ResponseClinicList> response) {
                int s = response.code();
                if (response.errorBody() != null) {
                    try {
                        Log.e("LOG", "Retrofit Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                registeredUser = response.body();
                if (registeredUser.getDocuments() != null && getActivity() != null) {
                    CURRENT_PAGE_NUM = 1;
                    loading = false;
                    //   Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_LONG).show();

                    GlobalVariables.documentClinicList = registeredUser.getDocuments();
                    recyclerСlinicListAdapter = new RecyclerСlinicListAdapter(GlobalVariables.documentClinicList, getActivity());

                    mRecyclerView.setLayoutManager(mLayoutManager);
                    pages = registeredUser.getPages();
                    addNewDataintoRecycler();

//                    mRecyclerViewChat.setLayoutManager(new LinearLayoutManager(getActivity()));
                    mRecyclerView.setAdapter(recyclerСlinicListAdapter);


                } else {
                    Toast.makeText(getActivity(), "Ошибочка", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseClinicList> call, Throwable t) {

                Toast.makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void seeClinicListByName(String name) {
        App.getApi().seeClinicListByName(name).enqueue(new Callback<ResponseClinicList>() {
            @Override
            public void onResponse(Call<ResponseClinicList> call, Response<ResponseClinicList> response) {
                int s = response.code();
                if (response.errorBody() != null) {
                    try {
                        Log.e("LOG", "Retrofit Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                registeredUser = response.body();

                if( registeredUser.getPages()  == 0){
                    resultSearchClinic.setVisibility(View.VISIBLE);
                    resultSearchClinic.setText("Не найдено клиник");
                }

                try {
                    if (registeredUser.getDocuments() != null && getActivity() != null) {
                        CURRENT_PAGE_NUM = 1;
                        loading = false;
                        //   Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_LONG).show();

                        GlobalVariables.documentClinicList = registeredUser.getDocuments();
                        recyclerСlinicListAdapter = new RecyclerСlinicListAdapter(GlobalVariables.documentClinicList, getActivity());

                        mRecyclerView.setLayoutManager(mLayoutManager);
                        pages = registeredUser.getPages();
                        addNewDataintoRecycler();

//                    mRecyclerViewChat.setLayoutManager(new LinearLayoutManager(getActivity()));
                        mRecyclerView.setAdapter(recyclerСlinicListAdapter);


                    } else {
                        Toast.makeText(getActivity(), "Ошибочка", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Error name clinic", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<ResponseClinicList> call, Throwable t) {
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
                seeClinicList();
                CURRENT_PAGE_NUM = 1;
            }
        }, 1000);
    }

}