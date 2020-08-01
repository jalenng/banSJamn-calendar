package calendar;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Creates the panel to input the filepath for recurring events
 * 
 * @author Jalen
 * @version 7/31/20
 * @copyright banSJamn
 */

class CreateView extends JPanel {

	CalendarModel model;

	/**
	 * Creates the Create view
	 * 
	 * @param m
	 */
	public CreateView(CalendarModel m) {
		model = m;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// create all JComponents
		JLabel titleLabel = new JLabel("Create New Event");
		JLabel nameLabel = new JLabel("Name");
		JLabel dateLabel = new JLabel("Date (MM/DD/YYYY)");
		JLabel startTimeLabel = new JLabel("Start time (HH)");
		JLabel endTimeLabel = new JLabel("End time (HH)");

		JTextField nameTextField = new JTextField();
		JTextField dateTextField = new JTextField();
		JTextField startTimeTextField = new JTextField();
		JTextField endTimeTextField = new JTextField();

		JButton saveButton = new JButton("Save");

		// set dimension of text fields
		Dimension TextFieldDimension = new Dimension(800, 20);
		nameTextField.setMaximumSize(TextFieldDimension);
		startTimeTextField.setMaximumSize(TextFieldDimension);
		endTimeTextField.setMaximumSize(TextFieldDimension);
		dateTextField.setMaximumSize(TextFieldDimension);

		// add action listener to save button
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String message = "";

				try {
					// parsing the text in the text fields
					String nameStr = nameTextField.getText();
					if (nameStr.length() == 0)
						throw new NullPointerException();

					String dateStr = dateTextField.getText();
					String[] dateSplitted = dateStr.split("/"); // 0: MM; 1: DD; 2: YYYY
					LocalDate date = LocalDate.of(Integer.parseInt(dateSplitted[2].trim()),
							Integer.parseInt(dateSplitted[0].trim()), Integer.parseInt(dateSplitted[1].trim()));

					String startTimeStr = startTimeTextField.getText();
					LocalTime startTime = LocalTime.of(Integer.parseInt(startTimeStr), 0);

					String endTimeStr = endTimeTextField.getText();
					LocalTime endTime = LocalTime.of(Integer.parseInt(endTimeStr), 0);

					// create and add event
					Event event = new Event(nameStr, date, startTime, endTime);

					if (model.addEvent(event)) // returns true if added successfully
						message = "Saved the event: " + event;
					else
						message = "Not saved. There is a conflict with an existing event.";
				}

				// catch exceptions thrown by invalid user input
				catch (ArrayIndexOutOfBoundsException exception) {
					message = "Check the date format";
				} catch (NumberFormatException | DateTimeException exception) {
					message = "Check the date and times";
				} catch (NullPointerException exception) {
					message = "Provide a name";
				} finally {
					JOptionPane.showMessageDialog(null, message, "Create Event", JOptionPane.INFORMATION_MESSAGE);
				}
			}

		});

		// add all JComponents to this JPanel
		this.add(titleLabel);

		this.add(nameLabel);
		this.add(nameTextField);

		this.add(dateLabel);
		this.add(dateTextField);

		this.add(startTimeLabel);
		this.add(startTimeTextField);

		this.add(endTimeLabel);
		this.add(endTimeTextField);

		this.add(saveButton);

	}

}