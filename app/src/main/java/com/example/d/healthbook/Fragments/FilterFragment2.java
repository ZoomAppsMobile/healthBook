package com.example.d.healthbook.Fragments;

/**
 * Created by D on 08.06.2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Activities.MainPageActivity;
import com.example.d.healthbook.Bottom_helper.SharedPreference;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.R;
import com.example.d.healthbook.RealmModels.ClickedFilterItems;
import com.example.d.healthbook.View.FilterInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by D on 31.05.2017.
 */


public class FilterFragment2 extends Fragment {
    private String specialityAutocompleteTV = "";
    public static AutoCompleteTextView myAutoComplete;
    private boolean checkAutocomplete = false;
    @BindView(R.id.searchBtn)
    Button searchBtn;
    @BindView(R.id.titleClinicET)
    EditText titleClinicET;

    private String titleClicnic="";
    private String cityName;
    private Realm mRealm;

    @BindView(R.id.free_space_container)
    LinearLayout free_space_container;
    @BindView(R.id.filter_container)
    LinearLayout filter_container;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //App.getInstance().setCurrentFragment(this);

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.filter_fragment, container, false);
        ButterKnife.bind(this, view);
        mRealm = Realm.getInstance(getActivity());
        Animation apper_from_top = AnimationUtils.loadAnimation(getContext(), R.anim.apper_from_top);
        Animation apper_alpha = AnimationUtils.loadAnimation(getContext(), R.anim.apper_alpha);
        filter_container.startAnimation(apper_from_top);
        free_space_container.setVisibility(View.VISIBLE);
        free_space_container.startAnimation(apper_alpha);

       // Toast.makeText(getContext(), ((MainPageActivity) getActivity()).getDefaultName(), Toast.LENGTH_SHORT).show();
        try {
            titleClinicET.setHint(((MainPageActivity) getActivity()).getDefaultName());

        }
        catch (Exception e){


        }

        return view;


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        final String[] SPINNERLIST_CITY = {"Алматы"};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.text_spinner_item_custom_gray, SPINNERLIST_CITY);
        adapter2.setDropDownViewResource(R.layout.my_spinner_drop_down);
        final Spinner spinner2 = (Spinner) view.findViewById(R.id.spinnerCity);
        spinner2.setAdapter(adapter2);
        spinner2.setSelection(adapter2.getCount() - 1);


        myAutoComplete = (AutoCompleteTextView) view.findViewById(R.id.myAutoComplete);
        myAutoComplete.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.my_spinner_drop_down, SPINNERLIST));
        myAutoComplete.setThreshold(0);
        myAutoComplete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                MainPageActivity.mKeyboardShown = true;

                myAutoComplete.showDropDown();
//                Context context = myAutoComplete.getContext();
//                InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(myAutoComplete.getWindowToken(), 0);
//                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                return false;
            }

        });


        loadRealmItem();


        myAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                specialityAutocompleteTV = String.valueOf(s);

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                titleClicnic = titleClinicET.getText().toString();
                cityName = spinner2.getSelectedItem().toString();

                for (int i = 0; i < SPINNERLIST.length; i++) {
                    if (specialityAutocompleteTV.equals(SPINNERLIST[i]) ) {

                        checkAutocomplete = true;
                    }
                }

                    Bundle bundle = new Bundle();




                    bundle.putString(GlobalVariables.titleClinic_key, titleClicnic);
                    bundle.putString(GlobalVariables.cityName_key, cityName);
                    bundle.putString(GlobalVariables.speciality_key, specialityAutocompleteTV);
                    bundle.putString(GlobalVariables.clinicState, "doctor2");


                    if(App.getInstance().getCurrentContext() instanceof FilterInterface) {
                        ((FilterInterface) App.getInstance().getCurrentContext()).onFilterSelected(bundle);
                        App.getInstance().closeFragment((AppCompatActivity) App.getInstance().getCurrentContext() );
                        return;
                    }
                    else {
                        App.getInstance().setFragment((MainPageActivity) getActivity(), MainPageFragment.class.getName(),
                                android.R.anim.fade_in, android.R.anim.fade_out, false, bundle, false);

                        MainPageActivity.hideKeyboard(myAutoComplete);
                        MainPageActivity.mToggle.setDrawerIndicatorEnabled(true);

                        setRealmItem();

                        GlobalVariables.alreadyFiltered = true;
                        checkAutocomplete = false;
                    }

