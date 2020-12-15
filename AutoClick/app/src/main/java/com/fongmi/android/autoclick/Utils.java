package com.fongmi.android.autoclick;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.os.Build;
import android.provider.Settings;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

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

	public static List<AppInfo> getApps() {
		List<AppInfo> list = new ArrayList<>();
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> packages = App.get().getPackageManager().queryIntentActivities(intent, 0);
		for (ResolveInfo info : packages) list.add(AppInfo.get(info));
		return list;
	}

	public static void checkSetting() {
		if (!isServiceEnabled()) openSetting();
	}

	private static void openSetting() {
		Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		App.get().startActivity(intent);
	}

	private static boolean isServiceEnabled() {
		String prefString = Settings.Secure.getString(App.get().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
		return prefString != null && prefString.contains(App.get().getPackageName() + "/" + AutoService.class.getName());
	}

	public static boolean isConnected() {
		ConnectivityManager cm = (ConnectivityManager) App.get().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			return cm.getNetworkCapabilities(cm.getActiveNetwork()) != null;
		} else {
			return cm.getActiveNetworkInfo() != null;
		}
	}
}

