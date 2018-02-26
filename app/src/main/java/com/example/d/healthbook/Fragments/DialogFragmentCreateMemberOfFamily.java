package com.example.d.healthbook.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.API.API_Controller;
import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.DefaultUpdateResponseModel;
import com.example.d.healthbook.Models.DiaryModel.CompleteDiaryData;
import com.example.d.healthbook.Models.DocumentMyFamily;
import com.example.d.healthbook.Models.ResponseMyFamilyMemberCreate;
import com.example.d.healthbook.R;
import com.example.d.healthbook.View.DialogCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.d.healthbook.Activities.UserActivityInfo.seeUserFamilyMembers;

/**
 * Created by D on 05.07.2017.
 */

public class DialogFragmentCreateMemberOfFamily extends DialogFragment {
    @BindView(R.id.nameProfileMember)
    EditText nameProfileMember;

    @BindView(R.id.surnameProfileMember)
    EditText surnameProfileMember;

    @BindView(R.id.middleameProfileMember)
    EditText middleameProfileMember;

    @BindView(R.id.birthdateProfileMember)
    EditText birthdateProfileMember;

    @BindView(R.id.heightProfileMember)
    EditText heightProfileMember;

    @BindView(R.id.weightProfileMember)
    EditText weightProfileMember;

    @BindView(R.id.email_et_member)
    EditText email_et_member;

    @BindView(R.id.clickBtnSaveProfileMember)
    Button clickBtnSaveProfileMember;

    @BindView(R.id.clickBtnExitProfileMember)
    Button clickBtnExitProfileMember;

    private String name = "", surname = "", middleName, gender,
            birthday, height, weight, role, email;


    private String LOG_TAG = "DIALOG";

    private ResponseMyFamilyMemberCreate registeredUser;
    private Spinner spinnerEditMemberGender;
    private Spinner spinnerEditMemberRole;

    private String aBirth, bBirth, cBirth;

    private String formatterCheck;
    private boolean checkBirthDate = false;
    private Matcher matcher;
    private long birthDateLong;

