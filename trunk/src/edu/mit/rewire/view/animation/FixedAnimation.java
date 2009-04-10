package edu.mit.rewire.view.animation;

import edu.mit.rewire.view.Bubble;

public class FixedAnimation implements Animation {

	private Bubble bubble;

	private final float x, y;
	
	public FixedAnimation(Bubble bubble, float width, float height) {
		this.bubble = bubble;
		this.x = bubble.getX();
		this.y = bubble.getY();
	}
	
	@Override
	public boolean step() {
		bubble.setX(x);
		bubble.setY(y);
		return (bubble.getState() != Bubble.State.EXPANDED);
	}

}
