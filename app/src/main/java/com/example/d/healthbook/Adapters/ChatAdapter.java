package com.example.d.healthbook.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.Chat.AllParticipant;
import com.example.d.healthbook.Models.ResponseGetUserData;
import com.example.d.healthbook.Models.SocketMessageModel;
import com.example.d.healthbook.R;
import com.google.gson.internal.bind.util.ISO8601Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 11.09.2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<SocketMessageModel> mDataSet ;

    private static final int MY_MESSAGE = 1;
    private static final int NOT_MY_MESSAGE = 2;

    public ChatAdapter( ){
        mDataSet = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case NOT_MY_MESSAGE:
                View v1 = inflater.inflate(R.layout.row_chat_item_come, parent, false);
                viewHolder = new OtherMessageViewHolder(v1);
                break;
            case MY_MESSAGE:
                View v2 = inflater.inflate(R.layout.row_chat_item_sent, parent, false);
                viewHolder = new MyMessageViewHolder(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case MY_MESSAGE:{
                ((MyMessageViewHolder) holder).bind(mDataSet.get(position));
                break;
            }
            case NOT_MY_MESSAGE:{
                ((OtherMessageViewHolder) holder).bind(mDataSet.get(position));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataSet.get(position).getFromUser().equals(GlobalVariables.user_id)) {
            return MY_MESSAGE;
        }
        else {
            return NOT_MY_MESSAGE;
        }
    }

    public void addMessage(SocketMessageModel socketMessageModel){
        mDataSet.add(socketMessageModel);
        notifyDataSetChanged();
    }

    public void addMessages(List<SocketMessageModel> socketMessageModelList){
        mDataSet.clear();
        mDataSet.addAll(socketMessageModelList);
        notifyDataSetChanged();
    }
    private List<ResponseGetUserData> usersList;
    public void setUsersData(List<ResponseGetUserData> usersList){
        this.usersList = usersList;
    }


    String getUserNameById(Integer id){
        String userName = "";
        for (ResponseGetUserData tmp:usersList) {
            if(tmp.getId().equals(id))
                userName = String.format("%s %s",tmp.getName(),tmp.getSurname());
        }
        return userName;
    }

    String getNormalTime(String raw_date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS",
                Locale.ENGLISH);
        try {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss",
                    Locale.ENGLISH);
            Date date = sdf.parse(raw_date);
            return  df.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return raw_date;
    }

    class MyMessageViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtMessageSent)
        TextView txtMessageTV;
        @BindView(R.id.createdTimeMessageSent)
        TextView sendTimeTV;

        private Context mContext;
            public MyMessageViewHolder(View itemView) {
                super(itemView);
                mContext = itemView.getContext();
                ButterKnife.bind(this,itemView);
            }

            public void bind(SocketMessageModel socketMessageModel){
                txtMessageTV.setText(socketMessageModel.getText());
                sendTimeTV.setText(getNormalTime(socketMessageModel.getCreatedAt()));
            }
    }

    class OtherMessageViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.user_name_TV)
        TextView userNameTV;
        @BindView(R.id.txtMessageCome)
        TextView txtMessageTV;
        @BindView(R.id.createdTimeMessageCome)
        TextView sendTimeTV;

        private Context mContext;
        public OtherMessageViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            ButterKnife.bind(this,itemView);
        }

        public void bind(SocketMessageModel socketMessageModel){
            userNameTV.setText(getUserNameById(socketMessageModel.getFromUser()));
            txtMessageTV.setText(socketMessageModel.getText());
            sendTimeTV.setText(getNormalTime(socketMessageModel.getCreatedAt()));
        }
    }
}
