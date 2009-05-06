package edu.mit.rewire.view;

import processing.core.PGraphics;

public class ChristopherWalken implements Drawable {

	private float x, y;
	
	public ChristopherWalken(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw(PGraphics graphics) {
		graphics.image(ViewResources.loadImage("walken"),x, y/2 - 200);
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}

}
