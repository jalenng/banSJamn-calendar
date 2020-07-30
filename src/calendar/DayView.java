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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
/**
 * 
 * */
public class DayView extends JPanel implements CalendarView{
	
	private CalendarModel model;
	private LocalDate now;
	private TreeSet<Event> events;
	private LocalDate startDate;
	
	/**
	 * DayView constructor
	 * */
	public DayView(CalendarModel m) {
		this.model = m;
		events = m.getEvents();
		display(now);
	}

	/**
	 * Creates the JComponents for the GUI of the calendar and calls displayEvents() to add the events for the
	 * specific day
	 * */
	public void display(LocalDate date) {
		// Clear old components
		this.removeAll();
		this.revalidate();
		
		now = model.getSelectedDate();
		this.setLayout(new BorderLayout());
		// variables to get the month, day, year, and day of the week
		String currentMonth = now.getMonth().name();
		String currentDay = now.getDayOfWeek().name();
		int day = now.getDayOfMonth();
		int year = now.getYear();
		
		// creates the title
		JLabel dayTitle = new JLabel(currentMonth + " " + day + ", " + year);

		
		// day of the week label
		JLabel dayName = new JLabel(currentDay.substring(0,3), SwingConstants.CENTER);
		
		JPanel dayPanel = new JPanel();	
		//dayPanel.setPreferredSize(new Dimension(100,50));
		dayPanel.setLayout(new GridLayout(3,1));
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		// current day label
		JLabel dayNumber = new JLabel(Integer.toString(day), SwingConstants.CENTER);	

		// add all the components into the panel together
		c.gridx = 0;
		c.gridy = 0;
		dayPanel.add(dayTitle, c);
		
		c.gridx = 0;
		c.gridy = 1;
		dayPanel.add(dayName, c);
		
		c.gridx = 0;
		c.gridy = 2;
		dayPanel.add(dayNumber, c);
		
		c.gridx = 0;
		c.gridy = 0;
		this.add(dayPanel, BorderLayout.NORTH);
		
		// insert method to call for "events"
		c.gridx = 0;
		c.gridy = 1;
		this.add(displayEvents(), BorderLayout.WEST);
	}
	
	/**
	 * Method stores all the events into a TreeSet and places the events into a label and returns a panel.
	 * */
	public JPanel displayEvents() {
		startDate = LocalDate.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
		JTextArea eventsLabel = new JTextArea();
		TreeSet<LocalDate> dates = new TreeSet<LocalDate>();
		LocalDate current = startDate;
		while(current.equals(startDate)) {
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
		newPanel.add(eventsLabel);
		return newPanel;
	}
	
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
