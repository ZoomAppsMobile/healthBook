package com.example.d.healthbook.Controller;

import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.SocketMessageModel;
import com.example.d.healthbook.Models.SocketResponseModel;
import com.example.d.healthbook.View.ChatView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Created by User on 08.09.2017.
 */

public class WebAppInterface {
    private Activity mContext;
    private WebView webView;
    private String chat_id;

    private Boolean isUserAuthed=false;
    private String authQuery;
    /**
     * Instantiate the interface and set the context
     */

    private static final String  TAG = "WebAppInterface";

    public WebAppInterface(Activity c , WebView webView , String chat_id) {
        mContext = c;
        this.webView = webView;
        this.chat_id = chat_id;
        prepareUserAuthData();
    }

    void prepareUserAuthData(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type","auth");
        jsonObject.addProperty("token",GlobalVariables.user_auth_token);
        jsonObject.addProperty("user_id", GlobalVariables.user_id);
        jsonObject.addProperty("role","user");
        authQuery = jsonObject.toString();
    }

    public void getMessages(String chat_id){
        //{"type":"getmsg","chat_id":"599a84513d1063042985da46","user_id":37575,"min_id":0,"limit":100}
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type","getmsg");
        jsonObject.addProperty("chat_id",chat_id);
        jsonObject.addProperty("user_id", GlobalVariables.user_id);
        jsonObject.addProperty("min_id",0);
        jsonObject.addProperty("limit",100);
        String getMsgQuery = jsonObject.toString();
        javaFnCall(getMsgQuery);
    }
    public void getMessages(){
        if(!isUserAuthed)
            return;
        //{"type":"getmsg","chat_id":"599a84513d1063042985da46","user_id":37575,"min_id":0,"limit":100}
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type","getmsg");
        jsonObject.addProperty("chat_id",chat_id);
        jsonObject.addProperty("user_id", GlobalVariables.user_id);
        jsonObject.addProperty("min_id",0);
        jsonObject.addProperty("limit",100);
        String getMsgQuery = jsonObject.toString();
        javaFnCall(getMsgQuery);
    }

    public void sendMessage(String message){
        if(!isUserAuthed)
            return;
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type","msg");
        jsonObject.addProperty("chat_id",chat_id);
        jsonObject.addProperty("user_id", GlobalVariables.user_id);
        jsonObject.addProperty("from", GlobalVariables.user_id);
        jsonObject.addProperty("message",message);
        String getMsgQuery = jsonObject.toString();
        javaFnCall(getMsgQuery);
    }
    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void onMessage(final String message) {
        Log.e(TAG,message);
        if(message!=null && message.contains("{") && message.contains("}") ){
            SocketResponseModel socketResponseModel = new Gson().fromJson(message,SocketResponseModel.class);
            processMessage(socketResponseModel);
        }
    }

    private void processMessage( SocketResponseModel socketResponseModel){
        if(socketResponseModel==null)
            return;
        switch (socketResponseModel.getType()) {
            case "auth":
                isUserAuthed = socketResponseModel.getResult().equals("success");
                getMessages();
                break;
            case "getunread":
                processUnreadMessage();
                break;
            case "getmsg":
                ((ChatView)mContext).onGetMessage(socketResponseModel.getMessages());
                break;
            case "msg":
                onMessageRecievied(socketResponseModel);
                break;
        }
    }

    void onMessageRecievied(SocketResponseModel socketResponseModel){
        SocketMessageModel socketMessageModel = new SocketMessageModel();
        socketMessageModel.setChatId(socketResponseModel.getChatId());
        socketMessageModel.setCreatedAt(socketResponseModel.getCreatedAt());
        socketMessageModel.setFromUser(socketResponseModel.getFromUser());
      //  socketMessageModel.setId(socketResponseModel.getChatId());
      // socketMessageModel.setRead();
        socketMessageModel.setText(socketResponseModel.getText());
        socketMessageModel.setToUsers(socketResponseModel.getToUsers());
       // socketMessageModel.setUpdatedAt(socketResponseModel.get);
        ((ChatView)mContext).onMessageReceived(socketMessageModel);
    }

    private void processUnreadMessage(){

    }

    @JavascriptInterface
    public void onOpen() {
        Log.e(TAG,"OnOpen");
        if(!isUserAuthed && authQuery!=null){
            javaFnCall(authQuery);
        }
    }

    @JavascriptInterface
    public void onClose() {
        Log.e(TAG,"OnClose");
    }

    public void javaFnCall(String jsString) {

        final String webUrl = "javascript:sendMessage('"+jsString+"')";
        // Add this to avoid android.view.windowmanager$badtokenexception unable to add window
        if(!mContext.isFinishing())
            // loadurl on UI main thread
            mContext.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    webView.loadUrl(webUrl);
                }
            });
    }

}
