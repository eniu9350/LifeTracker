package info.realjin.lifetracker.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author REALJIN
 * 
 */
public class Task {
	private List<TimeSlice> timeSlices;

	public enum State {
		INIT, RUNNING, /**
		 * paused
		 */
		SLEEPING, END
	}

	private State state;

	/**
	 * time of last start (valid only if state is RUNNING)
	 */
	private long lastStart;

	public void addTimeSlice(TimeSlice ts) {
		timeSlices.add(ts);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public long getLastStart() {
		return lastStart;
	}

	public void setLastStart(long lastStart) {
		this.lastStart = lastStart;
	}

	public Task() {
		timeSlices = new ArrayList<TimeSlice>();
		state = State.INIT;
	}

	public List<TimeSlice> getTimeSlices() {
		return timeSlices;
	}

	public void setTimeSlices(List<TimeSlice> timeSlices) {
		this.timeSlices = timeSlices;
	}

}
