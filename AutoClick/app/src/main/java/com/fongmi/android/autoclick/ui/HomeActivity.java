package com.fongmi.android.autoclick.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fongmi.android.autoclick.Utils;
import com.fongmi.android.autoclick.databinding.ActivityHomeBinding;
import com.fongmi.android.autoclick.ui.adapter.TargetAdapter;

public class HomeActivity extends AppCompatActivity {

	private ActivityHomeBinding binding;
	private TargetAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityHomeBinding.inflate(getLayoutInflater());
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
}