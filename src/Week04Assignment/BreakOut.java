package Week04Assignment;
/* File: Breakout.java
* -------------------
* This file will eventually implement the game of Breakout.
*/
import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
public class BreakOut extends GraphicsProgram {
	/** Width and height of application window in pixels. On some platforms
	* these may NOT actually be the dimensions of the graphics canvas. */
	public static final int APPLICATION_WIDTH = 404;
	public static final int APPLICATION_HEIGHT = 600;
	/** Dimensions of game board. On some platforms these may NOT actually
	* be the dimensions of the graphics canvas. */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;
	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;
	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;
	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;
	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;
	/** Separation between bricks */
	private static final int BRICK_SEP = 4;
	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;
	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;
	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;
	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;
	/** Number of turns */
	private static final int NTURNS = 3;
	
	GRect paddle;
	GOval ball;
	private double vx, vy;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	public void run() {
		setUpGame();
		playGame();
	}
	
	private void playGame() {
		moveBall();
	}
	
	private void moveBall() {
		vx =  rgen.nextDouble(-3.0, +3.0);
		vx = +3.0;
		/*if(vx > 0) {
			vy = +3.0;
			moveBallTopDown();
		}else if(vx < 0) {
			vy = -3.0;
			moveBallTopDown();
		}*/
		vy = -3.0;
		moveBallBotomUp();
		
	}
	
	private void moveBallBotomUp() {
		double x = ball.getX();
		double y = ball.getY();
		while(x < WIDTH-(BALL_RADIUS) && x > 0) {
			x = x + vy;
			if(vy > 0) {
				y = y - vy;
			}else {
				y = y + vy;
			}
			
			ball.setLocation(x, y);
			pause(80);
		}
	}
	
	private void moveBallTopDown() {
		double x = ball.getX();
		double y = ball.getY();
		while(x < WIDTH-(BALL_RADIUS) && x > 0) { 
			x = x + vy;
			if(vy < 0) {
				y = y - vy;
			}else {
				y = y + vy;
			}
			
			ball.setLocation(x, y);
			pause(80);
		}
	}
	
	private void setUpGame() {
		/* Sets up the bricks*/
		setUpBricks();
		
		/* Create the paddle to bouch the ball*/
		createPaddle();
		addMouseListeners();
		
		/* Create the ball*/
		createBall();
	}
	
	private void createBall() {
		ball = new GOval((WIDTH/2) - BALL_RADIUS, ( HEIGHT/2) - BALL_RADIUS, BALL_RADIUS, BALL_RADIUS);
		ball.setFilled(true);
		ball.setColor(Color.BLACK);
		add(ball);
	}
	/**Method: mouseMoved
	 * 
	 * Gets triggered when the mouse enters the game window
	 * and move in the window
	 */
	public void mouseMoved(MouseEvent e) {
		if(e.getX() < WIDTH - PADDLE_WIDTH) {
			paddle.setLocation(e.getX(), HEIGHT - PADDLE_Y_OFFSET);
		}
	}
	/** Method: createPaddle
	 * 
	 * Creates the paddle
	 */
	private void createPaddle() {
		paddle = new GRect((WIDTH - PADDLE_WIDTH)/2, HEIGHT - PADDLE_Y_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.BLACK);
		
		add(paddle);
	}
	
	private void setUpBricks() {
		
		/* Create all rows of bricks with appropriate color*/
		for(int i=0; i< NBRICK_ROWS; i++) {
			createRowOfBricks(getColor(i), BRICK_Y_OFFSET + i * (BRICK_HEIGHT + BRICK_SEP));
		}
	}
	/*Method: getColor 
	 * 
	 * returns appropriate color for each row
	 */
	private Color getColor(int rowNum) {
		switch(rowNum) {
		case 0:
			return Color.RED;
		case 1:
			return Color.RED;
		case 2:
			return Color.ORANGE;
		case 3:
			return Color.ORANGE;
		case 4:
			return Color.GREEN;
		case 5:
			return Color.GREEN;
		case 6:
			return Color.YELLOW;
		case 7:
			return Color.YELLOW;
		case 8:
			return Color.CYAN;
		case 9:
			return Color.CYAN;
		default:
			return Color.BLACK;
		}
	}
	
	/*Method: createRowOfBricks
	* 
	* Create a row of bricks
	*/
	private void createRowOfBricks(Color c, int y) {
		/* Keeps track of the position of the breick in x axies*/
		int x = BRICK_SEP;
		
		/* Creates a row of bricks */
		for(int i=0; i<NBRICKS_PER_ROW; i++) {
			GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
			brick.setColor(c);
			brick.setFilled(true);
			add(brick);
			x = x + BRICK_SEP + BRICK_WIDTH;
		}
	}
}
