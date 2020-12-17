package com.fongmi.android.autoclick.ui.choose;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fongmi.android.autoclick.databinding.AdapterChooseBinding;
import com.fongmi.android.autoclick.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ViewHolder> {

	private OnItemClickListener mItemClickListener;
	private List<AppInfo> mItems;

	public ChooseAdapter() {
		this.mItems = new ArrayList<>();
	}

	public interface OnItemClickListener {
		void onItemClick(AppInfo item);
	}

	public void setOnItemClickListener(OnItemClickListener itemClickListener) {
		this.mItemClickListener = itemClickListener;
	}

	class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private AdapterChooseBinding binding;

		ViewHolder(AdapterChooseBinding binding) {
			super(binding.getRoot());
			this.binding = binding;
			binding.getRoot().setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			mItemClickListener.onItemClick(mItems.get(getLayoutPosition()));
		}
	}

	public void addAll(List<AppInfo> items) {
		mItems.clear();
		mItems.addAll(items);
		AppInfo.Sorter.sort(mItems);
		notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {
		return mItems.size();
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new ViewHolder(AdapterChooseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		AppInfo item = mItems.get(position);
		holder.binding.name.setText(item.getName());
		holder.binding.icon.setImageDrawable(item.getIcon());
	}
}