package com.example.d.healthbook.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.Activities.DoctorActivityInfo;
import com.example.d.healthbook.Activities.SearchDoctorActivity2;
import com.example.d.healthbook.Controller.DateController;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.Models.Document;
import com.example.d.healthbook.R;
import com.example.d.healthbook.Utils.GlideClient;
import com.example.d.healthbook.View.AdapterSelection;

import java.util.HashMap;
import java.util.List;

/**
 * Created by D on 07.06.2017.
 */

public class RecyclerDoctorListAdapter2 extends RecyclerView.Adapter<RecyclerDoctorListAdapter2.ViewHolder> {
    private static final String TAG1 = "MY LIST ADAPTER";
    private Activity context;
    private List<Document> documents;
    private static final int TYPE_FOOTER = 2;
    private boolean is_selection = false;
    private String type = "once";
    int typeN ;
    public RecyclerDoctorListAdapter2(List<Document> documentsUrl, Activity context,int typeN) {
        documents = documentsUrl;
        this.context = context;
        this.typeN=typeN;

    }

    public RecyclerDoctorListAdapter2(List<Document> documentsUrl, Activity context , boolean is_selection , String type) {
        documents = documentsUrl;
        this.context = context;
        this.is_selection = is_selection;
        this.type = type.equals("group") ? "multi" : "once";
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_doctor_list2, parent, false);

        return new ViewHolder((View) view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        GlideClient.downloadImage(context, documents.get(position).getPhoto(),holder.profileIVList);
        holder.nameProfileList.setText(documents.get(position).getName() + " " + documents.get(position).getSurname());
        holder.specialityDocTVList.setText((CharSequence) documents.get(position).getSpecialityName());
        String experianceOfDoctor = "Не указано";
        String exp = DateController.getDateDifferenceWithCondition(documents.get(position).getExperience(),
                DateController.YEAR);
        if(exp!=null){
            experianceOfDoctor = String.format("%s %s" , exp, "лет");
        }
        holder.experienceDoctorList.setText(experianceOfDoctor);
        holder.workPlaceDoctorTVList.setText(documents.get(position).getClinicName());
        if(type.equals("multi")) {
            if(((SearchDoctorActivity2)context).isChecked(documents.get(position).getId())){
                holder.main_CV.setCardBackgroundColor(MainController.getColor(context,R.color.bpGreen_Transparent));
            }
            else{
                holder.main_CV.setCardBackgroundColor(MainController.getColor(context,R.color.white));
            }
        }
        if(documents.get(position).getClinicPrice()!=null) {
            double tmpval = (documents.get(position).getClinicPrice());
            int wholepart = (int)tmpval;
            holder.priceDoctorTVList.setText(String.format("%d тг"  ,wholepart));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(typeN==0) {
                        HashMap<String,String> data = new HashMap<String, String>();
                        data.put("id_doc", String.valueOf(documents.get(position).getUserId()));
                        data.put("name",String.format("%s %s",
                                documents.get(position).getName(),
                                documents.get(position).getSurname()));
                        ((AdapterSelection) context).onSelected(data);
                    }
                    else {

                        String data = String.valueOf(documents.get(position).getUserId());
                        if(!((SearchDoctorActivity2)context).isChecked(String.valueOf(documents.get(position).getUserId()))){
                            ((AdapterSelection) context).onChecked(data);
                            holder.main_CV.setCardBackgroundColor(MainController.getColor(context,R.color.bpGreen_Transparent));
                        }
                        else{
                            ((AdapterSelection) context).onUnChecked(data);
                            holder.main_CV.setCardBackgroundColor(MainController.getColor(context,R.color.white));
                        }
                    }



            }
        });


    }

    @Override
    public int getItemCount() {
        return documents.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView main_CV;
        LinearLayout avatar_container;
        ImageView profileIVList;
        TextView nameProfileList;

        TextView experienceDoctorList;
        TextView specialityDocTVList;
        TextView workPlaceDoctorTVList;
        TextView priceDoctorTVList;

        public ViewHolder(final View v) {
            super(v);
            main_CV = (CardView) v.findViewById(R.id.main_CV);
            avatar_container = (LinearLayout) v.findViewById(R.id.avatar_container);
            profileIVList = (ImageView) v.findViewById(R.id.profileAvaIVList);
            nameProfileList = (TextView) v.findViewById(R.id.nameProfileList);

            experienceDoctorList = (TextView) v.findViewById(R.id.experienceDoctorList);
            specialityDocTVList = (TextView) v.findViewById(R.id.specialityDocList);
            workPlaceDoctorTVList =(TextView) v.findViewById(R.id.workPlaceDoctorTVList);
            priceDoctorTVList = (TextView) v.findViewById(R.id.priceDoctorTVList);
        }
    }

}


