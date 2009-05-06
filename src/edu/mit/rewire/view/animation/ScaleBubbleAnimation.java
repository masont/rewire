package edu.mit.rewire.view.animation;

import edu.mit.rewire.view.Bubble;
import edu.mit.rewire.view.Bubble.State;

public class ScaleBubbleAnimation implements Animation {

	private Bubble bubble;


	// VARIABLES TO ADJUST EXPANSION ANIMATION
	private final float R;		// Radius of expanded bubble
	private final float thresh = 5;	// Threshold for stopping expansion (x and y direction)
	private final float expSpeed = 8;	// Speed of expansion (smaller = faster)

	private boolean checkR;


	// Fixed midpoints between starting value and desired value
	private final float midpointR;

	// Adjustment values for 'slow in' animation
	private float adjustR;

	// Recalculable 'distance' values of bubble from desired values 
	private float rDistance;
	
	private State state;

	// Constructor
	public ScaleBubbleAnimation(Bubble bubble, int frames, State state) {
		this.bubble = bubble;
		this.state = state;
		this.R = (state == State.MEDIUM ? 150 : 75);

		// Set initial midpoints between screen center and starting location of bubble for 'slow in' animation
		midpointR = (this.bubble.getR() + R)/2;

		// Set initial adjustment values for 'slow in' animation
		this.adjustR = 1;

		this.checkR = false;
		
		this.rDistance = 0;

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

		// EXPAND BUBBLE
		if (this.R == 150) {
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
		} else {
			if (this.bubble.getR() <= R) {
				checkR = true;
			} else {
				if (this.bubble.getR() > R) {
					// SLOW IN
					if (this.bubble.getR() > midpointR) {
						this.bubble.setR(this.bubble.getR() - adjustR);
						adjustR = adjustR + (adjustR/expSpeed);
					} 
					// SLOW OUT
					else {
						rDistance = this.bubble.getR() - R;
						this.bubble.setR(this.bubble.getR() - (float) Math.ceil(rDistance/expSpeed));
					}
				}	
			}
		}


		// Check to see if expanded bubble values are met and stop animation if true
		if (checkR) {
			System.out.println("truing");
			this.bubble.setDefaultState(state);
			this.bubble.clearState();
			return true;
		} else {
			System.out.println("failing");
			return false;
		}
	}
}