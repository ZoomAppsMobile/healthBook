package com.example.d.healthbook.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.d.healthbook.Models.DiaryModel.ParameterTableModel.Parameter;
import com.example.d.healthbook.R;
import com.example.d.healthbook.View.ParametersDetailsView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by User on 27.09.2017.
 */

public class ParamentersDetailAdapter extends BaseAdapter<Parameter,ParametersDetailsView> {

    public ParamentersDetailAdapter(List<Parameter> datum, ParametersDetailsView listener) {
        super(datum, listener);
    }

    @Override
    public BaseViewHolder<Parameter> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_parameter_detail, parent, false);
        return new ParamentersDetailAdapter.ParamentersDetailViewHolder(view,mListener);
    }

    public class ParamentersDetailViewHolder extends BaseViewHolder<Parameter>{
        @BindView(R.id.tv_parameter_name)
        TextView tv_parameter_name;
        @BindView(R.id.tv_parameter_value)
        TextView tv_parameter_value;

        public ParamentersDetailViewHolder(View itemView, BaseAdapterListener<Parameter> listener) {
            super(itemView, listener);
        }

        @Override
        protected void bind(Parameter data) {
            super.bind(data);
            tv_parameter_name.setText(data.getName());
            tv_parameter_value.setText(data.getValue());
        }
    }
}
