package com.example.d.healthbook.CalendarBekarysa;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.API.API_Controller;
import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Activities.BaseActivity;
import com.example.d.healthbook.Adapters.RecyclerAdapterCalendar;
import com.example.d.healthbook.Bottom_helper.SharedPreference;
import com.example.d.healthbook.CalendarBekarysa.Adapter.CalendarAdapter;
import com.example.d.healthbook.CalendarBekarysa.Calendar_UI.CalendarHelper;
import com.example.d.healthbook.CalendarBekarysa.Calendar_UI.CustomGridLayoutManager;
import com.example.d.healthbook.CalendarBekarysa.Models.CalendarModel;
import com.example.d.healthbook.CalendarBekarysa.Views.ICalendarView;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.DialogFragments.WomanCalendarFragment;
import com.example.d.healthbook.Fragments.DialogFragmentAddDrugs;
import com.example.d.healthbook.Fragments.DialogFragmentCalendarNote;
import com.example.d.healthbook.Fragments.FixedHoloDatePickerDialog;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.Event_Data;
import com.example.d.healthbook.Models.ResponseNoteType1;
import com.example.d.healthbook.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CalendarActivity2 extends BaseActivity implements ICalendarView {

    private ImageButton womenCalendarBtn;
    private ImageButton addDrugBtn;
//    private NavigationView navigation;
//    private ActionBarDrawerToggle mToggle;
//    private DrawerLayout drawerLayout;
//    private MyToolbar toolbar;
//    private TextView toolbar_title;
    private Intent intent;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView current_date;
    private RecyclerView mRecyclerView;
    private List<Event_Data> event;
    private List<Event_Data> eventAll = new ArrayList<>();
    private RecyclerAdapterCalendar adapterCalendar;
    private String stringSelectedDay = "";
    private Date dateSelected;
    private List<ResponseNoteType1> responseCalBody;
    private DialogFragmentCalendarNote dialogFragmentNote;
    private String dateStr = "";
    private DialogFragmentAddDrugs dialogFragmentAddDrugs;
    private WomanCalendarFragment dialogFragmentWomanCalendar;





   private int all_period=0;
   private int all_period_woman=0;

    String data_begin;
    String data_end=null;




    public void checkNoteEquals(ResponseNoteType1 responseNoteType1) {
        String splitdate[];

        int default_drugs_period=0;




        for (int i = 0; i < calendarDatum.size(); i++) {


            if (responseNoteType1.getDate() != null) {
                splitdate = responseNoteType1.getDate().split(" ");
            } else {
                splitdate = responseNoteType1.getDateBegin().split(" ");
            }



            if(all_period>0){
                if(i<all_period) {
                    calendarDatum.get(i).setDrugs(true);
                    all_period--;
                }
            }








            if(default_drugs_period!=0){
                if(i<default_drugs_period){
                    calendarDatum.get(i).setDrugs(true);
                    all_period--;
                }
                if(i==default_drugs_period){
                    default_drugs_period=0;
                }

            }




//WOMAN


          if(!SharedPreference.getDataBegin(CalendarActivity2.this).equals("")){

              if(compareTwoDates(SharedPreference.getDataBegin(CalendarActivity2.this),calendarDatum.get(i).getDate())){



                 if(getDaysBetweenDates(SharedPreference.getDataBegin(CalendarActivity2.this),calendarDatum.get(i).getDate())
                         >Integer.valueOf(SharedPreference.getPeriod(CalendarActivity2.this))  &  getDaysBetweenDates(SharedPreference.getDataBegin(CalendarActivity2.this),calendarDatum.get(i).getDate())
                         < Integer.valueOf(SharedPreference.getWomanduration(CalendarActivity2.this))){

                    calendarDatum.get(i).setWomanDay(true);



                }





              }

            }





//WOMAN END


            if (calendarDatum.get(i).getDate().equals(splitdate[0])) {


                if (responseNoteType1.getType() == 1) {
                    calendarDatum.get(i).setNote(true);
                }
                else if (responseNoteType1.getType() == 2) {
                    calendarDatum.get(i).setDrugs(true);
                    calendarDatum.get(i).setPeriod(responseNoteType1.getPeriod());
                    default_drugs_period=i+responseNoteType1.getPeriod();
                    all_period=responseNoteType1.getPeriod();
                }
                else if (responseNoteType1.getType() == 3) {

                    calendarDatum.get(i).setWomanDay1(true);

                    String splitdate_data_begin []= responseNoteType1.getDateBegin().split(" ");
                    data_begin=splitdate_data_begin[0];


                    SharedPreference.setDataBegin(CalendarActivity2.this,data_begin);
                    SharedPreference.setWomanduration(CalendarActivity2.this, String.valueOf(responseNoteType1.getDuration()));
                    SharedPreference.setPeriod(CalendarActivity2.this, String.valueOf(responseNoteType1.getDurationPeriod()));


                }

            }









        }


    }
    @Override
    public void onDateSelected(final CalendarModel out) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               // Toast.makeText(CalendarActivity.this, out.getDate(), Toast.LENGTH_SHORT).show();
                dateStr = out.getDate();
                DateFormat formatter = new SimpleDateFormat("EEE, dd MMMM");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    dateSelected = sdf.parse(out.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                stringSelectedDay = formatter.format(dateSelected.getTime());
                current_date.setText(stringSelectedDay);


                updateListUI(getEventCliclkedDay(eventAll, out.getDate()));
                adapterCalendar.notifyDataSetChanged();
//                mAdapter.notifyDataSetChanged();

            }
        });
    }



    FloatingActionButton add_note_fab;
    TextView date_info_TV;
    RecyclerView calendar_core_RV;
    ImageButton month_before_btn;
    ImageButton month_after_btn;
    final Calendar cal = Calendar.getInstance();
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    final List<CalendarModel> calendarDatum = new ArrayList<>();
    CalendarHelper calendarHelper = new CalendarHelper();

    CalendarAdapter mAdapter;



    boolean isDataLoaded = false;
    void showWaitToast(){
        Toast.makeText(CalendarActivity2.this,"Подождите, идет загрузка...",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_main);

        Log.d("Token", GlobalVariables.user_auth_token);
        SharedPreference.setDataBegin(this,"");

      //  Log.d("Vbetweenalll2",SharedPreference.getDataBegin(CalendarActivity.this)+" === "+ String.valueOf(getDaysBetweenDates("2017-10-11","2017-10-22")));




        addDrugBtn = (ImageButton) findViewById(R.id.addDrugBtn);
        womenCalendarBtn = (ImageButton) findViewById(R.id.womenCalendarBtn);
       add_note_fab = (FloatingActionButton) findViewById(R.id.add_note_fab);

        onViewSet();

        setToolbarTitleText("Календарь");
        navigationClickItemListener(CalendarActivity2.this , CalendarActivity2.class);

        navigation.getMenu().getItem(1).setChecked(true);
        current_date = (TextView) findViewById(R.id.text_field_current_date);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerCalendar);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        event = new ArrayList<Event_Data>();
        adapterCalendar = new RecyclerAdapterCalendar(event, this);
        mRecyclerView.setAdapter(adapterCalendar);

        date_info_TV = (TextView) findViewById(R.id.date_info_TV);
        datePickerMethod();

        ClickListenerBtnToolbar();


        calendar_core_RV = (RecyclerView) findViewById(R.id.calendar_core_RV);
        month_before_btn = (ImageButton) findViewById(R.id.month_before_btn);
        month_after_btn = (ImageButton) findViewById(R.id.month_after_btn);
        month_before_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(isDataLoaded) {
                            cal.add(Calendar.MONTH, -1);
                            calendarDatum.clear();
                            calendarDatum.addAll(calendarHelper.generateMonth(cal));
                            mAdapter.notifyDataSetChanged();
                            updateUI();

                            for (int i = 0; i < responseCalBody.size(); i++) {
                                checkNoteEquals(responseCalBody.get(i));
                            }
                        }
                        else{
                            showWaitToast();
                        }
                    }
                });
            }
        });
        month_after_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(isDataLoaded) {
                        cal.add(Calendar.MONTH, 1);
                        calendarDatum.clear();
                        calendarDatum.addAll(calendarHelper.generateMonth(cal));
                        mAdapter.notifyDataSetChanged();
                        updateUI();
                        if(responseCalBody!=null && !responseCalBody.isEmpty())
                            for (int i = 0; i < responseCalBody.size(); i++) {
                                checkNoteEquals(responseCalBody.get(i));
                            }
                        }
                        else{
                            showWaitToast();
                        }
                    }
                });
            }
        });


        calendar_core_RV.setLayoutManager(new CustomGridLayoutManager(this, 7));
        calendarDatum.addAll(calendarHelper.generateMonth(cal));
        mAdapter = new CalendarAdapter(this, calendarDatum, CalendarActivity2.this);
        calendar_core_RV.setAdapter(mAdapter);
        seeNote1("1");
        updateUI();
        add_note_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDataLoaded){
                FragmentManager fm = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("date", dateStr);
                dialogFragmentNote = new DialogFragmentCalendarNote();
                dialogFragmentNote.setArguments(bundle);
                dialogFragmentNote.show(fm, "dialogFragmentNote");
                fm.executePendingTransactions();

                }
                else{
                    showWaitToast();
                }
                //calendar_core_RV.findViewHolderForAdapterPosition(position).itemView.performClick();
            }
        });


    }

    private void ClickListenerBtnToolbar() {

        //womenCalendarBtn = (ImageButton) findViewById(R.id.womenCalendarBtn);
        womenCalendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Bundle bundle = new Bundle();
              //  bundle.putString("date", dateStr);
                if(isDataLoaded){
                dialogFragmentWomanCalendar = new WomanCalendarFragment();
               // dialogFragmentAddDrugs.setArguments(bundle);
                dialogFragmentWomanCalendar.show(getSupportFragmentManager(), "dialogFragmentWomanCalendar");
                }
                else{
                    showWaitToast();
                }
            }
        });
        addDrugBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDataLoaded){
                Bundle bundle = new Bundle();
                bundle.putString("date", dateStr);
                dialogFragmentAddDrugs = new DialogFragmentAddDrugs();
                dialogFragmentAddDrugs.setArguments(bundle);
                dialogFragmentAddDrugs.show(getSupportFragmentManager(), "dialogFragmentAddDrugs");
                }
                else{
                    showWaitToast();
                }
            }
        });
    }

    public List<Event_Data> getEventCliclkedDay(List<Event_Data> data, String formatted) {
        List<Event_Data> event_datas = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            String splitdate[] = data.get(i).getDate().split(" ");
            if (formatted.equals(splitdate[0])) {
                event_datas.add(data.get(i));
            }

        }

        return event_datas;
    }

    void updateListUI(List<Event_Data> show_date) {
        if (show_date == null || show_date.isEmpty()) {
            if (adapterCalendar != null) {
                event.clear();
                event.addAll(show_date);
                mAdapter.notifyDataSetChanged();
                adapterCalendar.notifyDataSetChanged();


                return;
            } else {
                return;
            }

        } else {
            event.clear();
            event.addAll(show_date);
            adapterCalendar.notifyDataSetChanged();

            mAdapter.notifyDataSetChanged();
        }


    }

    public void addElement(ResponseNoteType1 calMod) {
        //responseCalBody.add(calMod);
        processEventAll(eventAll, calMod);
        checkNoteEquals(calMod);
        String formatted = "";
        if (calMod.getType() == 1)
            formatted = format1.format((MainController.getCalendarByDate(calMod.getDate(), "yyyy-MM-dd HH:mm:ss")).getTime());
        else if (calMod.getType() == 2)
            formatted = format1.format((MainController.getCalendarByDate(calMod.getDateBegin(), "yyyy-MM-dd HH:mm:ss")).getTime());
        else
            try {
                throw new Exception("Not Implemented type");
            } catch (Exception e) {
                e.printStackTrace();
            }
        updateListUI(getEventCliclkedDay(eventAll, formatted));
        mAdapter.notifyDataSetChanged();
    }


    public void seeNote1(String type) {

        Log.d("TOKEN", GlobalVariables.user_auth_token);

        App.getApi().seeNoteType1(GlobalVariables.user_auth_token).enqueue(new Callback<List<ResponseNoteType1>>() {
            @Override
            public void onResponse(Call<List<ResponseNoteType1>> call, Response<List<ResponseNoteType1>> response) {

                if (response.code() == 200) {
                    event.clear();
                    eventAll.clear();

                    responseCalBody = response.body();
                    for (int i = 0; i < response.body().size(); i++) {
                        String date = "";

                        processEventAll(eventAll, responseCalBody.get(i));

                        checkNoteEquals(responseCalBody.get(i));
                    }

                    String formatted = format1.format(cal.getTime());

                    updateListUI(getEventCliclkedDay(eventAll, formatted));
                    isDataLoaded = true;

                } else {
                    MainController.showPreparedToast(CalendarActivity2.this, "Failed");
                }

            }

            @Override
            public void onFailure(Call<List<ResponseNoteType1>> call, Throwable t) {
                Toast.makeText(CalendarActivity2.this, "Error SeeNote", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void processEventAll(List<Event_Data> datum, ResponseNoteType1 data) {
        switch (data.getType()) {
            case 1: {
                datum.add(create_Default_Event(data));
                break;
            }
            case 2: {
                String Time = "";
                for (int j = 0; j < data.getTimes().size(); j++) {
                    Time += data.getTimes().get(j) + "/";
                }
                datum.add(create_Drug_Event(data, Time));
                break;
            }
            default: {

            }
        }
    }

    public Event_Data create_Default_Event(ResponseNoteType1 data) {
        return new Event_Data(data.getType(),
                data.getTitle(),
                data.getText(), data.getDate(),data.getId());
    }

    public Event_Data create_Drug_Event(ResponseNoteType1 data, String Time) {
        return new Event_Data(data.getType(),
                data.getTitle(),
                data.getText(), data.getDateBegin(), Time,
                data.getBody(),data.getId(),data.getDuration());
    }




    private void datePickerMethod() {
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
              //  Toast.makeText(CalendarActivity.this, "y" + year, Toast.LENGTH_SHORT).show();
                cal.set(year, month, dayOfMonth);
                calendarDatum.clear();
                calendarDatum.addAll(calendarHelper.generateMonth(cal));
                mAdapter.notifyDataSetChanged();
                updateUI();
            }
        };


        final Context themedContext = new ContextThemeWrapper(
                this,
                android.R.style.Theme_Holo_Light_Dialog
        );

        date_info_TV.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                final DatePickerDialog dialog = new FixedHoloDatePickerDialog(
                        themedContext,
                        mDateSetListener, year, month, day
                );
                dialog.show();



            }
        });
    }

    void updateUI() {

        SimpleDateFormat format4 = new SimpleDateFormat("MMM , yyyy");
        String formatted = format4.format(cal.getTime());
        date_info_TV.setText(formatted);
        DateFormat formatter = new SimpleDateFormat("EEE, dd MMMM");
        String date = formatter.format(cal.getTime());
        current_date.setText(date);

        adapterCalendar.notifyDataSetChanged();
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return mToggle.onOptionsItemSelected(item);
    }

    public void sendNewEvent(String type, String title, String text, String date) {
        App.getApi().saveNoteType1(GlobalVariables.user_auth_token,
                API_Controller.saveNoteType1Json(type, title, text, date)).enqueue(new Callback<ResponseNoteType1>() {
            @Override
            public void onResponse(Call<ResponseNoteType1> call, Response<ResponseNoteType1> response) {

                if (response.code() == 200) {
                    ResponseNoteType1 registeredUser = response.body();
                    addElement(registeredUser);
                } else {
                }

            }

            @Override
            public void onFailure(Call<ResponseNoteType1> call, Throwable t) {
                Toast.makeText(CalendarActivity2.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendNewTakingDrugs(String type, String title, String text, String date, String quantityDrugs,
                                   Integer period, Integer duration, List<String> times) {
                     App.getApi().saveNoteType1(GlobalVariables.user_auth_token,



                     API_Controller.saveNewTakingDrus(type, title, text, date, times, period, duration, quantityDrugs)).enqueue(new Callback<ResponseNoteType1>() {
            @Override
            public void onResponse(Call<ResponseNoteType1> call, Response<ResponseNoteType1> response) {

                Log.d("res_ggfg",call.toString());

              //  Toast.makeText(CalendarActivity.this, "+", Toast.LENGTH_SHORT).show();
                if (response.code() == 200) {
                    ResponseNoteType1 registeredUser = response.body();
                    addElement(registeredUser);
                } else {
                }

            }

            @Override
            public void onFailure(Call<ResponseNoteType1> call, Throwable t) {
                Toast.makeText(CalendarActivity2.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendNewTakingWoman(String dateBegin,Integer duration,Integer durationPeriod) {
                     App.getApi().saveNoteType1(GlobalVariables.user_auth_token,



                     API_Controller.saveNewTakingWoman(dateBegin,duration,durationPeriod)).enqueue(new Callback<ResponseNoteType1>() {
            @Override
            public void onResponse(Call<ResponseNoteType1> call, Response<ResponseNoteType1> response) {

                Log.d("res_ggfg",call.toString());

                Toast.makeText(CalendarActivity2.this, "+", Toast.LENGTH_SHORT).show();
                if (response.code() == 200) {
                    ResponseNoteType1 registeredUser = response.body();
                    addElement(registeredUser);
                } else {
                }

            }

            @Override
            public void onFailure(Call<ResponseNoteType1> call, Throwable t) {
                Toast.makeText(CalendarActivity2.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public  boolean compareTwoDates(String str1,String str2){
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            Date date1 = formatter.parse(str1);

            Date date2 = formatter.parse(str2);

            if (date1.compareTo(date2)<0)
            {
                return  true;

            }

        }catch (ParseException e1){
            e1.printStackTrace();
        }

        return  false;

    }








    public static long getDaysBetweenDates(String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date startDate, endDate;
        long numberOfDays = 0;
        try {
            startDate = dateFormat.parse(start);
            endDate = dateFormat.parse(end);
            numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAYS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfDays;
    }
    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }

}
