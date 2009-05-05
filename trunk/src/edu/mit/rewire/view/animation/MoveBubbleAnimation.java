package edu.mit.rewire.view.animation;

import edu.mit.rewire.view.Bubble;
import edu.mit.rewire.view.Bubble.State;

public class MoveBubbleAnimation implements Animation {

	private Bubble bubble;

	// VARIABLES TO ADJUST MOVEMENT ANIMATION
	private final float thresh = 5;	// Threshold for stopping expansion (x and y direction)
	private final float expSpeed = 3;	// Speed of expansion (smaller = faster)

	private boolean checkX;
	private boolean checkY;

	// Fixed values for the destination of the bubble
	private final float xDest, yDest;

	// Fixed midpoints between starting value and desired value
	private final float midpointX;
	private final float midpointY;

	// Adjustment values for 'slow in' animation
	private float adjustX;
	private float adjustY;

	// Recalculable 'distance' values of bubble from desired values 
	private float xDistance;
	private float yDistance;

	// Constructor
	public MoveBubbleAnimation(Bubble bubble, int frames, float xDest, float yDest) {
		this.bubble = bubble;		
		this.xDest = xDest;
		this.yDest = yDest;

		// Set initial midpoints between screen center and starting location of bubble for 'slow in' animation
		midpointX = (this.bubble.getX() + xDest)/2;
		midpointY = (this.bubble.getY() + yDest)/2;

		// Set initial adjustment values for 'slow in' animation
		this.adjustX = 1;
		this.adjustY = 1;

		this.checkX = false;
		this.checkY = false;
		
		this.xDistance = 0;
		this.yDistance = 0;

		// Stop regular animation of bubble
		this.bubble.setDx(0);
		this.bubble.setDy(0);
		
		this.bubble.setState(State.TRANSITIONING);
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


		// MOVE BUBBLE TO CENTER IN X DIRECTION
		// From left of center
		if (Math.abs(xDistance) < thresh) {
			checkX = true;
		} else {
			if (this.bubble.getX() < xDest) {
				// SLOW IN
				if (this.bubble.getX() < midpointX) {
					this.bubble.setX(this.bubble.getX() + adjustX);
					adjustX = adjustX + (adjustX/expSpeed);
				} 
				// SLOW OUT
				else {
					xDistance = (float) Math.abs(xDest - this.bubble.getX());
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
					xDistance = (float) Math.abs(this.bubble.getX() - xDest);
					this.bubble.setX((float) (this.bubble.getX() - Math.ceil(xDistance/expSpeed)));
				}
			}
		}

		// MOVE BUBBLE TO CENTER IN Y DIMENSION
		// From above center
		if (Math.abs(yDistance) < thresh) {
			checkY = true;
		} else {
			if (this.bubble.getY() < yDest) {
				// SLOW IN
				if (this.bubble.getY() < midpointY) {
					this.bubble.setY(this.bubble.getY() + adjustY);
					adjustY = adjustY + (adjustY/expSpeed);
				} 
				// SLOW OUT
				else {
					yDistance = (float) Math.abs(yDest - this.bubble.getY());
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
					yDistance = (float) Math.abs(this.bubble.getY() - yDest);
					this.bubble.setY((float) (this.bubble.getY() - Math.ceil(yDistance/expSpeed)));
				}
			}
		}


		// Check to see if expanded bubble values are met and stop animation if true
		if (checkX && checkY) {
			this.bubble.clearState();
			return true;
		} else {
			return false;
		}
	}
}