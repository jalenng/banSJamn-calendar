package calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeSet;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A calendar model holds the selected date of the program, as well as houses a
 * data structure to aggregate and manage events
 * 
 * @author Jalen
 * @version 7/31/20
 * @copyright banSJamn
 */
public class CalendarModel {

	private TreeSet<Event> events;
	private ArrayList<ChangeListener> listeners;
	private static LocalDate selectedDate;

	/**
	 * Default constructor initializes values
	 */
	public CalendarModel() {
		events = new TreeSet<>();
		listeners = new ArrayList<ChangeListener>();
		selectedDate = LocalDate.now();
	}

	/**
	 * Returns all events of the calendar
	 * 
	 * @return TreeSet of all events
	 */
	public TreeSet<Event> getEvents() {
		return events;
	}

	/**
	 * Returns all one-time events of the calendar that occur on the given date
	 * 
	 * @param date Date of one-time events to retrieve
	 * @return TreeSet of all one-time events on given date
	 */
	public TreeSet<Event> getOneTimeEvents(LocalDate date) {
		TreeSet<Event> result = new TreeSet<>();
		for (Event e : events) {
			if (!e.isRecursive() && e.getDate().equals(date))
				result.add(e);
		}
		return result;
	}

	/**
	 * Returns the selected LocalDate
	 * 
	 * @return the selected LocalDate
	 */
	public LocalDate getSelectedDate() {
		return selectedDate;
	}

	/**
	 * Changes the selected LocalDate
	 * 
	 * @param date new date to be selected
	 */
	public void setSelectedDate(LocalDate date) {
		selectedDate = date;
		update();
	}

	/**
	 * Advances the selected date by days
	 * 
	 * @param i number of dates to advance selected date. Negative numbers go back
	 *          in time.
	 */
	public void advanceSelectedDateByDays(int i) {
		selectedDate = selectedDate.plusDays(i);
		update();
	}

	/**
	 * Advances the selected date by weeks
	 * 
	 * @param i number of weeks to advance selected date. Negative numbers go back
	 *          in time.
	 */
	public void advanceSelectedDateByWeek(int i) {
		selectedDate = selectedDate.plusWeeks(i);
		update();
	}

	/**
	 * Advances the selected date by months. Sets selected date to the first day of
	 * the month.
	 * 
	 * @param i number of months to advance selected date. Negative numbers go back
	 *          in time.
	 */
	public void advanceSelectedDateByMonth(int i) {
		selectedDate = selectedDate.plusMonths(i).withDayOfMonth(1);
		update();
	}

	/**
	 * Attaches a Change Listener to this model
	 * 
	 * @param listener listener to attach
	 */
	public void attach(ChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * Calls stateChanged to all the change listeners attached to the model
	 */
	private void update() {
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * Adds event to calendar if it does not conflict with existing events
	 * 
	 * @param e	Event to add
	 * @return true if event was added, false if conflict detected
	 */
	public boolean addEvent(Event e) {
		for (Event i : events) {
			if (i.conflictsWith(e)) {
				return false;
			}
		}
		events.add(e);
		update();
		return true;
	}

	/**
	 * Adds event to calendar regardless of conflict
	 * 
	 * @param e Event to add
	 */
	public void addEventForce(Event e) {
		events.add(e);
		update();
	}

}
