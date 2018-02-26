package com.example.d.healthbook.Adapters;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by User on 27.09.2017.
 */

public abstract class BaseAdapter<T,E extends BaseAdapterListener<T>> extends RecyclerView.Adapter<BaseViewHolder<T>> {
    protected List<T> mDatum;
    protected E mListener;

    public BaseAdapter(List<T> datum , E listener){
        mDatum = datum;
        mListener = listener;
    }

    public void addData(T data){
        mDatum.add(data);
        notifyDataSetChanged();
    }

    public void addData(List<T> datum){
        mDatum.addAll(datum);
        notifyDataSetChanged();
    }

    public void updateData(List<T> datum){
        mDatum.clear();
        addData(datum);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<T> holder, int position) {
        holder.bind(mDatum.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatum.size();
    }
}
