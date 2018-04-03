package com.example.d.healthbook.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.d.healthbook.API.API_Controller;
import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Activities.ClinicActivityInfo;
import com.example.d.healthbook.Activities.DoctorActivityInfo;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.Clinic;
import com.example.d.healthbook.Models.Document;
import com.example.d.healthbook.Models.ResponseDoctorList;
import com.example.d.healthbook.R;
import com.example.d.healthbook.Utils.GlideClient;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by D on 31.05.2017.
 */

public class RecyclerСlinicListAdapter extends RecyclerView.Adapter<RecyclerСlinicListAdapter.ViewHolder> {
    private static final String TAG1 = "MY LIST ADAPTER";
    private Activity context;
    private List<Clinic> documents;
    ResponseDoctorList responseDoctorList;
    private static final int TYPE_FOOTER = 2;


    public RecyclerСlinicListAdapter(List<Clinic> documentsUrl, Activity context) {
        documents = documentsUrl;
        this.context = context;

    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_clinic_list, parent, false);

        return new ViewHolder((View) view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

       GlideClient.downloadImage(context, documents.get(position).getPhoto(),holder.profileIVList);

        holder.title_info_clinicTV.setText( documents.get(position).getName());
        holder.adress_clinic_TV.setText(documents.get(position).getAddress());
        holder.description_of_clinic.setText((CharSequence) documents.get(position).getInfo().toString());
        holder.clinic_list_star_TV.setText(documents.get(position).getRate().toString());
        holder.clinic_list_man_TV.setText(documents.get(position).getExpertCount().toString());
        holder.clinic_list_message_TV.setText(documents.get(position).getMentionCount().toString());



//        App.getApi().seeDoctorListFilter2("1", "2").enqueue(new Callback<ResponseDoctorList>() {
//            @Override
//            public void onResponse(Call<ResponseDoctorList> call, Response<ResponseDoctorList> response) {
//                if(response.body() != null){
//                    responseDoctorList = response.body();
//                    holder.clinic_list_man_TV.setText(responseDoctorList.getPages().toString());
//                    Log.e("clinic_list_man_TV", responseDoctorList.getNumfound().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseDoctorList> call, Throwable throwable) {
//
//            }
//        });





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClinicActivityInfo.class);
                intent.putExtra("idClinic", (String) documents.get(position).getId());
               // GlobalVariables.mentions = documents.get(position).;
//                intent.putExtra("imageDoc",String.valueOf(documents.get(position).getPhoto()));
//                intent.putExtra("nameDoc",String.valueOf(documents.get(position).getName()));
//                intent.putExtra("surNameDoc",String.valueOf(documents.get(position).getSurname()));

                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;

            }
        });
        holder.cliinic_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClinicActivityInfo.class);
                intent.putExtra("idClinic", (String) documents.get(position).getId());
                intent.putExtra("current_item", "user");
               // GlobalVariables.mentions = documents.get(position).getMentions();
//                intent.putExtra("imageDoc",String.valueOf(documents.get(position).getPhoto()));
//                intent.putExtra("nameDoc",String.valueOf(documents.get(position).getName()));
//                intent.putExtra("surNameDoc",String.valueOf(documents.get(position).getSurname()));

                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;
            }
        });
        holder.cliinic_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClinicActivityInfo.class);
                intent.putExtra("idClinic", (String) documents.get(position).getId());
                intent.putExtra("current_item", "comments");
               // GlobalVariables.mentions = documents.get(position).getMentions();
//                intent.putExtra("imageDoc",String.valueOf(documents.get(position).getPhoto()));
//                intent.putExtra("nameDoc",String.valueOf(documents.get(position).getName()));
//                intent.putExtra("surNameDoc",String.valueOf(documents.get(position).getSurname()));

                context.startActivity(intent);

                context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;


            }
        });


    }

    @Override
    public int getItemCount() {
        return documents.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView profileIVList;
        LinearLayout                      cliinic_users;
        LinearLayout                      cliinic_comments;

        TextView title_info_clinicTV;
        TextView adress_clinic_TV;
        TextView description_of_clinic;
        TextView clinic_list_star_TV,clinic_list_man_TV,clinic_list_message_TV;


        public ViewHolder(final View v) {
            super(v);
            profileIVList = (RoundedImageView) v.findViewById(R.id.clinic_Image_list);
            title_info_clinicTV = (TextView) v.findViewById(R.id.title_info_clinicTV);
            clinic_list_star_TV = v.findViewById(R.id.clinic_list_star_TV);
            adress_clinic_TV = (TextView) v.findViewById(R.id.adress_clinic_TV);
            description_of_clinic = (TextView) v.findViewById(R.id.description_of_clinic);
            cliinic_users = (LinearLayout) v.findViewById(R.id.cliinic_users);
            cliinic_comments = (LinearLayout) v.findViewById(R.id.cliinic_comments);
            clinic_list_man_TV = v.findViewById(R.id.clinic_list_man_TV);
            clinic_list_message_TV = v.findViewById(R.id.clinic_list_message_TV);
        }
    }

}


