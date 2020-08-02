package calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * An event has a name, date, and time interval. Events can also be recursive so
 * that they repeat on certain days of the week and from a starting date to an
 * ending date.
 * 
 * @author Jalen, Shana
 * @version 7/31/20
 * @copyright banSJamn
 */
public class Event implements Comparable<Event> {

	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
	private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
	private String dowOrder = "MTWHFAS";

	private String name;
	private LocalDate date;
	private TimeInterval timeInterval;
	private boolean isRecursive;

	// for recurring events
	private ArrayList<DayOfWeek> days;
	private LocalDate startDate, endDate;

	/**
	 * Instantiates a new recurring event
	 * 
	 * @param name      Name of event
	 * @param days      String of character(s) in set {SMTWHFA} to indicate days of
	 *                  the week to recurse
	 * @param startTime Starting time of event
	 * @param endTime   Ending time of event
	 * @param startDate Starting date of recursion
	 * @param endDate   Ending date of recursion
	 */
	public Event(String name, String days, LocalTime startTime, LocalTime endTime, LocalDate startDate,
			LocalDate endDate) {
		this.name = name;
		this.date = startDate;

		this.days = new ArrayList<>();
		char[] daysChar = days.toUpperCase().toCharArray();
		for (char c : daysChar) {
			this.days.add(DayOfWeek.of(dowOrder.indexOf(c) + 1));
		}

		this.timeInterval = new TimeInterval(startTime, endTime);
		this.startDate = startDate;
		this.endDate = endDate;
		this.isRecursive = true;
	}

	/**
	 * Instantiates a one-time, nonrecurring event
	 * 
	 * @param name      Name of event
	 * @param date      Date of event
	 * @param startTime Starting time of event
	 * @param endTime   Ending time of event
	 */
	public Event(String name, LocalDate date, LocalTime startTime, LocalTime endTime) {
		this.name = name;
		this.date = date;
		this.timeInterval = new TimeInterval(startTime, endTime);
		this.isRecursive = false;
	}

	/**
	 * Returns the event name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the event date
	 * 
	 * @return date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Checks to see if an event occurs on a given date. Useful for recurring
	 * events, but also works on nonrecurring events.
	 * 
	 * @param Date to check if event occurs on
	 * @return true if event occurs on the given date, false otherwise
	 */
	public boolean occursOn(LocalDate date) {
		if (!this.isRecursive && this.date.equals(date))
			return true;
		else if (this.isRecursive() && !this.getStartDate().isAfter(date) && !this.getEndDate().isBefore(date)
				&& this.getDays().contains(date.getDayOfWeek()))
			return true;
		return false;
	}

	/**
	 * Returns the start date
	 * 
	 * @return start date
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * Returns the end date
	 * 
	 * @return end date
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * Returns an ArrayList of DayOfWeeks the recurring event recurs on
	 * 
	 * @return ArrayList of DayOfWeeks
	 */
	public ArrayList<DayOfWeek> getDays() {
		return days;
	}

	/**
	 * Returns the TimeInterval of the event that houses the startTime and endTime
	 * 
	 * @return TimeInterval of event
	 */
	public TimeInterval getTimeInterval() {
		return timeInterval;
	}

	/**
	 * Tells whether it is recursive or not
	 * 
	 * @return isRecursive
	 */
	public boolean isRecursive() {
		return isRecursive;
	}

	/**
	 * Checks to see if this event conflicts with a given one-time event
	 * 
	 * @param that one-time, nonrecurring event to check for conflict
	 * @return true if conflict detected, false otherwise
	 */
	public boolean conflictsWith(Event that) { // "that" will never be recurring
		return this.occursOn(that.getDate()) && this.timeInterval.conflictsWith(that.timeInterval);
	}

	/**
	 * Compares two events by its date, then starting time, then by name.
	 * 
	 * @param that
	 * @return an integer < 0 if this comes before that, > 0 if this comes after
	 *         that, and = 0 if this is the same as that.
	 */
	public int compareTo(Event that) {
		LocalDateTime startTime1 = LocalDateTime.of(this.date, this.timeInterval.getStartTime());
		LocalDateTime startTime2 = LocalDateTime.of(that.date, that.timeInterval.getStartTime());
		if (startTime1.compareTo(startTime2) != 0)
			return startTime1.compareTo(startTime2);
		return this.getName().compareTo(that.getName());
	}

	/**
	 * Returns the string representation of the event in a format compatible with
	 * events.txt and output.txt
	 * 
	 * @return String representation of event
	 */
	public String toString() {
		if (!this.isRecursive()) {
			return String.format("%s \n%s %s %s", this.name, this.date.format(dateFormatter),
					this.timeInterval.getStartTime().format(timeFormatter),
					this.timeInterval.getEndTime().format(timeFormatter));
		} else {
			String daysAbbrev = "";
			for (DayOfWeek d : days) {
				daysAbbrev += dowOrder.charAt(d.getValue() - 1);
			}
			return String.format("%s \n%s %s %s %s %s", this.name, daysAbbrev,
					this.timeInterval.getStartTime().format(timeFormatter),
					this.timeInterval.getEndTime().format(timeFormatter), this.startDate.format(dateFormatter),
					this.endDate.format(dateFormatter));
		}
	}

	/**
	 * Returns the string representation of the event in a format suitable for
	 * day-view
	 * 
	 * @return String representation of event
	 */
	public String toStringForDayView() {
		return String.format("%s - %s: %s", this.timeInterval.getStartTime().format(timeFormatter),
				this.timeInterval.getEndTime().format(timeFormatter), this.name);
	}

}
