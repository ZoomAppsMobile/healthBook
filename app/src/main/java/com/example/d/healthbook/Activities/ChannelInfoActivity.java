package com.example.d.healthbook.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.API.API_Controller;
import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Adapters.RecyclerNewsListAdapter;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.Document;
import com.example.d.healthbook.Models.ResponseSubscribe;
import com.example.d.healthbook.Presenter.ChannelInfoPresenter;
import com.example.d.healthbook.R;
import com.example.d.healthbook.UI.MyToolbar;
import com.example.d.healthbook.View.ChannelInfoView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChannelInfoActivity extends AppCompatActivity implements ChannelInfoView{
    @BindView(R.id.titleChannelInfo)
    TextView titleChannelInfo;
    @BindView(R.id.subTitleChannelInfo)
    TextView subTitleChannelInfo;
    private MyToolbar toolbar;
    @BindView(R.id.subscribeBtn)
    Button subscribeBtn;
    @BindView(R.id.channel_news_RV)
    RecyclerView channel_news_RV;
    private String id;
    private RecyclerNewsListAdapter recycler_news_list;
    private List<Document> adapter_data;

    private ChannelInfoPresenter presenter = new ChannelInfoPresenter(this) ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_info);
        ButterKnife.bind(this);
        toolbar = (MyToolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        String title = getIntent().getStringExtra("title");
        String subtitle = getIntent().getStringExtra("subtitle");
        id = getIntent().getStringExtra("id");
        titleChannelInfo.setText(title);
        subTitleChannelInfo.setText(subtitle);
        subscribeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.actionSubscribe(2, id);
            }
        });
        adapter_data = new ArrayList<Document>();
        channel_news_RV.setLayoutManager(new LinearLayoutManager(this));
        recycler_news_list = new RecyclerNewsListAdapter(adapter_data ,this);
        channel_news_RV.setAdapter(recycler_news_list);
        presenter.getData(id);
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
    public void onSubscribed() {
        changeBtnUI(true);
    }

    @Override
    public void onUnSubscribed() {
        changeBtnUI(false);
    }

    void changeBtnUI(Boolean isSubsed){
        if(isSubsed){
            subscribeBtn.setText("Отписаться");
            subscribeBtn.setBackgroundColor(MainController.getColor(this,R.color.bpRed));
        }
        else{
            subscribeBtn.setText("Подписаться");
            subscribeBtn.setBackgroundColor(MainController.getColor(this,R.color.bpGreen));
        }
    }

    @Override
    public void onDataLoaded(List<Document> datum) {
        adapter_data.clear();
        adapter_data.addAll(datum);
        recycler_news_list.notifyDataSetChanged();
    }

    @Override
    public void onLoadedError(String reason) {
        Toast.makeText(this,reason,Toast.LENGTH_SHORT).show();
    }
}
