package calendar;

import java.time.LocalDate;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

public class SelectedMonthView extends JPanel implements CalendarView {

	// Private instance variable used to navigate between months
	private String month;
	private int year;
	private int day;

	private LocalDate cal;

	private JButton highlighted;
	private CalendarModel model;

	private static int todayDate;
	private static String thisMonth;
	private static int thisYear;

	/**
	 * Constructor which updates the value of n and calls the display method to
	 * display the calendar.
	 * 
	 * @param n is the month you want to show (either before or after the current
	 *          month)
	 */
	public SelectedMonthView(CalendarModel m) {
		this.model = m;
		cal = model.getSelectedDate();
		thisMonth = cal.getMonth().name();
		thisYear = cal.getYear();
		todayDate = cal.getDayOfMonth();
		display();
	}

	/**
	 * Creates the calendar. The method takes in the number of months before or
	 * after the current month and creates an updated calendar.
	 * 
	 */
	public void display() {

		// Clear old components
		this.removeAll();
		this.revalidate();
		// Get today's date and month
		cal = model.getSelectedDate();
		int today = cal.getDayOfMonth();

		// Creating the title and adding the left and right button for navigation
		JButton left = new JButton();
		JButton right = new JButton();

		for (int i = 0; i < 7; i++) {
			if (i == 0) {
				JLabel month = new JLabel(cal.getMonth().name(), SwingConstants.CENTER);
				month.setBackground(Color.WHITE);
				month.setOpaque(true);
				add(month);
			} else if (i == 1) {
				JLabel year = new JLabel("" + cal.getYear(), SwingConstants.CENTER);
				year.setBackground(Color.WHITE);
				year.setOpaque(true);
				add(year);
			} else if (i == 5) {
				left = new JButton(" < ");
				left.setBackground(Color.LIGHT_GRAY);
				left.setOpaque(true);
				left.setBorderPainted(false);
				add(left);
			} else if (i == 6) {
				right = new JButton(" > ");
				right.setBackground(Color.LIGHT_GRAY);
				right.setOpaque(true);
				right.setBorderPainted(false);
				add(right);
			} else {
				JLabel empty = new JLabel("");
				empty.setBackground(Color.WHITE);
				empty.setOpaque(true);
				add(empty);
			}
		}

		// Adding functionality to the left button so that it moves one month before
		left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				previous();
			}
		});

		// Adding functionality to the right button so that it moves one month after
		right.addActionListener(new ActionListener() {
			@Override
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
			add(daysOfWeek);
		}

		// Creaing the calendar with each date as a button
		JButton[][] daysHolder = new JButton[6][7];
		this.setLayout(new GridLayout(8, 7));
		for (int m = 0; m < 6; m++) {
			for (int y = 0; y < 7; y++) {
				daysHolder[m][y] = new JButton();
				daysHolder[m][y].setBackground(Color.WHITE);
				daysHolder[m][y].setOpaque(true);
				daysHolder[m][y].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						month = cal.getMonth().name();
						year = cal.getYear();

						if (((JButton) e.getSource()).getText() == "") {
							day = 0;
						} else {
							day = Integer.parseInt(((JButton) e.getSource()).getText());
						}

						if (highlighted != null) {
							highlighted.setBackground(Color.WHITE);
							highlighted.setOpaque(true);
							highlighted.setBorderPainted(true);
						}

						if (thisMonth.compareTo(month) == 0 && thisYear == year && todayDate == day) {
							highlighted.setBackground(Color.BLUE);
							highlighted.setOpaque(false);
							highlighted.setBorderPainted(true);
						}

						if ((thisMonth.compareTo(month) == 0 && thisYear == year && todayDate != day)) {
							highlighted = ((JButton) e.getSource());
							highlighted.setBackground(Color.CYAN);
							highlighted.setOpaque(true);
							highlighted.setBorderPainted(false);
						}

						if ((thisMonth.compareTo(month) == 0 && thisYear != year && todayDate != day)) {
							highlighted = ((JButton) e.getSource());
							highlighted.setBackground(Color.CYAN);
							highlighted.setOpaque(true);
							highlighted.setBorderPainted(false);
						}

						if ((thisMonth.compareTo(month) != 0 && thisYear == year && todayDate != day)) {
							highlighted = ((JButton) e.getSource());
							highlighted.setBackground(Color.CYAN);
							highlighted.setOpaque(true);
							highlighted.setBorderPainted(false);
						}

						if ((thisMonth.compareTo(month) != 0 && thisYear != year && todayDate != day)) {
							highlighted = ((JButton) e.getSource());
							highlighted.setBackground(Color.CYAN);
							highlighted.setOpaque(true);
							highlighted.setBorderPainted(false);
						}

						if (day == 0) {
							highlighted.setBackground(Color.WHITE);
							highlighted.setOpaque(false);
							highlighted.setBorderPainted(true);
						}

					}

				});
				add(daysHolder[m][y]);
			}
		}

		// First day of the month
		LocalDate cl = LocalDate.of(cal.getYear(), cal.getMonthValue(), 1);
		String firstDay = cl.getDayOfWeek().name();

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
			if(i == 1) {
				daysHolder[week][counter].setText("" + i);
				daysHolder[week][counter].setForeground(Color.RED);
			}
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
				daysHolder[week][counter].setBackground(Color.blue);
				daysHolder[week][counter].setOpaque(true);
				daysHolder[week][counter].setBorderPainted(false);
				daysHolder[week][counter].setForeground(Color.white);
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
				daysHolder[week][counter].setBackground(Color.blue);
				daysHolder[week][counter].setOpaque(true);
				daysHolder[week][counter].setBorderPainted(false);
				daysHolder[week][counter].setForeground(Color.white);
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
				daysHolder[week][counter].setBackground(Color.blue);
				daysHolder[week][counter].setOpaque(true);
				daysHolder[week][counter].setBorderPainted(false);
				daysHolder[week][counter].setForeground(Color.white);
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
				daysHolder[week][counter].setBackground(Color.blue);
				daysHolder[week][counter].setOpaque(true);
				daysHolder[week][counter].setBorderPainted(false);
				daysHolder[week][counter].setForeground(Color.white);
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
				daysHolder[week][counter].setBackground(Color.blue);
				daysHolder[week][counter].setOpaque(true);
				daysHolder[week][counter].setBorderPainted(false);
				daysHolder[week][counter].setForeground(Color.white);
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
		this.display();
	}

	/**
	 * Moves the selected date forwards by a month
	 */
	public void previous() {
		model.advanceSelectedDateByMonth(-1);
		this.display();
	}
}
