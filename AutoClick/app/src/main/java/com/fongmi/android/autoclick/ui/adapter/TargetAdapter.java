package com.fongmi.android.autoclick.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fongmi.android.autoclick.databinding.AdapterTargetBinding;
import com.fongmi.android.autoclick.db.AppDatabase;
import com.fongmi.android.autoclick.model.Target;

import java.util.ArrayList;
import java.util.List;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.ViewHolder> {

	private List<Target> mItems;

	public TargetAdapter() {
		this.mItems = new ArrayList<>();
	}

	class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private AdapterTargetBinding binding;

		ViewHolder(AdapterTargetBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
			binding.remove.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			Target target = mItems.get(getLayoutPosition());
			AppDatabase.get().getTargetDao().delete(target.getId());
			mItems.remove(getLayoutPosition());
			notifyItemRemoved(getLayoutPosition());
		}
	}

	public void update() {
		mItems.clear();
		mItems.addAll(AppDatabase.get().getTargetDao().getAll());
		notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		return mItems.size();
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(AdapterTargetBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		Target item = mItems.get(position);
		holder.binding.name.setText(item.getName());
		holder.binding.info.setText(item.getInfo());
	}
}
