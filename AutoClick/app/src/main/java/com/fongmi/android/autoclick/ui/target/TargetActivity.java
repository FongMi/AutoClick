package com.fongmi.android.autoclick.ui.target;

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

public class TargetActivity extends AppCompatActivity {

	private ActivityTargetBinding binding;
	private TargetAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityTargetBinding.inflate(getLayoutInflater());
		View view = binding.getRoot();
		setContentView(view);
		initView();
	}

	private void initView() {
		Utils.checkSetting();
		setRecyclerView();
		mAdapter.update();
	}

	private void setRecyclerView() {
		mAdapter = new TargetAdapter();
		binding.recycler.setHasFixedSize(true);
		binding.recycler.setLayoutManager(new LinearLayoutManager(this));
		binding.recycler.setAdapter(mAdapter);
	}

	public void onAdd(View view) {
		ChooseActivity.newInstance(this);
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