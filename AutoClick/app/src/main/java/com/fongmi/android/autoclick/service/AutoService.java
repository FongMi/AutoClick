package com.fongmi.android.autoclick.service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.fongmi.android.autoclick.Constant;
import com.fongmi.android.autoclick.Prefers;
import com.fongmi.android.autoclick.Utils;
import com.fongmi.android.autoclick.db.AppDatabase;
import com.fongmi.android.autoclick.model.Target;

import java.util.Collections;
import java.util.List;

public class AutoService extends AccessibilityService {

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		try {
			for (Target item : AppDatabase.get().getTargetDao().getAll()) find(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void find(Target item) {
		for (AccessibilityNodeInfo info : find(item.getKeyword())) {
			if (info.getPackageName().equals(item.getPack())) {
				onClick(info);
			} else if (info.getPackageName().equals(Constant.SYSTEM_UI) && find(item.getName()).size() > 0) {
				onClick(info);
			}
		}
	}

	private void onClick(AccessibilityNodeInfo info) {
		if (Prefers.isKeepWiFi()) {
			if (Utils.isOnline()) info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
		} else {
			info.performAction(AccessibilityNodeInfo.ACTION_CLICK);
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
