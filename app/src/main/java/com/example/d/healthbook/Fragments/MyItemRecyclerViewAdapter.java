package com.example.d.healthbook.Fragments;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.d.healthbook.Fragments.FoldersFragment.OnListFragmentInteractionListener;
import com.example.d.healthbook.Fragments.dummy.DummyContent.DummyItem;
import com.example.d.healthbook.Models.DiaryModel.CompleteDiaryData;
import com.example.d.healthbook.Models.DocumentMyFamily;
import com.example.d.healthbook.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<DocumentMyFamily> mValues;
    private final String[] mCategories;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<DocumentMyFamily> items, String[] categories, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        mCategories = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mCategory = mCategories[position];
        holder.mItem = searchDiaryDataById(holder.mCategory);

       // Log.d("xcxc",holder.mItem.getId());
        if(holder.mItem!=null) {
            holder.mCreationDate.setVisibility(View.VISIBLE);
            holder.mFolderName.setText(holder.mItem.getName());
            holder.mCreationDate.setText(mValues.get(position).getCreateDate());

        }
        else{
            holder.mCreationDate.setVisibility(View.GONE);
            holder.mFolderName.setText(holder.mCategory);
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder,holder.mCategory);
                }
            }
        });
    }

    DocumentMyFamily searchDiaryDataById(String name){
        for (DocumentMyFamily tmpData:mValues) {
            if(tmpData.getName().equals(name)){
                return tmpData;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mCategories.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mFolderName;
        public final TextView mCreationDate;
        public String mCategory;
        public DocumentMyFamily mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mFolderName = (TextView) view.findViewById(R.id.tv_folder_name);
            mCreationDate = (TextView) view.findViewById(R.id.tv_creation_date);
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}
