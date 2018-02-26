package com.example.d.healthbook.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.d.healthbook.R;

/**
 * Created by D on 23.06.2017.
 */

public class ViewHolder2 extends RecyclerView.ViewHolder {

    private TextView eventCalendargray;
    private TextView description_event;


    public ViewHolder2(View v) {
        super(v);
        eventCalendargray = (TextView) v.findViewById(R.id.eventCalendargray);
        description_event = (TextView) v.findViewById(R.id.description_event);
    }

    public TextView getEventCalendargray() {
        return eventCalendargray;
    }

    public void setEventCalendargray(TextView eventCalendargray) {
        this.eventCalendargray = eventCalendargray;
    }

    public TextView getDescription_event() {
        return description_event;
    }

    public void setDescription_event(TextView description_event) {
        this.description_event = description_event;
    }
}