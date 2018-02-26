package com.example.d.healthbook.Adapters;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by User on 23.09.2017.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    protected Context mContext;
    protected T mData;

    public BaseViewHolder(View itemView,final BaseAdapterListener<T> listener) {
        super(itemView);
        mContext = itemView.getContext();
        ButterKnife.bind(this,itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view,mData);
            }
        });
    }
    @CallSuper
    protected void bind(final T data){
        mData = data;
    }


}
