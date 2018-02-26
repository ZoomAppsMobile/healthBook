package com.example.d.healthbook.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.Adapters.FolderParametersAdapter;
import com.example.d.healthbook.CalendarBekarysa.CalendarActivity;
import com.example.d.healthbook.Fragments.DialogFragmentCreateMemberOfFamily;
import com.example.d.healthbook.Models.DiaryModel.CompleteDiaryData;
import com.example.d.healthbook.Models.DiaryModel.ParameterTableModel.Parameter;
import com.example.d.healthbook.Models.DiaryModel.ParameterTableModel.Week;
import com.example.d.healthbook.Models.DiaryModel.ParametersResponse;
import com.example.d.healthbook.Models.DocumentMyFamily;
import com.example.d.healthbook.Presenter.FilePresenter;
import com.example.d.healthbook.R;
import com.example.d.healthbook.UI.MyToolbar;
import com.example.d.healthbook.View.DialogCallback;
import com.example.d.healthbook.View.FileView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.d.healthbook.Activities.UserActivityInfo.seeUserFamilyMembers;

public class FileActivity extends AppCompatActivity implements FileView , DialogCallback<CompleteDiaryData> {

    @BindView(R.id.tv_fam_mem_FullName)
    TextView tv_fam_mem_FullName;
    @BindView(R.id.tv_fam_mem_BornDate)
    TextView tv_fam_mem_BornDate;
    @BindView(R.id.tv_fam_mem_Role)
    TextView tv_fam_mem_Role;
    @BindView(R.id.tv_fam_mem_Sex)
    TextView tv_fam_mem_Sex;
    @BindView(R.id.tv_fam_mem_Email)
    TextView tv_fam_mem_Email;
    @BindView(R.id.tv_fam_mem_Height)
    TextView tv_fam_mem_Height;
    @BindView(R.id.tv_fam_mem_Weight)
    TextView tv_fam_mem_Weight;
    @BindView(R.id.edit_icon)
    ImageView edit_icon;
    @BindView(R.id.ll_basic_info)
    LinearLayout ll_basic_info;

    @BindView(R.id.btn_add_parameter)
    LinearLayout btn_add_parameter;
    @BindView(R.id.ed_new_weight)
    EditText ed_new_weight;
    @BindView(R.id.btn_send_new_weight)
    Button btn_send_new_weight;
    @BindView(R.id.ed_new_height)
    EditText ed_new_height;
    @BindView(R.id.btn_send_new_height)
    Button btn_send_new_height;
    @BindView(R.id.rv_parameters)
    RecyclerView rv_parameters;
    FolderParametersAdapter mAdapter;

    public static String DATA_KEY = "RAW_DATA";
    private DocumentMyFamily data;
    private CompleteDiaryData complete_data;
    private FilePresenter presenter = new FilePresenter(this);


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
        setContentView(R.layout.activity_file);




        ButterKnife.bind(this);
        Toolbar toolbar = (MyToolbar) findViewById(R.id.main_page_toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        mAdapter = new FolderParametersAdapter(new ArrayList<Week>(), this);
        rv_parameters.setLayoutManager(new LinearLayoutManager(this));
        rv_parameters.setAdapter(mAdapter);
        showBackArrow();
        presenter.parseData(getIntent().getStringExtra(DATA_KEY));


        if (getIntent().getStringExtra("keys") != null) {

            edit_icon.setVisibility(View.GONE);
        }


    }

