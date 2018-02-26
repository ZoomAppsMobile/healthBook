package com.example.d.healthbook.Activities;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.d.healthbook.Controller.ImageHandler;
import com.example.d.healthbook.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZoomActivity extends AppCompatActivity {

    public static String IMAGE_KEY="image_src";

    @BindView(R.id.iv_fullImage)
    ImageView iv_fullImage;

    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        //mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iv_fullImage.setTransitionName("cakeImageAnimation");
        }
        showBackArrow();

        Glide.with(this).load(getIntent().getStringExtra(IMAGE_KEY))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new ImageHandler(iv_fullImage));
        //iv_fullImage
    }

    protected void showBackArrow() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
