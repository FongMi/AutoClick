package com.fongmi.android.autoclick;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.fongmi.android.autoclick.model.AppInfo;
import com.fongmi.android.autoclick.service.AutoService;

import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static String getString(int resId) {
		return App.get().getString(resId);
	}

	public static void showKeyboard(EditText editText) {
		editText.requestFocus();
		editText.postDelayed(() -> {
			InputMethodManager imm = (InputMethodManager) App.get().getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm != null) imm.showSoftInput(editText, 0);
		}, 250);
	}

	public static boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) App.get().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) return cm.getNetworkCapabilities(cm.getActiveNetwork()) != null;
		else return cm.getActiveNetworkInfo() != null;
	}

	public static boolean isOffline() {
		return !isOnline();
	}

	public static List<AppInfo> getApps() {
		List<AppInfo> list = new ArrayList<>();
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> packages = App.get().getPackageManager().queryIntentActivities(intent, 0);
		for (ResolveInfo info : packages) list.add(AppInfo.get(info));
		return list;
	}

	public static boolean isAccessEnabled() {
		String prefString = Settings.Secure.getString(App.get().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
		return prefString != null && prefString.contains(App.get().getPackageName() + "/" + AutoService.class.getName());
	}

	public static void openAccessSetting() {
		Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		App.get().startActivity(intent);
	}

	public static void checkBatterySetting() {
		PowerManager pm = (PowerManager) App.get().getSystemService(Context.POWER_SERVICE);
		if (!pm.isIgnoringBatteryOptimizations(App.get().getPackageName())) ignoreBattery();
		else Toast.makeText(App.get(), R.string.setting_battery_closed, Toast.LENGTH_SHORT).show();
	}

	private static void ignoreBattery() {
		Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
		intent.setData(Uri.parse("package:" + App.get().getPackageName()));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		App.get().startActivity(intent);
	}
}

