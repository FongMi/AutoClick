package com.fongmi.android.autoclick.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fongmi.android.autoclick.db.AppDatabase;
import com.fongmi.android.autoclick.model.Target;

import java.util.List;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		List<Target> items = AppDatabase.get().getTargetDao().getAll();
		for (Target item : items) if (item.isBoot()) openApp(context, item.getPack());
	}

	private void openApp(Context context, String pack) {
		Intent intent = context.getPackageManager().getLaunchIntentForPackage(pack);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
