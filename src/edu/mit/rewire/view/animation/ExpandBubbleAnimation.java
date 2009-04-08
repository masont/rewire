package edu.mit.rewire.view.animation;

import edu.mit.rewire.view.Bubble;

public class ExpandBubbleAnimation implements Animation {
	
	private Bubble bubble;
	
	public ExpandBubbleAnimation(Bubble bubble, int frames) {
		this.bubble = bubble;
	}

	@Override
	public boolean step() {
		this.bubble.setR(this.bubble.getR() + 1);
		return false;
	}

}
