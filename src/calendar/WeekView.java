package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.TreeSet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;

public class WeekView extends JPanel implements CalendarView{
	
	
	private CalendarModel model;
	private TreeSet<Event> events;
	private LocalDate now;
	private LocalDate startDate;
	private LocalDate endDate;
	public WeekView(CalendarModel m) {
		this.model = m;
		events = m.getEvents();
		display(now);
		
	}
	
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
		if(monthCheck(day) == 1) {
			dayTitle = new JLabel(currentMonth.substring(0,3) + " - " + now.plusMonths(1).getMonth().name().substring(0,3) + " " + year);
		}
		
		else if(monthCheck(day) == -1) {
			dayTitle = new JLabel(now.minusMonths(1).getMonth().name().substring(0,3) + " - " + currentMonth.substring(0,3) + " " + year);
		}

		else {
			dayTitle = new JLabel(currentMonth + " " + year);
		}

		JPanel firstPanel = new JPanel();
		firstPanel.setLayout(new GridLayout(3,1));
		//firstPanel.setPreferredSize(new Dimension(100,100));
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		firstPanel.add(dayTitle,c);

		// second panel holds the days of the week
		JPanel secondPanel = new JPanel();
		secondPanel.setLayout(new GridBagLayout());
		String[] days = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
		for(int i = 0; i < days.length; i++) {
			c.gridx = i;
			c.gridy = 1;
			c.ipadx = 20;
			c.fill = GridBagConstraints.CENTER;
			secondPanel.add(new JLabel(days[i]), c);
		}
		
		this.add(firstPanel, BorderLayout.NORTH);
		displayDayView(secondPanel, c);
		revalidate();
		repaint();
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
	public void displayDayView(JPanel secondPanel, GridBagConstraints c) {
		LocalDate today = model.getSelectedDate();
		
		switch(today.getDayOfWeek().ordinal()) {
		
		// if the current day starts on Monday
		case 0:
			LocalDate oneBeforeToday = today.minusDays(1);
			this.addDayNumber(oneBeforeToday, today, secondPanel,c);
			displayEvents(oneBeforeToday,c);
			break;
		
		// if the current day starts on Tuesday	
		case 1:
			LocalDate twoBeforeToday = today.minusDays(2);
			addDayNumber(twoBeforeToday, today, secondPanel,c);
			displayEvents(twoBeforeToday,c);
			break;
		
		// if the current day starts on Wednesday
		case 2:
			System.out.println("cake");
			LocalDate threeBeforeToday = today.minusDays(3);
			addDayNumber(threeBeforeToday, today, secondPanel,c);
			displayEvents(threeBeforeToday,c);
			break;
		
		// if the current day starts on Thursday
		case 3:
			LocalDate fourBeforeToday = today.minusDays(4);
			addDayNumber(fourBeforeToday, today, secondPanel,c);
			displayEvents(fourBeforeToday,c);
			break;
		
		//if the current day starts on Friday
		case 4:
			LocalDate fiveBeforeToday = today.minusDays(5);
			addDayNumber(fiveBeforeToday, today, secondPanel,c);
			displayEvents(fiveBeforeToday,c);
			break;
		
		//if the current day starts on Saturday
		case 5:
			LocalDate sixBeforeToday = today.minusDays(6);
			addDayNumber(sixBeforeToday, today, secondPanel,c);
			displayEvents(sixBeforeToday,c);
			break;
			
		// if the current day starts on Sunday
		case 6:
			LocalDate sunday = today;
			addDayNumber(sunday, today, secondPanel,c);
			displayEvents(today,c);
			break;
		}// end of switch case
	}// end of displayDayView
	
	// method to fill in the days of the week
	public void addDayNumber(LocalDate day, LocalDate today, JPanel secondPanel, GridBagConstraints c) {
		for (int i = 0; i < 7; i++) {
			if(day.getDayOfMonth() == today.getDayOfMonth()) {
				c.gridx = i;
				c.gridy = 2;
				JLabel todayLabel = new JLabel(Integer.toString(day.getDayOfMonth()));
				todayLabel.setForeground(Color.RED);
				todayLabel.setBackground(Color.BLUE);
				secondPanel.add(todayLabel,c);
				
				day = day.plusDays(1);
			}
			else {
				c.gridx = i;
				c.gridy = 2;
				secondPanel.add(new JLabel(Integer.toString(day.getDayOfMonth())),c);
				day = day.plusDays(1);
			}
		}
		this.add(secondPanel, BorderLayout.WEST);
	}
	
	public void displayEvents(LocalDate sunday, GridBagConstraints c) {
		startDate = LocalDate.of(sunday.getYear(), sunday.getMonthValue(), sunday.getDayOfMonth());
		endDate = startDate.plusDays(6);
		JTextArea eventsLabel = new JTextArea();
		TreeSet<LocalDate> dates = new TreeSet<LocalDate>();
		LocalDate current = startDate;
		while(!current.equals(endDate)) {
			dates.add(current);
			current = current.plusDays(1);
		}
	
		String eventDisplay = "";
		JPanel newPanel = new JPanel();

		for(LocalDate day : dates) {
			boolean noEvents = true;
			for(Event e : events) {
				if(e.occursOn(day)) {
					if(noEvents) {
						eventDisplay = eventDisplay + "\n" + day + "\n";
					}
					noEvents = false;
					eventDisplay = eventDisplay + "  " + e.toStringForDayView() + "\n";
				}
			}
		}// end of for loop
		eventsLabel.setText(eventDisplay);
		this.add(eventsLabel);
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
