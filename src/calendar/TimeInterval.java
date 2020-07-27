package calendar;

import java.time.LocalTime;

/**
 * A time interval is a span of time that has a starting time and ending time
 * @author Jalen
 * @version 1.0
 * @copyright banSJamn
 */
public class TimeInterval {
	
	LocalTime startTime, endTime;
	
	public TimeInterval(LocalTime startTime, LocalTime endTime) {		
		
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}
	
	/**
	 * Checks to see if this time interval overlaps a given time interval
	 * @param that	time interval to check for conflict
	 * @return	true if conflict detected, false otherwise
	 */
	public boolean conflictsWith(TimeInterval that) {
		LocalTime startTime1 = this.getStartTime();
		LocalTime endTime1 = this.getEndTime();
		LocalTime startTime2 = that.getStartTime();
		LocalTime endTime2 = that.getEndTime();
		
		if ((startTime1.isBefore(endTime2) && endTime1.isAfter(startTime2))
				|| (startTime2.isBefore(endTime1) && endTime2.isAfter(startTime1))) {
			return true;
		}
		return false;
	}
	
}
	

