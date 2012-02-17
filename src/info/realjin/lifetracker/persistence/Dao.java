package info.realjin.lifetracker.persistence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Dao {
	DbHelper helper;
	SQLiteDatabase wdb;
	SQLiteDatabase rdb;

	public Dao(DbHelper hlp) {
		helper = hlp;
		wdb = hlp.getWritableDatabase();
		rdb = hlp.getReadableDatabase();
	}

	/**
	 * check if init info exist(by checking if table exist)
	 */
	public boolean isInfoExist() {
		Cursor c;
		c = rdb.rawQuery(
				"select DISTINCT tbl_name from sqlite_master where tbl_name = 'INFO'",
				null);
		if (c != null && c.getCount() > 0) { // exist
			return true;
		} else {
			return false;
		}
	}

	public void createInfo() {
		wdb.execSQL("create table INFO (_id INTEGER PRIMARY KEY  AUTOINCREMENT, name TEXT, lasting INTEGER, created INTEGER);");
	}
}
