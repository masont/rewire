package edu.mit.rewire.view;

import processing.core.PGraphics;
import processing.core.PShape;

public class StarButton extends Button {
    
	public enum State {
		UP, DOWN
	}
	
	private PShape currentImage;
	private final PShape upImage, downImage, hoverImage, hoverSelectImage;
	
	private State state;
	
	private Bubble bubble;
		
	private final float x, y, width, height;
	
	public StarButton (Bubble bubble, float x, float y, float height, float width) {
		super(x, y, height, width);
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.bubble = bubble;
		this.upImage = ViewResources.loadShape("starButton");
		this.hoverImage = ViewResources.loadShape("starButtonOver");
		this.hoverSelectImage = ViewResources.loadShape("starSelectedButtonOver");
		this.downImage = ViewResources.loadShape("starButtonClick");
		this.currentImage = this.bubble.isStarred() ? downImage : upImage;
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
		if (this.state == State.UP) {
			this.currentImage = hoverImage;
		} else {
			this.currentImage = hoverSelectImage;
		}
		
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
			this.bubble.setStarred(true);
			this.state = State.DOWN;
		} else {
			this.currentImage = upImage;
			this.bubble.setStarred(false);
			this.state = State.UP;
		}

	}

	public boolean hits(int x, int y) {
		return !(x < this.x || x > this.x + width || y < this.y || y > this.y + height);
	}

	@Override
	public void execute(ProcessingView view, int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
