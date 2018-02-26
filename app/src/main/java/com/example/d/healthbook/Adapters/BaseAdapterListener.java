package com.example.d.healthbook.Adapters;

import android.view.View;

/**
 * Created by User on 23.09.2017.
 */

public interface BaseAdapterListener<T> {
    void onItemClick(View view, T data);
}