    private String getRawData(){
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            return bundle.getString(KEY_DATA);
        }
        return null;
    }

    private int getType(){
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            return bundle.getInt(KEY_TYPE);
        }
        return 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_create_member_of_family, null);
        spinnerEditMemberGender = (Spinner) v.findViewById(R.id.spinnerEditMemberGender);
        final String[] spinnerGender = {"Мужской", "Женский"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                R.layout.text_spinner_item_custom_gray, spinnerGender);
        adapter2.setDropDownViewResource(R.layout.my_spinner_drop_down);
        spinnerEditMemberGender.setAdapter(adapter2);
        spinnerEditMemberGender.setSelection(adapter2.getCount() - 1);


        spinnerEditMemberRole = (Spinner) v.findViewById(R.id.spinnerEditMemberRole);
        final String[] spinnerRole = {"Родитель", "Супруг(а)", "Ребенок"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                R.layout.text_spinner_item_custom_gray, spinnerRole);
        adapter1.setDropDownViewResource(R.layout.my_spinner_drop_down);
        spinnerEditMemberRole.setAdapter(adapter1);
        spinnerEditMemberRole.setSelection(adapter1.getCount() - 1);


        ButterKnife.bind(this, v);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);


        return v;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        birthdayTextChangeListener();

        clickBtnSaveProfileMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = nameProfileMember.getText().toString();
                surname = surnameProfileMember.getText().toString();
                middleName = middleameProfileMember.getText().toString();


                if (spinnerEditMemberGender.getSelectedItem().toString().equals("Мужской")) {
                    gender = "1";
                } else {
                    gender = "2";
                }


                if (spinnerEditMemberRole.getSelectedItem().toString().equals("Родитель")) {
                    role = "1";
                } else if (spinnerEditMemberRole.getSelectedItem().toString().equals("Супруг(а)")) {
                    role = "2";
                } else if (spinnerEditMemberRole.getSelectedItem().toString().equals("Ребенок")) {
                    role = "3";
                }


                birthday = birthdateProfileMember.getText().toString();


                height = heightProfileMember.getText().toString();

                weight = weightProfileMember.getText().toString();
                email = email_et_member.getText().toString();


                if (name.equals("")) {
                    MainController.showToast(getActivity(), "Пожалуйста введите Имя");
                } else if (surname.equals("")) {
                    MainController.showToast(getActivity(), "Пожалуйста введите Фамилию");
                } else if (birthday.equals("")) {
                    MainController.showToast(getActivity(), "Введите день рожденья (ДД)");
                }  else if (height.equals("")) {
                    MainController.showToast(getActivity(), "Введите рост");
                }
                else if (weight.equals("")) {
                    MainController.showToast(getActivity(), "Введите вес");
                }


                else if (!birthday.equals("")) {

                    String birthdaySplit[] = birthday.split("/");

                    aBirth = birthdaySplit[0];
                    bBirth = birthdaySplit[1];
                    cBirth = birthdaySplit[2];

                    if (checkMathcher(aBirth)) {
                        MainController.showToast(getActivity(), "Введите день рожденья (ДД)");
                    } else if (checkMathcher(bBirth)) {
                        MainController.showToast(getActivity(), "Введите месяц рожденья (MM)");
                    } else if (checkMathcher(cBirth)) {
                        MainController.showToast(getActivity(), "Введите год рожденья (ГГГГ)");
                    } else {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            Date date = sdf.parse(birthday);

                            birthDateLong = date.getTime();
                            birthday = String.valueOf(birthDateLong);

                            checkBirthDate = true;


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (getType() == TYPE_EDIT) {
                            editMyfamilyMember( null,name + " " + surname + " " + middleName, name, surname, middleName,
                                    gender, role, birthday,
                                    height, weight, "2",
                                    email);
                        } else {
                            createMyfamilyMember(name + " " + surname + " " + middleName, name, surname, middleName,
                                    gender, role, birthday,
                                    height, weight, "2",
                                    email);
                        }
                    }


                }


            }
        });


        email_et_member.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    clickBtnSaveProfileMember.performClick();
                    return true;
                }
                return false;
            }
        });
        clickBtnExitProfileMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO ВВОД ДАТЫ РОЖДЕНИЯ, НУЖНО ОТПРАВЛЯТЬ В LONGE
                dismiss();


            }
        });

        if(getType()==TYPE_EDIT){
            old_data = new Gson().fromJson(getRawData(),CompleteDiaryData.class);
            setSavedData();
        }
    }

    void setSavedData(){
        nameProfileMember.setText(old_data.getUserName());

        surnameProfileMember.setText(old_data.getUserSurname());

        middleameProfileMember.setText(old_data.getUserPatronymic());
        //// TODO: convert Date
       // birthdateProfileMember.setText(old_data.getBirthday());
        //"06/27/2007"
        String startDateString = old_data.getBirthday();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        Date startDate;
        try {
            startDate = df.parse(startDateString);
            DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            String newDateString = df2.format(startDate);
            birthdateProfileMember.setText(newDateString);

        } catch (ParseException e) {
            e.printStackTrace();

            long ms = Long.parseLong(old_data.getBirthday());
            Date date = new Date(ms);
            SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
            String newDateString= dateformat.format(date);
            birthdateProfileMember.setText(newDateString);


        }
         heightProfileMember.setText(old_data.getHeight().toString());

         weightProfileMember.setText(old_data.getWeight().toString());

        email_et_member.setText(old_data.getEmail().toString());

        spinnerEditMemberRole.setSelection(old_data.getRole()-1);
        spinnerEditMemberGender.setSelection(old_data.getGender()-1);



    }
    private CompleteDiaryData old_data;

    public boolean checkMathcher(String a) {
        Pattern pattern = Pattern.compile("[а-яА-ЯёЁa-zA-Z]");
        matcher = pattern.matcher(a);
        return matcher.find();

    }

    private void birthdayTextChangeListener() {
        TextWatcher tw = new TextWatcher() {
            String current = "";
            String ddmmyyyy = "ДДMMГГГГ";
            Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
//                    String clean = s.toString().replaceAll("/", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }

                    else {
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2013
                        //would be automatically corrected to 28/02/2013

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    birthdateProfileMember.setText(current);
                    birthdateProfileMember.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        birthdateProfileMember.addTextChangedListener(tw);


    }

    public void editMyfamilyMember( String id,String name, String user_name, String user_surname,
                                     String user_patronymic, String gender, String role,
                                     String birthday, String height, String weight,
                                     String type, String email) {
        CompleteDiaryData changedData = old_data;
        changedData.setName(name);
        changedData.setUserName(user_name);
        changedData.setUserSurname(user_surname);
        changedData.setUserPatronymic(user_patronymic);
        changedData.setRole(Integer.parseInt(role));
        changedData.setGender(Integer.parseInt(gender));
        changedData.setBirthday(birthday);
        changedData.setHeight(Integer.parseInt(height));
        changedData.setWeight(Integer.parseInt(weight));
        changedData.setType(Integer.parseInt(type));
        changedData.setEmail(email);

        Gson gson = new GsonBuilder().disableInnerClassSerialization().create();
        String json_body = gson.toJson(changedData);
        final CompleteDiaryData tmp_data_holder = changedData;
        App.getApi().editFamilyMember(GlobalVariables.user_auth_token,
                changedData).enqueue(new Callback<DefaultUpdateResponseModel>() {
            @Override
            public void onResponse(Call<DefaultUpdateResponseModel> call, Response<DefaultUpdateResponseModel> response) {
                int s = response.code();
                if (response.errorBody() != null) {
                    try {
                        Log.e("LOG", "Retrofit Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                if(response.body()==null){
                    return;
                }
                if (response.body().getSuccess()!=null && response.body().getSuccess()) {
                    old_data = tmp_data_holder;
                    Toast.makeText(getActivity(), "Данные обновлены", Toast.LENGTH_SHORT).show();
                    ((DialogCallback<CompleteDiaryData>)getActivity()).onDialogResult(old_data,5);
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Ошибка при редактировании", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultUpdateResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void createMyfamilyMember(String name, String user_name, String user_surname,
                                     String user_patronymic, String gender, String role,
                                     String birthday, String height, String weight,
                                     String type, String email) {

        App.getApi().createFamilyMember(GlobalVariables.user_auth_token,
                API_Controller.createFamilyMember(name, user_name, user_surname,
                        user_patronymic, gender, role,
                        birthday, height, weight,
                        type, email)).enqueue(new Callback<ResponseMyFamilyMemberCreate>() {
            @Override
            public void onResponse(Call<ResponseMyFamilyMemberCreate> call, Response<ResponseMyFamilyMemberCreate> response) {
                int s = response.code();
                if (response.errorBody() != null) {
                    try {
                        Log.e("LOG", "Retrofit Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                registeredUser = response.body();

                if (registeredUser != null) {
                    String b = registeredUser.getId();

                   // Toast.makeText(getActivity(), "Загрузка прошла успешно ", Toast.LENGTH_SHORT).show();

                    dismiss();

                } else {
                    Toast.makeText(getActivity(), "Ошибка при загрузке ", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseMyFamilyMemberCreate> call, Throwable t) {
                Toast.makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 1: onDismiss");

    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 1: onCancel");
    }

    private static final String  KEY_DATA = "RAW_DATA";
    private static final String  KEY_TYPE = "DIALOG_TYPE";
    public static final int TYPE_EDIT = 265;
    public static final int TYPE_NEW = 906;

    public static DialogFragmentCreateMemberOfFamily newInstance(FragmentManager fm, String data,Integer type){
        DialogFragmentCreateMemberOfFamily fragmentCreateMemberOfFamily = new DialogFragmentCreateMemberOfFamily();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_DATA, data);
        bundle.putInt(KEY_TYPE ,type );
        fragmentCreateMemberOfFamily.setArguments(bundle);
        fragmentCreateMemberOfFamily.show(fm, "fragmentCreateMemberOfFamily");
        fm.executePendingTransactions();
        return fragmentCreateMemberOfFamily;
    }
}
