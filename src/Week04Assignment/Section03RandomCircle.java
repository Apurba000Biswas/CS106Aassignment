package Week04Assignment;

import java.awt.Color;
import acm.graphics.*;
import acm.program.*;
import acm.util.RandomGenerator;

public class Section03RandomCircle extends GraphicsProgram{
	

	
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	public void run() {
		for(int i=0; i<10; i++) {
			drawRandromCircle();
		}
	}
	
	private void drawRandromCircle() {
		double x = rgen.nextDouble(0, getWidth());
		double y = rgen.nextDouble(0, getHeight());
		double size = rgen.nextDouble(5, 50);
		Color c = rgen.nextColor();
		drawCircle(x, y, size, size, c);
	}
	
	/**drawBall(x,y) mehtod
	 * ********************************************
	 */
	private void drawCircle(double x, double y, double width, double height, Color c) {
		GOval ball = new GOval(x,y,width,height);
		ball.setFilled(true);
		ball.setColor(c);
		add(ball);
	}
}
