package info.realjin.lifetracker.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper {
	private static final String DB_NAME = "lifetracker_test";
	private static final int DB_VERSION = 1;

	private Context context;
	private CustomSQLiteOpenHelper helper;

	private static DbHelper instance;

	private Dao daoInstance; // also singleton

	public synchronized static DbHelper getHelper(Context ctx) {
		if (instance == null) {
			instance = new DbHelper(ctx);
		}
		return instance;
	}

	public SQLiteDatabase getWritableDatabase() {
		if (instance == null) {
			new Exception(
					"db helper not initialized before calling getDb(), use getHelper() first to gurantee initialization.")
					.printStackTrace();
		}
		return helper.getWritableDatabase();
	}

	public SQLiteDatabase getReadableDatabase() {
		if (instance == null) {
			new Exception(
					"db helper not initialized before calling getDb(), use getHelper() first to gurantee initialization.")
					.printStackTrace();
		}
		return helper.getReadableDatabase();
	}

	public Dao getDao() {
		if (daoInstance == null) {
			daoInstance = new Dao(this);
		}
		return daoInstance;
	}

	private DbHelper(Context ctx) {
		context = ctx;
		helper = new CustomSQLiteOpenHelper(context);
	}

	private class CustomSQLiteOpenHelper extends SQLiteOpenHelper {
		public CustomSQLiteOpenHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}
}