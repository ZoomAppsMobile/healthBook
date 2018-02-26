package com.example.d.healthbook.CalendarBekarysa.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.CalendarBekarysa.CalendarActivity;
import com.example.d.healthbook.CalendarBekarysa.Models.CalendarModel;
import com.example.d.healthbook.CalendarBekarysa.Views.ICalendarView;
import com.example.d.healthbook.Controller.MainController;
import com.example.d.healthbook.Fragments.DialogFragmentCalendarNote;
import com.example.d.healthbook.Fragments.ViewPagerNewsFragment;
import com.example.d.healthbook.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


/**
 * Created by User on 06.07.2017.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    private List<CalendarModel> list;
    private Context context;
    private ICalendarView viewInterface;


    public CalendarModel getSelected_date() {
        return selected_date;
    }

    public void setSelected_date(CalendarModel selected_date) {
        this.selected_date = selected_date;
    }

    private CalendarModel selected_date;

    public CalendarAdapter(Context context, List<CalendarModel> list, ICalendarView viewInterface) {
        this.context = context;
        this.list = (list);
        this.viewInterface = viewInterface;
        for (CalendarModel tmp : list) {
            if (tmp.getDay() == 1) {
                this.selected_date = tmp;
            }
        }
    }
    /* metodo per filtrare la RV, esempio di uso */

    @Override
    public CalendarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.layout_calendar_date, parent, false);
        return new ViewHolder(row);

    }

    TextView selected_TV;

    @Override
    public void onBindViewHolder(final CalendarAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.woman_mark_IV1.setVisibility(View.GONE);

        if (list.get(position).getDay() == 0 ) {
            viewHolder.date.setText("");
            viewHolder.woman_mark_IV1.setVisibility(View.GONE);
            viewHolder.woman_mark_IV.setVisibility(View.GONE);

            viewHolder.event_mark_IV.setVisibility(View.GONE);
            viewHolder.drugs_mark_IV.setVisibility(View.GONE);


            return;
        }





        viewHolder.event_mark_IV.setVisibility(View.GONE);
        viewHolder.drugs_mark_IV.setVisibility(View.GONE);

        if (list.get(position).isNote()) {
            viewHolder.event_mark_IV.setVisibility(View.VISIBLE);


        }
        if (list.get(position).isDrugs()) {
            viewHolder.drugs_mark_IV.setVisibility(View.VISIBLE);
         //   periodDrugs=position+list.get(position).getPeriod();

        }

        if (list.get(position).isWomanDay1()) {
            if (list.get(position).getDate().equals("")){
                viewHolder.woman_mark_IV1.setVisibility(View.GONE);

            }
            else {
                viewHolder.woman_mark_IV1.setVisibility(View.VISIBLE);
            }
        }

        if (list.get(position).isEvolition()) {

                viewHolder.evolution.setVisibility(View.VISIBLE);

        }

        if (list.get(position).isWomanDay()) {
            if (list.get(position).getDate().equals("")){
                viewHolder.woman_mark_IV.setVisibility(View.GONE);

            }
            else {
                viewHolder.woman_mark_IV.setVisibility(View.VISIBLE);
            }
        }















        viewHolder.main_item_fl.setTag(list.get(position));
        if (selected_date == list.get(position)) {
//            viewHolder.date.setTextColor(Color.parseColor("#52acc7"));
            viewHolder.date.setTextColor(MainController.getColor(context, R.color.bpGreen));
            selected_TV = viewHolder.date;
        } else {
            viewHolder.date.setTextColor(Color.BLACK);
        }


        viewHolder.date.setText(list.get(position).getDay().toString());

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //viewInterface.onDateSelectedLongClick(list.get(position),position);
                return true;
            }
        });


        viewHolder.main_item_fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                    selected_date = (CalendarModel) view.getTag();
                    selected_TV.setTextColor(Color.BLACK);
                    selected_TV = (TextView) view.findViewById(R.id.date_item_TV);
                    selected_TV.setTextColor(Color.parseColor("#52acc7"));
                    viewInterface.onDateSelected((CalendarModel) view.getTag());

                }






        });

    }

    public Calendar getCalendarByDate(String date) {
        boolean isSuccessfull = false;
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(format1.parse(date));
            isSuccessfull = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (isSuccessfull)
            return cal;
        return null;
    }

    @Override
    public int getItemCount() {

        return list != null ? list.size() : 0;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView event_mark_IV;
        public ImageView ovulation_mark_IV;
        public ImageView drugs_mark_IV;
        public ImageView woman_mark_IV;
        public ImageView woman_mark_IV1;
        public ImageView evolution;
        public TextView date;
        public ImageView selected_date_IV;
        public FrameLayout main_item_fl;

        //        public ImageView selected_date_IV;
        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date_item_TV);
            //selected_date_IV = (ImageView) itemView.findViewById(R.id.selected_date_IV);
            evolution = (ImageView) itemView.findViewById(R.id.evolution);
            event_mark_IV = (ImageView) itemView.findViewById(R.id.event_mark_IV);
         //   ovulation_mark_IV = (ImageView) itemView.findViewById(R.id.ovulation_mark_IV);
            drugs_mark_IV=(ImageView) itemView.findViewById(R.id.drugs_mark_IV);
            woman_mark_IV=(ImageView) itemView.findViewById(R.id.womanday);
            woman_mark_IV1=(ImageView) itemView.findViewById(R.id.womanday2);
//            selected_date_IV = (ImageView)  itemView.findViewById(R.id.selected_date_IV);
            main_item_fl = (FrameLayout) itemView.findViewById(R.id.main_item_fl);
        }

    }
}