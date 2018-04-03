package com.example.d.healthbook.FragmentsTab;

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

import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Models.ResponseDoctorInfo;
import com.example.d.healthbook.R;
import com.yydcdut.rxmarkdown.RxMDConfiguration;
import com.yydcdut.rxmarkdown.RxMDEditText;
import com.yydcdut.rxmarkdown.RxMDTextView;
import com.yydcdut.rxmarkdown.RxMarkdown;
import com.yydcdut.rxmarkdown.syntax.edit.EditFactory;
import com.yydcdut.rxmarkdown.syntax.text.TextFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import us.feras.mdv.MarkdownView;

/**
 * Created by D on 02.06.2017.
 */


public class TabFragmentInfoDoc extends Fragment {
    private TextView textView;
    private boolean isViewCreated = false;
    ResponseDoctorInfo mainData;
    MarkdownView markdownView;
    private RxMDTextView mRxMDTextView;
    private RxMDConfiguration mRxMDConfiguration;
    RatingBar rating;


    public void upDateData(ResponseDoctorInfo data) {
        if (data != null) {
            mainData = data;
            setDataToViews();

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
                                //String text = charSequence.toString();
                                //String textResult = text.replace("\\", "");
                                //Log.e("textResult", charSequence + "");
                                mRxMDTextView.setText(charSequence, TextView.BufferType.SPANNABLE);
                            }
                        });



             //markdownView.loadMarkdown((parseJson(mainData.getInfo()).replaceAll("\"", "")));
                //testTest.setText((parseJson(mainData.getInfo())).replaceAll("", ""));
                Log.e("log", (parseJson(mainData.getInfo())));

            } else {
                textView.setText(Html.fromHtml(mainData.getInfo().replaceAll("\"", "")));
               Log.e("ParseJSON", mainData.getInfo().toString());

//                testTest.setText((parseJson(mainData.getInfo())).replaceAll("((^|\\s+)(\\w))", ""));
            }


        }
    }



    public String parseJson(String jsonStr) {

        String content = "";
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


        return content.replace("\\", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        App.getApi().seedoctorInfo("440").enqueue(new Callback<ResponseDoctorInfo>() {
            @Override
            public void onResponse(Call<ResponseDoctorInfo> call, Response<ResponseDoctorInfo> response) {


                String CurrentString = response.body().getInfo().toString();
                StringTokenizer tokens = new StringTokenizer(CurrentString, "\\\\n\\\\n");
                String first = tokens.nextToken();// this will contain "Fruit"
                String second = tokens.nextToken();// this will contain " they taste good"

                Log.e("repser", second);
            }

            @Override
            public void onFailure(Call<ResponseDoctorInfo> call, Throwable t) {
                Log.e("infoDoctor", t.getMessage().toString());
                String s = t.getMessage().toString();


            }
        });


//        String s = "40386{#}Для каких баннеров проверяется скорость канала посетителя, и если скорость канала недостаточна, то баннер демонстрироваться не будет?\n112077{#}Предельная скорость к потреблению - это:"; // видите \n - перевод строки
//        String[] q = s.split("\n|\\{#\\}");
//        for (int i = 0; i < q.length; i++) {
//            Toast.makeText(this, q[i], Toast.LENGTH_SHORT).show();
//            Log.e("repser", q[i] );
//        }


        return inflater.inflate(R.layout.tab_fragment_doc_info, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textView = (TextView) view.findViewById(R.id.pagerTVdocInfo);
       // markdownView = (MarkdownView) view.findViewById(R.id.markdownView);
        mRxMDTextView = view.findViewById(R.id.txt_md_show);
        rating = view.findViewById(R.id.rating);
        isViewCreated = true;
        setDataToViews();

    }
}