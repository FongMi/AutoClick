package com.fongmi.android.autoclick.ui.setting;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.fongmi.android.autoclick.Constant;
import com.fongmi.android.autoclick.Prefers;
import com.fongmi.android.autoclick.R;
import com.fongmi.android.autoclick.Utils;

public class SettingFragment extends PreferenceFragmentCompat {

	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
		setPreferencesFromResource(R.xml.setting, rootKey);
		setWifi();
	}

	private void setWifi() {
		SwitchPreference pref = findPreference(Constant.SETTING_WIFI);
		pref.setChecked(Prefers.getBoolean(Constant.SETTING_WIFI));
		pref.setOnPreferenceChangeListener((Preference preference, Object newValue) -> {
			Prefers.put(Constant.SETTING_WIFI, newValue);
			return true;
		});
	}

	@Override
	public boolean onPreferenceTreeClick(Preference preference) {
		switch (preference.getKey()) {
			case Constant.SETTING_ACCESS:
				Utils.openAccessSetting();
				return true;
			case Constant.SETTING_BATTERY:
				Utils.checkBatterySetting();
				return true;
			case Constant.SETTING_WIFI:
				return true;
			default:
				return super.onPreferenceTreeClick(preference);
		}
	}
}
