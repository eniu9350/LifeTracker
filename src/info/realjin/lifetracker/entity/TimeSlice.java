package info.realjin.lifetracker.entity;

/**
 * @author REALJIN
 * 
 */
public class TimeSlice {
	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

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
