package calendar;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
		now = CalendarModel.getSelectedDate();
		
		// variables to get the month, day, year, and day of the week
		String currentMonth = now.getMonth().name();
		int day = now.getDayOfMonth();
		int year = now.getYear();
		
		// creates the left and right buttons
		JButton previousButton = new JButton("<");
		JButton nextButton = new JButton(">");
		
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

		firstPanel.add(previousButton, c);
		
		c.gridx = 1;
		c.gridy = 0;
		firstPanel.add(nextButton, c);
		
		c.gridx = 2;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		firstPanel.add(dayTitle, c);

		
		// second panel holds the days of the week
		JPanel secondPanel = new JPanel();
		
		String[] days = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
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
	
	void printWeek(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
		System.out.println(" " + formatter.format(date));
		
		LocalDate first = LocalDate.of(date.getYear(),date.getMonthValue(), 1);
		int previousDays = first.getDayOfWeek().getValue() - 1;
		int daysInWeek = 7;
		
		LocalDate today = LocalDate.now();
		
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(" dd ");
		DateTimeFormatter highlightFormatter = DateTimeFormatter.ofPattern(" dd ");
		
		System.out.println("");
		
		for (int i = -previousDays; i <= daysInWeek; i++) {
			String output;
			LocalDate currentDate = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
    		boolean isToday = i == today.getDayOfMonth() && today.getMonth() == currentDate.getMonth() && currentDate.getYear() == today.getYear();
    		
    		output = dateFormatter.format(currentDate);
    		System.out.println("This is output:  " + output);
    		
    		int daysPrinted = i + previousDays + 1;
    		if(daysPrinted % daysInWeek == 0 && daysPrinted != 0) {
    			System.out.println();
    		}

		}
	}
	public void displayDayView() {
		this.removeAll();
	}
	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void previous() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}

}
