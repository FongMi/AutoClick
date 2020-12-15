package com.fongmi.android.autoclick.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import com.fongmi.android.autoclick.db.AppDatabase;
import com.fongmi.android.autoclick.model.Target;

import java.util.List;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		List<Target> items = AppDatabase.get().getTargetDao().getBoot();
		for (Target item : items) openApp(context, item.getPack());
		resetWifi(context);
	}

	private void openApp(Context context, String pack) {
		Intent intent = context.getPackageManager().getLaunchIntentForPackage(pack);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	private void resetWifi(Context context) {
		WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		wifi.setWifiEnabled(false);
		wifi.setWifiEnabled(true);
	}
}
