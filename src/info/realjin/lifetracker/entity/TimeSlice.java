package info.realjin.lifetracker.entity;

/**
 * @author REALJIN
 * 
 */
public class TimeSlice {
	public TimeSlice(long start, long end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * start time (in ms)
	 */
	private long start;

	/**
	 * end time (in ms)
	 */
	private long end;

}
