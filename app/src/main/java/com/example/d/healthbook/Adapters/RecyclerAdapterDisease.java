package com.example.d.healthbook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.d.healthbook.Activities.DiseaseCategoryActivity;
import com.example.d.healthbook.Activities.DrugsCategoryActivity;
import com.example.d.healthbook.Models.DocumentProtocol;
import com.example.d.healthbook.R;

import java.util.List;

/**
 * Created by D on 16.06.2017.
 */

public class RecyclerAdapterDisease extends RecyclerView.Adapter<RecyclerAdapterDisease.ViewHolder> {

    private Context context;
    private List<DocumentProtocol> documentProtocols;


    public RecyclerAdapterDisease(List<DocumentProtocol> documentProtocols, Context context) {
        this.documentProtocols = documentProtocols;
        this.context = context;

    }


    @Override
    public RecyclerAdapterDisease.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_protocol, parent, false);

        return new RecyclerAdapterDisease.ViewHolder((View) view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterDisease.ViewHolder holder, final int position) {
        holder.protocolTV.setText(documentProtocols.get(position).getNameRu());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DiseaseCategoryActivity.class);
                intent.putExtra("id", documentProtocols.get(position).getId());
                context.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return documentProtocols.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView protocolTV;



        public ViewHolder(final View v) {
            super(v);

            protocolTV = (TextView) v.findViewById(R.id.protocolTV);

        }
    }

}
