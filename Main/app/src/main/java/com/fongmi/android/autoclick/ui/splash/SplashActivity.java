package com.fongmi.android.autoclick.ui.splash;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fongmi.android.autoclick.ui.target.TargetActivity;

public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new Handler().postDelayed(() -> TargetActivity.newInstance(this), 250);
	}
}
