package edu.mit.rewire.view.animation;

import edu.mit.rewire.view.Bubble;
import edu.mit.rewire.view.Bubble.State;

public class ExpandBubbleAnimation implements Animation {

	private Bubble bubble;

	private final float width, height;

	public ExpandBubbleAnimation(Bubble bubble, int frames, float width, float height) {
		this.bubble = bubble;
		this.width = width;
		this.height = height;
	}

	@Override
	public boolean step() {

		if (this.bubble.getR() < 400) {
			this.bubble.setR(this.bubble.getR() + 10);
		}

		if (this.bubble.getX() < width/2) {
			this.bubble.setX(this.bubble.getX() + 10);
		} else {
			this.bubble.setX(this.bubble.getX() - 10);
		}

		if (this.bubble.getY() < height/2) {
			this.bubble.setY(this.bubble.getY() + 10);
		} else {
			this.bubble.setY(this.bubble.getY() - 10);
		}

		if ((this.bubble.getR() >= 300) && (this.bubble.getX() - width/2 < 10) && (this.bubble.getY() - height/2 < 10)) {		
//			System.out.println("true " + this.bubble.getR() + " " + (this.bubble.getX() - width/2) + " " + (this.bubble.getY() - height/2));
			this.bubble.setState(State.EXPANDED);
			return true;
		} else {
//			System.out.println("false");
			return false;
		}
	}
}
