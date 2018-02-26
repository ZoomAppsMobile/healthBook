package com.example.d.healthbook.new_code.choose_service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.example.d.healthbook.Activities.FileActivity.DATA_KEY;


/**
 * Created by User on 14.08.2017.
 */

public class AddParametrDialogFragment extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.ed_new_weight)
    EditText edNewWeight;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btnLayout)
    LinearLayout btnLayout;
    @BindView(R.id.textView1)
    TextView textView1;
    List<FolderDatum> folderDatumList = new ArrayList<>();

    @Override
    public void onClick(View view) {

    }

    private FolderResponse registeredUser;
    private MonitorResponse monitorResponse;
    @BindView(R.id.parent_linear_layout)
    LinearLayout parentLinearLayout;

    String currentDateandTime;
    String weighyt;
    String param_name;
    String param_value;
    private String diary_id;
    private String parameter_type;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.update_folder_info, null);
        ButterKnife.bind(this, v);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        currentDateandTime = sdf.format(new Date());
        diary_id = getArguments().getString("diary_id");
        parameter_type = getArguments().getString("parameter_type");//parameter_type
        param_name = getArguments().getString("param_name");
        param_value = getArguments().getString("param_value");//parameter_type



        textView1.setText(param_name);
        edNewWeight.setHint(param_name);
        edNewWeight.setText(param_value);


        weighyt = edNewWeight.getText().toString();


        return v;
    }


    @OnClick(R.id.clickBtnSaveEvent)
    public void onAddField() {

        if (edNewWeight.getText().toString().length() != 0) {


            progressBar.setVisibility(View.VISIBLE);
            btnLayout.setVisibility(View.GONE);






            Log.d("diarypadiary_id",String.valueOf(diary_id));
            Log.d("diaryparametr_type",parameter_type);



         MonitorRequest monitorRequest1 = new MonitorRequest();

               ParameterType parameterType = new ParameterType();
               parameterType.setId(parameter_type);

            monitorRequest1.setDiaryId(diary_id);
            monitorRequest1.setParameterType(parameterType);

            addMonitorype(monitorRequest1);


        } else {
            Toast.makeText(getContext(), "Заполните обязательные поля", Toast.LENGTH_SHORT).show();
        }


    }


    @OnClick(R.id.clickBtnCancelEvent)
    public void onCancleield() {


        dismiss();

    }





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


                    FolderDatum folderDatum2 = new FolderDatum();
                    folderDatum2.setDiaryId(diary_id);
                    folderDatum2.setParameterTypeId(parameter_type);
                    folderDatum2.setValue(edNewWeight.getText().toString());
                    folderDatum2.setMeasureDate(currentDateandTime +" 13:51:45");
                    folderDatumList.add(folderDatum2);
                    addFolderData(folderDatumList);






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


                Toast.makeText(getActivity(), "Данные успешно изменены!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                btnLayout.setVisibility(View.VISIBLE);
                dismiss();
                Intent intent = new Intent(getContext(), FoldersActivity.class)
                        .putExtra(DATA_KEY,getActivity().getIntent().getStringExtra(DATA_KEY))
                        .putExtra("folder_id",getActivity().getIntent().getStringExtra("folder_id"));

                startActivity(intent);

            }

            @Override
            public void onFailure(Call<List<FolderResponse2>> call, Throwable t) {
                Toast.makeText(getActivity(), "Ошибка", Toast.LENGTH_SHORT).show();
                Log.d("errerer", t.getMessage());
            }
        });
    }



}
