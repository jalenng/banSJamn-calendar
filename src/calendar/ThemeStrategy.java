package calendar;

import javax.swing.JPanel;

/**
 * Interface for Calendar themes
 */
public interface ThemeStrategy {

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