//
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                MainPageActivity.hideKeyboard(myAutoComplete);
                MainPageActivity.mToggle.setDrawerIndicatorEnabled(true);
                App.getInstance().setFragment((MainPageActivity) getActivity(), MainPageFragment.class.getName(),
                        android.R.anim.fade_in, android.R.anim.fade_out, false);
//                getActivity().onBackPressed();
                return true;
            // TODO: обработчик нажатия здесь

        }

        return super.onOptionsItemSelected(item);
    }


    public void loadRealmItem() {
        RealmResults<ClickedFilterItems> clickedFilterItemses = mRealm.allObjects(ClickedFilterItems.class);
        if (!clickedFilterItemses.isEmpty()) {
            mRealm.beginTransaction();
            specialityAutocompleteTV = clickedFilterItemses.get(0).getSpeciality();
           // myAutoComplete.setText(specialityAutocompleteTV);
        //    titleClinicET.setText(clickedFilterItemses.get(0).getTitleClicnic());
            mRealm.commitTransaction();
        }
    }

    public void setRealmItem() {

    }

    final String[] SPINNERLIST = {"Акушер-гинеколог",
            "Аллерголог",
            "Аллерголог-иммунолог",
            "Андролог",
            "Анестезиолог",
            "Анестезиолог-реаниматолог",
            "Вакцинолог",
            "Вертебролог",
            "Врач восстановительной медицины",
            "Врач ЛФК",
            "Врач МРТ",
            "Врач общей практики",
            "Врач УЗИ",
            "Врач функциональной диагностики",
            "Гастроэнтеролог",
            "Гематолог",
            "Гепатолог",
            "Гинеколог",
            "Гинеколог-онколог",
            "Гинеколог-эндокринолог",
            "Гирудотерапевт (лечение пиявками)",
            "Гомеопат",
            "Дерматовенеролог",
            "Дерматокосметолог",
            "Дерматолог",
            "Дерматоонколог",
            "Диабетолог",
            "Диетолог",
            "Иглорефлексотерапевт",
            "Иммунолог",
            "Инфекционист",
            "Кардиолог",
            "Кардиохирург",
            "Кинезиолог",
            "Клинический психолог",
            "Логопед",
            "Логопед-дефектолог",
            "ЛОР (отоларинголог)",
            "Маммолог",
            "Мануальный терапевт",
            "Массажист",
            "Медицинский генетик",
            "Миколог",
            "Нарколог",
            "евролог",
            "невропатолог",
            "Нейрофизиолог",
            "Нейрохирург",
            "Неонатолог",
            "Нефролог",
            "Онколог",
            "Онколог-маммолог",
            "Ортопед",
            "Остеопат",
            "Оториноларинголог",
            "Офтальмолог (окулист)",
            "Офтальмохирург",
            "Педиатр",
            "Пластический хирург",
            "Проктолог",
            "Психиатр",
            "Психоаналитик",
            "Психолог",
            "Психоневролог",
            "Психотерапевт",
            "Пульмонолог",
            "Радиолог",
            "Реаниматолог",
            "Ревматолог",
            "Рентгенолог",
            "Репродуктолог",
            "Рефлексотерапевт",
            "Сексопатолог",
            "Семейный врач",
            "Семейный психолог",
            "Сомнолог",
            "Сосудистый хирург",
            "Стоматолог",
            "Стоматолог-имплантолог",
            "Стоматолог-ортодонт",
            "Стоматолог-ортопед",
            "Стоматолог-терапевт",
            "Стоматолог-хирург",
            "Терапевт",
            "Травматолог",
            "Травматолог-ортопед",
            "Трихолог",
            "Уролог",
            "Уролог-андролог",
            "Физиотерапевт",
            "Флеболог",
            "Хирург",
            "Хирург-ортопед",
            "Хирург-флеболог",
            "Челюстно-лицевой хирург",
            "Эндокринолог",
            "Эндоскопист"};


    @Override
    public void onStop() {
        super.onStop();
        try {
            ((MainPageActivity)getActivity()).closeSerach();

        }
        catch (Exception e){}
    }
}



