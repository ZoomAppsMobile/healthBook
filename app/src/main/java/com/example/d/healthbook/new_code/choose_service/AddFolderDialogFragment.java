package com.example.d.healthbook.new_code.choose_service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.d.healthbook.API.API_Controller;
import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Activities.FoldersActivity;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.R;
import com.example.d.healthbook.new_code.choose_service.model.FolderDatum;
import com.example.d.healthbook.new_code.choose_service.model.FolderResponse;
import com.example.d.healthbook.new_code.choose_service.model.FolderResponse2;
import com.example.d.healthbook.new_code.choose_service.model.MonitorRequest;
import com.example.d.healthbook.new_code.choose_service.model.MonitorResponse;
import com.example.d.healthbook.new_code.choose_service.model.ParameterType;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 14.08.2017.
 */

public class AddFolderDialogFragment extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.ed_new_weight)
    EditText edNewWeight;
    @BindView(R.id.ed_new_height)
    EditText edNewHeight;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btnLayout)
    LinearLayout btnLayout;
    @BindView(R.id.ed_temp)
    EditText edTemp;
    private int parametr_type = 2;
    List<FolderDatum> folderDatumList = new ArrayList<>();

    @Override
    public void onClick(View view) {

    }

    private String diary_id;
    private String folder_name;
    private FolderResponse registeredUser;
    private MonitorResponse monitorResponse;
    @BindView(R.id.parent_linear_layout)
    LinearLayout parentLinearLayout;
    @BindView(R.id.type_spinner)
    Spinner spinner;
    String height;
    String weighyt;
    String currentDateandTime;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_folder_info, null);
        ButterKnife.bind(this, v);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        currentDateandTime = sdf.format(new Date());
        Toast.makeText(getContext(), currentDateandTime, Toast.LENGTH_SHORT).show();


        folder_name = getArguments().getString("folder_name");


        height = edNewHeight.getText().toString();
        weighyt = edNewWeight.getText().toString();


        final List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("");
        spinnerArray.add("Температура");
        spinnerArray.add("Калории");
        spinnerArray.add("Верхнее давление");
        spinnerArray.add("Нижнее давление");
        spinnerArray.add("Пульс");
        spinnerArray.add("Частота дыхательных движений");
        spinnerArray.add("Сатурация кислорода");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);


                if (!spinnerArray.get(position).equals("")) {

                    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View rowView = inflater.inflate(R.layout.field, null);
                    parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
                    EditText editType = (EditText) rowView.findViewById(R.id.edit_type);
                    editType.setHint(item.toString());
                    spinnerArray.remove(position);


                }


              /*  if (item != null) {
                    Toast.makeText(getContext(), item.toString(),
                            Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getContext(), "Selected",
                        Toast.LENGTH_SHORT).show();*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });


        //  addFolder(folder_name);

        return v;
    }

    @OnClick(R.id.add_file)
    public void addFile() {

        ((FoldersActivity) getActivity()).addFile(folder_name);



    }

    @OnClick(R.id.clickBtnSaveEvent)
    public void onAddField() {

        if (edNewHeight.getText().toString().length() != 0 && edNewWeight.getText().toString().length() != 0 && edTemp.getText().toString().length() != 0) {


            addFolder(folder_name);
            progressBar.setVisibility(View.VISIBLE);
            btnLayout.setVisibility(View.GONE);


        } else {
            Toast.makeText(getContext(), "Заполните обязательные поля", Toast.LENGTH_SHORT).show();
        }


    }


    @OnClick(R.id.clickBtnCancelEvent)
    public void onCancleield() {


        dismiss();

    }

   /* @OnClick(R.id.add_field_button)
    public void onAddField() {
        LayoutInflater inflater = (LayoutInflater)getActivity(). getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }*/


    public void addMonitorype(final MonitorRequest monitorRequest) {

        App.getApi().addMonitorType(GlobalVariables.user_auth_token,
                monitorRequest).enqueue(new Callback<MonitorResponse>() {
            @Override
            public void onResponse(Call<MonitorResponse> call, Response<MonitorResponse> response) {
                int s = response.code();
                if (response.errorBody() != null) {
                    try {
                        Log.e("LOG", "Retrofit Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                monitorResponse = response.body();


                if (parametr_type < 4) {

                    MonitorRequest monitorRequest1 = new MonitorRequest();
                    ParameterType parameterType = new ParameterType();
                    parameterType.setId(String.valueOf(parametr_type));
                    monitorRequest1.setDiaryId(diary_id);
                    monitorRequest1.setParameterType(parameterType);
                    addMonitorype(monitorRequest1);
                    parametr_type++;


                } else {


                    addFolderData(folderDatumList);

                }


            }

            @Override
            public void onFailure(Call<MonitorResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void addFolderData(List<FolderDatum> folderDatum) {

        App.getApi().addFolderData(GlobalVariables.user_auth_token,
                folderDatum).enqueue(new Callback<List<FolderResponse2>>() {
            @Override
            public void onResponse(Call<List<FolderResponse2>> call, Response<List<FolderResponse2>> response) {
                int s = response.code();
                if (response.errorBody() != null) {
                    try {
                        Log.e("LOG", "Retrofit Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Toast.makeText(getActivity(), "Данные успешно добавлены!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btnLayout.setVisibility(View.VISIBLE);
                dismiss();
                Intent intent = new Intent(getContext(), FoldersActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<List<FolderResponse2>> call, Throwable t) {
                Toast.makeText(getActivity(), "Ошибка", Toast.LENGTH_SHORT).show();
                Log.d("errerer", t.getMessage());
            }
        });
    }


    public void addFolder(String name) {

        App.getApi().addFolder(GlobalVariables.user_auth_token,
                API_Controller.createFolder(name)).enqueue(new Callback<FolderResponse>() {
            @Override
            public void onResponse(Call<FolderResponse> call, Response<FolderResponse> response) {
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
                    diary_id = registeredUser.getId();
                    Log.d("diary_id", diary_id);

                    MonitorRequest monitorRequest1 = new MonitorRequest();
                    ParameterType parameterType = new ParameterType();
                    parameterType.setId(String.valueOf(1));
                    monitorRequest1.setDiaryId(diary_id);
                    monitorRequest1.setParameterType(parameterType);
                    addMonitorype(monitorRequest1);


                    FolderDatum folderDatum = new FolderDatum();
                    folderDatum.setDiaryId(diary_id);
                    folderDatum.setParameterTypeId(String.valueOf(1));
                    folderDatum.setValue(edNewWeight.getText().toString());
                    folderDatum.setMeasureDate(currentDateandTime + " 13:51:45");

                    FolderDatum folderDatum2 = new FolderDatum();
                    folderDatum2.setDiaryId(diary_id);
                    folderDatum2.setParameterTypeId(String.valueOf(2));
                    folderDatum2.setValue(edNewHeight.getText().toString());
                    folderDatum2.setMeasureDate(currentDateandTime + " 13:51:45");



                    FolderDatum folderDatum3 = new FolderDatum();
                    folderDatum3.setDiaryId(diary_id);
                    folderDatum3.setParameterTypeId(String.valueOf(3));
                    folderDatum3.setValue(edTemp.getText().toString());
                    folderDatum3.setMeasureDate(currentDateandTime + " 13:51:45");

                    folderDatumList.add(folderDatum);
                    folderDatumList.add(folderDatum2);
                    folderDatumList.add(folderDatum3);


                } else {
                    Toast.makeText(getActivity(), "Ошибка при загрузке ", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<FolderResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
