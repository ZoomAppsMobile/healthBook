package com.example.d.healthbook.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.Adapters.ParamentersDetailAdapter;
import com.example.d.healthbook.Models.DiaryModel.ParameterTableModel.Parameter;
import com.example.d.healthbook.Models.DiaryModel.ParameterTableModel.Week;
import com.example.d.healthbook.Presenter.ParameterDetailsPresenter;
import com.example.d.healthbook.R;
import com.example.d.healthbook.View.ParametersDetailsView;
import com.example.d.healthbook.new_code.choose_service.AddParametrDialogFragment;
import com.example.d.healthbook.new_code.choose_service.UpdateFolderDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParameterDetailsActivity extends AppCompatActivity implements ParametersDetailsView{
    @BindView(R.id.type_spinner)
    Spinner spinner;
    public static String FULL_PARAMETER_DATA = "full_param_data";
    public static String FULL_PARAMETER_DATE = "full_param_date";
    private ParameterDetailsPresenter presenter;
    @BindView(R.id.rv_parameters)
    RecyclerView rv_parameters;
    @BindView(R.id.tv_parameter_date)
    TextView tv_parameter_date;
    ParamentersDetailAdapter mAdapter;
    protected void showBackArrow() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter_details);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Запись");
        setSupportActionBar(toolbar);
        mAdapter = new ParamentersDetailAdapter(new ArrayList<Parameter>(),this);
        rv_parameters.setLayoutManager(new LinearLayoutManager(this));
        rv_parameters.setAdapter(mAdapter);
        presenter = new ParameterDetailsPresenter(this);
        presenter.parseData(getIntent().getStringExtra(FULL_PARAMETER_DATA));
        showBackArrow();
        if(getIntent().getStringExtra(FULL_PARAMETER_DATE)!=null)
            try {
                tv_parameter_date.setText(getIntent().getStringExtra(FULL_PARAMETER_DATE));
            }
            catch (Exception ex){

            }









        final List<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("");
        spinnerArray.add("Калории");
        spinnerArray.add("Верхнее давление");
        spinnerArray.add("Нижнее давление");
        spinnerArray.add("Пульс");
        spinnerArray.add("Частота дыхательных движений");
        spinnerArray.add("Сатурация кислорода");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);


                if (!spinnerArray.get(position).equals("")) {

                    LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View rowView = inflater.inflate(R.layout.field, null);




                    Bundle bundle1 = new Bundle();
                    bundle1.putString("diary_id", getIntent().getStringExtra("folder_id"));
                    bundle1.putString("parameter_type", String.valueOf(String.valueOf(9+position)));
                    bundle1.putString("param_name", item.toString());
                    bundle1.putString("param_value", "");


                    AddParametrDialogFragment serviceDialogFragment = new AddParametrDialogFragment();
                    serviceDialogFragment.setArguments(bundle1);
                    serviceDialogFragment.show(getSupportFragmentManager(), "dialogFragmentWomanCalendar2");



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












    }

    @Override
    public void onDataReady(Week datum) {
        mAdapter.updateData(datum.getParameters());
    }

    @Override
    public void onShowToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(View view, Parameter data) {




        Bundle bundle = new Bundle();
        bundle.putString("diary_id", getIntent().getStringExtra("folder_id"));
        bundle.putString("parameter_type", String.valueOf(data.getParameterTypeId()));
        bundle.putString("param_name", data.getName());
        bundle.putString("param_value", data.getValue());


        UpdateFolderDialogFragment serviceDialogFragment = new UpdateFolderDialogFragment();
        serviceDialogFragment.setArguments(bundle);
        serviceDialogFragment.show(getSupportFragmentManager(), "dialogFragmentWomanCalendar");









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
