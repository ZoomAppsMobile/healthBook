package com.example.d.healthbook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.d.healthbook.Activities.DrugsCategoryActivity;
import com.example.d.healthbook.Models.DocumentMyFamily;
import com.example.d.healthbook.Models.ResponseMyFamilyMembers;
import com.example.d.healthbook.R;
import com.example.d.healthbook.View.AdapterInteraction;

import java.util.List;

/**
 * Created by D on 05.07.2017.
 */

public class RecyclerAdapterMyFamilyMembers extends RecyclerView.Adapter<RecyclerAdapterMyFamilyMembers.ViewHolder> {

    private Context context;
    private List<DocumentMyFamily> myFamilyMemberses;
    private AdapterInteraction viewInteraction;

    public RecyclerAdapterMyFamilyMembers(List<DocumentMyFamily> documentProtocols, Context context , AdapterInteraction viewInteraction) {
        this.myFamilyMemberses = documentProtocols;
        this.context = context;
        this.viewInteraction = viewInteraction;
    }


    @Override
    public RecyclerAdapterMyFamilyMembers.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_my_family_members, parent, false);

        return new RecyclerAdapterMyFamilyMembers.ViewHolder((View) view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterMyFamilyMembers.ViewHolder holder, final int position) {
        holder.name_surname_memberOfFamily.setText(myFamilyMemberses.get(position).getName());
        holder.imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewInteraction.onItemClick(v,myFamilyMemberses.get(position));

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewInteraction.onItemClick(view,myFamilyMemberses.get(position));
            }
        });

    }


    @Override
    public int getItemCount() {
        return myFamilyMemberses.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name_surname_memberOfFamily;
        LinearLayout linearLayout;
        ImageButton imgBtn;

        public ViewHolder(final View v) {
            super(v);
            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Myriad Pro Regular.ttf");

            linearLayout = (LinearLayout) v.findViewById(R.id.line1);
            imgBtn = (ImageButton) v.findViewById(R.id.imageButton);
            name_surname_memberOfFamily = (TextView) v.findViewById(R.id.name_surname_memberOfFamily);
            name_surname_memberOfFamily.setTypeface(font);

        }
    }

}

