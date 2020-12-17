package com.fongmi.android.autoclick.ui.choose;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fongmi.android.autoclick.R;
import com.fongmi.android.autoclick.Utils;
import com.fongmi.android.autoclick.databinding.ActivityChooseBinding;
import com.fongmi.android.autoclick.databinding.DialogChooseBinding;
import com.fongmi.android.autoclick.db.AppDatabase;
import com.fongmi.android.autoclick.bean.AppInfo;
import com.fongmi.android.autoclick.bean.Target;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ChooseActivity extends AppCompatActivity implements ChooseAdapter.OnItemClickListener {

	private ActivityChooseBinding binding;
	private ChooseAdapter mAdapter;

	public static void newInstance(Activity activity) {
		activity.startActivityForResult(new Intent(activity, ChooseActivity.class), 1000);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityChooseBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initView();
		initEvent();
	}

	private void initView() {
		setRecyclerView();
		new Handler().postDelayed(() -> mAdapter.addAll(Utils.getApps()), 250);
	}

	private void initEvent() {
		mAdapter.setOnItemClickListener(this);
	}

	private void setRecyclerView() {
		mAdapter = new ChooseAdapter();
		binding.recycler.setHasFixedSize(true);
		binding.recycler.setLayoutManager(new LinearLayoutManager(this));
		binding.recycler.setAdapter(mAdapter);
	}

	@Override
	public void onItemClick(AppInfo item) {
		DialogChooseBinding binding = DialogChooseBinding.inflate(LayoutInflater.from(this));
		new MaterialAlertDialogBuilder(this).setView(binding.getRoot()).setPositiveButton(R.string.dialog_positive, (dialog, which) -> onSave(item, binding.keyword.getText().toString(), binding.boot.isChecked())).setNegativeButton(R.string.dialog_negative, null).show();
		Utils.showKeyboard(binding.keyword);
	}

	private void onSave(AppInfo item, String keyword, boolean boot) {
		AppDatabase.get().getTargetDao().insert(Target.create(item, keyword, boot));
		setResult(RESULT_OK);
		finish();
	}
}