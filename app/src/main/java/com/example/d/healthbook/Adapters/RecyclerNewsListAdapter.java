package com.example.d.healthbook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.d.healthbook.Activities.NewsActivityInfo;
import com.example.d.healthbook.Controller.DateController;
import com.example.d.healthbook.Models.Document;
import com.example.d.healthbook.Models.NewsBodyModel;
import com.example.d.healthbook.R;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by D on 14.06.2017.
 */

public class RecyclerNewsListAdapter extends RecyclerView.Adapter<RecyclerNewsListAdapter.ViewHolder> {
    private Context context;
    private List<Document> documents;


    public RecyclerNewsListAdapter(List<Document> documentsUrl, Context context) {
        documents = documentsUrl;
        this.context = context;

    }

    @Override
    public RecyclerNewsListAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_news_final, parent, false);

        return new RecyclerNewsListAdapter.ViewHolder((View) view);
    }

    @Override
    public void onBindViewHolder(RecyclerNewsListAdapter.ViewHolder holder, final int position) {
        Glide.with(context).load(documents.get(position).getImage()).centerCrop().into(holder.imageNews);
        holder.titleNews.setText(documents.get(position).getTitle());
        holder.categoryName.setText(documents.get(position).getCategoryName());
        holder.previewNews.setText(documents.get(position).getPreview());
        String show_date = DateController.convertFormat(DateController.default_format_with_time ,
                "MMM, yyyy HH:mm",documents.get(position).getCreatedTime());
        holder.createdTimeNews.setText(show_date);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String raw_body = documents.get(position).getBody();
                NewsBodyModel.RootObject bodyData = new Gson().fromJson(raw_body, NewsBodyModel.RootObject.class);
                Intent intent = new Intent(context, NewsActivityInfo.class);
                intent.putExtra("titleNews", documents.get(position).getTitle());
                intent.putExtra("categoryName", documents.get(position).getCategoryName());
                if(bodyData!=null)
                    intent.putExtra("body", bodyData.getData().get(0).getData().getText());
                intent.putExtra("image", documents.get(position).getImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageNews;
        TextView titleNews;
        TextView previewNews;
        TextView categoryName;
        TextView createdTimeNews;


        public ViewHolder(final View v) {
            super(v);

            imageNews = (ImageView) v.findViewById(R.id.imageNews);
            titleNews = (TextView) v.findViewById(R.id.titleNews);
            categoryName = (TextView) v.findViewById(R.id.categoryName);
            previewNews = (TextView) v.findViewById(R.id.previewNews);
            createdTimeNews = (TextView) v.findViewById(R.id.createdTimeNews);
        }
    }
}
