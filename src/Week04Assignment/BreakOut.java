package Week04Assignment;
/* File: Breakout.java
* --------------------------------
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
	private double vx, vy, x, y; //vy represents the velocity of the ball
	private RandomGenerator rgen = RandomGenerator.getInstance();
	boolean leftWallHit = false;
	boolean RightWallHit = false;
	boolean bottomUpMode = false;
	boolean topDownMode = true;
	boolean brickHit = false;
	int count;
	
	/* Program starts here*/
	public void run() {
		setUpGame();
		playGame();
	}
	
	/* Method: playGame*/
	/* Initialize the start of game
	 * and then control it
	 */
	private void playGame() {
		vx =  rgen.nextDouble(-3.0, +3.0);
		startGame();
	}
	
	/* Method setValocity*/
	/* Set initial velocity of the ball movement
	 */
	private void setValocity() {
		/** Initial moving of the ball*/
		if(vx > 0) {//checks the vx is negative or positive, if positiv then the velocity should be positive
			vy = +3.0;
		}else if(vx < 0) { // if negative the velocity should be negative
			vy = -3.0;
		}
	}
	
	/* Method: startGame*/
	/*Start the game and controls it
	 * until game is over
	 */
	private void startGame() {
		x = ball.getX();
		y = ball.getY();
		setValocity();
		int time = 20; //to set up speed of ball in different level
		count = 0; //counts the hit of bricks
		
		/**Moves the ball towerds the wall or paddle*/
		while(x < WIDTH-(BALL_RADIUS) && x > 0 && y < HEIGHT) { 
			if(bottomUpMode) { //When ball gose from bottom to top
				setXYForBotomUpMove();
			}else if(topDownMode){ //When ball gose from top to bottom
				setXYForTopDownMove();
			}
			
			/*Finaly sets the balls updated location*/
			ball.setLocation(x, y);   
			pause(time);
			/*Sets the time for pause in different level*/
			time = setTime(time);
			
			/* Checks the ball is hitting on the right wall or the leftWall*/
			checkBallPosition();
			/* Checks the ball is hitting on any bricks or The paddle*/
			checkForCollision();
			/* If ball hits any wall or bricks or paddle, it bounce the ball with reverse valocity*/
			bounceTheBall();
			
			/*If the game turns out to the end state then its print the ending messege*/
			printEndingMessage();
			if(count == 100) { // Finally breaks the loop when no more bricks to hit
				return;
			}
		}
	}
	
	/* Method : setTime*/
	/* Change the different level
	 */
	private int setTime(int time) {
		if(count == 20) { //When 20 bricks are hitted,game gose to 2nd level
			if(time >10) {
				time --;
			}
		}else if(count == 50) { //when 50 bricks are hitted, game gose to 3rd level
			if(time > 9) {
				time = 8;
			}
			ball.setColor(Color.RED);
		}else if(count == 80) { // When 80 bricks are hitted, game gose to 4th level
			if(time >= 8) {
				time = 7;
			}
			ball.setColor(Color.BLUE);
		}else if(count == 90) { // Finally when 90 bricks are hitted, game gose to last level
			if(time >= 7) {
				time = 6;
			}
			ball.setColor(Color.MAGENTA);
		}
		return time;
	}
	
	/* Method : bounceTheBall*/
	/* Bounch the ball with revrse valocity
	 * when it hitts any wall, bricks or the paddle
	 */
	private void bounceTheBall() {
		if(RightWallHit) {
			bounceIt();
		}else if(leftWallHit) {
			bounceIt();
		}else if(brickHit) {
			
			if(bottomUpMode) { //When brick hit happens and ball in bottom to Up mode it should imediately chenge the direction
				bottomUpMode = false;
				topDownMode = true;
				brickHit = false;
			}else { //When brick hit happens and ball in top to Bottom mode it should imediately chenge the direction
				bottomUpMode = true;
				topDownMode = false;
				brickHit = false;
			}
		}
	}
	
	/* Method: printEndingMessage*/
	/* Decide whether player wins or lose
	 * and then print a messege
	 */
	private void printEndingMessage() {
		if(y > HEIGHT ) { //Checks the game out of the window or not, if so then game is in End State and Player Loses
			println("Game Over y = " + y);
			GLabel messege = new GLabel("Game Over");
			messege.setFont("Times-22");
			messege.setColor(Color.RED);
			add(messege, getWidth()/2 - messege.getWidth()/2,(getHeight()/2));
			
		}else if(count == 100){ //Checks the player Hitts all bicks or not, if so then game is in End State and Player Wins
			println("Congratulation");
			GLabel messege = new GLabel("Congratulation");
			messege.setFont("Times-22");
			messege.setColor(Color.RED);
			add(messege, getWidth()/2 - messege.getWidth()/2,(getHeight()/2));
		}
	}
	
	/* Method: setXYForBotomUpMove*/
	/* Change the value of x and y
	 * so that ball can move Botom to Up
	 */
	private void setXYForBotomUpMove() {
		x = x + vy;
		if(vy > 0) { // when vy positive the value of y should be decrease for bttom to up move
			y = y - vy;
		}else { // when vy negative the value of y should be increase for bttom to up move
			y = y + vy;
		}
	}
	
	/* Method: setXYForTopDownMove*/
	/* Change the value of x and y
	 * so that ball can move top to Down
	 */
	private void setXYForTopDownMove() {
		x = x + vy;
		if(vy < 0) { // when vy negative the value of y should be decrease for top to down move
			y = y - vy;
		}else{ // when vy positive the value of y should be increase for top to down move
			y = y + vy;
		}
	}
	
	/* Method: checkForCollision**/
	/* Checks any hit occurs or not,
	 * if brick hit occurs then it change the balls move direction
	 * and delete the brick
	 */
	private void checkForCollision() {
		if(getElementAt(x,y + BALL_RADIUS) == paddle) {//paddle hitting
			bottomUpMode = true;
			topDownMode = false;
			
		}else if(getElementAt(x,y) != null) { //brick hitting
			println("brick hit Count = " + count);
			brickHit = true;
			
			//removing the hitted brick
			GObject collider = getElementAt(x,y);
			if(collider != paddle) {
				remove(collider);
				count++;
			}
		}
		
		//top wall checking
		if(y <= 0) {
			bottomUpMode = false;
			topDownMode = true;
		}
	}
	
	private void bounceIt() {
		vy = -(vy); //reverse the valocity
		RightWallHit = false;
		leftWallHit = false;
	}
	
	private void checkBallPosition() {
		if(x == 0.0) { // checks the ball is on the Left wall or not
			x = x + 3.0;
			leftWallHit = true;
		}else if(x == 396.0) {//checks the ball is on the Right wall or not
			x = x - 3.0;
			RightWallHit = true;
		}

	}
	
	/* Method: setUpGame */
	/* Set up the game window with bricks, 
	 * ball and paddle
	 */
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
	
	/* Method: mouseMoved */
	/* Gets triggered when the mouse enters the game window
	 * and move in the window
	 */
	public void mouseMoved(MouseEvent e) {
		if(e.getX() < WIDTH - PADDLE_WIDTH) {
			paddle.setLocation(e.getX(), HEIGHT - PADDLE_Y_OFFSET);
		}
	}
	
	/* Method: createPaddle */
	/* Creates the paddle
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
	
	/* Method: getColor */
	/* returns appropriate color for each row
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
	
	/* Method: createRowOfBricks*/
	/* Create a row of bricks
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
