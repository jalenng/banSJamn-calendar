package calendar;

import javax.swing.JPanel;

/**
 * Interface for Calendar themes
 * 
 * @author shana
 * @version 7/31/20
 * @copyright banSJamn
 */
public interface ThemeStrategy {

	/**
	 * Update the width of the theme
	 * 
	 * @param value
	 */
	void updateWidth(int value);

	/**
	 * Get the JPanel for the top part of the theme
	 * 
	 * @return JPanel 
	 */
	JPanel displayTop();

	/**
	 * Get the JPanel for the bottom part of the theme
	 *
	 * @return JPanel
	 */
	JPanel displayBottom();

}
