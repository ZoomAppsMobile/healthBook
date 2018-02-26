package com.example.d.healthbook.UI;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.d.healthbook.API.App;
import com.example.d.healthbook.R;

import java.util.concurrent.atomic.AtomicLong;

public class CoverBlock extends BlockLayout implements IBlockLayout
{

    static final AtomicLong NEXT_ID = new AtomicLong(1000);
    final long id = NEXT_ID.getAndIncrement();

    public CoverBlock(Context context)
    {
        super(context);
    }

    @Override
    public IBlockLayout __construct()
    {
        Context ctx = App.getInstance().getCurrentContext();
        this.setGravity(Gravity.CENTER);

        TextView heading = new TextView(ctx);
        heading.setId((int) id);
        heading.setTextColor(ContextCompat.getColor(ctx, R.color.colorDarkGray));
        heading.setTextSize(36);
        LayoutParams hdr_lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        hdr_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        heading.setLayoutParams(hdr_lp);
        this.addView(heading);

        TextView description = new TextView(ctx);
        description.setTextColor(ContextCompat.getColor(ctx, R.color.colorDarkGray));
        LayoutParams dsc_lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        description.setGravity(Gravity.CENTER_HORIZONTAL);
        dsc_lp.addRule(RelativeLayout.CENTER_HORIZONTAL | RelativeLayout.BELOW, heading.getId());
        description.setLayoutParams(dsc_lp);
        this.addView(description);

        return this;
    }

    @Override
    public void adjustToView(View view, Size constraints)
    {
        super.adjustToView(view, constraints);
    }

    public void setHeadingText(String text)
    {
        TextView view = (TextView) getChildAt(0);
        view.setText(text);
    }

    public void setHeadingSize(int size)
    {
        TextView view = (TextView) getChildAt(0);
        view.setTextSize(size);
    }

    public void setHeadingColor(int color)
    {
        TextView view = (TextView) getChildAt(0);
        view.setTextColor(ContextCompat.getColor(App.getInstance().getCurrentContext(), color));
    }

    public void setDescriptionText(String text)
    {
        TextView view = (TextView) getChildAt(1);
        view.setText(text);
    }

    public void setDescriptionSize(int size)
    {
        TextView view = (TextView) getChildAt(1);
        view.setTextSize(size);
    }

    public void setDescriptionColor(int color)
    {
        TextView view = (TextView) getChildAt(1);
        view.setTextColor(ContextCompat.getColor(App.getInstance().getCurrentContext(), color));
    }

}