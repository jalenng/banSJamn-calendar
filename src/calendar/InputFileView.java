package calendar;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Creates the panel to input the filepath for recurring events
 */
class InputFileView extends JPanel {
	
	CalendarModel model;
	JLabel errorLabel;
	
	
	public InputFileView(CalendarModel m) {
		model = m;
		
		this.setLayout(new GridBagLayout());
		
		JLabel titleLabel = new JLabel("Add Recurring Events From File");
		
		JLabel fileLabel = new JLabel("Enter the filepath: ");
		JTextField fileInput = new JTextField();

		errorLabel = new JLabel();
		JButton submitButton = new JButton("Submit");
		
		fileInput.setPreferredSize(new Dimension(300,20));
		
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setHorizontalAlignment(JLabel.RIGHT);
		errorLabel.setHorizontalAlignment(JLabel.CENTER);
				
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(fileInput.getText().length() == 0) {
					errorLabel.setText("No filepath entered");
				}
				else {
					addEvents(fileInput.getText());
				}
			}
			
		});
		
		GridBagConstraints c = new GridBagConstraints();
		Insets insets = new Insets(5,5,5,5);
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = insets;
		this.add(titleLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.insets = insets;
		this.add(fileLabel, c);
		c.gridx++;
		this.add(fileInput, c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		this.add(errorLabel, c);
		c.gridx = 0;
		c.gridy = 3;
		this.add(submitButton, c);
		
		
	}
	
	
	private void addEvents(String filepath) {
		
		try {
			File file = new File(filepath);
	        Scanner filesc = new Scanner(file);

	        while (filesc.hasNextLine()) {
	            String eventString = filesc.nextLine();
	            String[] eventArray = eventString.split(";");
	            
	            //
	            // Assuming recurring events start at the first day
	            // of the specified months
	            //
	            LocalDate startDate = LocalDate.of(
	            		Integer.parseInt(eventArray[1].trim()), 
	            		Integer.parseInt(eventArray[2].trim()),
	            		1);
	            
	            LocalDate endDate = LocalDate.of(
	            		Integer.parseInt(eventArray[1].trim()), 
	            		Integer.parseInt(eventArray[3].trim()),
	            		1);
	            
	            LocalTime startTime = LocalTime.of(Integer.parseInt(eventArray[5].trim()), 0);
	            
	            LocalTime endTime = LocalTime.of(Integer.parseInt(eventArray[6].trim()), 0);
	            
	            Event newEvent = new Event(
	            		eventArray[0].trim(),
	            		eventArray[4].trim(),
	            		startTime, endTime,
	            		startDate, endDate);
	            
	            model.addEvent(newEvent);
	            System.out.println("Event added");
	            
	        }
	        filesc.close();
	        errorLabel.setText("Events Added!");
	    } 
	    catch (Exception e) {
	        System.out.println(e);
	    }
		
	}
	
}