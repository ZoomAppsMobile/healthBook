package com.example.d.healthbook.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.d.healthbook.Models.DocumentMyFamily;
import com.example.d.healthbook.Presenter.FoldersPresenter;
import com.example.d.healthbook.R;
import com.example.d.healthbook.View.FoldersView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FoldersFragment extends Fragment implements FoldersView {

    private List<DocumentMyFamily> mAdapterData = new ArrayList();
    private MyItemRecyclerViewAdapter mAdapter;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;
      //"Медицинские показатели",
    private String[] folder_categories= {
                "HealthBook",
                "Режим питания",
                "Здоровье",
                "Фитнес",
                "Моя семья",
                "Медицинские",
                "Мужское здоровье",
                "Женское здоровье",
                "Рецепты",
                "Красота",
                "Беременность"
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    FoldersPresenter presenter;
    public FoldersFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FoldersFragment newInstance(int columnCount) {
        FoldersFragment fragment = new FoldersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mAdapter = new MyItemRecyclerViewAdapter(mAdapterData,folder_categories ,mListener);
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FoldersPresenter(this);
        presenter.getData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onShowToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowError(String message) {
        onShowToast(message);
    }

    @Override
    public <T> void onDataLoaded(List<T> data, Class<T> tClass) {
        mAdapterData.clear();
        mAdapterData.addAll((Collection<? extends DocumentMyFamily>) data);
//        for (T tmp:data) {
//            if(tmp instanceof DocumentMyFamily) {
//                mAdapterData.add((DocumentMyFamily) tmp);
//            }
//        }
        if(mAdapter!=null){
            mAdapter.notifyDataSetChanged();
        }
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(MyItemRecyclerViewAdapter.ViewHolder item,String fName);
    }
}
