package com.fongmi.android.autoclick.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.fongmi.android.autoclick.bean.Target;

import java.util.List;

@Dao
public abstract class TargetDao {

	@Query("SELECT * FROM target")
	public abstract List<Target> getAll();

	@Query("SELECT * FROM target WHERE boot = 1")
	public abstract List<Target> getBoot();

	@Query("DELETE FROM target WHERE id = :id")
	public abstract void delete(int id);

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	public abstract void insert(Target item);
}
