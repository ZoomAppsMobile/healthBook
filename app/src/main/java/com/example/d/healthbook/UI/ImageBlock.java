package com.example.d.healthbook.UI;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.d.healthbook.API.App;
import com.example.d.healthbook.R;

public class ImageBlock extends BlockLayout implements IBlockLayout
{
    public ImageBlock(Context context)
    {
        super(context);
    }

    @Override
    public IBlockLayout __construct()
    {
        Context ctx = App.getInstance().getCurrentContext();

        TextView label = new TextView(ctx);
        label.setTextColor(ContextCompat.getColor(ctx, R.color.black));
        LayoutParams lbl_lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lbl_lp.setMargins(0, 10, 0, 0);
        lbl_lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        label.setLayoutParams(lbl_lp);

        ImageView image_value = new ImageView(ctx);
        LayoutParams val_lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        val_lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        image_value.setLayoutParams(val_lp);

        TextView text_value = new TextView(ctx);
        text_value.setTextColor(ContextCompat.getColor(ctx, R.color.colorDarkGray));
        text_value.setTextSize(24);
        LayoutParams txt_val_lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        txt_val_lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        text_value.setLayoutParams(txt_val_lp);

        this.setBackground(ContextCompat.getDrawable(ctx, R.drawable.shadow_wrc_layout));
        this.addView(label);
        this.addView(image_value);
        this.addView(text_value);

        return this;
    }

    @Override
    public void adjustToView(View view, Size constraints)
    {
        super.adjustToView(view, constraints);
    }

    public void setLabelText(String text)
    {
        TextView view = (TextView) getChildAt(0);
        view.setText(text);
    }

    public void setLabelSize(int size)
    {
        TextView view = (TextView) getChildAt(0);
        view.setTextSize(size);
    }

    public void setLabelColor(int color)
    {
        TextView view = (TextView) getChildAt(0);
        view.setTextColor(ContextCompat.getColor(App.getInstance().getCurrentContext(), color));
    }

    public void setImageResourse(int drawable)
    {
        ImageView view = (ImageView) getChildAt(1);
        view.setImageResource(drawable);
    }

    public void setImageDrawable(Drawable drawable)
    {
        ImageView view = (ImageView) getChildAt(1);
        view.setImageDrawable(drawable);
    }


    public void setTextValue(String text)
    {
        TextView view = (TextView) getChildAt(2);
        view.setText(text);
    }

    public void setTextValueSize(int size)
    {
        TextView view = (TextView) getChildAt(2);
        view.setTextSize(size);
    }

    public void setTextValueColor(int color)
    {
        TextView view = (TextView) getChildAt(2);
        view.setTextColor(ContextCompat.getColor(App.getInstance().getCurrentContext(), color));
    }

}