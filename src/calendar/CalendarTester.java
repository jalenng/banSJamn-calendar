package calendar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Calendar Project
 * @author banSJamn
 * @version 1.0
 * @copyright banSJamn
 */

/**
 * Class to display the calendar
 */
public class CalendarTester {

	/**
	 * Displays the calendar with buttons for different views,
	 * such as Day, Month, Week, Agenda, FileImporter, and ThemeStrategy 
	 * @param args
	 */
	public static void main(String[] args) {

		final CalendarModel model = new CalendarModel();

		final CreateView createView = new CreateView(model);
		final DayView dayView = new DayView(model); 
		final WeekView weekView = new WeekView(model); 
		final MonthView monthView = new MonthView(model);
		final AgendaView agendaView = new AgendaView(model);
		

		// Attach views/change listeners to model
		model.attach(dayView); 
		model.attach(weekView); 
		model.attach(monthView); 

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
		changeViewNav.setLayout(new GridLayout(1, 5));

		final JButton dayButton = new JButton("Day");
		final JButton weekButton = new JButton("Week");
		final JButton monthButton = new JButton("Month");
		final JButton agendaButton = new JButton("Agenda");
		final JButton importFileButton = new JButton("Import File");


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

		// DayView Action Listener
		dayButton.addActionListener(new ActionListener(){
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
			public void actionPerformed(ActionEvent arg0) {
				rightControls.removeAll();
				rightControls.add(changeViewNav, BorderLayout.NORTH);
				rightControls.add(weekView, BorderLayout.WEST);
				rightControls.revalidate();
				rightControls.repaint();	
			}		
		});

		// MonthView Action Listener
		monthButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				rightControls.removeAll();
				rightControls.add(changeViewNav, BorderLayout.NORTH);
				rightControls.add(monthView, BorderLayout.CENTER);
				rightControls.revalidate();
				rightControls.repaint();	
			}		
		});

		// Agenda action listener
		agendaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rightControls.removeAll();
				rightControls.add(changeViewNav, BorderLayout.NORTH);
				rightControls.add(agendaView, BorderLayout.CENTER);
				agendaView.inputIntervalView();
				rightControls.revalidate();
				rightControls.repaint();
			}

		});

		// InputFile action listener
		importFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (FileImporter.importFile(model))
					importFileButton.setEnabled(false);
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

		leftControls.add(new SelectedMonthView(0), BorderLayout.CENTER);

		// Theme Panels at the top and bottom of frame
		JPanel themePanelTop = new JPanel();
		JPanel themePanelBottom = new JPanel();
		frame.add(themePanelTop, BorderLayout.NORTH);
		frame.add(themePanelBottom, BorderLayout.SOUTH);

		// Theme Selector
		ThemeStrategy[] themes = {
				new SpaceTheme(),
				new AnimalTheme()
		};
		
		JComboBox<ThemeStrategy> themeSelector = new JComboBox<ThemeStrategy>(themes);
		themeSelector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ThemeStrategy selectedTheme = 
						(ThemeStrategy)(themeSelector.getSelectedItem());

				themePanelTop.removeAll();
				themePanelBottom.removeAll();

				themePanelTop.add(selectedTheme.displayTop());
				themePanelBottom.add(selectedTheme.displayBottom());

				themePanelTop.revalidate();
				themePanelTop.repaint();

				themePanelBottom.revalidate();
				themePanelBottom.repaint();
			}

		});
		
		// Adding resizing listener to JFrame to resize the theme
		frame.addComponentListener(new ComponentAdapter() 
		{  
		        public void componentResized(ComponentEvent evt) {
		        	for(ThemeStrategy theme : themes) {
		        		theme.updateWidth(frame.getWidth());
		        	}
		        	ThemeStrategy selectedTheme = 
							(ThemeStrategy)(themeSelector.getSelectedItem());

					themePanelTop.removeAll();
					themePanelBottom.removeAll();

					themePanelTop.add(selectedTheme.displayTop());
					themePanelBottom.add(selectedTheme.displayBottom());

					themePanelTop.revalidate();
					themePanelTop.repaint();

					themePanelBottom.revalidate();
					themePanelBottom.repaint();
		        }
		});
		
		
		leftControls.add(themeSelector, BorderLayout.SOUTH);

		// Adding left and right halves to the frame
		frame.add(leftControls, BorderLayout.WEST);
		frame.add(rightControls, BorderLayout.CENTER);

		// Set frame properties
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}

}
