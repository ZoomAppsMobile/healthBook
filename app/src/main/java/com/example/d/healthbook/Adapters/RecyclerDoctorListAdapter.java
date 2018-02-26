package com.example.d.healthbook.Adapters;

import android.app.Activity;
import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.example.d.healthbook.Activities.DoctorActivityInfo;
import com.example.d.healthbook.Activities.SearchDoctorActivity;
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

public class RecyclerDoctorListAdapter extends RecyclerView.Adapter<RecyclerDoctorListAdapter.ViewHolder> {
    private static final String TAG1 = "MY LIST ADAPTER";
    private Activity context;
    private List<Document> documents;
    private static final int TYPE_FOOTER = 2;
    private boolean is_selection = false;
    private String type = "once";
    public RecyclerDoctorListAdapter(List<Document> documentsUrl, Activity context) {
        documents = documentsUrl;
        this.context = context;

    }

    public RecyclerDoctorListAdapter(List<Document> documentsUrl, Activity context , boolean is_selection , String type) {
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
            switch (exp) {
                case "1":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "год");
                    break;
                case "2":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                case "3":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                case "4":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                case "5":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "лет");
                    break;
                case "21":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "год");
                    break;
                case "22":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                case "23":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                case "24":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                case "31":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "год");
                    break;
                case "32":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                case "33":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                case "34":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                case "41":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "год");
                    break;
                case "42":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                case "43":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                case "44":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                case "51":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "год");
                    break;
                case "52":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                case "53":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                case "54":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                case "61":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "год");
                    break;
                case "62":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                case "63":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                case "64":
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "года");
                    break;
                default: {
                    experianceOfDoctor = String.format("Опыт работы: %s %s", exp, "лет");
                }
            }
        }
        holder.experienceDoctorList.setText(experianceOfDoctor);
        holder.workPlaceDoctorTVList.setText(documents.get(position).getClinicName());
        if(type.equals("multi")) {
            if(((SearchDoctorActivity)context).isChecked(documents.get(position).getId())){
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
                if(is_selection){

                    if(type.equals("once")) {
                        HashMap<String,String> data = new HashMap<String, String>();
                        data.put("id_doc", String.valueOf(documents.get(position).getUserId()));
                        data.put("name",String.format("%s %s",
                                documents.get(position).getName(),
                                documents.get(position).getSurname()));
                        ((AdapterSelection) context).onSelected(data);
                    }
                    else if(type.equals("multi")) {
                        String data = String.valueOf(documents.get(position).getUserId());
                        if(!((SearchDoctorActivity)context).isChecked(String.valueOf(documents.get(position).getUserId()))){
                            ((AdapterSelection) context).onChecked(data);
                            holder.main_CV.setCardBackgroundColor(MainController.getColor(context,R.color.bpGreen_Transparent));
                        }
                        else{
                            ((AdapterSelection) context).onUnChecked(data);
                            holder.main_CV.setCardBackgroundColor(MainController.getColor(context,R.color.white));
                        }
                    }
                }
                else {

                    Intent intent = new Intent(context, DoctorActivityInfo.class);
                    intent.putExtra("idDoctor", documents.get(position).getId());
                    Toast.makeText(context, "ssdsdsdsd", Toast.LENGTH_SHORT).show();

                    intent.putExtra("imageDoc", String.valueOf(documents.get(position).getPhoto()));
                    intent.putExtra("nameDoc", String.valueOf(documents.get(position).getName()));
                    intent.putExtra("surNameDoc", String.valueOf(documents.get(position).getSurname()));
                    intent.putExtra("company_name", String.valueOf(documents.get(position).getClinicName()));

                    context.startActivity(intent);
                    context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);;

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


