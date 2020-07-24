package calendar;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;

public class DayView extends JPanel implements CalendarView{
	
	private CalendarModel model;
	private LocalDate now;
	
	public DayView(CalendarModel m) {
		this.model = m;
		this.setLayout(new BorderLayout());

		display(now);
	}

	public void display(LocalDate date) {
		// Clear old components
		this.removeAll();
		this.revalidate();
		
		now = model.getSelectedDate();
		
		// variables to get the month, day, year, and day of the week
		String currentMonth = now.getMonth().name();
		String currentDay = now.getDayOfWeek().name();
		int day = now.getDayOfMonth();
		int year = now.getYear();
		
		// creates the left and right buttons
		JButton previousButton = new JButton("<");
		JButton nextButton = new JButton(">");
		
		// creates the title
		JLabel dayTitle = new JLabel(" " + currentMonth + " " + day + ", " + year);

		
		// day of the week label
		JLabel dayName = new JLabel(currentDay.substring(0,3), SwingConstants.CENTER);
		
		JPanel dayPanel = new JPanel();	
		dayPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// current day label
		JLabel dayNumber = new JLabel(Integer.toString(day), SwingConstants.CENTER);	

		// add all the components into the panel together
		c.gridx = 0;
		c.gridy = 0;
		dayPanel.add(previousButton, c);
		
		c.gridx = 1;
		c.gridy = 0;
		dayPanel.add(nextButton, c);
		
		c.gridx = 2;
		c.gridy = 0;
		dayPanel.add(dayTitle, c);
		
		c.gridx = 0;
		c.gridy = 1;
		dayPanel.add(dayName, c);
		
		c.gridx = 0;
		c.gridy = 2;
		dayPanel.add(dayNumber, c);


		this.add(dayPanel, BorderLayout.NORTH);
		
		// insert method to call for "events"
	}
	
	@Override
	public void stateChanged(ChangeEvent arg0) {
		this.display(now);
	}

	/**
	 * Moves the selected date forwards by a day
	 */
	public void next() {
		model.advanceSelectedDateByDays(1);
	}

	/**
	 * Moves the selected date forwards by a day
	 */
	public void previous() {
		model.advanceSelectedDateByDays(-1);
	}

}
