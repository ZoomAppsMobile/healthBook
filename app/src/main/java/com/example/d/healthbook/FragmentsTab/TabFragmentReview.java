package com.example.d.healthbook.FragmentsTab;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.API.App;
import com.example.d.healthbook.Adapters.RecyclerAdapterMentionsClinicInfo;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.Models.CheckVisit;
import com.example.d.healthbook.Models.MentionsModel;
import com.example.d.healthbook.Models.ResponseDoctorInfo;
import com.example.d.healthbook.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by D on 02.06.2017.
 */

public class TabFragmentReview extends Fragment {
    ResponseDoctorInfo mainData;
    FloatingActionButton fabClinic;
    @BindView(R.id.rel_layout)
    RelativeLayout rel_layout;
    //    private boolean isViewCreated = false;
//    private TextView textView;
//
//    public void upDateData(ResponseDoctorInfo data) {
//        if (data != null) {
//            mainData = data;
//            setDataToViews();
//        }
//    }
//
//    public void setDataToViews() {
//        if (mainData != null && isViewCreated)
//            textView.setText(mainData.getInfoDs());
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.tab_fragment_review, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        textView = (TextView) view.findViewById(R.id.pagerReviewTV);
//        isViewCreated = true;
//        setDataToViews();
//
//    }
    private RecyclerView recycler_mention_clinic;
    private boolean isViewCreated;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapterMentionsClinicInfo recyclerAdapterMentionsClinicInfo;
    String doctorId;
    float efficiency_mark, performance_mark, recommendation_mark;

    public void upDateData(ResponseDoctorInfo data) {
        if (data != null) {
            mainData = data;
            setDataToViews();
            Log.e("response", mainData.getId());
        }
    }

    public void setDataToViews() {
//        if (mainData != null && isViewCreated)
//            textView.setText(mainData.getInfoDs());
        mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_mention_clinic.setLayoutManager(mLayoutManager);
        //recyclerAdapterMentionsClinicInfo = new RecyclerAdapterMentionsClinicInfo(mainData.get.mentions, getActivity());
        recycler_mention_clinic.setAdapter(recyclerAdapterMentionsClinicInfo);

        isViewCreated = true;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_mention_clinic, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick(R.id.fabClinic)
    public void ggg() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            doctorId = bundle.getString("doctorId", "");
            App.getApi().checkVisit(GlobalVariables.user_auth_token, doctorId, "EXPERT").enqueue(new Callback<CheckVisit>() {
                @Override
                public void onResponse(Call<CheckVisit> call, final Response<CheckVisit> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getSuccess()) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                LinearLayout linearLayout = new LinearLayout(getContext());
                                final TextView rewiews = new TextView(getContext());
                                final EditText rewiewsEditText = new EditText(getContext());
                                final TextView qualifications = new TextView(getContext());
                                final TextView mindfulness = new TextView(getContext());
                                final TextView price_quality = new TextView(getContext());
                                final Button buttonSendRewiews = new Button(getContext());
                                rewiews.setText(getString(R.string.your_rewiews));
                                qualifications.setText(getString(R.string.qualifications));
                                mindfulness.setText(getString(R.string.mindfulness));
                                price_quality.setText(getString(R.string.price_quality));
                                final RatingBar ratingqualifications = new RatingBar(getContext());
                                ratingqualifications.setNumStars(5);
                                ratingqualifications.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT));
                                final RatingBar ratingmindfulness = new RatingBar(getContext());
                                ratingmindfulness.setNumStars(5);
                                ratingmindfulness.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT));
                                final RatingBar ratingBarprice_quality = new RatingBar(getContext());
                                ratingBarprice_quality.setNumStars(5);
                                ratingBarprice_quality.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT));
                                buttonSendRewiews.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.menu_bg2));
                                buttonSendRewiews.setText("Опубликовать отзыв");
                                linearLayout.setOrientation(LinearLayout.VERTICAL);
                                linearLayout.addView(rewiews);
                                linearLayout.addView(rewiewsEditText);
                                linearLayout.addView(qualifications);
                                linearLayout.addView(ratingqualifications);
                                linearLayout.addView(mindfulness);
                                linearLayout.addView(ratingmindfulness);
                                linearLayout.addView(price_quality);
                                linearLayout.addView(ratingBarprice_quality);
                                linearLayout.addView(buttonSendRewiews);
                                linearLayout.setPadding(50, 40, 50, 50);
                                builder.setView(linearLayout);
                                buttonSendRewiews.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (rewiewsEditText.getText().toString().length() > 12 && rewiewsEditText.getText().toString() != null) {

                                            App.getApi().addRewiews(GlobalVariables.user_auth_token, "EXPERT", doctorId, response.body().getVisitId(),
                                                    ratingBarprice_quality.getRating(),
                                                    ratingqualifications.getRating(),
                                                    ratingmindfulness.getRating(),
                                                    rewiewsEditText.getText().toString()).enqueue(new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {

                                                }

                                                @Override
                                                public void onFailure(Call<Void> call, Throwable throwable) {

                                                }
                                            });

                                        } else {
                                            Toast.makeText(getContext(), "Отзыв должен быть не менее 12 символов", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                                builder.create().show();

                            } else {
                                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("К сожалению вы не можете оставить отвыв, так-как не были на приеме у врача!")
                                        .setCancelable(false)
                                        .setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
                                            public void onClick(final DialogInterface dialog, final int id) {
                                                dialog.cancel();

                                            }
                                        });
                                final AlertDialog alert = builder.create();
                                alert.show();


                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<CheckVisit> call, Throwable throwable) {

                }
            });
        }

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recycler_mention_clinic = (RecyclerView) view.findViewById(R.id.recycler_mention_clinic);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            doctorId = bundle.getString("doctorId", "");
            App.getApi().getMentionExspert(doctorId, GlobalVariables.user_auth_token).enqueue(new Callback<MentionsModel>() {
                @Override
                public void onResponse(Call<MentionsModel> call, Response<MentionsModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            MentionsModel mentionsModel = response.body();
                            recyclerAdapterMentionsClinicInfo = new RecyclerAdapterMentionsClinicInfo(mentionsModel, getActivity());
                            recycler_mention_clinic.setAdapter(recyclerAdapterMentionsClinicInfo);
                        }
                    } else
                        Toast.makeText(getContext(), "Ошибка загрузки отзывов", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<MentionsModel> call, Throwable throwable) {

                }
            });
        }
        setDataToViews();
    }
}