    @Override
    public void onSetViewData(DocumentMyFamily data) {
        this.data = data;
        tv_fam_mem_FullName.setText(data.getName());
        edit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditClick();
            }
        });
    }

    @Override
    public void onFullDataLoaded(CompleteDiaryData full_data) {
        this.complete_data = full_data;

        if (full_data.getType() == 2) {


            try {
                long ms = Long.parseLong(full_data.getBirthday());
                Date date = new Date(ms);
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                String newDateString= dateformat.format(date);

                tv_fam_mem_BornDate.setText(newDateString);

            }
            catch (Exception e){
                tv_fam_mem_BornDate.setText(full_data.getBirthday());



            }







            tv_fam_mem_Role.setText(familyRoleConverter(full_data.getRole()));
            tv_fam_mem_Sex.setText(genderConverter(full_data.getGender()));
            tv_fam_mem_Email.setText(full_data.getEmail());
            tv_fam_mem_FullName.setText(full_data.getName());
             tv_fam_mem_Height.setText(String.valueOf(full_data.getHeight()));
             tv_fam_mem_Weight.setText(String.valueOf(full_data.getWeight()));
        }
        else{
            ll_basic_info.setVisibility(View.GONE);
        }
    }

    @Override
    public void onParametersLoaded(ParametersResponse data) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, UserActivityInfo.class);
        intent.putExtra("dswitch","123");
        startActivity(intent);
    }

    private String folderTypeConverter(int type){
        switch (type){
            case 2 :
                return "Семья";
        }
        return "Не известно";
    }

    private String familyRoleConverter(int role){
        switch (role){
            case 1 :
                return "Родитель";
            case 2 :
                return "Супруг(а)";
            case 3 :
                return "Ребенок";
        }
        return "Не известно";
    }

    private String genderConverter(int id){
        switch (id){
            case 0 :
                return "Женский";

            case 1 :
                return "Мужской";


            case 2 :
                return "Женский";
        }
        return "Не известно";
    }

    private DialogFragmentCreateMemberOfFamily fragmentCreateMemberOfFamily;
    @Override
    public void onEditClick() {

        try {
            FragmentManager fm = getSupportFragmentManager();
            fragmentCreateMemberOfFamily = DialogFragmentCreateMemberOfFamily.newInstance(fm,new Gson().toJson(complete_data),
                    DialogFragmentCreateMemberOfFamily.TYPE_EDIT);
            fragmentCreateMemberOfFamily.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    //do whatever you want when dialog is dismissed
//                        MainController.showToast(getActivity(),"DISMISS");

                }
            });
        }
        catch (Exception e){



        }


//        fragmentCreateMemberOfFamily = new DialogFragmentCreateMemberOfFamily();
//        fragmentCreateMemberOfFamily.show(fm, "fragmentCreateMemberOfFamily");
//        fm.executePendingTransactions();
//        fragmentCreateMemberOfFamily.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialogInterface) {
//                //do whatever you want when dialog is dismissed
////                        MainController.showToast(getActivity(),"DISMISS");
//                seeUserFamilyMembers();
//            }
//        });
    }

    @Override
    public void onShowMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFatalError() {
        finish();
    }

    @Override
    public void onTableDataLoaded(List<Week> datum) {
        mAdapter.updateData(datum);
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

    @Override
    public <T> void onDialogResult(T data, Integer id) {
        if(data instanceof CompleteDiaryData){
            onFullDataLoaded((CompleteDiaryData)data);
        }
    }

    @Override
    public void onDialogFailed(String message) {

    }
/*        Intent familyUserInfo = new Intent( this, FileActivity.class);
            familyUserInfo.putExtra(FileActivity.DATA_KEY, new Gson().toJson(item.mItem));

            familyUserInfo.putExtra("folder_id",item.mItem.getId()); */
    @Override
    public void onItemClick(View view, Week data) {
                 Intent intent = new Intent(this, ParameterDetailsActivity.class)
                .putExtra(ParameterDetailsActivity.FULL_PARAMETER_DATA, new Gson().toJson(data))
                .putExtra(ParameterDetailsActivity.FULL_PARAMETER_DATE, data.getDatetime())



                 .putExtra(FileActivity.DATA_KEY,getIntent().getStringExtra(DATA_KEY))
                 .putExtra("folder_id",getIntent().getStringExtra("folder_id"));

        startActivity(intent);
    }
}
