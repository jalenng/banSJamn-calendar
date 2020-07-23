package calendar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalendarTester {
	
	public static void main(String[] args) {
		
		final CalendarModel model = new CalendarModel();
		
		CreateView createView = new CreateView(model);
		DayView dayView = new DayView(model); //uncomment when DayView is implemented
		//WeekView weekView = new MonthView(model); //uncomment when WeekView is implemented
		//MonthView monthView = new MonthView(model); //uncomment when MonthView is implemented
		AgendaView agendaView = new AgendaView(model);
		
		// Attach views/change listeners to model
		model.attach(dayView); //uncomment when DayView is implemented
		//model.attach(weekView); //uncomment when WeekView is implemented
		//model.attach(monthView); //uncomment when MonthView is implemented
		
		final JFrame frame = new JFrame();
		frame.setTitle("Calendar");
		frame.setMinimumSize(new Dimension(900, 500));
		
		// Split JFrame into two halves
		final JPanel leftControls = new JPanel();
		leftControls.setLayout(new BorderLayout());
		final JPanel rightControls = new JPanel();
		rightControls.setLayout(new BorderLayout());
		
		
		// Left half has a JPanel for the top buttons (top-left panel)
		JPanel currentViewNav = new JPanel();
		currentViewNav.setLayout(new GridBagLayout());
		
		JButton todayButton = new JButton("Today");
		JButton todayLeftButton = new JButton("<");
		JButton todayRightButton = new JButton(">");
		JButton createButton = new JButton("Create Event");				
		
		// Adding buttons to top-left panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;	
		currentViewNav.add(todayButton, c);
		c.gridx++;
		currentViewNav.add(todayLeftButton, c);
		c.gridx++;
		currentViewNav.add(todayRightButton, c);
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 3;
		currentViewNav.add(createButton, c);
		
		// Right half has a JPanel for the top buttons (top-right panel)
		JPanel changeViewNav = new JPanel();
		changeViewNav.setLayout(new GridLayout(1,5));
		
		final JButton dayButton = new JButton("Day");
		JButton weekButton = new JButton("Week");
		JButton monthButton = new JButton("Month");
		JButton agendaButton = new JButton("Agenda");
		JButton uploadFileButton = new JButton("Upload File");
		
		
		// Agenda action listener
		agendaButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				rightControls.removeAll();
				rightControls.add(changeViewNav, BorderLayout.NORTH);
				rightControls.add(agendaView, BorderLayout.CENTER);
				rightControls.revalidate();
				rightControls.repaint();
			}
			
		});
		
		// InputFile action listener
		uploadFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rightControls.removeAll();
				rightControls.add(changeViewNav, BorderLayout.NORTH);
				rightControls.add(new InputFileView(model), BorderLayout.CENTER);
				rightControls.revalidate();
				rightControls.repaint();
				uploadFileButton.setEnabled(false);
			}
		});
		
		// Today action listener
		todayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setSelectedDate(LocalDate.now());
			}
		});
				
		// Create Event action listener
		createButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rightControls.removeAll();
					rightControls.add(changeViewNav, BorderLayout.NORTH);
					rightControls.add(createView, BorderLayout.CENTER);
					rightControls.revalidate();
					rightControls.repaint();
				}
			});
		
		// Adding buttons to top-right panel
		changeViewNav.add(dayButton);
		changeViewNav.add(weekButton);
		changeViewNav.add(monthButton);
		changeViewNav.add(agendaButton);
		changeViewNav.add(uploadFileButton);
				
		// Adding top-left panel to left half & top-right panel to right half
		leftControls.add(currentViewNav, BorderLayout.NORTH);
		rightControls.add(changeViewNav, BorderLayout.NORTH);
		
		// DayView Action Listener
		dayButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rightControls.removeAll();
				rightControls.add(changeViewNav, BorderLayout.NORTH);
				rightControls.add(new DayView(model), BorderLayout.WEST);
				rightControls.revalidate();
				rightControls.repaint();	
			}		
		});
		
		// WeekView Action Listener
		weekButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rightControls.removeAll();
				rightControls.add(changeViewNav, BorderLayout.NORTH);
				rightControls.add(new WeekView(model), BorderLayout.WEST);
				rightControls.revalidate();
				rightControls.repaint();	
			}		
		});
		
		// You can use these to test your calendar views
//		leftControls.add(new SelectedMonthView(0), BorderLayout.CENTER);
//		rightControls.add(new MonthView(0), BorderLayout.CENTER);
		
		
		// Adding left and right halves to the frame
		frame.add(leftControls, BorderLayout.WEST);
		frame.add(rightControls, BorderLayout.EAST);
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		System.out.println(todayLeftButton.getSize());
	}
	
}
