package calendar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;

/**
 * Calendar Project
 * @author Shana, Jalen (?)
 * @version 1.0
 * @copyright banSJamn
 */

/**
 * A JPanel view to display the Agenda
 */
public class AgendaView extends JPanel implements CalendarView {
	
	private CalendarModel model;
	private TreeSet<Event> events;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean newInterval;
	
	
	/**
	 * Creates the view for the user to enter a date interval
	 * @param m
	 */
	public AgendaView(CalendarModel m) {
		this.setLayout(new BorderLayout());
		this.model = m;
		events = m.getEvents();
		newInterval = true;
	}
	
	 @Override
     public void paintComponent(Graphics g) {
         super.paintComponent(g);
         if(newInterval) {
        	 inputIntervalView();
         }
     }
	
	 
	 public void setNewInterval(boolean n) {
		 newInterval = n;
	 }
	 
	 public void inputIntervalView() {
		 
		 this.removeAll();
		 
		 JLabel titleLabel = new JLabel("What time frame do you want to view? (MM/DD/YYYY)");
			
		 JLabel startLabel = new JLabel("Start Date: ");
		 JTextField startInput = new JTextField();
		 JLabel endLabel = new JLabel("End Date: ");
  		 JTextField endInput = new JTextField();
 		
		 titleLabel.setHorizontalAlignment(JLabel.CENTER);
		 startLabel.setHorizontalAlignment(JLabel.RIGHT);
	     endLabel.setHorizontalAlignment(JLabel.RIGHT);
		
         startInput.setPreferredSize(new Dimension(300,20));
         endInput.setPreferredSize(new Dimension(300,20));

		
		 JButton submitButton = new JButton("Submit");
		 submitButton.addActionListener(new ActionListener() {
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 try {
					 String[] startArray =  startInput.getText().split("/");
					 String[] endArray = endInput.getText().split("/");
				
					 startDate = LocalDate.of(
							Integer.parseInt(startArray[2]), 
							Integer.parseInt(startArray[0]), 
							Integer.parseInt(startArray[1]));
					
					 endDate = LocalDate.of(
							Integer.parseInt(endArray[2]), 
							Integer.parseInt(endArray[0]), 
							Integer.parseInt(endArray[1]));
					
					 setNewInterval(false);
					 displayAgenda();
				 } 
				 catch (DateTimeException exception) {
					 JOptionPane.showMessageDialog(
			    			null, 
			    			"Invalid date.", 
			    			"Agenda View", 
			    			JOptionPane.ERROR_MESSAGE
			    		);
				 }
				 catch (Exception exception2) {
					 JOptionPane.showMessageDialog(
			    			null, 
			    			"Invalid date format.", 
			    			"Agenda View", 
			    			JOptionPane.ERROR_MESSAGE
			    		);
				 }
				
			 }
			
		 });
		
		
		 JPanel inputPanel = new JPanel();
		 inputPanel.setLayout(new GridBagLayout());
		
		 GridBagConstraints c = new GridBagConstraints();
		 Insets insets = new Insets(5,5,5,5);
		 
		 c.gridx = 0;
		 c.gridy = 0;
		 c.insets = insets;
		 c.gridwidth = 2;
		 inputPanel.add(titleLabel, c);
		 c.gridx = 0;
		 c.gridy = 1;
		 c.gridwidth = 1;
		 inputPanel.add(startLabel, c);
		 c.gridx = 1;
		 inputPanel.add(startInput, c);
		 c.gridx = 0;
		 c.gridy = 2;
		 inputPanel.add(endLabel, c);
		 c.gridx = 1;
		 inputPanel.add(endInput, c);
		 c.gridx = 0;
		 c.gridy = 3;
		 c.gridwidth = 2;
		 inputPanel.add(submitButton, c);
		
		 this.add(inputPanel, BorderLayout.CENTER);
		 
		 this.revalidate();
		 this.repaint();
	 }
	
	/**
	 * Creates a view to display the agenda within the specified interval
	 */
	public void displayAgenda() {
		this.removeAll();
				
		if(endDate.isBefore(startDate)) {
			LocalDate toBefore = endDate;
			endDate = startDate;
			startDate = toBefore;
		}

		JLabel dateIntervalLabel = new JLabel();
		dateIntervalLabel.setText("Events: " + startDate + " --> " + endDate);
				
		JTextArea eventsLabel = new JTextArea();
		
		JScrollPane scroll = new JScrollPane(eventsLabel);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		TreeSet<LocalDate> dates = new TreeSet<LocalDate>();
		LocalDate current = startDate;
		
		while(!current.equals(endDate)) {
			dates.add(current);
			current = current.plusDays(1); 
		}
		
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
		
		this.add(dateIntervalLabel, BorderLayout.NORTH);
		this.add(scroll);
		
		this.revalidate();
		this.repaint();
	}

	
	@Override
	public void next() {}

	
	@Override
	public void previous() {}


	/**
	 * Updates the model when CalendarModel changes
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		events = model.getEvents();
		if(newInterval) {
			repaint();
		}
		else {
			displayAgenda();
		}
	}


}
