package calendar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

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
 * A Calendar Theme with Turtles and Sheep
 */
public class AnimalTheme implements ThemeStrategy {

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
		
		Turtle turtle = new Turtle(width, 50);
		JLabel topLabel = new JLabel(turtle);
		
		panel.add(topLabel);
		
		panel.setBackground(Color.orange);
		
		return panel;
	}

	
	/**
	 * Creates a view for the bottom part of the theme
	 * @return panel
	 */
	public JPanel displayBottom() {
		JPanel panel = new JPanel();
		
		Sheep sheep = new Sheep(width, 50);
		JLabel bottomLabel = new JLabel(sheep);
		
		panel.add(bottomLabel);
		
		panel.setBackground(Color.orange);
		
		return panel;
	}
	
	/**
	 * Returns a string representation of the theme's name
	 */
	public String toString() {
		return "Animal Theme";
	}	
	
	/**
	 * A class that creates the turtles
	 */
	private class Turtle implements Icon {

		int width = 1000;
		int height;
		
		/**
		 * Constructs a Turtle
		 * @param h
		 */
		public Turtle(int w, int h) {
			width = w;
			height = h;
		}
		
		
		/**
		 * Creates multiple turtles
		 */
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			Graphics2D g2 = (Graphics2D) g;
						
			createTurtle(g2, width/5);
			createTurtle(g2, 3.3*width/5);
		}
		
		
		/**
		 * Creates the turtle graphics
		 * @param g2
		 * @param shellX
		 */
		public void createTurtle(Graphics2D g2, double shellX) {
			
			int turtleWidth = 100;
			double shellY = 3*height/4;
			
			Path2D.Double topShell = new Path2D.Double();
			topShell.moveTo(shellX, shellY);
			topShell.curveTo(shellX, shellY, shellX + turtleWidth/2, 0, shellX + turtleWidth, shellY);
			
			Path2D.Double bottomShell = new Path2D.Double();
			bottomShell.moveTo(shellX, shellY);
			bottomShell.curveTo(shellX, shellY, shellX + turtleWidth/2, 4*height/5, shellX + turtleWidth, shellY);
			
			Path2D.Double head = new Path2D.Double();
			head.moveTo(shellX + 10, shellY);
			head.curveTo(shellX - 10, shellY + 10, shellX - 20, shellY - 15, shellX - 10, shellY - 10);
			
			Line2D.Double completeHead = new Line2D.Double(shellX - 10, shellY - 10, shellX + 3, shellY - 3);
			
			Path2D.Double leftLeftFin = new Path2D.Double();
			leftLeftFin.moveTo(shellX + 10, shellY);
			leftLeftFin.curveTo(shellX + 15, shellY + 2, shellX - 15, 4*height/5, shellX + 30, height);
			
			Path2D.Double rightLeftFin = new Path2D.Double();
			rightLeftFin.moveTo(shellX + 30, height);
			rightLeftFin.curveTo(shellX + 30, height, shellX + 20, 4*height/5, shellX + 40, shellY + 1);
			
			
			Path2D.Double leftRightFin = new Path2D.Double();
			leftRightFin.moveTo(shellX + 75, shellY + 1);
			leftRightFin.curveTo(shellX + 75, shellY + 1, shellX + 40, 4*height/5, shellX + 80, height);
			
			Path2D.Double rightRightFin = new Path2D.Double();
			rightRightFin.moveTo(shellX + 80, height);
			rightRightFin.curveTo(shellX + 80, height, shellX + 70, 4*height/5, shellX + 85, shellY + 1);
			
			g2.draw(topShell);
			g2.fill(topShell);
			
			g2.draw(bottomShell);
			g2.fill(bottomShell);
			
			g2.draw(head);
			g2.draw(completeHead);
			g2.draw(leftLeftFin);
			g2.draw(rightLeftFin);
			g2.draw(leftRightFin);
			g2.draw(rightRightFin);
			
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
		 * @return height + 20
		 */
		@Override
		public int getIconHeight() {
			return height + 20;
		}
		
	} // end Turtle class
	
	
	/**
	 * A class to create sheep
	 */
	private class Sheep implements Icon {
		
		int width = 1000;
		int height;
		
		
		/**
		 * Constructs a Sheep
		 * @param h
		 */
		public Sheep(int w, int h) {
			width = w;
			height = h;
		}
		
		
		/**
		 * Creates multiple sheep
		 */
		@Override
		public void paintIcon(Component c, Graphics g, int x, int y) {
			Graphics2D g2 = (Graphics2D) g;
			
			createSheep(g2, width/15);
			createSheep(g2, 7*width/15);
			createSheep(g2, 13*width/15);	
		}
		
		
		/**
		 * Creates the sheep graphics
		 * @param g2
		 * @param sheepX
		 */
		public void createSheep(Graphics2D g2, double sheepX) {
			
			int sheepWidth = 100;
			double sheepY = height/2;
			
			Ellipse2D.Double mainPuff = new Ellipse2D.Double(sheepX, sheepY, sheepWidth/2, height/3);
			
			Ellipse2D.Double puff1 = new Ellipse2D.Double(sheepX - 5, sheepY, 10, 10);
			Ellipse2D.Double puff2 = new Ellipse2D.Double(sheepX + 5, sheepY - 3, 10, 10);
			Ellipse2D.Double puff3 = new Ellipse2D.Double(sheepX + 15, sheepY - 5, 10, 10);
			Ellipse2D.Double puff4 = new Ellipse2D.Double(sheepX + 25, sheepY - 5, 10, 10);
			Ellipse2D.Double puff5 = new Ellipse2D.Double(sheepX + 35, sheepY - 3, 10, 10);
			Ellipse2D.Double puff6 = new Ellipse2D.Double(sheepX + 45, sheepY, 10, 10);
			
			Ellipse2D.Double puff7 = new Ellipse2D.Double(sheepX + 43, sheepY + 5, 10, 10);
			Ellipse2D.Double puff8 = new Ellipse2D.Double(sheepX + 35, sheepY + 9, 10, 10);
			Ellipse2D.Double puff9 = new Ellipse2D.Double(sheepX + 25, sheepY + 9, 10, 10);
			Ellipse2D.Double puff10 = new Ellipse2D.Double(sheepX + 25, sheepY + 10, 10, 10);
			Ellipse2D.Double puff11 = new Ellipse2D.Double(sheepX + 15, sheepY + 10, 10, 10);
			Ellipse2D.Double puff12 = new Ellipse2D.Double(sheepX + 5, sheepY + 9, 10, 10);
			Ellipse2D.Double puff13 = new Ellipse2D.Double(sheepX - 3, sheepY + 5, 10, 10);
			
			Ellipse2D.Double[] puffs = {puff1, puff2, puff3, puff4, puff5, 
					puff6, puff7, puff8, puff9, puff10, puff11, puff12, puff13};
			
			Rectangle2D.Double foot1 = new Rectangle2D.Double(sheepX + 5, sheepY + 8, 7, 20);
			Rectangle2D.Double foot2 = new Rectangle2D.Double(sheepX + 38, sheepY + 8, 7, 20);
			
			Path2D.Double head = new Path2D.Double();
			head.moveTo(sheepX + 50, sheepY + 10);
			head.curveTo(sheepX + 65, sheepY + 15, sheepX + 75, sheepY - 3, sheepX + 60, sheepY - 3);
			
			Line2D.Double completeHead = new Line2D.Double(sheepX + 60, sheepY - 2, sheepX + 50, sheepY + 7);
			
			Path2D.Double tail = new Path2D.Double();
			tail.moveTo(sheepX - 3, sheepY + 9);
			tail.curveTo(sheepX - 3, sheepY + 9, sheepX - 20, sheepY + 14, sheepX - 2, sheepY + 12);
			
			g2.draw(foot1);
			g2.draw(foot2);
			
			g2.draw(head);
			g2.draw(completeHead);
			
			g2.draw(tail);
						
			g2.draw(mainPuff);
			g2.fill(mainPuff);
			
			for(Ellipse2D.Double puff : puffs) {
				g2.draw(puff);
				g2.fill(puff);
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
		 * @return height + 20
		 */
		@Override
		public int getIconHeight() {
			return height + 20;
		}
		
	} // end Sheep class
	
}
