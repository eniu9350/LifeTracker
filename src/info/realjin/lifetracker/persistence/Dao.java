package info.realjin.lifetracker.persistence;

import info.realjin.lifetracker.entity.Task;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Dao {
	private static String TABLENAME_TIMESLICE = "LIFETRACKER_TIMESLICE";
	private static String TABLECOLUMNNAME_TIMESLICE_ID = "_id";
	/**
	 * task id
	 */
	private static String TABLECOLUMNNAME_TIMESLICE_TID = "tid";
	private static String TABLECOLUMNNAME_TIMESLICE_STARTTIME = "starttime";
	private static String TABLECOLUMNNAME_TIMESLICE_STOPTIME = "stoptime";
	private static String TABLECOLUMNNAME_TIMESLICE_FIRST = "first";
	private static String TABLECOLUMNNAME_TIMESLICE_LAST = "last";

	private static String TABLENAME_TASK = "LIFETRACKER_TASK";
	private static String TABLECOLUMNNAME_TASK_ID = "_id";
	private static String TABLECOLUMNNAME_TASK_NAME = "name";
	private static String TABLECOLUMNNAME_TASK_LASTING = "lasting";
	private static String TABLECOLUMNNAME_TASK_CREATED = "created";

	// private static String TABLENAME_INFO = "LIFETRACKER_INFO";

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

	/**
	 * a dao's job?
	 */
	public void initTables() {
		String sql;

		// init table for storing timeslice
		sql = "create table if not exists " + TABLENAME_TIMESLICE + " ("
				+ TABLECOLUMNNAME_TIMESLICE_ID
				+ " integer primary key autoincrement not null,"
				+ TABLECOLUMNNAME_TIMESLICE_TID + " integer,"
				+ TABLECOLUMNNAME_TIMESLICE_STARTTIME + " integer,"
				+ TABLECOLUMNNAME_TIMESLICE_STOPTIME + " integer,"
				+ TABLECOLUMNNAME_TIMESLICE_FIRST + " integer,"
				+ TABLECOLUMNNAME_TIMESLICE_LAST + " integer" + ");";
		wdb.execSQL(sql);

		// init table for storing task
		sql = ("create table " + TABLENAME_TASK + " ("
				+ TABLECOLUMNNAME_TASK_ID
				+ " INTEGER PRIMARY KEY  AUTOINCREMENT, "
				+ TABLECOLUMNNAME_TASK_NAME + " TEXT, "
				+ TABLECOLUMNNAME_TASK_LASTING + " INTEGER, "
				+ TABLECOLUMNNAME_TASK_CREATED + " INTEGER);");
		wdb.execSQL(sql);
	}

	public void createInfo() {
		// mmm: not implemented yet
	}

	public void saveTask(Task t) {

	}
}
