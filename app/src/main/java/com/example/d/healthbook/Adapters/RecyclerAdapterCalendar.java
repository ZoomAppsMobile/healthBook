package com.example.d.healthbook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.d.healthbook.API.App;
import com.example.d.healthbook.CalendarBekarysa.CalendarActivity;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.Event_Data;
import com.example.d.healthbook.R;
import com.example.d.healthbook.ViewHolders.ViewHolder1;
import com.example.d.healthbook.ViewHolders.ViewHolder2;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pc on 23.06.2017.
 */

public class RecyclerAdapterCalendar extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG1 = "MY LIST ADAPTER";
    private Context context;
    private List<Event_Data> documents;
    private static final int TYPE_FOOTER = 2;
    private final int ROW_GREEN = 0, ROW_GRAY = 1;

    public RecyclerAdapterCalendar(List<Event_Data> documentsUrl, Context context) {
        documents = documentsUrl;
        this.context = context;

    }

    public RecyclerAdapterCalendar() {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ROW_GREEN:
                View v1 = inflater.inflate(R.layout.row_item_calendar, parent, false);
                viewHolder = new ViewHolder1(v1);
                break;
            case ROW_GRAY:
                View v2 = inflater.inflate(R.layout.row_item_calendar_gray, parent, false);
                viewHolder = new ViewHolder2(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch (holder.getItemViewType()) {
            case ROW_GREEN:
                ViewHolder1 vh1 = (ViewHolder1) holder;
                configureViewHolder1(vh1, position);
                break;
            case ROW_GRAY:
                ViewHolder2 vh2 = (ViewHolder2) holder;
                configureViewHolder2(vh2, position);
                break;


        }


    }

    private void configureViewHolder2(ViewHolder2 vh2, int position) {
        vh2.getEventCalendargray().setText(documents.get(position).getTitle());
        vh2.getDescription_event().setText(documents.get(position).getDescription());
        vh2.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configureViewHolder1(ViewHolder1 vh1, int position) {
        final Event_Data event = (Event_Data) documents.get(position);
        if (event != null) {
            vh1.getTitleGarmones().setText(event.getTitle());
            vh1.getTimeCheckGarmones().setText(event.getTimes());
            vh1.getEventCalendar().setText(event.getBody());
            vh1.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 deleteWomanTask(event.getId());
                }
            });
            vh1.imgUpd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    ((CalendarActivity)context).updateDrugs(event);
                }
            });

//            vh1.getEventCalendar().setText(calendarModelRecycler.getComments());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (documents.get(position).getType()==2) {
            return ROW_GREEN;
        } else if (documents.get(position).getType()==1) {
            return ROW_GRAY;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }


    public void deleteWomanTask(String id) {



        Call<Void> deleteRequest = App.getApi().deleteWomanTask(id, GlobalVariables.user_auth_token);
        deleteRequest.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // use response.code, response.headers, etc.
                Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, CalendarActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // handle failure
                Toast.makeText(context,  "An error occurred during networking", Toast.LENGTH_SHORT).show();
            }
        });






    }
}
