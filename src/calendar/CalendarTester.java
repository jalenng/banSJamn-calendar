package calendar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
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
		
		final CreateView createView = new CreateView(model);
		final DayView dayView = new DayView(model); 
		final WeekView weekView = new WeekView(model); 
		final MonthView monthView = new MonthView(0); //month view should take in model as param
		final AgendaView agendaView = new AgendaView(model);
		
		// Attach views/change listeners to model
		model.attach(dayView); 
		model.attach(weekView); 
		//model.attach(monthView); //uncomment when month view implements ChangeListener
		
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
		
		final JButton todayButton = new JButton("Today");
		final JButton todayLeftButton = new JButton("<");
		final JButton todayRightButton = new JButton(">");
		final JButton createButton = new JButton("Create Event");		
		
		// Disable todayLeftButton and todayRightButton by default
		todayLeftButton.setEnabled(false);
		todayRightButton.setEnabled(false);
		
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
		final JButton weekButton = new JButton("Week");
		final JButton monthButton = new JButton("Month");
		final JButton agendaButton = new JButton("Agenda");
		final JButton importFileButton = new JButton("Import File");
		
		
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
		importFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rightControls.removeAll();
				rightControls.add(changeViewNav, BorderLayout.NORTH);
				rightControls.add(new InputFileView(model), BorderLayout.CENTER);
				rightControls.revalidate();
				rightControls.repaint();
				importFileButton.setEnabled(false);
			}
		});
		
		// Today action listener
		todayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setSelectedDate(LocalDate.now());
			}
		});
				
		// Left/Previous (<) action listener
		todayLeftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component rightView = rightControls.getComponent(1);
				if (rightView instanceof CalendarView) {
					((CalendarView) rightView).previous();
				}
			}
		});
			
		// Right/Next (>) action listener
		todayRightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component rightView = rightControls.getComponent(1);
				if (rightView instanceof CalendarView) {
					((CalendarView) rightView).next();
				}
			}
		});
		
		// Checks whether a CalendarView is in the right side to determine
		// whether to disable the left/right buttons
		rightControls.addContainerListener(new ContainerListener() {
			public void componentAdded(ContainerEvent arg0) {
				updateLeftRightButtonsAbility();			
			}
			public void componentRemoved(ContainerEvent arg0) {
				updateLeftRightButtonsAbility();
			}
			public void updateLeftRightButtonsAbility() {
				if (rightControls.getComponentCount() > 1 ) {
					Component rightView = rightControls.getComponent(1);
					if (rightView instanceof CalendarView) {
						todayLeftButton.setEnabled(true);
						todayRightButton.setEnabled(true);
					}
					else {
						todayLeftButton.setEnabled(false);
						todayRightButton.setEnabled(false);
					}
				}
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
		changeViewNav.add(importFileButton);
				
		// Adding top-left panel to left half & top-right panel to right half
		leftControls.add(currentViewNav, BorderLayout.NORTH);
		rightControls.add(changeViewNav, BorderLayout.NORTH);
		
		// DayView Action Listener
		dayButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rightControls.removeAll();
				rightControls.add(changeViewNav, BorderLayout.NORTH);
				rightControls.add(dayView, BorderLayout.WEST);
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
				rightControls.add(weekView, BorderLayout.WEST);
				rightControls.revalidate();
				rightControls.repaint();	
			}		
		});
		
		leftControls.add(new SelectedMonthView(0), BorderLayout.CENTER);
		
		// MonthView Action Listener
		monthButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				rightControls.removeAll();
				rightControls.add(changeViewNav, BorderLayout.NORTH);
				rightControls.add(monthView, BorderLayout.CENTER);
				rightControls.revalidate();
				rightControls.repaint();	
			}		
		});
		
		// Adding left and right halves to the frame
		frame.add(leftControls, BorderLayout.WEST);
		frame.add(rightControls, BorderLayout.EAST);
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
	
}
