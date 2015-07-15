package com.lslutnfra.futuramagame.data.infraestructure;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.lslutnfra.futuramagame.R;
import com.lslutnfra.futuramagame.domain.entity.Score;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "futuramagame.db";
	private static final int DATABASE_VERSION = 2;	
	
	private Dao<Score, Integer> scoreDao;	
	private RuntimeExceptionDao<Score, Integer> scoreRuntimeExceptionDao;
	
	 public DatabaseHelper(Context context) {		 			 		 
	     super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);	
	     Log.i(this.getClass().getName(), "DatabaseHelper Constructor");
	 }

	@Override public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
		try{
			Log.i(this.getClass().getName(), "onCreate");			
			TableUtils.createTable(connectionSource, Score.class);
		}
		catch(Exception e){
			Log.e(this.getClass().getName(), "Could not create Score table", e);
		}
	}

	@Override public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int arg2, int arg3) {
		try{
			Log.i(this.getClass().getName(), "onUpgrade");			
			TableUtils.dropTable(connectionSource, Score.class, true);
			onCreate(sqliteDatabase);
		}	
		catch(Exception e){
			Log.e(this.getClass().getName(), "Could not Upgrade");
		}
	}
	
	@Override public void close() {
		super.close();
		scoreDao = null;
	}
	
	public Dao<Score, Integer> getScoreDao() throws SQLException{		
		if(this.scoreDao == null){
			this.scoreDao = getDao(Score.class);
		}						
		return this.scoreDao;		
	} 	
	
	public RuntimeExceptionDao<Score, Integer> getRuntimeExceptionScoreDao(){
		if(this.scoreRuntimeExceptionDao == null){
			this.scoreRuntimeExceptionDao = getRuntimeExceptionDao(Score.class);
		}						
		return this.scoreRuntimeExceptionDao;	
	}
}
