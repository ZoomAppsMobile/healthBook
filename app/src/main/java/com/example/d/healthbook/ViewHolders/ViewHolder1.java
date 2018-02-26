package com.example.d.healthbook.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.d.healthbook.R;

/**
 * Created by D on 23.06.2017.
 */

public class ViewHolder1 extends RecyclerView.ViewHolder {

    public TextView getTitleGarmones() {
        return titleGarmones;
    }

    public void setTitleGarmones(TextView titleGarmones) {
        this.titleGarmones = titleGarmones;
    }

    public TextView getTimeCheckGarmones() {
        return timeCheckGarmones;
    }

    public void setTimeCheckGarmones(TextView timeCheckGarmones) {
        this.timeCheckGarmones = timeCheckGarmones;
    }

    public TextView getEventCalendar() {
        return eventCalendar;
    }

    public void setEventCalendar(TextView eventCalendar) {
        this.eventCalendar = eventCalendar;
    }

    TextView titleGarmones;
    TextView timeCheckGarmones;
    TextView eventCalendar;
   public ImageView imgDelete;
   public ImageView imgUpd;
    public ViewHolder1(View v) {
        super(v);
        titleGarmones = (TextView) v.findViewById(R.id.titleGarmones);
        timeCheckGarmones = (TextView) v.findViewById(R.id.timeCheckGarmones);
        eventCalendar = (TextView) v.findViewById(R.id.eventCalendar);
        imgDelete   = (ImageView) itemView.findViewById(R.id.img_delete);
        imgUpd   = (ImageView) itemView.findViewById(R.id.img_upd);


    }


}
