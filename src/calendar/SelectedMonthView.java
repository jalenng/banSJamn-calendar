package calendar;

import java.time.LocalDate;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

/**
 * This class creates the smaller left side calendar with the clickable dates.
 * Today is highlighted in blue.
 * 
 * @author Aryan Maheshwari, Jalen Ng
 * @version 7/31/20
 * @copyright banSJamn
 */

public class SelectedMonthView extends JPanel implements CalendarView {

	private LocalDate visibleDate;
	private CalendarModel model;

	/**
	 * Constructor which updates all the values and creates the
	 * initial display.
	 * 
	 * @param m is the model
	 */
	public SelectedMonthView(CalendarModel m) {
		this.model = m;
		visibleDate = model.getSelectedDate();
		display();
	}

	/**
	 * Creates the calendar. It has its own buttons to 
	 * navigate the different months. Each date is clickable.
	 */
	public void display() {

		// Clear old components
		this.removeAll();
		this.revalidate();

		// Create header and body of the panel. Header has month, year, and buttons.
		// Body has the days
		JPanel header = new JPanel();
		header.setLayout(new BorderLayout());
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JPanel body = new JPanel();
		body.setLayout(new GridLayout(8, 7));

		// Creating the header which has the title and left/right buttons
		JLabel titleLabel = new JLabel(visibleDate.getMonth().name() + " " + visibleDate.getYear(),
				SwingConstants.CENTER); // configure titleLabel

		JButton left = new JButton(" < ");
		left.setBackground(Color.LIGHT_GRAY); // configure left button

		JButton right = new JButton(" > ");
		right.setBackground(Color.LIGHT_GRAY); // configure right button

		buttonPanel.add(left);
		buttonPanel.add(right); // add buttons to buttonPanel

		header.add(titleLabel, BorderLayout.WEST);
		header.add(buttonPanel, BorderLayout.EAST); // add label and buttonPanel to header

		// Left button action listener
		left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previous();
			}
		});

		// Right button action listener
		right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				next();
			}
		});

		// Show the days on top
		String[] days = { "S", "M", "T", "W", "T", "F", "S" };

		for (int i = 0; i < 7; i++) {
			JLabel daysOfWeek = new JLabel(days[i], SwingConstants.CENTER);
			daysOfWeek.setBackground(Color.WHITE);
			daysOfWeek.setOpaque(true);
			body.add(daysOfWeek);
		}

		// Creating and populating the calendar with each date as a button
		JButton[][] daysHolder = new JButton[6][7];
		LocalDate firstDayOfWeek = visibleDate.withDayOfMonth(1);
		int day = 1;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {

				// configure calendar date button defaults
				JButton dateButton = new JButton();
				dateButton.setBackground(new Color(230, 230, 230));
				dateButton.setOpaque(true);
				dateButton.setEnabled(false);

				// if on the 1st row and before the 1st day of the month, do nothing
				if (i == 0 && j < firstDayOfWeek.getDayOfWeek().getValue()) {
				}
				// else if we haven't reached the end of the month
				else if (day < visibleDate.lengthOfMonth() + 1) {
					dateButton.setText("" + day);
					dateButton.setEnabled(true);

					LocalDate buttonRepresentedDate = visibleDate
							.withDayOfMonth(Integer.parseInt(dateButton.getText()));

					// set color of date button
					if (buttonRepresentedDate.equals(LocalDate.now())) { // today
						dateButton.setBackground(new Color(122, 138, 153));
						dateButton.setForeground(Color.WHITE);
					} else if (buttonRepresentedDate.equals(model.getSelectedDate())) // selected date
						dateButton.setBackground(new Color(198, 217, 234));
					else
						dateButton.setBackground(Color.WHITE);

					// date button action listener
					dateButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							model.setSelectedDate(buttonRepresentedDate);
						}
					});
					day++;
				}
				// else, we've reached the end of the month. do nothing.

				daysHolder[i][j] = dateButton;
				body.add(daysHolder[i][j]);
			}
		}

		// configures this main panel, adds header and body
		this.setLayout(new BorderLayout());
		this.add(header, BorderLayout.NORTH);
		this.add(body, BorderLayout.CENTER);
	}

	/**
	 * If there is a change in date, this will update the date.
	 * 
	 * @param arg0	the ChangeEvent
	 */
	public void stateChanged(ChangeEvent arg0) {
		visibleDate = model.getSelectedDate();
		this.display();
	}

	/**
	 * Moves the selected date forwards by a month
	 */
	public void next() {
		visibleDate = visibleDate.plusMonths(1);
		this.display();
	}

	/**
	 * Moves the selected date back by a month
	 */
	public void previous() {
		visibleDate = visibleDate.plusMonths(-1);
		this.display();
	}
}
