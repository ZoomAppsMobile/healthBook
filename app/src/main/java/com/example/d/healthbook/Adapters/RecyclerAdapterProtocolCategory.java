package com.example.d.healthbook.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.d.healthbook.Activities.ProtocolCategoryActivity;
import com.example.d.healthbook.Models.DocumentProtocolInfo;
import com.example.d.healthbook.R;

import java.util.List;

/**
 * Created by D on 16.06.2017.
 */

public class RecyclerAdapterProtocolCategory extends RecyclerView.Adapter<RecyclerAdapterProtocolCategory.ViewHolder> {

    private Context context;
    private List<DocumentProtocolInfo> documentProtocols;


    public RecyclerAdapterProtocolCategory(List<DocumentProtocolInfo> documentProtocols, Context context) {
        this.documentProtocols = documentProtocols;
        this.context = context;

    }


    @Override
    public RecyclerAdapterProtocolCategory.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_protocol_category, parent, false);


        return new RecyclerAdapterProtocolCategory.ViewHolder((View) view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.protocolTV.setText(documentProtocols.get(position).getNameRu());
        holder.itemView.setTag(documentProtocols.get(position).getProtocolRu());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String protocol = view.getTag().toString();
                Intent i=new Intent(Intent.ACTION_VIEW);
                String link= "https://cdn5.prodoc.kz/files/"+protocol+".pdf";
                //"http://docs.google.com/gview?embedded=true&url="+
                i.setData(Uri.parse(link));
                ((Activity)context).startActivity(i);
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


