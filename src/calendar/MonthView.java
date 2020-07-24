package calendar;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

public class MonthView extends JPanel implements CalendarView {
	// Private instance variable used to navigate between months
	private CalendarModel model;

	/**
	 * Constructor which updates the value of n and calls the display method to
	 * display the calendar.
	 * 
	 * @param n is the month you want to show (either before or after the current
	 * month)
	 */
	public MonthView(CalendarModel m) {
		this.model = m;
		display();
	}

	/**
	 * Creates the calendar. The method takes in the number of months before or
	 * after the current month and creates an updated calendar.
	 */
	public void display() {
		// Clear old components
		this.removeAll();
		this.revalidate();
		// Get today's date and month
		LocalDate cal = model.getSelectedDate();
		int today = cal.getDayOfMonth();
		String thisMonth = cal.getMonth().name();
		int thisYear = cal.getYear();

		// Creating the title and adding the left and right button for navigation
		for (int i = 0; i < 7; i++) {
			if (i == 0) {
				JLabel month = new JLabel(cal.getMonth().name(), SwingConstants.CENTER);
				add(month);
			} else if (i == 1) {
				JLabel year = new JLabel("" + cal.getYear(), SwingConstants.CENTER);
				add(year);
			} else {
				JLabel empty = new JLabel("");
				add(empty);
			}
		}

		// Show the days on top
		String[] days = { "S", "M", "T", "W", "T", "F", "S" };

		for (int i = 0; i < 7; i++) {
			add(new JLabel(days[i], SwingConstants.CENTER));
		}

		// Creaing the calendar with each date as a button
		JLabel[][] daysHolder = new JLabel[6][7];
		this.setLayout(new GridLayout(8, 7));
		for (int m = 0; m < 6; m++) {
			for (int y = 0; y < 7; y++) {
				daysHolder[m][y] = new JLabel();
				daysHolder[m][y].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				add(daysHolder[m][y]);
			}
		}

		// First day of the month
		cal = LocalDate.of(cal.getYear(), cal.getMonthValue(), 1);
		String firstDay = cal.getDayOfWeek().name();

		// Where to start for the first day of the month
		int counter = 0;
		if (firstDay.compareTo("MONDAY") == 0) {
			counter = counter + 1;
			daysHolder[0][0].setText("");
		} else if (firstDay.compareTo("TUESDAY") == 0) {
			counter = counter + 2;
			for (int i = 0; i <= 1; i++) {
				daysHolder[0][i].setText("");
			}
		} else if (firstDay.compareTo("WEDNESDAY") == 0) {
			counter = counter + 3;
			for (int i = 0; i <= 2; i++) {
				daysHolder[0][i].setText("");
			}
		} else if (firstDay.compareTo("THURSDAY") == 0) {
			counter = counter + 4;
			for (int i = 0; i <= 3; i++) {
				daysHolder[0][i].setText("");
			}
		} else if (firstDay.compareTo("FRIDAY") == 0) {
			counter = counter + 5;
			for (int i = 0; i <= 4; i++) {
				daysHolder[0][i].setText("");
			}
		} else if (firstDay.compareTo("SATURDAY") == 0) {
			counter = counter + 6;
			for (int i = 0; i <= 5; i++) {
				daysHolder[0][i].setText("");
			}
		}

		// Populating the calendar and identifying today
		int i = 1;
		int week = 0;
		int lengthOfMonth = cal.lengthOfMonth();
		boolean sameMonth = (cal.getMonth().name().compareTo(thisMonth) == 0);
		boolean sameYear = (cal.getYear() == thisYear);

		while (i <= lengthOfMonth) {
			if (counter < 6 && i < 10 && i != today) {
				daysHolder[week][counter].setText("" + i);
				counter++;
				i++;
			} else if (counter < 6 && i < 10 && i == today && !sameMonth && !sameYear) {
				daysHolder[week][counter].setText("" + i);
				counter++;
				i++;
			} else if (counter < 6 && i < 10 && i == today && sameMonth && sameYear) {
				daysHolder[week][counter].setText("" + i);
				daysHolder[week][counter].setForeground(Color.blue);
				counter++;
				i++;
			} else if (counter == 6 && i < 10 && i != today) {
				daysHolder[week][counter].setText("" + i);
				counter = 0;
				i++;
				week++;
			} else if (counter == 6 && i < 10 && i == today && !sameMonth) {
				daysHolder[week][counter].setText("" + i);
				counter = 0;
				i++;
				week++;
			} else if (counter == 6 && i < 10 && i == today && sameMonth && !sameYear) {
				daysHolder[week][counter].setText("" + i);
				counter = 0;
				i++;
				week++;
			} else if (counter == 6 && i < 10 && i == today && sameMonth && sameYear) {
				daysHolder[week][counter].setText("" + i);
				daysHolder[week][counter].setForeground(Color.blue);
				counter++;
				i++;
			} else if (counter < 6 && i >= 10 && i < lengthOfMonth && i != today) {
				daysHolder[week][counter].setText("" + i);
				counter++;
				i++;
			} else if (counter < 6 && i >= 10 && i < lengthOfMonth && i == today && !sameMonth) {
				daysHolder[week][counter].setText("" + i);
				counter++;
				i++;
			} else if (counter < 6 && i >= 10 && i < lengthOfMonth && i == today && sameMonth && !sameYear) {
				daysHolder[week][counter].setText("" + i);
				counter++;
				i++;
			} else if (counter < 6 && i >= 10 && i < lengthOfMonth && i == today && sameMonth && sameYear) {
				daysHolder[week][counter].setText("" + i);
				daysHolder[week][counter].setForeground(Color.blue);
				counter++;
				i++;
			} else if (counter == 6 && i >= 10 && i < lengthOfMonth && i != today) {
				daysHolder[week][counter].setText("" + i);
				counter = 0;
				i++;
				week++;
			} else if (counter == 6 && i >= 10 && i < lengthOfMonth && i == today && !sameMonth) {
				daysHolder[week][counter].setText("" + i);
				counter = 0;
				i++;
				week++;
			} else if (counter == 6 && i >= 10 && i < lengthOfMonth && i == today && sameMonth && !sameYear) {
				daysHolder[week][counter].setText("" + i);
				counter = 0;
				i++;
				week++;
			} else if (counter == 6 && i >= 10 && i < lengthOfMonth && i == today && sameMonth && sameYear) {
				daysHolder[week][counter].setText("" + i);
				daysHolder[week][counter].setForeground(Color.blue);
				counter++;
				i++;
			} else if (counter <= 6 && i >= 10 && i == lengthOfMonth && i != today) {
				daysHolder[week][counter].setText("" + i);
				break;
			} else if (counter == 6 && i >= 10 && i == lengthOfMonth && i == today && !sameMonth) {
				daysHolder[week][counter].setText("" + i);
				break;
			} else if (counter == 6 && i >= 10 && i == lengthOfMonth && i == today && sameMonth && !sameYear) {
				daysHolder[week][counter].setText("" + i);
				break;
			} else if (counter == 6 && i >= 10 && i == lengthOfMonth && i == today && sameMonth && sameYear) {
				daysHolder[week][counter].setText("" + i);
				daysHolder[week][counter].setForeground(Color.blue);
				counter++;
				i++;
				break;
			} else {
				System.out.println("error");
				break;
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		this.display();
	}

	/**
	 * Moves the selected date forwards by a month
	 */
	public void next() {
		model.advanceSelectedDateByMonth(1);
	}

	/**
	 * Moves the selected date forwards by a month
	 */
	public void previous() {
		model.advanceSelectedDateByMonth(-1);		
	}

}