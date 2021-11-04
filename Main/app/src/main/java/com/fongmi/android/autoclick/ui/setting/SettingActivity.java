package com.fongmi.android.autoclick.ui.setting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fongmi.android.autoclick.R;

public class SettingActivity extends AppCompatActivity {

	public static void newInstance(Activity activity) {
		activity.startActivity(new Intent(activity, SettingActivity.class));
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		getSupportFragmentManager().beginTransaction().replace(R.id.content, new SettingFragment()).commit();
	}
}
