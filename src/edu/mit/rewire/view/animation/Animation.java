package edu.mit.rewire.view.animation;

public interface Animation {
	
	/**
	 * Runs the animation for one step (one frame), returning false if the
	 * animation has not finished yet, and true if it has.
	 * 
	 * @return true if the animation has finished, false otherwise
	 */
	boolean step();

}
