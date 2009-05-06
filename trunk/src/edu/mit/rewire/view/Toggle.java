package edu.mit.rewire.view;

import processing.core.PGraphics;
import processing.core.PShape;

public class Toggle implements Drawable, MouseAware {

	public enum State {
		UP, DOWN
	}
	
	private PShape currentImage;
	private final PShape upImage, downImage, hoverImage;
	
	private State state;
	
	private final float x, y, width, height;
	
	public Toggle(float x, float y, float width, float height, PShape upImage, PShape hoverImage, PShape downImage) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.upImage = upImage;
		this.hoverImage = hoverImage;
		this.downImage = downImage;
		this.currentImage = upImage;
		this.state = State.UP;

	}

	public void draw(PGraphics graphics) {
		
		graphics.shape(currentImage, x, y, width, height);

	}

	public void dispatchDown(ProcessingView view, int x, int y) {
		// TODO Auto-generated method stub

	}

	public void dispatchDrag(ProcessingView view, int x, int y) {
		// TODO Auto-generated method stub

	}

	public void dispatchIn(ProcessingView view, int x, int y) {
		this.currentImage = hoverImage;
		
	}

	public void dispatchOut(ProcessingView view, int x, int y) {
		if (this.state == State.UP) {
			this.currentImage = upImage;
		} else {
			this.currentImage = downImage;
		}
	}

	public void dispatchUp(ProcessingView view, int x, int y) {
		if (this.state == State.UP) {
			this.currentImage = downImage;
			this.state = State.DOWN;
		} else {
			this.currentImage = upImage;
			this.state = State.UP;
		}

	}

	public boolean hits(int x, int y) {
		return !(x < this.x || x > this.x + width || y < this.y || y > this.y + height);
	}

}
