package calendar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Calendar Project
 * @author shana
 * @version 1.0
 * @copyright banSJamn
 */

/**
 * A Calendar theme with Moon and Stars
 */
public class SpaceTheme implements ThemeStrategy {
	
	private int width = 1000;
	
	/**
	 * Updates the width of the theme
	 */
	public void updateWidth(int w) {
		width = w;
	}
	
	
	/**
	 * Creates a view for the top part of the theme
	 * @return panel
	 */
	public JPanel displayTop() {
		JPanel panel = new JPanel();
		
		MoonAndStar moonAndStar = new MoonAndStar(width, 50);
		JLabel moonAndStarLabel = new JLabel(moonAndStar);
		
		panel.add(moonAndStarLabel);
		
		panel.setBackground(Color.gray);
				
		return panel;
	}
	
	
	/**
	 * Creates a view for the bottom part of the theme
	 * @return panel
	 */
	public JPanel displayBottom() {
		JPanel panel = new JPanel();

		Stars moon = new Stars(width, 50);
		JLabel moonLabel = new JLabel(moon);
		
		panel.add(moonLabel);
		
		panel.setBackground(Color.gray);
		
		return panel;
	}
	
	/**
	 * Returns a string representation of the theme's name
	 */
	public String toString() {
		return "Space Theme";
	}
	
	/**
	 * A class that creates a moon and star design
	 */
	private class MoonAndStar implements Icon {

		private int width;
		private int height;
		
		/**
		 * Constructs the MoonAndStar
		 * @param diameter
		 */
		public MoonAndStar(int w, int diameter) {
			width = w;
			height = diameter;
		}
		
		/**
		 * Creates the Moon and Star graphics
		 */
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setColor(Color.white);
			
			Ellipse2D.Double moon = new Ellipse2D.Double(5, 5, height, height);
			
			int starCount = 0;
			while(starCount < 20) {
				double leftX = height + 10 + Math.random()*900;
				double centerY = height/4 + Math.random() * height/2;
				double sizeH = 15 + Math.random() * 10;
				double sizeV = 20 + Math.random() * 20;
				double topY = centerY - sizeV/2;
				// vertical line
				Line2D.Double starLine1 = new Line2D.Double(leftX + sizeH/2, topY, leftX + sizeH/2, topY + sizeV);
				// horizontal line
				Line2D.Double starLine2 = new Line2D.Double(leftX, topY + sizeV/2, leftX + sizeH, topY + sizeV/2);
				// bottom left to top right
				Line2D.Double starLine3 = new Line2D.Double(leftX + 2*sizeH/6, topY + 3*sizeV/4, leftX + 4*sizeH/6, topY + sizeV/4);
				Line2D.Double starLine4 = new Line2D.Double(leftX + 2*sizeH/6, topY + sizeV/4, leftX + 4*sizeH/6, topY + 3*sizeV/4);

				g2.draw(starLine1);
				g2.draw(starLine2);
				g2.draw(starLine3);
				g2.draw(starLine4);
				
				starCount++;
			}
			
			g2.draw(moon);
			g2.fill(moon);
	
		}
		

		/**
		 * Returns icon width
		 * @return width
		 */
		@Override
		public int getIconWidth() {
			return width;
		}

		/**
		 * Returns icon height
		 * @return height + 10
		 */
		@Override
		public int getIconHeight() {
			return height + 10;
		}
		
	} // end Moon class
	
	
	/**
	 * Creates the Stars graphics
	 */
	private class Stars implements Icon {
		
		private int width;
		private int height;
		
		/**
		 * Constructs the Stars
		 * @param h
		 */
		public Stars(int w, int h) {
			width = w;
			height = h;
		}
		
		
		/**
		 * Creates the stars graphics
		 */
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setColor(Color.white);
			
			int starCount = 0;
			while(starCount < 40) {
				double leftX = Math.random() * 1000;
				double centerY = height/4 + Math.random() * height/2;
				double sizeH = 15 + Math.random() * 10;
				double sizeV = 20 + Math.random() * 20;
				double topY = centerY - sizeV/2;
				Line2D.Double starLine1 = new Line2D.Double(leftX + sizeH/2, topY, leftX + sizeH/2, topY + sizeV);
				Line2D.Double starLine2 = new Line2D.Double(leftX, topY + sizeV/2, leftX + sizeH, topY + sizeV/2);
				Line2D.Double starLine3 = new Line2D.Double(leftX + 2*sizeH/6, topY + 3*sizeV/4, leftX + 4*sizeH/6, topY + sizeV/4);
				Line2D.Double starLine4 = new Line2D.Double(leftX + 2*sizeH/6, topY + sizeV/4, leftX + 4*sizeH/6, topY + 3*sizeV/4);

				g2.draw(starLine1);
				g2.draw(starLine2);
				g2.draw(starLine3);
				g2.draw(starLine4);
				
				starCount++;
			}
				
		}
		

		/**
		 * Returns the width
		 * @return width
		 */
		@Override
		public int getIconWidth() {
			return width;
		}

		
		/**
		 * Returns the height
		 * @return height;
		 */
		@Override
		public int getIconHeight() {
			return height;
		}
		
	} // end Stars class
	
}
