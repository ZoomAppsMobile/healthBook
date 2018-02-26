package com.example.d.healthbook.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.Adapters.ChatAdapter;
import com.example.d.healthbook.Adapters.RecyclerAdapterChatActivity;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.Controller.SocketController;
import com.example.d.healthbook.Controller.SocketControllerInterface;
import com.example.d.healthbook.Controller.WebAppInterface;
import com.example.d.healthbook.Models.Chat.DialogModel;
import com.example.d.healthbook.Models.ChatModel;
import com.example.d.healthbook.Models.ResponseGetUserData;
import com.example.d.healthbook.Models.SocketMessageModel;
import com.example.d.healthbook.Presenter.ChatPresenter;
import com.example.d.healthbook.R;
import com.example.d.healthbook.UI.MyToolbar;
import com.example.d.healthbook.View.ChatView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.vishu.sockjsandroidclient.SockJsClientListener;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity implements ChatView  {

    private MyToolbar toolbar;


    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.profileAvaChatPreviewRIV)
    RoundedImageView profileAvaChatPreviewIV;

    @BindView(R.id.people_chat_TV)
    TextView people_chat_TV;

    @BindView(R.id.recyclerChat)
    RecyclerView mRecyclerViewChat;

    @BindView(R.id.main_container)
    LinearLayout main_container;

    @BindView(R.id.loading_container)
    LinearLayout loading_container;

    @BindView(R.id.loading_PB)
    ProgressBar loading_PB;

    @BindView(R.id.status_TV)
    TextView status_TV;

    @BindView(R.id.socket_WV)
    WebView socket_WV;

    @BindView(R.id.sentButton)
    ImageView sentButton;

    @BindView(R.id.editTextChat)
    EditText editTextChat;

    ChatPresenter presenter;
    WebAppInterface webAppInterface;
    ChatAdapter mChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        toolbar = (MyToolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_black_24x24);
      //  socketController = new SocketController(this);
        init_RV();
        presenter = new ChatPresenter(this,main_container);
        presenter.setData(getIntent().getStringExtra("chat_data") , getIntent().getStringExtra("chat_id"));
    }

    void init_RV(){
        mChatAdapter = new ChatAdapter();
        mRecyclerViewChat.hasFixedSize();
        mRecyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewChat.setAdapter(mChatAdapter);
    }

    void init_WV(String chat_id){
        WebSettings webSettings = socket_WV.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheMaxSize(1024 * 8);
        webSettings.setAppCacheEnabled(true);
        webAppInterface = new WebAppInterface(this,socket_WV,chat_id);
        socket_WV.addJavascriptInterface(webAppInterface , "WebAppInterface");
        webSettings.setUseWideViewPort(false);
        socket_WV.setWebChromeClient(new WebChromeClient());
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        socket_WV.requestFocus(View.FOCUS_DOWN);
        socket_WV.loadUrl("file:///android_asset/socket_page.html");
    }



    @Override
    public void onSocketConnected() {

    }

    @Override
    public void onDataLoaded() {
        loading_container.setVisibility(View.GONE);
        loading_PB.setVisibility(View.GONE);
        startConnectingToSocket(presenter.getChat_datum().getId());
        //   socketController.initSocket(presenter.getChat_datum().getId());
    }

    void startConnectingToSocket(String chat_id){
        onLoadStatusChanged("Идет соединение ...");
        init_WV(chat_id);
    }

    @Override
    public void onLoadStatusChanged(String message) {
        loading_container.setVisibility(View.VISIBLE);
        loading_PB.setVisibility(View.VISIBLE);
        status_TV.setText(message);
    }

    @Override
    public void onDataLoadError(String reason) {
        loading_container.setVisibility(View.VISIBLE);
        loading_PB.setVisibility(View.GONE);
        status_TV.setText(reason);
        Toast.makeText(ChatActivity.this,reason,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserLoadError() {
        Toast.makeText(ChatActivity.this,"Не удалось получить данные о пользоваталях",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserDataLoaded(DialogModel dialogData) {
        mChatAdapter.setUsersData(dialogData.getUsersData());
    }

    @Override
    public void onGetMessage(List<SocketMessageModel> messages) {
        final List<SocketMessageModel> shallowCopy = messages.subList(0, messages.size());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Collections.reverse(shallowCopy);
                loading_container.setVisibility(View.GONE);
                loading_PB.setVisibility(View.GONE);
                mChatAdapter.addMessages(shallowCopy);
                mRecyclerViewChat.scrollToPosition(shallowCopy.size()-1);
                setActions();
            }
        });
    }

    void setActions(){
        sentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editTextChat.getText().toString();
                webAppInterface.sendMessage(message);
                editTextChat.getText().clear();
                hideKeyboard();
            }
        });
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onEditButtonClicked(String chat_id , String chat_name , List<String> user_ids) {
        Intent intent = new Intent(this, SearchDoctorActivity.class);
        intent.putExtra("is_edit",true);
        intent.putExtra("type","group");
        intent.putExtra("group_name",chat_name);
        String[] users = new String[user_ids.size()];
        users = user_ids.toArray(users);
        intent.putExtra("checked_users",users );
        startActivityForResult(intent,178);
    }

    @Override
    public void onMessageReceived(SocketMessageModel message) {
        final SocketMessageModel tmp_message = message;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mChatAdapter.addMessage(tmp_message);
                mRecyclerViewChat.scrollToPosition(mChatAdapter.getItemCount()-1);
            }
        });

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 178 && data!=null) {
            if(resultCode == Activity.RESULT_OK){
                String group_name = data.getStringExtra("group_name");
                String[] result=data.getStringArrayExtra("experts_id");
                presenter.editGroup(group_name,result);
                // Toast.makeText(DialogsActivity.this,result,Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

//    @Override
//    public void onOpen(String json) {
//        Log.d("open", json);
//    }
//
//    @Override
//    public void onMessage(String json) {
//        //To do currently only json string is supported...
//        Log.d("success", json);
//    }
//
//    @Override
//    public void onError(String json) {
//        Log.d("error", json);
//    }
//
//    @Override
//    public void onClose(String json) {
//        Log.d("close", json);
//    }


    @Override
    protected void onStop() {
        super.onStop();
    }

//    @Override
//    protected void onStart() {
//        //To do Here connect sockjs again...
//        //sockJsClient.connectSockJs("url", "accessKey");
//    }
}
