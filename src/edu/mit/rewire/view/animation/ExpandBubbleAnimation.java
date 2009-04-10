package edu.mit.rewire.view.animation;

import edu.mit.rewire.view.Bubble;
import edu.mit.rewire.view.Bubble.State;

public class ExpandBubbleAnimation implements Animation {

	private Bubble bubble;

	private final float width, height;

	private int adjustR = 1;
	private int adjustX = 1;
	private int adjustY = 1;
	
	private float xDistance;
	private float yDistance;

	
	public ExpandBubbleAnimation(Bubble bubble, int frames, float width, float height) {
		this.bubble = bubble;
		this.width = width;
		this.height = height;
		this.adjustR = 1;
		this.adjustX = 1;
		this.adjustY = 1;
		this.xDistance = this.bubble.getX() - width/2;
		this.yDistance = this.bubble.getY() - height/2;
	}

	//@Override
	public boolean step() {

		// Expand bubble
		if (this.bubble.getR() < 200) {
			this.bubble.setR(this.bubble.getR() + adjustR);
			adjustR += 6;
		} else if (this.bubble.getR() < 400) {
			this.bubble.setR(this.bubble.getR() + adjustR);
			if (adjustR > 6) {
				adjustR -= 4;
			}
		}

		// Move bubble to center horizontally
				
		if (this.bubble.getX() < width/2) {
			if (this.bubble.getX() - width/2 < xDistance/2) {
				this.bubble.setX(this.bubble.getX() + adjustX);
				adjustX += 10;
			} else {
				this.bubble.setX(this.bubble.getX() + adjustX);
				if (adjustX > 11) {
					adjustX -= 10;
				}
			}
		} else {
			if (this.bubble.getX() - width/2 > xDistance/2) {
				this.bubble.setX(this.bubble.getX() - adjustX);
				adjustX += 10;
			} else {
				this.bubble.setX(this.bubble.getX() - adjustX);
				if (adjustX > 11) {
					adjustX -= 10;
				}
			}
			
		}

		
		// Move bubble to center vertically		
		if (this.bubble.getY() < height/2) {
			if (this.bubble.getY() - height/2 < yDistance/2) {
				this.bubble.setY(this.bubble.getY() + adjustY);
				adjustY += 10;
			} else {
				this.bubble.setY(this.bubble.getY() + adjustY);
				if (adjustY > 11) {
					adjustY -= 10;
				}
			}
		} else {
			if (this.bubble.getY() - height/2 > yDistance/2) {
				this.bubble.setY(this.bubble.getY() - adjustY);
				adjustY += 10;
			} else {
				this.bubble.setY(this.bubble.getY() - adjustY);
				if (adjustY > 11) {
					adjustY -= 10;
				}
			}
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
