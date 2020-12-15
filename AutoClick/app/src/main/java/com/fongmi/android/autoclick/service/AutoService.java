package com.fongmi.android.autoclick.service;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.fongmi.android.autoclick.db.AppDatabase;
import com.fongmi.android.autoclick.model.Target;

import java.util.List;

public class AutoService extends AccessibilityService {

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		List<Target> items = AppDatabase.get().getTargetDao().getAll();
		for (Target item : items) if (shouldClick(event, item.getPack())) clickWord(item.getKeyword());
	}

	private boolean shouldClick(AccessibilityEvent event, String pack) {
		return event.getPackageName().equals(pack) || event.getPackageName().equals("com.android.systemui");
	}

	private void clickWord(String word) {
		try {
			if (TextUtils.isEmpty(word)) return;
			if (getRootInActiveWindow() == null) return;
			List<AccessibilityNodeInfo> list = getRootInActiveWindow().findAccessibilityNodeInfosByText(word);
			for (AccessibilityNodeInfo item : list) item.performAction(AccessibilityNodeInfo.ACTION_CLICK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onInterrupt() {

	}
}
