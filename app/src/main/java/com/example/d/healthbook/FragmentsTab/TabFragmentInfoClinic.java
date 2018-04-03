package com.example.d.healthbook.FragmentsTab;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.Models.ResponseClinicInfo;
import com.example.d.healthbook.Models.ResponseClinicInfo2;
import com.example.d.healthbook.Models.ResponseDoctorInfo;
import com.example.d.healthbook.R;
import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.RxMDTextView;
import com.yydcdut.rxmarkdown.RxMarkdown;
import com.yydcdut.rxmarkdown.syntax.text.TextFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import us.feras.mdv.MarkdownView;

/**
 * Created by D on 09.06.2017.
 */

public class TabFragmentInfoClinic extends Fragment {
    private TextView textView;
    private boolean isViewCreated = false;
    ResponseClinicInfo2 mainData;
    MarkdownView markdownView;
    RatingBar  rating;
    private RxMDTextView mRxMDTextView;
    private RxMDConfiguration mRxMDConfiguration;
    public void upDateData(ResponseClinicInfo2 data) {
        if (data != null) {
            mainData = data;
            setDataToViews();
            rating.setRating(Float.parseFloat(mainData.getRate()));
        }
    }


    public void setDataToViews() {
        if (mainData != null && isViewCreated) {
            String first = mainData.getInfo().substring(0, 2);
            Log.d("First", first);
            if (first.equals("{\"")) {
                mRxMDConfiguration = new RxMDConfiguration.Builder(getContext())
                        .build();
                RxMarkdown.with((parseJson(mainData.getInfo())),getContext())
                        .config(mRxMDConfiguration)
                        .factory(TextFactory.create())
                        .intoObservable()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<CharSequence>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(getContext(),  e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(CharSequence charSequence) {
                                mRxMDTextView.setText(charSequence, TextView.BufferType.SPANNABLE);
                            }
                        });
                Log.e("log", (parseJson(mainData.getInfo())));
            } else {
                mRxMDTextView.setVisibility(View.GONE);
                textView.setText(Html.fromHtml(mainData.getInfo().replaceAll("\"", "")));
            }


        }
    }



//    public void setDataToViews() {
//        if (mainData != null && isViewCreated) {
//                String first =mainData.getInfo().substring(0, 2);
//                Log.d("First",first);
//                //if(first.equals("{\"")){
//
//                    //textView.setText(Html.fromHtml( (parseJson(mainData.getInfo()))));
//                    markdownView.loadMarkdown((parseJson(mainData.getInfo())));
//                //}
//               // else {
//                    //textView.setText(Html.fromHtml( mainData.getInfo() ));
//
//                //}
//
//        }
//    }










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
        mRxMDTextView = view.findViewById(R.id.txt_md_show);
        rating = view.findViewById(R.id.rating);
        isViewCreated = true;
        setDataToViews();

    }


}