package calendar;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.TreeSet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
/**
 * DayView has a model, now, events, and startDate. DayView will update the right-hand side of the GUI
 * for the user to see the Day and events for the specific day.
 * @author Alein Bartolome
 * @version 1.5
 * @copyright banSJamn
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
	 * @param date 
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
		dayPanel.setLayout(new GridLayout(4,1));
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		// current day label
		JLabel dayNumber = new JLabel(Integer.toString(day), SwingConstants.CENTER);	

		// ADDED A PADDING FROM THE EDGES
		setBorder(new EmptyBorder(10, 15, 10, 10));
		
		// add all the components into the panel together
		c.gridx = 0;
		c.gridy = 0;
		dayPanel.add(dayTitle, c);
		
		c.gridx = 0;
		c.gridy = 1;
		// CHANGED TO STAY ON THE LEFT  
		dayName.setHorizontalAlignment(JLabel.LEFT);
		dayPanel.add(dayName, c);
		
		c.gridx = 0;
		c.gridy = 2;
		// CHANGED TO STAY ON THE LEFT
		dayNumber.setHorizontalAlignment(JLabel.LEFT);
		dayPanel.add(dayNumber, c);
		
		// DONT NEED C FOR BORDERLAYOUT
//		c.gridx = 0;
//		c.gridy = 0;
		this.add(dayPanel, BorderLayout.NORTH);
		
		
		// insert method to call for "events"
		
		// DONT NEED C FOR BORDERLAYOUT
//		c.gridx = 0;
//		c.gridy = 1;
		
		//CHANGED TO BORDERLAYOUT CENTER
		this.add(displayEvents(), BorderLayout.CENTER);
	}
	
	
	// CHANGED THIS TO RETURN A JTEXTAREA BECAUSE THAT DISPLAYS MORE
	// NICELY THAN ADDING A JPANEL TO THE MAIN PANEL
	//
	// REMEMBER TO UPDATE THE JAVADOC IF THIS CHANGE IS KEPT PLS
	//
	/**
	 * Method stores all the events into a TreeSet and places the events into a label and returns a panel.
	 * @return newPanel a panel with the events from getEvents()
	 * */
	public JTextArea displayEvents() {
		startDate = LocalDate.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
		JTextArea eventsLabel = new JTextArea();
		TreeSet<LocalDate> dates = new TreeSet<LocalDate>();
		LocalDate current = startDate;
		while(current.equals(startDate)) {
			dates.add(current);
			current = current.plusDays(1);
		}
		
		String eventDisplay = "";
		
		// NO NEED FOR JPANEL 
//		JPanel newPanel = new JPanel();

		for(LocalDate day : dates) {
			for(Event e : events) {
				if(e.occursOn(day)) {
					eventDisplay = eventDisplay + e.toStringForDayView() + "\n";
				}
			}
		}// end of for loop
		eventsLabel.setText(eventDisplay);
		
		// NO NEED FOR JPANEL
//		newPanel.add(eventsLabel);
		return eventsLabel;
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
