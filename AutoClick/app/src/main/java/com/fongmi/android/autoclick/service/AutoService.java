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
		for (Target item : AppDatabase.get().getTargetDao().getAll()) clickWord(item);
	}

	private boolean shouldClick(AccessibilityNodeInfo info, Target item) {
		return info.getPackageName() != null && (info.getPackageName().equals(item.getPack()) || info.getPackageName().equals("com.android.systemui"));
	}

	private void clickWord(Target item) {
		try {
			if (getRootInActiveWindow() == null) return;
			if (TextUtils.isEmpty(item.getKeyword())) return;
			List<AccessibilityNodeInfo> list = getRootInActiveWindow().findAccessibilityNodeInfosByText(item.getKeyword());
			for (AccessibilityNodeInfo info : list) if (shouldClick(info, item)) info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onInterrupt() {

	}
}
