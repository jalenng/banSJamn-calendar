package calendar;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A file importer allows for the user to import recurring events from a
 * user-selected .txt file
 * 
 * @author Shana, Jalen
 * @version 7/31/20
 * @copyright banSJamn
 */
class FileImporter {

	/**
	 * Imports events to a model from a user-selected .txt file
	 * 
	 * @param model to import events to
	 * @return true if events from file were imported, false otherwise
	 */
	public static boolean importFile(CalendarModel model) {
		JFileChooser fileChooser;

		fileChooser = new JFileChooser(new File("src"));
		fileChooser.setFileFilter(new FileNameExtensionFilter("Text Documents (*.txt)", "txt"));

		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			if (addEvents(fileChooser.getSelectedFile(), model)) {
				JOptionPane.showMessageDialog(null, "Events imported successfully.", "Import File",
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Error importing events from file.", "Import File",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		return false;
	}

	/**
	 * Adds files to the CalendarModel model
	 * 
	 * @param file File with events
	 * @param model Calendar model to add events to
	 * @return true if file was successfully parsed and events were imported, false
	 *         otherwise
	 */
	private static boolean addEvents(File file, CalendarModel model) {

		try {
			Scanner filesc = new Scanner(file);

			while (filesc.hasNextLine()) {
				String eventString = filesc.nextLine();
				String[] eventArray = eventString.split(";");

				//
				// Assuming recurring events start at the first day
				// of the specified months
				//
				LocalDate startDate = LocalDate.of(Integer.parseInt(eventArray[1].trim()),
						Integer.parseInt(eventArray[2].trim()), 1);

				LocalDate endDate = LocalDate.of(Integer.parseInt(eventArray[1].trim()),
						Integer.parseInt(eventArray[3].trim()), 1);

				LocalTime startTime = LocalTime.of(Integer.parseInt(eventArray[5].trim()), 0);

				LocalTime endTime = LocalTime.of(Integer.parseInt(eventArray[6].trim()), 0);

				Event newEvent = new Event(eventArray[0].trim(), eventArray[4].trim(), startTime, endTime, startDate,
						endDate);

				model.addEvent(newEvent);

			}
			filesc.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}

}
