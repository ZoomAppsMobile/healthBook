package com.example.d.healthbook.View;

/**
 * Created by User on 19.09.2017.
 */

import android.view.View;

public interface AdapterInteraction {
    <T> void onItemClick (View v , T data);
}
