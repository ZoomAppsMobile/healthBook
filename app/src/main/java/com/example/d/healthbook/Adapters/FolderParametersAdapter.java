package com.example.d.healthbook.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.d.healthbook.Models.DiaryModel.ParameterTableModel.Parameter;
import com.example.d.healthbook.Models.DiaryModel.ParameterTableModel.Week;
import com.example.d.healthbook.R;
import com.example.d.healthbook.View.FileView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by User on 27.09.2017.
 */

public class FolderParametersAdapter extends BaseAdapter<Week,FileView> {
    public FolderParametersAdapter(List<Week> datum, FileView listener) {
        super(datum, listener);
    }

    @Override
    public BaseViewHolder<Week> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_folder_parametes, parent, false);
        return new FolderParamentersHolder(view,mListener);
    }

    public class FolderParamentersHolder extends BaseViewHolder<Week>{
       // @BindView(R.id.param_date)
       // TextView param_date;

        @BindView(R.id.param_weight)
        TextView param_weight;

        @BindView(R.id.param_height)
        TextView param_height;

        public FolderParamentersHolder(View itemView, BaseAdapterListener<Week> listener) {
            super(itemView, listener);
        }

        @Override
        protected void bind(Week data) {
            super.bind(data);
          //  param_date.setText(data.getDatetime());
            String weight =getSpecificParameter(1);
            String height =getSpecificParameter(2);
            param_weight.setText(weight);
            param_height.setText(height);
        }

        String getSpecificParameter(int id){
            for (Parameter tmp: mData.getParameters()) {
                if(tmp.getParameterTypeId() == id){
                    return tmp.getValue();
                }
            }
            return "";
        }
    }
}
