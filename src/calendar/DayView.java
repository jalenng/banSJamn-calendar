package calendar;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDate;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;

public class DayView extends JPanel implements CalendarView{
	
	private CalendarModel model;
	private LocalDate now;
	private TreeSet<Event> events;

	private LocalDate startDate;
	private LocalDate endDate;
	
	public DayView(CalendarModel m) {
		this.model = m;
		this.setLayout(new BorderLayout());
		events = m.getEvents();
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

		dayPanel.add(dayTitle, c);
		
		c.gridx = 0;
		c.gridy = 1;
		dayPanel.add(dayName, c);
		
		c.gridx = 0;
		c.gridy = 2;
		dayPanel.add(dayNumber, c);


		this.add(dayPanel, BorderLayout.NORTH);
		
		// insert method to call for "events"
		displayEvents();
	}
	
	public void displayEvents() {
		
		JTextArea eventsLabel = new JTextArea();
		
		JScrollPane scroll = new JScrollPane(eventsLabel);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		TreeSet<LocalDate> dates = new TreeSet<LocalDate>();
		//LocalDate current = startDate;
		
		String eventDisplay = "";
		
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
		}
		
		eventsLabel.setText(eventDisplay);
		
		this.add(scroll);
		
		this.revalidate();
		this.repaint();
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
