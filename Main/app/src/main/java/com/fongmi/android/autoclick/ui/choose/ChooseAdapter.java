package com.fongmi.android.autoclick.ui.choose;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fongmi.android.autoclick.Utils;
import com.fongmi.android.autoclick.bean.AppInfo;
import com.fongmi.android.autoclick.databinding.AdapterChooseBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ViewHolder> {

	private OnItemClickListener mItemClickListener;
	private final ExecutorService executor;
	private final List<AppInfo> mItems;
	private final Handler handler;
	private boolean system;

	public ChooseAdapter() {
		this.mItems = new ArrayList<>();
		this.executor = Executors.newSingleThreadExecutor();
		this.handler = new Handler(Looper.getMainLooper());
	}

	public interface OnItemClickListener {
		void onItemClick(AppInfo item);
	}

	public void setOnItemClickListener(OnItemClickListener itemClickListener) {
		this.mItemClickListener = itemClickListener;
	}

	class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		private final AdapterChooseBinding binding;

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

	public boolean isSystem() {
		return system;
	}

	private void setSystem(boolean system) {
		this.system = system;
	}

	public void getUser() {
		executor.execute(() -> {
			setSystem(false);
			addAll(Utils.getApps(false));
			handler.post(this::notifyDataSetChanged);
		});
	}

	public void getSystem() {
		executor.execute(() -> {
			setSystem(true);
			addAll(Utils.getApps(true));
			handler.post(this::notifyDataSetChanged);
		});
	}

	private void addAll(List<AppInfo> items) {
		mItems.clear();
		mItems.addAll(items);
		AppInfo.Sorter.sort(mItems);
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
