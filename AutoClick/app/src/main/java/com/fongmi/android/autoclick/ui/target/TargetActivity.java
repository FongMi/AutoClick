package com.fongmi.android.autoclick.ui.target;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fongmi.android.autoclick.R;
import com.fongmi.android.autoclick.Utils;
import com.fongmi.android.autoclick.databinding.ActivityTargetBinding;
import com.fongmi.android.autoclick.ui.choose.ChooseActivity;
import com.fongmi.android.autoclick.ui.setting.SettingActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class TargetActivity extends AppCompatActivity {

	private ActivityTargetBinding binding;
	private TargetAdapter mAdapter;

	public static void newInstance(Activity activity) {
		activity.startActivity(new Intent(activity, TargetActivity.class));
		activity.finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityTargetBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		initView();
	}

	private void initView() {
		setRecyclerView();
	}

	private void setRecyclerView() {
		binding.recycler.setHasFixedSize(true);
		binding.recycler.setLayoutManager(new LinearLayoutManager(this));
		binding.recycler.setAdapter(mAdapter = new TargetAdapter());
		mAdapter.update();
	}

	public void onAdd(View view) {
		if (Utils.isAccessEnabled()) ChooseActivity.newInstance(this);
		else showDialog();
	}

	private void showDialog() {
		new MaterialAlertDialogBuilder(this)
				.setMessage(R.string.accessibility_request)
				.setPositiveButton(R.string.dialog_positive, (dialog, which) -> Utils.openAccessSetting())
				.setNegativeButton(R.string.dialog_negative, null)
				.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) mAdapter.update();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_target, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		SettingActivity.newInstance(this);
		return true;
	}
}