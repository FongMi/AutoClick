package com.fongmi.android.autoclick.service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.fongmi.android.autoclick.db.AppDatabase;
import com.fongmi.android.autoclick.model.Target;

import java.util.Collections;
import java.util.List;

public class AutoService extends AccessibilityService {

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		for (Target item : AppDatabase.get().getTargetDao().getAll()) onClick(item);
	}

	private void onClick(Target item) {
		for (AccessibilityNodeInfo info : find(item.getKeyword())) {
			if (info.getPackageName().equals(item.getPack())) {
				info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
			} else if (info.getPackageName().equals("com.android.systemui") && find(item.getName()).size() > 0) {
				info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
			}
		}
	}

	private List<AccessibilityNodeInfo> find(String text) {
		if (getRootInActiveWindow() == null) return Collections.emptyList();
		return getRootInActiveWindow().findAccessibilityNodeInfosByText(text);
	}

	@Override
	public void onInterrupt() {

	}
}
