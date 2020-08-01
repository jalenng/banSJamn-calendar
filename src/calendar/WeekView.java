package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.TreeSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;

/**
 * WeekView has a model, events, now, startDate, and endDate. WeekView will
 * update the Calendar on the right side to show the weekly view of a calendar
 * dependent on the selected date. Afterwards, it will load the events from the
 * model's getEvents().
 * 
 * @author Alein Bartolome
 * @version 7/31/20
 * @copyright banSJamn
 */
public class WeekView extends JPanel implements CalendarView {
	private CalendarModel model;
	private TreeSet<Event> events;
	private LocalDate now;
	private LocalDate startDate;
	private LocalDate endDate;

	/**
	 * Constructor of WeekView
	 * 
	 * @param m name of the CalendarModel
	 */
	public WeekView(CalendarModel m) {
		this.model = m;
		events = m.getEvents();
		display(now);
	}

	/**
	 * Displays the weekly view of the calendar with events from Event.java
	 * 
	 * @param date The LocalDate date of the CalendarModel
	 */
	public void display(LocalDate date) {
		// Clear old components
		this.removeAll();
		this.revalidate();
		now = model.getSelectedDate();

		this.setLayout(new BorderLayout());

		// variables to get the month, day, year, and day of the week
		String currentMonth = now.getMonth().name();
		int day = now.getDayOfMonth();
		int year = now.getYear();

		// creates the title
		JLabel dayTitle = new JLabel();
		if (monthCheck(day) == 1) {
			dayTitle = new JLabel(currentMonth.substring(0, 3) + " - "
					+ now.plusMonths(1).getMonth().name().substring(0, 3) + " " + year);
		}

		else if (monthCheck(day) == -1) {
			dayTitle = new JLabel(now.minusMonths(1).getMonth().name().substring(0, 3) + " - "
					+ currentMonth.substring(0, 3) + " " + year);
		}

		else {
			dayTitle = new JLabel(currentMonth + " " + year);
		}

		JPanel firstPanel = new JPanel();

		GridBagConstraints c = new GridBagConstraints();

		firstPanel.add(dayTitle);

		// second panel holds the days of the week
		JPanel secondPanel = new JPanel();
		secondPanel.setLayout(new GridLayout(2, 7));

		c.gridx = 0;
		c.gridy = 0;

		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(firstPanel, BorderLayout.NORTH);

		c.gridx = 0;
		c.gridy = 1;
		displayDayView(secondPanel, c, giveLocalDate());
		displayEvents(giveLocalDate(), c, secondPanel);
		this.add(secondPanel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

	/**
	 * Verifies if the current day will go over or under the length of the selected
	 * month
	 * 
	 * @param day The current day of the month
	 * @return int to print specific JLabel
	 */
	public int monthCheck(int day) {
		if (day + 7 > now.lengthOfMonth()) {
			return 1;
		}

		if (day - 7 < 0) {
			return -1;
		}
		return 0;
	}// end of monthCheck

	/**
	 * Converts the current day of the week and returns the day of the week with the
	 * starting point set to Sunday
	 * 
	 * @return LocalDate representation of Sunday based on getSelectedDate().
	 */
	public LocalDate giveLocalDate() {
		LocalDate today = model.getSelectedDate();

		switch (today.getDayOfWeek().ordinal()) {
		// if the current day starts on Monday
		case 0:
			LocalDate oneBeforeToday = today.minusDays(1);
			return oneBeforeToday;

		// if the current day starts on Tuesday
		case 1:
			LocalDate twoBeforeToday = today.minusDays(2);
			return twoBeforeToday;

		// if the current day starts on Wednesday
		case 2:
			LocalDate threeBeforeToday = today.minusDays(3);
			return threeBeforeToday;

		// if the current day starts on Thursday
		case 3:
			LocalDate fourBeforeToday = today.minusDays(4);
			return fourBeforeToday;

		// if the current day starts on Friday
		case 4:
			LocalDate fiveBeforeToday = today.minusDays(5);
			return fiveBeforeToday;

		// if the current day starts on Saturday
		case 5:
			LocalDate sixBeforeToday = today.minusDays(6);
			return sixBeforeToday;

		// if the current day starts on Sunday
		case 6:
			LocalDate sunday = today;
			return sunday;
		}// end of switch case
		return null;
	}

	/**
	 * Prints out the day buttons
	 * 
	 * @param secondPanel     Name of the panel used to store the JLabel of the day
	 *                        number
	 * @param c               GridBagConstraints used to store data in GridBagLayout
	 * @param daysBeforeToday the value of the current week's Sunday
	 */
	public void displayDayView(JPanel secondPanel, GridBagConstraints c, LocalDate daysBeforeToday) {
		this.addDayNumber(daysBeforeToday, secondPanel, c);
	}// end of displayDayView

	/**
	 * Method used to fill in the days of the week
	 * 
	 * @param day         name of the variable starting from Sunday
	 * @param secondPanel the panel used to store the creation of additional labels
	 * @param c           GridBagConstraints used to store data in GridBagLayout
	 */
	public void addDayNumber(LocalDate day, JPanel secondPanel, GridBagConstraints c) {
		LocalDate today = model.getSelectedDate();
		c.gridy = 0;
		c.fill = GridBagConstraints.CENTER;

		for (int i = 0; i < 7; i++) {
			if (day.getDayOfMonth() == today.getDayOfMonth()) {
				c.gridx = i;

				String weekdayAndDateText = "<html>" + day.getDayOfWeek() + "<br>"
						+ Integer.toString(day.getDayOfMonth()) + "</html>";
				JLabel todayLabel = new JLabel(weekdayAndDateText);
				todayLabel.setForeground(Color.RED);
				todayLabel.setBackground(Color.BLUE);
				todayLabel.setHorizontalAlignment(SwingConstants.CENTER);
				todayLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
				secondPanel.add(todayLabel, c);

				day = day.plusDays(1);
			} else {
				c.gridx = i;

				String weekdayAndDateText = "<html>" + day.getDayOfWeek() + "<br>"
						+ Integer.toString(day.getDayOfMonth()) + "</html>";
				JLabel dayNumberLabel = new JLabel(weekdayAndDateText);
				dayNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
				dayNumberLabel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
				secondPanel.add(dayNumberLabel, c);
				day = day.plusDays(1);
			}

		}
	}

	/**
	 * Displays the events for each day
	 * 
	 * @param sunday      the starting date to start adding events
	 * @param c           GridBagConstraints used to store events in a GridBagLayout
	 * @param secondPanel
	 */
	public void displayEvents(LocalDate sunday, GridBagConstraints c, JPanel secondPanel) {
		startDate = LocalDate.of(sunday.getYear(), sunday.getMonthValue(), sunday.getDayOfMonth());
		endDate = startDate.plusDays(7);
		TreeSet<LocalDate> dates = new TreeSet<LocalDate>();
		LocalDate current = startDate;
		c.gridx = 0;
		c.gridy = 1;

		while (!current.equals(endDate)) {
			dates.add(current);
			current = current.plusDays(1);
		}

		String eventDisplay = "";

		int i = 0;

		for (LocalDate day : dates) {
			for (Event e : events) {
				if (e.occursOn(day)) {
					eventDisplay = eventDisplay + e.toStringForDayView() + "\n";
				} // end of if statement
			}
			c.gridx = i;
			c.fill = GridBagConstraints.BOTH;
			JTextArea myTextArea = new JTextArea(eventDisplay);

			myTextArea.setLineWrap(true);
			myTextArea.setWrapStyleWord(true);

			// ADDED SCROLL TO THE EVENTS JTEXTFIELD
			JScrollPane scroll = new JScrollPane(myTextArea);
			scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scroll.setPreferredSize(new Dimension(105, 300));

			secondPanel.add(scroll, c);

			// resets eventDisplay to be empty for the next day
			eventDisplay = "";
			i++;
		} // end of for loop

	}

	/**
	 * If there is a change in date, this will update the date.
	 * 
	 * @param indicates that there is a change
	 */
	public void stateChanged(ChangeEvent arg0) {
		this.display(now);
	}

	/**
	 * Moves the selected date forwards by a week
	 */
	public void next() {
		model.advanceSelectedDateByWeek(1);
	}

	/**
	 * Moves the selected date backwards by a week
	 */
	public void previous() {
		model.advanceSelectedDateByWeek(-1);
	}

}
