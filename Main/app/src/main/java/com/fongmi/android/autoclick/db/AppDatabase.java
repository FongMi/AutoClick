package com.fongmi.android.autoclick.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.fongmi.android.autoclick.App;
import com.fongmi.android.autoclick.bean.Target;

@Database(entities = {Target.class}, version = AppDatabase.VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

	public static final int VERSION = 2;

	private static volatile AppDatabase instance;

	public static synchronized AppDatabase get() {
		if (instance == null) instance = create(App.get());
		return instance;
	}

	private static AppDatabase create(Context context) {
		return Room.databaseBuilder(context, AppDatabase.class, "database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
	}

	public abstract TargetDao getTargetDao();
}
