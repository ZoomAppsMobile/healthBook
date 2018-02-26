//package com.example.d.healthbook.Fragments;
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.DialogFragment;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.view.inputmethod.EditorInfo;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.d.healthbook.Activities.UserActivityInfo;
//import com.example.d.healthbook.Appi.API_Controller;
//import com.example.d.healthbook.Appi.App;
//import com.example.d.healthbook.Controller.MainController;
//import com.example.d.healthbook.GlobalVariables.GlobalVariables;
//import com.example.d.healthbook.Models.ResponseEditUserProfile;
//import com.example.d.healthbook.R;
//
//import java.io.IOException;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//import static com.example.d.healthbook.GlobalVariables.GlobalVariables.responseGetUserData;
//
///**
// * Created by D on 04.07.2017.
// */
//
//public class DialogFragmentEditUserProfile extends DialogFragment {
//    @BindView(R.id.nameProfileEdit)
//    EditText nameProfileEdit;
//
//    @BindView(R.id.surnameProfileEdit)
//    EditText surnameProfileEdit;
//
//    @BindView(R.id.middleameProfileEdit)
//    EditText middleameProfileEdit;
//
//    @BindView(R.id.birthdateProfileEdit)
//    EditText birthdateProfileEdit;
//
//    @BindView(R.id.heightProfileEdit)
//    EditText heightProfileEdit;
//
//    @BindView(R.id.weightProfileEdit)
//    EditText weightProfileEdit;
//
//    @BindView(R.id.pastIlnessesProfileEdit)
//    EditText pastIlnessesProfileEdit;
//
//    @BindView(R.id.chronicDiseasesProfileEdit)
//    EditText chronicDiseasesProfileEdit;
//
//    @BindView(R.id.bloodGroupProfileEdit)
//    EditText bloodGroupProfileEdit;
//
//    @BindView(R.id.heredityProfileEdit)
//    EditText heredityProfileEdit;
//
//    @BindView(R.id.badhabitsProfileEdit)
//    EditText badhabitsProfileEdit;
//
//    @BindView(R.id.homePhoneProfileEdit)
//    EditText homePhoneProfileEdit;
//
////    @BindView(R.id.spinnerEditProfile)
////    Spinner spinnerEditProfile;
//
//    @BindView(R.id.allergyDiseasesProfileEdit)
//    EditText allergyDiseasesProfileEdit;
//
//    private Spinner spinnerEditProfile;
//
////    @BindView(R.id.clickBtnSaveProfileEdit)
////    Button clickBtnSaveProfileEdit;
////
////    @BindView(R.id.clickBtnExitProfileEdit)
////    Button clickBtnExitProfileEdit;
//
//    private String name = "", surname = "", middleName, gender,
//            birthday, height, weight,
//            allergy, pastIllnesses, chronicDiseases,
//            bloodGroup, heredity,
//            badHabits, homePhone, city_id;
//
//    private String LOG_TAG = "DIALOG";
//
//    private ResponseEditUserProfile registeredUser;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//    }
//
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        getDialog().setTitle("Title!");
//        View v = inflater.inflate(R.layout.dialog_fragment_edit_user_profile, null);
//
//        spinnerEditProfile = (Spinner) v.findViewById(R.id.spinnerEditProfile);
//        final String[] spinnerGender = {"Мужской", "Женский"};
//        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
//                R.layout.text_spinner_item_custom, spinnerGender);
//        adapter2.setDropDownViewResource(R.layout.my_spinner_drop_down);
//        spinnerEditProfile.setAdapter(adapter2);
//        spinnerEditProfile.setSelection(adapter2.getCount() - 1);
//
//
//        ButterKnife.bind(this, v);
//
//
//        return v;
//
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//
//        setOldDataUser();
//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN );
//
//
//        clickBtnSaveProfileEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                name = nameProfileEdit.getText().toString();
//                surname = surnameProfileEdit.getText().toString();
//                middleName = middleameProfileEdit.getText().toString();
//
//
//                if (spinnerEditProfile.getSelectedItem().toString().equals("Мужской")) {
//                    gender = "1";
//                } else {
//                    gender = "0";
//                }
//
//                birthday = birthdateProfileEdit.getText().toString();
//                height = heightProfileEdit.getText().toString();
//
//                weight = weightProfileEdit.getText().toString();
//                allergy = allergyDiseasesProfileEdit.getText().toString();
//                pastIllnesses = pastIlnessesProfileEdit.getText().toString();
//                chronicDiseases = chronicDiseasesProfileEdit.getText().toString();
//                bloodGroup = bloodGroupProfileEdit.getText().toString();
//                heredity = heredityProfileEdit.getText().toString();
//                badHabits = badhabitsProfileEdit.getText().toString();
//                homePhone = homePhoneProfileEdit.getText().toString();
//                city_id = "1";
//
//                if (name.equals("")) {
//                    MainController.showToast(getActivity(), "Пожалуйста введите Имя");
//                } else if (surname.equals("")) {
//                    MainController.showToast(getActivity(), "Пожалуйста введите Фамилию");
//
//                } else {
//                    saveNewInfo(name, surname, middleName, gender,
//                            birthday, height, weight,
//                            allergy, pastIllnesses, chronicDiseases,
//                            bloodGroup, heredity,
//                            badHabits, homePhone, city_id);
//                }
//
//
//            }
//        });
//
//
//        homePhoneProfileEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEND) {
//                    clickBtnSaveProfileEdit.performClick();
//                    return true;
//                }
//                return false;
//            }
//        });
//        clickBtnExitProfileEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//    }
//
//    private void setOldDataUser() {
//        if (!responseGetUserData.getName().equals("")) {
//            nameProfileEdit.setText(responseGetUserData.getName());
//        }
//        if (!responseGetUserData.getSurname().equals("")) {
//            surnameProfileEdit.setText(responseGetUserData.getSurname());
//        }
//        if (!responseGetUserData.getBirthday().equals("")) {
//            birthdateProfileEdit.setText(responseGetUserData.getBirthday());
//        }
//        if (!responseGetUserData.getMiddleName().equals("")) {
//            middleameProfileEdit.setText(responseGetUserData.getMiddleName());
//        }
//        if (!responseGetUserData.getGender().equals("")) {
//            String genderCheck = responseGetUserData.getGender();
//            if (genderCheck.equals("Мужской")) {
//                spinnerEditProfile.setSelection(0);
//            } else {
//                spinnerEditProfile.setSelection(1);
//            }
//
//        }
//        if (!responseGetUserData.getHeight().toString().equals("0")) {
//            heightProfileEdit.setText(String.valueOf(responseGetUserData.getHeight()));
//        }
//        if (!responseGetUserData.getWeight().toString().equals("0")) {
//            weightProfileEdit.setText(String.valueOf(responseGetUserData.getWeight()));
//        }
//        if (!responseGetUserData.getAllergy().equals("")) {
//            allergyDiseasesProfileEdit.setText(responseGetUserData.getAllergy());
//        }
//        if (!responseGetUserData.getPastIllnesses().equals("")) {
//            pastIlnessesProfileEdit.setText(responseGetUserData.getPastIllnesses());
//        }
//        if (!responseGetUserData.getChronicDiseases().equals("")) {
//            chronicDiseasesProfileEdit.setText(responseGetUserData.getChronicDiseases());
//        }
//        if (!responseGetUserData.getBloodGroup().equals("")) {
//            bloodGroupProfileEdit.setText(responseGetUserData.getBloodGroup());
//        }
//        if (!responseGetUserData.getHeredity().equals("")) {
//            heredityProfileEdit.setText(String.valueOf(responseGetUserData.getHeredity()));
//        }
//        if (!responseGetUserData.getBadHabits().equals("")) {
//            badhabitsProfileEdit.setText(String.valueOf(responseGetUserData.getBadHabits()));
//        }
//        if (!responseGetUserData.getHomePhone().equals("")) {
//            homePhoneProfileEdit.setText(String.valueOf(responseGetUserData.getHomePhone()));
//        }
//
//
//    }
//
//
//    public void saveNewInfo(String name, String surname, String middleName, String gender, String
//            birthday, String height, String weight, String
//                                    allergy, String pastIllnesses, String chronicDiseases, String
//                                    bloodGroup, String heredity, String
//                                    badHabits, String homePhone, String city_id) {
//
//        App.getApi().editUserProfile(GlobalVariables.user_id, GlobalVariables.user_auth_token,
//                API_Controller.editUserProfile(name, surname,
//                        middleName, gender,
//                        birthday, height, weight,
//                        allergy, pastIllnesses, chronicDiseases,
//                        bloodGroup, heredity,
//                        badHabits, homePhone, city_id)).enqueue(new Callback<ResponseEditUserProfile>() {
//            @Override
//            public void onResponse(Call<ResponseEditUserProfile> call, Response<ResponseEditUserProfile> response) {
//                int s = response.code();
//                if (response.errorBody() != null) {
//                    try {
//                        Log.e("LOG", "Retrofit Response: " + response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                registeredUser = response.body();
//
//                if (registeredUser != null) {
//
//
//                    Toast.makeText(getActivity(), "Загрузка прошла успешно ", Toast.LENGTH_SHORT).show();
//                    getActivity().finish();
//                    Intent intent = new Intent(getActivity(), UserActivityInfo.class);
//                    startActivity(intent);
//                    dismiss();
//
//                } else {
//                    Toast.makeText(getActivity(), "Ошибка при загрузке ", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseEditUserProfile> call, Throwable t) {
//                Toast.makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    public void onDismiss(DialogInterface dialog) {
//        super.onDismiss(dialog);
//        Log.d(LOG_TAG, "Dialog 1: onDismiss");
//    }
//
//    public void onCancel(DialogInterface dialog) {
//        super.onCancel(dialog);
//        Log.d(LOG_TAG, "Dialog 1: onCancel");
//    }
//}