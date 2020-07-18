package calendar;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;

public class AgendaView extends JPanel implements CalendarView {
	
	private CalendarModel model;
	private TreeSet<Event> events;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public AgendaView(CalendarModel m) {
		
		this.setLayout(new BorderLayout());
		
		this.model = m;
		events = m.getEvents();
		
		
		JLabel titleLabel = new JLabel("What time frame do you want to view? (MM/DD/YYYY)");
		
		JLabel startLabel = new JLabel("Start Date: ");
		JTextField startInput = new JTextField();
		JLabel endLabel = new JLabel("End Date: ");
		JTextField endInput = new JTextField();
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String[] startArray =  startInput.getText().split("/");
					String[] endArray = endInput.getText().split("/");
					
					//
					// !! check for input errors !!
					//
					
					startDate = LocalDate.of(
							Integer.parseInt(startArray[2]), 
							Integer.parseInt(startArray[0]), 
							Integer.parseInt(startArray[1]));
					
					endDate = LocalDate.of(
							Integer.parseInt(endArray[2]), 
							Integer.parseInt(endArray[0]), 
							Integer.parseInt(endArray[1]));
									
					displayAgenda();
				}
				catch (Exception exception) {
					System.out.println(exception.getMessage());
				}
				
			}
			
		});
		
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(2,2));
		
		inputPanel.add(startLabel);
		inputPanel.add(startInput);
		inputPanel.add(endLabel);
		inputPanel.add(endInput);
		
		this.add(titleLabel, BorderLayout.NORTH);
		this.add(inputPanel, BorderLayout.CENTER);
		this.add(submitButton, BorderLayout.SOUTH);
		
		
	}
	
	
	public void displayAgenda() {
		this.removeAll();

		JLabel dateIntervalLabel = new JLabel();
		dateIntervalLabel.setText("Displaying Events from" + startDate + " to " + endDate);
				
		JTextArea eventsLabel = new JTextArea();
		String eventDisplay = "";
		
		for(Event e : events) {
			if( (e.getDate().isAfter(startDate) && e.getDate().isBefore(endDate))
					|| e.getDate().equals(startDate)
					|| e.getDate().equals(endDate) ) {
				eventDisplay = eventDisplay + e.getDate() + " " + e.toStringForDayView() + "\n";
			}
		}
		
		eventsLabel.setText(eventDisplay);
		
		this.add(dateIntervalLabel, BorderLayout.NORTH);
		this.add(eventsLabel, BorderLayout.CENTER);
		
		this.revalidate();
		this.repaint();
	}
	
	
	public void next() {
		
	}
	
	
	public void previous() {
		
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		
		
	}


}
