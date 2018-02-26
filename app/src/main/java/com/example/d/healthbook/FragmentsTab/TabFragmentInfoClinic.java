package com.example.d.healthbook.FragmentsTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.d.healthbook.Models.ResponseClinicInfo;
import com.example.d.healthbook.Models.ResponseClinicInfo2;
import com.example.d.healthbook.Models.ResponseDoctorInfo;
import com.example.d.healthbook.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import us.feras.mdv.MarkdownView;

/**
 * Created by D on 09.06.2017.
 */

public class TabFragmentInfoClinic extends Fragment {
    private TextView textView;
    private boolean isViewCreated = false;
    ResponseClinicInfo2 mainData;
    MarkdownView markdownView;
    public void upDateData(ResponseClinicInfo2 data) {
        if (data != null) {
            mainData = data;
            setDataToViews();
        }
    }


    public void setDataToViews() {
        if (mainData != null && isViewCreated) {
                String first =mainData.getInfo().substring(0, 2);
                Log.d("First",first);
                if(first.equals("{\"")){

                    //textView.setText(Html.fromHtml( (parseJson(mainData.getInfo()))));
                    markdownView.loadMarkdown((parseJson(mainData.getInfo())));
                }
                else {
                    textView.setText(Html.fromHtml( mainData.getInfo() ));

                }

        }
    }
    public String parseJson(String jsonStr) {

         String content="";
        if (jsonStr != null) {

            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Place
                JSONArray place = jsonObj.getJSONArray("data");

                // Address
                JSONObject address = place.getJSONObject(0).getJSONObject("data");

                content = address.getString("text");
                // String country = address.getString("country");
                // String state = address.getString("state");
                // String county = address.getString("county");

                Log.d("SUCCESS", " County: " + content);

            } catch (final JSONException e) {
                Log.e("FAILED", "Json parsing error: " + e.getMessage());
            }
        }


        return content;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_clinic_info, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textView = (TextView) view.findViewById(R.id.pagerTVClinicInfo);
        markdownView = (MarkdownView)view. findViewById(R.id.markdownView);
        isViewCreated = true;
        setDataToViews();

    }


}