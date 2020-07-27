package calendar;

import javax.swing.event.ChangeListener;

/**
 * CalendarViews can be traversed by changing the selected date 
 * (previous/next), and are updated when the model's state is changed.
 * @author jalen
 * @version 1.0
 * @copyright banSJamn
 */

public interface CalendarView extends ChangeListener {
	
	/**
	 * Moves the selected date forwards by the Calendar View's time frame 
	 * (a day, week, month, etc)
	 */
	public void next();
	
	/**
	 * Moves the selected date forwards by the Calendar View's time frame 
	 * (a day, week, month, etc)
	 */
	public void previous();
}
