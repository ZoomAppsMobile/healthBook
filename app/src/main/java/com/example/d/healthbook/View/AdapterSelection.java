package com.example.d.healthbook.View;

import java.util.HashMap;

/**
 * Created by User on 21.08.2017.
 */

public interface AdapterSelection {
    void onSelected(HashMap<String, String> out);
    void onChecked(String out);
    void onUnChecked(String out);
    void onSelect(HashMap<String, String> out);
}
