package com.fongmi.android.autoclick.bean;

import android.graphics.drawable.Drawable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fongmi.android.autoclick.App;
import com.fongmi.android.autoclick.R;
import com.fongmi.android.autoclick.Utils;

@Entity
public class Target {

	@PrimaryKey(autoGenerate = true)
	private int id;
	private String keyword;
	private String name;
	private String pack;
	private boolean boot;

	public static Target create(AppInfo info, String keyword, boolean boot) {
		return new Target(info.getName(), info.getPack(), keyword, boot);
	}

	public Target(String name, String pack, String keyword, boolean boot) {
		this.keyword = keyword;
		this.name = name;
		this.pack = pack;
		this.boot = boot;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public String getName() {
		return name;
	}

	public String getPack() {
		return pack;
	}

	public boolean isBoot() {
		return boot;
	}

	public Drawable getIcon() {
		try {
			return App.get().getPackageManager().getApplicationIcon(getPack());
		} catch (Exception e) {
			return null;
		}
	}

	public String getInfo() {
		StringBuilder sb = new StringBuilder();
		if (isBoot() && getKeyword().length() > 0) sb.append(Utils.getString(R.string.choose_boot)).append(", ").append(Utils.getString(R.string.choose_keyword)).append(" : ").append(getKeyword());
		else if (getKeyword().length() > 0) sb.append(Utils.getString(R.string.choose_keyword)).append(" : ").append(getKeyword());
		else if (isBoot()) sb.append(Utils.getString(R.string.choose_boot));
		return sb.toString();
	}
}
