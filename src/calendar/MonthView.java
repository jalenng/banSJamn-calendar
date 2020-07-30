package calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.swing.*;
import javax.swing.event.ChangeEvent;

/**
 * This class creates the MonthView. It has the whole month's
 * calendar along with the events for each day.
 * @author aryanmaheshwari
 * @version 7/29/20
 */
public class MonthView extends JPanel implements CalendarView {
	// Private instance variable used to navigate between months
	private CalendarModel model;
	private int lengthOfMonth;
	private boolean sameMonth;
	private boolean sameYear;
	private LocalDate cal;
	private String thisMonth;
	private int thisYear;

	/**
	 * Constructor which updates the value of n and calls the display method to
	 * display the calendar.
	 * 
	 * @param n is the month you want to show (either before or after the current
	 *          month)
	 */
	public MonthView(CalendarModel m) {
		this.model = m;
		cal = m.getSelectedDate();
		lengthOfMonth = cal.lengthOfMonth();
		sameMonth = true;
		sameYear = true;
		thisMonth = cal.getMonth().name();
		thisYear = cal.getYear();
		display();
	}

	public String getThisMonth() {
		return thisMonth;
	}

	public void setThisMonth(String thisMonth) {
		this.thisMonth = thisMonth;
	}

	public int getThisYear() {
		return thisYear;
	}

	public void setThisYear(int thisYear) {
		this.thisYear = thisYear;
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
		cal = model.getSelectedDate();
		int today = cal.getDayOfMonth();

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
		lengthOfMonth = cal.lengthOfMonth();
		sameMonth = (cal.getMonth().name().compareTo(this.getThisMonth()) == 0);
		sameYear = (cal.getYear() == this.getThisYear());

		ArrayList<String> data = new ArrayList<String>();
		try {
			FileReader fr = new FileReader("/Users/aryanmaheshwari/eclipse-workspace/banSJamn-calendar/src/input.txt");
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				data.add(line);
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException f) {
			// If the file does not exist, handle the exception
			f.printStackTrace();
		} catch (java.io.IOException e) {
			// If there is any error in the input, handle the exception
			e.printStackTrace();
		}

		String[] info = data.get(0).split(";");
		for (int k = 0; k < data.size(); k++) {
			info = data.get(k).split(";");
		}

		while (i <= lengthOfMonth) {
			if (i == 1) {
				daysHolder[week][counter].setText("" + i);
				daysHolder[week][counter].setForeground(Color.RED);
			}
			if (counter < 6 && i < 10 && i != today) {
				daysHolder[week][counter].setText("" + i);
				counter++;
				i++;
			} else if (counter < 6 && i < 10 && i == today && !sameMonth) {
				daysHolder[week][counter].setText("" + i);
				counter++;
				i++;
			} else if (counter < 6 && i < 10 && i == today && !sameMonth && !sameYear) {
				daysHolder[week][counter].setText("" + i);
				counter++;
				i++;
			} else if (counter < 6 && i < 10 && i == today && sameMonth && sameYear) {
				daysHolder[week][counter].setText("" + i);
				// today
				daysHolder[week][counter].setForeground(Color.BLUE);
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
				// today
				daysHolder[week][counter].setForeground(Color.BLUE);
				counter = 0;
				i++;
				week++;
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
				// today
				daysHolder[week][counter].setForeground(Color.BLUE);
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
				// today
				daysHolder[week][counter].setForeground(Color.BLUE);
				counter = 0;
				i++;
				week++;
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
				// today
				daysHolder[week][counter].setForeground(Color.BLUE);
				counter++;
				i++;
				break;
			} else {
				System.out.println("error");
				break;
			}
		}

		/*i = 1;
		week = 0;
		boolean equalYear = (cal.getYear() == Integer.parseInt(info[1]));
		System.out.println(equalYear);
		boolean equalStartMonth = (cal.getMonth().getValue() == Integer.parseInt(info[2]));
		boolean equalLastMonth = (cal.getMonth().getValue() == Integer.parseInt(info[3]));
		while (i <= lengthOfMonth) {
			if (equalYear && (equalStartMonth || equalLastMonth)) {
				if (info[5].contains("S")) {
					daysHolder[week][0].setText(daysHolder[week][0].getText() + "\n" + info[1]);
				}
				if (info[5].contains("M")) {
					daysHolder[week][1].setText(daysHolder[week][1].getText() + "\n" + info[1]);
				}
				if (info[5].contains("T")) {
					daysHolder[week][2].setText(daysHolder[week][2].getText() + "\n" + info[1]);
				}
				if (info[5].contains("W")) {
					daysHolder[week][3].setText(daysHolder[week][3].getText() + "\n" + info[1]);
				}
				if (info[5].contains("H")) {
					daysHolder[week][4].setText(daysHolder[week][4].getText() + "\n" + info[1]);
				}
				if (info[5].contains("F")) {
					daysHolder[week][5].setText(daysHolder[week][5].getText() + "\n" + info[1]);
				}
				if (info[5].contains("A")) {
					daysHolder[week][6].setText(daysHolder[week][6].getText() + "\n" + info[1]);
				}
			}
			i++;
		}*/
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
		display();
	}

	/**
	 * Moves the selected date forwards by a month
	 */
	public void previous() {
		model.advanceSelectedDateByMonth(-1);
		display();
	}

}