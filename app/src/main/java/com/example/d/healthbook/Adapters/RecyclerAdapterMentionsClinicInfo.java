package com.example.d.healthbook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Activities.DoctorActivityInfo;
import com.example.d.healthbook.Activities.MainPageActivity;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.Models.Document;
import com.example.d.healthbook.Models.Mention;
import com.example.d.healthbook.Models.MentionsModel;
import com.example.d.healthbook.Models.ResponseEditUserProfile;
import com.example.d.healthbook.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pc on 09.06.2017.
 */

public class RecyclerAdapterMentionsClinicInfo extends RecyclerView.Adapter<RecyclerAdapterMentionsClinicInfo.ViewHolder> {

    private Context context;
    private MentionsModel mention;
    private ResponseEditUserProfile responseEditUserProfile;


    public RecyclerAdapterMentionsClinicInfo(MentionsModel mentions, Context context) {
        mention = mentions;
        this.context = context;

    }

    public RecyclerAdapterMentionsClinicInfo(ResponseEditUserProfile responseEditUserProfile) {
        this.responseEditUserProfile = responseEditUserProfile;
    }

    @Override
    public RecyclerAdapterMentionsClinicInfo.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_mentions_clinic_info, parent, false);





        return new RecyclerAdapterMentionsClinicInfo.ViewHolder((View) view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //MainController.setImageToViewById(context, mention.getDocuments().get(position).get, holder.profileMentionIV);
//        holder.mention_info.setText(mention.get(position).getText());
        holder.mention_info.setText(mention.getDocuments().get(position).getText().toString());
        holder.time_Mention_public_TV.setText(mention.getDocuments().get(position).getCreatedTime().toString());
        holder.rating_qu.setRating(Float.parseFloat(mention.getDocuments().get(position).getPerformanceMark()));
        holder.rating_md.setRating(Float.parseFloat(mention.getDocuments().get(position).getEfficiencyMark()));
        holder.rating_pr.setRating(Float.parseFloat(mention.getDocuments().get(position).getRecommendationMark()));
        App.getApi().getUserData(mention.getDocuments().get(position).getUserId()).enqueue(new Callback<ResponseEditUserProfile>() {
            @Override
            public void onResponse(Call<ResponseEditUserProfile> call, Response<ResponseEditUserProfile> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        holder.nameProfileListMention.setText(response.body().getName() + " " + response.body().getSurname());
                    }
                }else{
                    holder.nameProfileListMention.setText("Анонимный");
                }
            }

            @Override
            public void onFailure(Call<ResponseEditUserProfile> call, Throwable throwable) {
                holder.nameProfileListMention.setText("Анонимный");
            }
        });

    }


    @Override
    public int getItemCount() {
        try {
            return mention.getDocuments().size();
        }
        catch (Exception e){
            return 0;
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileMentionIV;
        TextView nameProfileListMention;
        TextView time_Mention_public_TV;
        TextView mention_info;
        RatingBar rating_qu, rating_md, rating_pr;

        public ViewHolder(final View v) {
            super(v);
            //profileMentionIV = (ImageView) v.findViewById(R.id.profileMentionIV);
            nameProfileListMention = (TextView) v.findViewById(R.id.nameProfileListMention);
            time_Mention_public_TV = (TextView) v.findViewById(R.id.time_Mention_public_TV);
            mention_info = (TextView) v.findViewById(R.id.mention_info);
            rating_qu = v.findViewById(R.id.rating_qu);
            rating_md = v.findViewById(R.id.rating_md);
            rating_pr = v.findViewById(R.id.rating_pr);
        }
    }

}