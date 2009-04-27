package edu.mit.rewire.view.animation;

import edu.mit.rewire.view.Bubble;
import edu.mit.rewire.view.Bubble.State;

public class ExpandBubbleAnimation implements Animation {

	private Bubble bubble;


	// VARIABLES TO ADJUST EXPANSION ANIMATION
	private final float R = 400;		// Radius of expanded bubble
	private final float thresh = 5;	// Threshold for stopping expansion (x and y direction)
	private final float expSpeed = 3;	// Speed of expansion (smaller = faster)

	private boolean checkR;
	private boolean checkX;
	private boolean checkY;

	// Fixed values for center of the screen
	private final float xCenter, yCenter;

	// Fixed midpoints between starting value and desired value
	private final float midpointX;
	private final float midpointY;
	private final float midpointR;

	// Adjustment values for 'slow in' animation
	private float adjustX;
	private float adjustY;
	private float adjustR;

	// Recalculable 'distance' values of bubble from desired values 
	private float xDistance;
	private float yDistance;
	private float rDistance;

	// Constructor
	public ExpandBubbleAnimation(Bubble bubble, int frames, float width, float height) {
		this.bubble = bubble;		
		this.xCenter = width/2;
		this.yCenter = height/2;

		// Set initial midpoints between screen center and starting location of bubble for 'slow in' animation
		midpointX = (this.bubble.getX() + xCenter)/2;
		midpointY = (this.bubble.getY() + yCenter)/2;
		midpointR = (this.bubble.getR() + R)/2;

		// Set initial adjustment values for 'slow in' animation
		this.adjustX = 1;
		this.adjustY = 1;
		this.adjustR = 1;

		this.checkR = false;
		this.checkX = false;
		this.checkY = false;
		
		this.xDistance = width;
		this.yDistance = height;
		this.rDistance = (float) Math.sqrt(width * width + height * height);

		// Stop regular animation of bubble
		this.bubble.setDx(0);
		this.bubble.setDy(0);
		
		this.bubble.setState(State.EXPANDING);
	}

	//@Override
	public boolean step() {

		/* SLOW IN, SLOW OUT ANIMATION
		 * 
		 * Using calculated midpoints in constructor, adjust values
		 * accordingly for smooth animation.
		 * 
		 * Slow in starts by increasing by an adjustment value of 1
		 * and increases this adjustment value each step based on
		 * the amount of "distance traveled".
		 * 
		 * Slow out decreases the amount of adjustment based on "how
		 * much is left to go". (minimum step size of 1)
		 */

		// EXPAND BUBBLE
		if (this.bubble.getR() >= R) {
			checkR = true;
		} else {
			if (this.bubble.getR() < R) {
				// SLOW IN
				if (this.bubble.getR() < midpointR) {
					this.bubble.setR(this.bubble.getR() + adjustR);
					adjustR = adjustR + (adjustR/expSpeed);
				} 
				// SLOW OUT
				else {
					rDistance = R - this.bubble.getR();
					this.bubble.setR((float) Math.ceil(rDistance/expSpeed) + this.bubble.getR());
				}
			}	
		}



		// MOVE BUBBLE TO CENTER IN X DIRECTION
		// From left of center
		if (Math.abs(xDistance) < thresh) {
			checkX = true;
		} else {
			if (this.bubble.getX() < xCenter) {
				// SLOW IN
				if (this.bubble.getX() < midpointX) {
					this.bubble.setX(this.bubble.getX() + adjustX);
					adjustX = adjustX + (adjustX/expSpeed);
				} 
				// SLOW OUT
				else {
					xDistance = (float) Math.abs(xCenter - this.bubble.getX());
					this.bubble.setX((float) (Math.ceil(xDistance/expSpeed) + this.bubble.getX()));
				}			
			}

			// From right of center
			else {
				// SLOW IN
				if (this.bubble.getX() > midpointX) {
					this.bubble.setX(this.bubble.getX() - adjustX);
					adjustX = adjustX + (adjustX/expSpeed);
				} 
				// SLOW OUT
				else {
					xDistance = (float) Math.abs(this.bubble.getX() - xCenter);
					this.bubble.setX((float) (this.bubble.getX() - Math.ceil(xDistance/expSpeed)));
				}
			}
		}

		// MOVE BUBBLE TO CENTER IN Y DIMENSION
		// From above center
		if (Math.abs(yDistance) < thresh) {
			checkY = true;
		} else {
			if (this.bubble.getY() < yCenter) {
				// SLOW IN
				if (this.bubble.getY() < midpointY) {
					this.bubble.setY(this.bubble.getY() + adjustY);
					adjustY = adjustY + (adjustY/expSpeed);
				} 
				// SLOW OUT
				else {
					yDistance = (float) Math.abs(yCenter - this.bubble.getY());
					this.bubble.setY((float) (Math.ceil(yDistance/expSpeed) + this.bubble.getY()));
				}
			} 
			// From below center
			else {
				// SLOW IN
				if (this.bubble.getY() > midpointY) {
					this.bubble.setY(this.bubble.getY() - adjustY);
					adjustY = adjustY + (adjustY/expSpeed);
				}
				// SLOW OUT
				else {
					yDistance = (float) Math.abs(this.bubble.getY() - yCenter);
					this.bubble.setY((float) (this.bubble.getY() - Math.ceil(yDistance/expSpeed)));
				}
			}
		}


		// Check to see if expanded bubble values are met and stop animation if true
		if (checkR && checkX && checkY) {
			this.bubble.setState(State.EXPANDED);
			return true;
		} else {
			return false;
		}
	}
}