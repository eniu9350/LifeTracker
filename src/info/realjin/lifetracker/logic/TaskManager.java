package info.realjin.lifetracker.logic;

import info.realjin.lifetracker.entity.Task;
import info.realjin.lifetracker.entity.TimeSlice;
import info.realjin.lifetracker.persistence.Dao;
import info.realjin.lifetracker.persistence.DbHelper;
import android.content.Context;
import android.util.Log;

public class TaskManager {
	private Dao dao;
	
	/**
	 * @param ctx context for creating dao
	 */
	public TaskManager(Context ctx){
		DbHelper helper = DbHelper.getHelper(ctx);
		dao = new Dao(helper);
	}
	
	public void start(Task t) {
		t.setLastStart(System.currentTimeMillis());

		// update state
		t.setState(Task.State.RUNNING);
	}

	public void sleep(Task t) {
		long now = System.currentTimeMillis();
		if (t.getState() == Task.State.RUNNING) {
			TimeSlice ts = new TimeSlice(t.getLastStart(), now);
			t.addTimeSlice(ts);

			// update state
			t.setState(Task.State.SLEEPING);
		} else {
			Log.e("Error when sleeping task",
					"RUNNING expected, but current state is "
							+ t.getState().name());
		}
	}

	public void wakeup(Task t) {
		long now = System.currentTimeMillis();
		if (t.getState() == Task.State.SLEEPING) {
			t.setLastStart(now);

			// update state
			t.setState(Task.State.RUNNING);
		} else {
			Log.e("Error when sleeping task",
					"RUNNING expected, but current state is "
							+ t.getState().name());
		}

	}

	public void terminate(Task t) {
		long now = System.currentTimeMillis();
		if (t.getState() == Task.State.INIT && t.getState() == Task.State.END) {
			Log.e("Error when ending task",
					"RUNNING or SLEEPING expected, but current state is "
							+ t.getState().name());
		} else if (t.getState() == Task.State.SLEEPING) {
			// do nothing
		} else { // RUNNING
			TimeSlice ts = new TimeSlice(t.getLastStart(), now);
			t.addTimeSlice(ts);
		}

		// update state
		t.setState(Task.State.END);
	}
	
	/**
	 * save history of task running
	 */
	public void save(Task t){
		
	}

}
