package edu.mit.rewire.view.animation;

import edu.mit.rewire.view.Bubble;

public class FixedAnimation implements Animation {

	private Bubble bubble;

	private float x, y;
	
	private boolean first = true;
	
	public FixedAnimation(Bubble bubble) {
		this.bubble = bubble;
	}
	
	@Override
	public boolean step() {
	    if (first) {
	        this.x = bubble.getX();
	        this.y = bubble.getY();
	        first = false;
	    }
		bubble.setX(x);
		bubble.setY(y);
		return (bubble.getState() != Bubble.State.EXPANDED);
	}

}
