package calendar;

import javax.swing.JPanel;

/**
 * Calendar Project
 * @author shana
 * @version 1.0
 * @copyright banSJamn
 */

/**
 * Interface for Calendar themes
 */
public interface ThemeStrategy {
	
	/**
	 * Update the width of the theme
	 */
	void updateWidth(int value);
	
	/**
	 * Get the JPanel for the top part of the theme
	 * @return
	 */
	JPanel displayTop();
	
	/**
	 * Get the JPanel for the bottom part of the theme
	 * @return
	 */
	JPanel displayBottom();
	
	
}
