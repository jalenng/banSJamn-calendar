package calendar;
import java.time.LocalDate;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SelectedMonthView extends JPanel implements CalendarView{

	// Private instance variable used to navigate between months
	private int n;
	private String month;
	private int year;
	private int day;
	private LocalDate cal;
	private JButton highlighted;
	private LocalDate todayDate;
	
	/**
	 * Constructor which updates the value of n and calls the display method to
	 * display the calendar.
	 * 
	 * @param n is the month you want to show (either before or after the current
	 * month)
	 */
	public SelectedMonthView(int n) {
		this.n = n;
		this.setSize(400, 400);
		this.setBackground(Color.white);
		this.setOpaque(true);
		this.todayDate = LocalDate.now();
		display(n);
	}

	/**
	 * Helper method that returns the value of n
	 * 
	 * @return value of n
	 */
	public int getN() {
		return n;
	}

	/**
	 * Helper method that sets the value of n
	 * 
	 * @param new value of n
	 */
	public void setN(int n) {
		this.n = n;
	}

	/**
	 * Creates the calendar. The method takes in the number of months before or
	 * after the current month and creates an updated calendar.
	 * 
	 * @param number of months previous or after the current month
	 */
	public void display(int n) {

		// Get today's date and month
		cal = LocalDate.now();
		int today = cal.getDayOfMonth();
		String thisMonth = cal.getMonth().name();
		int thisYear = cal.getYear();

		// For previous or next month
		if (n < 0) {
			cal = cal.minusMonths(Math.abs(n));
		} else if (n > 0) {
			cal = cal.plusMonths(n);
		}

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
				removeAll();
				setN(getN() - 1);
				display(getN());
				repaint();
			}
		});

		// Adding functionality to the right button so that it moves one month after
		right.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				setN(getN() + 1);
				display(getN());
				repaint();
			}

		});
		
		// Show the days on top
		String[] days = { "S", "M", "T", "W", "T", "F", "S" };

		for (int i = 0; i < 7; i++) {
			add(new JLabel(days[i], SwingConstants.CENTER));
		}

		// Creaing the calendar with each date as a button
		JButton[][] daysHolder = new JButton[6][7];
		this.setLayout(new GridLayout(8, 7));
		for (int m = 0; m < 6; m++) {
			for (int y = 0; y < 7; y++) {
				daysHolder[m][y] = new JButton();
				daysHolder[m][y].setBackground(Color.WHITE);
				daysHolder[m][y].setOpaque(true);
				daysHolder[m][y].addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						month = cal.getMonth().name(); 
						year = cal.getYear();
						if(((JButton)e.getSource()).getText() == "") {
							day = 0;
						} else {
							day = Integer.parseInt(((JButton)e.getSource()).getText());
						}
						String thisMonth = getTodayDate().getMonth().name();
						
						if(highlighted != null) {
							highlighted.setBackground(Color.WHITE);
							highlighted.setOpaque(true);
							highlighted.setBorderPainted(true);
						}
						
						if (thisMonth.compareTo(month) == 0 && getTodayDate().getYear() == year
								&& getTodayDate().getDayOfMonth() == day) {
							highlighted.setBackground(Color.BLUE);
							highlighted.setOpaque(false);
							highlighted.setBorderPainted(true);
						} 
						
						if( (thisMonth.compareTo(month) == 0 && getTodayDate().getYear() == year
								&& getTodayDate().getDayOfMonth() != day)){
							highlighted = ((JButton) e.getSource());
							highlighted.setBackground(Color.CYAN);
							highlighted.setOpaque(true);
							highlighted.setBorderPainted(false);
						} 
						
						if(day == 0) {
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
	public LocalDate getTodayDate() {
		return todayDate;
	}

	public void setTodayDate(LocalDate todayDate) {
		this.todayDate = todayDate;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
	}

	@Override
	public void previous() {
		// TODO Auto-generated method stub
	}
}
