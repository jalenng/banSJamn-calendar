package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDate;
import java.util.TreeSet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;

public class WeekView extends JPanel implements CalendarView{
	
	
	private CalendarModel model;
	private TreeSet<Event> events;
	private LocalDate now;
	
	public WeekView(CalendarModel m) {
		this.setLayout(new BorderLayout());
		this.model = m;
		display(now);
		
	}
	
	public void display(LocalDate date) {
		// Clear old components
		this.removeAll();
		this.revalidate();
		
		now = model.getSelectedDate();
		
		// variables to get the month, day, year, and day of the week
		String currentMonth = now.getMonth().name();
		int day = now.getDayOfMonth();
		int year = now.getYear();
		
		// creates the title
		JLabel dayTitle = new JLabel();
		if(monthCheck(day) == 1) {
			dayTitle = new JLabel(" " + currentMonth.substring(0,3) + " - " + now.plusMonths(1).getMonth().name().substring(0,3) + " " + year);
		}
		
		else if(monthCheck(day) == -1) {
			dayTitle = new JLabel(" " + now.minusMonths(1).getMonth().name().substring(0,3) + " - " + currentMonth.substring(0,3) + " " + year);
		}

		else {
			dayTitle = new JLabel(" " + currentMonth + " " + year);
		}
		// holds the previous and next buttons and date
		JPanel firstPanel = new JPanel();
		firstPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		firstPanel.add(dayTitle, c);

		
		// second panel holds the days of the week
		JPanel secondPanel = new JPanel();
		
		String[] days = {"SAT", "FRI", "THU", "WED", "TUE", "MON", "SUN"};
		for(int i = 0; i < days.length; i++) {
			c.fill = GridBagConstraints.HORIZONTAL;
			secondPanel.add(new JLabel(days[i]), c, SwingConstants.CENTER);
			// for some reason, this is adding the labels in reverse order of the String[] days
		}
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		firstPanel.add(secondPanel,c);
		this.add(firstPanel, BorderLayout.NORTH);
		displayDayView();

	}
	
	
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
	 * Prints out the day buttons
	 * */
	public void displayDayView() {
		LocalDate today = model.getSelectedDate();
		
		JPanel daysPanel = new JPanel();
		daysPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		switch(today.getDayOfWeek().ordinal()) {
		
		// if the current day starts on Monday
		case 0:
			LocalDate oneBeforeToday = today.minusDays(1);
			addDayNumber(oneBeforeToday, today, daysPanel);
			break;
		
		// if the current day starts on Tuesday	
		case 1:
			LocalDate twoBeforeToday = today.minusDays(2);
			addDayNumber(twoBeforeToday, today, daysPanel);
			break;
		
		// if the current day starts on Wednesday
		case 2:
			LocalDate threeBeforeToday = today.minusDays(2);
			addDayNumber(threeBeforeToday, today, daysPanel);
			break;
		
		// if the current day starts on Thursday
		case 3:
			LocalDate fourBeforeToday = today.minusDays(4);
			addDayNumber(fourBeforeToday, today, daysPanel);
			break;
		
		//if the current day starts on Friday
		case 4:
			LocalDate fiveBeforeToday = today.minusDays(5);
			addDayNumber(fiveBeforeToday, today, daysPanel);
			break;
		
		//if the current day starts on Saturday
		case 5:
			LocalDate sixBeforeToday = today.minusDays(6);
			addDayNumber(sixBeforeToday, today, daysPanel);
			break;
			
		// if the current day starts on Sunday
		case 6:
			LocalDate sunday = today;
			addDayNumber(sunday, today, daysPanel);
			break;
		}// end of switch case
	}// end of displayDayView
	
	// method to fill in the days of the week
	public void addDayNumber(LocalDate day, LocalDate today, JPanel daysPanel) {
		for (int i = 0; i < 7; i++) {
			if(day.getDayOfMonth() == today.getDayOfMonth()) {
				JButton todayButton = new JButton(Integer.toString(day.getDayOfMonth()));
				todayButton.setForeground(Color.WHITE);
				todayButton.setBackground(Color.BLUE);
				daysPanel.add(todayButton);
				
				day = day.plusDays(1);
			}
			else {
				daysPanel.add(new JButton(Integer.toString(day.getDayOfMonth())));
				day = day.plusDays(1);
			}
		}
		this.add(daysPanel, BorderLayout.CENTER);
	}
	
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
