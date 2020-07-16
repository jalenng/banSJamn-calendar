/**
 * 
 */

package calendar;

import javax.swing.event.ChangeListener;

public interface CalendarView extends ChangeListener {
	
	public void next();
	public void previous();
	
}
