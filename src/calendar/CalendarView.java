/**
 * 
 */

package calendar;

import java.time.LocalDate;
import javax.swing.event.ChangeListener;

/**
 * CalendarViews can be traversed by changing the selected date 
 * (previous/next), and are updated when the model's state is changed.
 * 
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
