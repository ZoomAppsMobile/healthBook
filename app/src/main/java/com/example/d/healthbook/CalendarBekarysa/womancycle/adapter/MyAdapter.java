package com.example.d.healthbook.CalendarBekarysa.womancycle.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.d.healthbook.API.App;
import com.example.d.healthbook.CalendarBekarysa.CalendarActivity;
import com.example.d.healthbook.CalendarBekarysa.womancycle.Result;
import com.example.d.healthbook.CalendarBekarysa.womancycle.WomanCycle;
import com.example.d.healthbook.GlobalVariables.GlobalVariables;
import com.example.d.healthbook.R;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.NewSentenceHolder>{

	private List<WomanCycle> objectList;
	private LayoutInflater inflater;
	Context context;

	public MyAdapter(Context context, List<WomanCycle> objectList) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.objectList = objectList;
	}

	@Override
	public NewSentenceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.list_item, parent, false);
		NewSentenceHolder holder = new NewSentenceHolder(view);

		return holder;
	}

	@Override
	public int getItemCount() {
		return objectList.size();
	}

	@Override
	public void onBindViewHolder(NewSentenceHolder holder, int position) {
		WomanCycle current = objectList.get(position);
		holder.setData(current, position);
		holder.setListeners();
	}

	class NewSentenceHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private TextView title;
		private ImageView imgDelete;
		private int position;
		private WomanCycle currentObject;
		private String id;

		public NewSentenceHolder(View itemView) {
			super(itemView);
			title       = (TextView)  itemView.findViewById(R.id.tvTitle);
			imgDelete   = (ImageView) itemView.findViewById(R.id.img_delete);
		}

		public void setData(WomanCycle currentObject, int position) {
			this.title.setText("Дата начала: "+currentObject.getDataBegin());
			this.position = position;
			this.currentObject = currentObject;
			this.id=currentObject.getId();
		}

		public void setListeners() {
			imgDelete.setOnClickListener(NewSentenceHolder.this);

		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.img_delete:
					removeItem(position,id);
					break;

			}
		}
	}

	public void removeItem(int position,String id) {
		objectList.remove(position);
		notifyItemRemoved(position);
		notifyItemRangeChanged(position, objectList.size());
		deleteWomanTask(id);
//		notifyDataSetChanged();
	}

	public void addItem(int position, WomanCycle currentObject) {
		objectList.add(position, currentObject);
		notifyItemInserted(position);
		notifyItemRangeChanged(position, objectList.size());
//		notifyDataSetChanged();
	}






	public void deleteWomanTask(String id) {



		Call<Void> deleteRequest = App.getApi().deleteWomanTask(id,GlobalVariables.user_auth_token);
		deleteRequest.enqueue(new Callback<Void>() {
			@Override
			public void onResponse(Call<Void> call, Response<Void> response) {
				// use response.code, response.headers, etc.
				Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(context, CalendarActivity.class);
				context.startActivity(intent);
			}

			@Override
			public void onFailure(Call<Void> call, Throwable t) {
				// handle failure
				Toast.makeText(context,  "An error occurred during networking", Toast.LENGTH_SHORT).show();
			}
		});






	}
}