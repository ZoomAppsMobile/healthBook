package com.example.d.healthbook.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.d.healthbook.Fragments.FoldersFragment;
import com.example.d.healthbook.Fragments.MyItemRecyclerViewAdapter;
import com.example.d.healthbook.R;
import com.example.d.healthbook.new_code.choose_service.AddFileDialogFragment;
import com.example.d.healthbook.new_code.choose_service.AddFolderDialogFragment;
import com.google.gson.Gson;

import static com.example.d.healthbook.Activities.FileActivity.DATA_KEY;

public class FoldersActivity extends BaseActivity  implements FoldersFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);
        onViewSet();
        setToolbarTitleText("Папки");
        navigationClickItemListener(FoldersActivity.this , FoldersActivity.class);
        navigation.getMenu().getItem(5).setChecked(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);

        if(getIntent().getStringExtra("folder_id")!=null){

            Toast.makeText(this, getIntent().getStringExtra("folder_id"), Toast.LENGTH_SHORT).show();

            Intent familyUserInfo = new Intent( this, FileActivity.class);
            familyUserInfo.putExtra(DATA_KEY, getIntent().getStringExtra(DATA_KEY));
            familyUserInfo.putExtra("keys","1");

            familyUserInfo.putExtra("folder_id",getIntent().getStringExtra("folder_id"));
            startActivity(familyUserInfo);
        }




    }

    @Override
    public void onListFragmentInteraction(MyItemRecyclerViewAdapter.ViewHolder item,String fName) {
        if(item.mItem!=null){
            Intent familyUserInfo = new Intent( this, FileActivity.class);
            familyUserInfo.putExtra(DATA_KEY, new Gson().toJson(item.mItem));

            familyUserInfo.putExtra("folder_id",item.mItem.getId());
            familyUserInfo.putExtra("keys","1");

            startActivity(familyUserInfo);
            //Show info
        }
        else{

            Bundle bundle = new Bundle();
            bundle.putString("folder_name", fName);


            AddFolderDialogFragment  addFileDialogFragment = new AddFolderDialogFragment();
          addFileDialogFragment.setArguments(bundle);
          addFileDialogFragment.show(getSupportFragmentManager(), "dialogFragmentWomanCalendar");
        }
    }

    public void addFile(String fName){

        Bundle bundle = new Bundle();
        bundle.putString("folder_name", fName);
        AddFileDialogFragment serviceDialogFragment = new AddFileDialogFragment();
        serviceDialogFragment.setArguments(bundle);

        serviceDialogFragment.show(getSupportFragmentManager(), "dialogFragmentWomanCalendar");


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
