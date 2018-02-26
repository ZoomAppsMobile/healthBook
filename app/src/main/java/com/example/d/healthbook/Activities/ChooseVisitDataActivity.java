package com.example.d.healthbook.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.Adapters.RecyclerAapterChooseVisitFinal;
import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Controller.ScheduleTimeConvertor;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.ResponseCloseOrOpenVisit;
import com.example.d.healthbook.Models.Schedule;
import com.example.d.healthbook.Models.VisitWorkTimeElement;
import com.example.d.healthbook.R;
import com.example.d.healthbook.UI.MyToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChooseVisitDataActivity extends AppCompatActivity {
    private String docID;
    private MyToolbar toolbar;
    private Spinner spinner;
    private RecyclerView mRecyclerView;
    private ScheduleTimeConvertor sch_convertor;
    private String TAG = "MMMMMMMMMMMM";
    private String roomID;
    private RecyclerAapterChooseVisitFinal recyclerAdapterChooseVisit;
    private List<Schedule> schedules;
    private List<VisitWorkTimeElement> datas;
    @SuppressLint("StaticFieldLeak")
    public static TextView dayofweekTV;
    private boolean nextWeekOfMoth=false;
    @BindView(R.id.clickNextWeekOrBack)
    Button btnNextWeek;


    @BindView(R.id.clickPreviousWeek)
    Button btnPreviousWeek;

    int defaultWeek=0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_visit_data);
        ButterKnife.bind(this);
        btnPreviousWeek.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerChooseVisit);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dayofweekTV = (TextView) findViewById(R.id.dayofweekTV);

        docID = getIntent().getStringExtra("id");
        schedules = (List<Schedule>) getIntent().getBundleExtra("extra").getSerializable("schedules");
        roomID = getIntent().getStringExtra("roomID");

        toolbar = (MyToolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        spinner = (Spinner) findViewById(R.id.spinner_chooseVisitAct);
        final String[] SPINNERLIST_Week = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.text_spinner_item_custom, SPINNERLIST_Week);
        adapter.setDropDownViewResource(R.layout.my_spinner_drop_down);
        spinner.setAdapter(adapter);


        visitMethod(spinner.getSelectedItemPosition(),docID,1);
        clickNextWeekListener();
        spinnerSelectedListener();
        clicPreviousWeekListener();

    }

    private void clickNextWeekListener() {
        btnNextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                nextWeekOfMoth=true;
                defaultWeek++;
                btnPreviousWeek.setVisibility(View.VISIBLE);
                visitMethod(spinner.getSelectedItemPosition(),docID,defaultWeek);

                if(defaultWeek==0){
                    btnPreviousWeek.setVisibility(View.GONE);
                }



            }
        });
    }

    private void clicPreviousWeekListener() {
        btnPreviousWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nextWeekOfMoth=true;
                defaultWeek--;
                if(defaultWeek==0){
                    btnPreviousWeek.setVisibility(View.GONE);
                }
                visitMethod(spinner.getSelectedItemPosition(),docID,defaultWeek);

            }
        });
    }

    private void spinnerSelectedListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (selectedItem) {
                    case "Понедельник":
                        visitMethod(spinner.getSelectedItemPosition(),docID,defaultWeek);
                        break;
                    case "Вторник":
                        visitMethod(spinner.getSelectedItemPosition(),docID,defaultWeek);
                        break;
                    case "Среда":
                        visitMethod(spinner.getSelectedItemPosition(),docID,defaultWeek);

                        break;
                    case "Четверг":
                        visitMethod(spinner.getSelectedItemPosition(),docID,defaultWeek);
                        break;
                    case "Пятница":
                        visitMethod(spinner.getSelectedItemPosition(),docID,defaultWeek);
                        break;
                    case "Суббота":
                        visitMethod(spinner.getSelectedItemPosition(),docID,defaultWeek);
                        break;
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void visitMethod(final int selectedDayOfWeek,String id,final int week) {
        final ProgressDialog progressDialog = ProgressDialog.show(this, "", "Загрузка данных");
        progressDialog.setCancelable(false);
        App.getApi().visitCheck(GlobalVariables.user_auth_token, id, "2017-07-30", "2017-07-05").enqueue
                (new Callback<List<ResponseCloseOrOpenVisit>>() {
                    @Override
                    public void onResponse(Call<List<ResponseCloseOrOpenVisit>> call, Response<List<ResponseCloseOrOpenVisit>> response) {
                        int s = response.code();


                        sch_convertor = new ScheduleTimeConvertor(schedules, response.body());


                        datas = sch_convertor.mappedList(selectedDayOfWeek, roomID,nextWeekOfMoth,week);
                        recyclerAdapterChooseVisit = new RecyclerAapterChooseVisitFinal(datas, ChooseVisitDataActivity.this);
                        mRecyclerView.setAdapter(recyclerAdapterChooseVisit);
                        recyclerAdapterChooseVisit.notifyDataSetChanged();
                        progressDialog.dismiss();

                    }


                    @Override
                    public void onFailure(Call<List<ResponseCloseOrOpenVisit>> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplication(), "Пожалуйста повторите попытку", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, t.getMessage());
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


}
