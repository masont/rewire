package edu.mit.rewire.view.animation;

import edu.mit.rewire.view.Bubble;

public class FixedAnimation implements Animation {

	private Bubble bubble;

	private final float width, height;
	
	public FixedAnimation(Bubble bubble, float width, float height) {
		this.bubble = bubble;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public boolean step() {
		bubble.setDx(0);
		bubble.setDy(0);
		return false;
	}

}
