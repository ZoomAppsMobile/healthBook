package com.example.d.healthbook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.Activities.DoctorActivityInfo;
import com.example.d.healthbook.Activities.SearchDoctorActivity;
import com.example.d.healthbook.Controller.DateController;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.Models.ResponseAllSubscriptionsToDoctor;
import com.example.d.healthbook.R;
import com.example.d.healthbook.View.AdapterSelection;

import java.util.HashMap;
import java.util.List;

/**
 * Created by D on 03.07.2017.
 */

public class RecyclerAdapterAllSubscriptionToDoc extends RecyclerView.Adapter<RecyclerAdapterAllSubscriptionToDoc.ViewHolder> {

    private Context context;
    private boolean is_selection = false;
    private String type = "once";
    private List<ResponseAllSubscriptionsToDoctor> responseAllSubscriptionsToDoctors;


    public RecyclerAdapterAllSubscriptionToDoc(List<ResponseAllSubscriptionsToDoctor> subscriptions, boolean is_selection, Context context) {
        responseAllSubscriptionsToDoctors = subscriptions;
        this.is_selection = is_selection;
        this.context = context;
        this.type = type.equals("group") ? "multi" : "once";

    }


    @Override
    public RecyclerAdapterAllSubscriptionToDoc.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_all_subscription_to_doctors, parent, false);

        return new RecyclerAdapterAllSubscriptionToDoc.ViewHolder((View) view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterAllSubscriptionToDoc.ViewHolder holder, final int position) {
        MainController.setImageToViewById(context, responseAllSubscriptionsToDoctors.get(position).getPhoto(), holder.imageDocSubscription);
        holder.nameSurnameDocSubscription.setText(
                responseAllSubscriptionsToDoctors.get(position).getName() + " " +
                        MainController.stringChecker(responseAllSubscriptionsToDoctors.get(position).getSurname()));
        String experianceOfDoctor = "Не указано";
        String exp = DateController.getDateDifferenceWithCondition(responseAllSubscriptionsToDoctors.get(position).getExperience(),
                DateController.YEAR);
        if (exp != null) {
            switch (exp) {
                case "1":
                    experianceOfDoctor = exp + " год";
                    break;
                case "2":
                    experianceOfDoctor = exp + " года";
                    break;
                case "3":
                    experianceOfDoctor =  exp + " года";
                    break;
                case "4":
                    experianceOfDoctor =  exp +  " года";
                    break;
                case "5":
                    experianceOfDoctor =  exp +  " лет";
                    break;
                case "21":
                    experianceOfDoctor = exp +  " год";
                    break;
                case "22":
                    experianceOfDoctor =  exp +  " года";
                    break;
                case "23":
                    experianceOfDoctor =  exp +  " года";
                    break;
                case "24":
                    experianceOfDoctor =  exp +  " года";
                    break;
                case "31":
                    experianceOfDoctor =  exp +  " год";;
                    break;
                case "32":
                    experianceOfDoctor = exp +  " года";
                    break;
                case "33":
                    experianceOfDoctor = exp +  " года";
                    break;
                case "34":
                    experianceOfDoctor = exp + " года";
                    break;
                case "41":
                    experianceOfDoctor = exp +  " год";
                    break;
                case "42":
                    experianceOfDoctor = exp + " года";
                    break;
                case "43":
                    experianceOfDoctor = exp +  " года";
                    break;
                case "44":
                    experianceOfDoctor = exp +  " года";
                    break;
                case "51":
                    experianceOfDoctor = exp +  " год";
                    break;
                case "52":
                    experianceOfDoctor = exp +  " года";
                    break;
                case "53":
                    experianceOfDoctor = exp +  " года";
                    break;
                case "54":
                    experianceOfDoctor = exp + " года";
                    break;
                case "61":
                    experianceOfDoctor = exp +  " год";
                    break;
                case "62":
                    experianceOfDoctor = exp +  " года";
                    break;
                case "63":
                    experianceOfDoctor = exp +  " года";
                    break;
                case "64":
                    experianceOfDoctor = exp + " года";
                    break;
                default: {
                    experianceOfDoctor = exp +  " лет";
                }
            }
        }
        holder.experienceDocSubscription.setText(experianceOfDoctor);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_selection) {

                    if (type.equals("once")) {
                        HashMap<String, String> data = new HashMap<String, String>();
                        data.put("id_doc", String.valueOf(responseAllSubscriptionsToDoctors.get(position).getUserId()));
                        data.put("name", String.format("%s %s",
                                responseAllSubscriptionsToDoctors.get(position).getName(),
                                responseAllSubscriptionsToDoctors.get(position).getSurname()));
                        ((AdapterSelection) context).onSelected(data);
                    } else if (type.equals("multi")) {
                        String data = String.valueOf(responseAllSubscriptionsToDoctors.get(position).getUserId());
                        if (!((SearchDoctorActivity) context).isChecked(String.valueOf(responseAllSubscriptionsToDoctors.get(position).getUserId()))) {
                            ((AdapterSelection) context).onChecked(data);
//                            holder.main_CV.setCardBackgroundColor(MainController.getColor(context,R.color.bpGreen_Transparent));
                        } else {
                            ((AdapterSelection) context).onUnChecked(data);
//                            holder.main_CV.setCardBackgroundColor(MainController.getColor(context,R.color.white));
                        }
                    }
                } else {

                    Intent intent = new Intent(context, DoctorActivityInfo.class);
                    MainController.setImageToViewById(context, responseAllSubscriptionsToDoctors.get(position).getPhoto(), holder.imageDocSubscription);
                    intent.putExtra("idDoctor", responseAllSubscriptionsToDoctors.get(position).getId());
                    intent.putExtra("imageDoc", responseAllSubscriptionsToDoctors.get(position).getPhoto());
                    intent.putExtra("nameDoc", String.valueOf(responseAllSubscriptionsToDoctors.get(position).getName()));
                    intent.putExtra("surNameDoc", String.valueOf(responseAllSubscriptionsToDoctors.get(position).getSurname()));
                    intent.putExtra("company_name", String.valueOf(responseAllSubscriptionsToDoctors.get(0).getSpeciality()));
                   // Toast.makeText(context, String.valueOf(responseAllSubscriptionsToDoctors.get(position).getRooms().get(0).getName()) + "", Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);

                }


            }
        });


    }


    @Override
    public int getItemCount() {
        return responseAllSubscriptionsToDoctors.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageDocSubscription;
        TextView nameSurnameDocSubscription;
        TextView experienceDocSubscription;

        public ViewHolder(final View v) {
            super(v);
            imageDocSubscription = (ImageView) v.findViewById(R.id.imageDocSubscription);
            nameSurnameDocSubscription = (TextView) v.findViewById(R.id.nameSurnameDocSubscription);
            experienceDocSubscription = (TextView) v.findViewById(R.id.experienceDocSubscription);

        }
    }

}