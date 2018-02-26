package com.example.d.healthbook.Controller;

import android.content.Context;
import android.util.Log;

import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.google.gson.JsonObject;
import com.vishu.sockjsandroidclient.SockJsClient;
import com.vishu.sockjsandroidclient.SockJsClientListener;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by User on 08.09.2017.
 */

public class SocketController{

    public SocketController(Context ctx) {
        sockJsClient =new SockJsClient(ctx);
        sockJsClient.connectSockJs("healthbook.kz/chat_socket");
    }
    private String chat_id;

    private SockJsClient sockJsClient;

    public void initSocket(String chat_id){
        this.chat_id=chat_id;
//chatMsg
    }




    void getMessages(){
        JsonObject msg = new JsonObject();
        msg.addProperty("type","getmsg");
        msg.addProperty("chat_id",chat_id);
        msg.addProperty("user_id", GlobalVariables.user_id);
        msg.addProperty("min_id","0");
        msg.addProperty("limit","100");
       // sockJsClient.(msg.toString());
    }

    public void disconnect(){
        sockJsClient.disconnectSockJs();
    }
}
