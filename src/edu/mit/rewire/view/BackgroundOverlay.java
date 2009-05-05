package edu.mit.rewire.view;

import edu.mit.rewire.view.animation.ShrinkBubbleAnimation;
import processing.core.PGraphics;

public class BackgroundOverlay implements Drawable, MouseAware {

	private float width;
	private float height;
	private float alpha;
	private Bubble bubble;
	
	public BackgroundOverlay(Bubble bubble, float width, float height) {
		this.bubble = bubble;
		this.width = width;
		this.height = height;
		this.alpha = 10;
	}

	public void draw(PGraphics graphics) {
		graphics.fill(255, alpha);
		graphics.rect(0, 0, width, height);
		
	}
	
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	
	public float getAlpha() {
		return alpha;
	}
	
	public void setBubble(Bubble bubble) {
		this.bubble = bubble;
	}

    public void dispatchDown(ProcessingView view, int x, int y) {
        // TODO Auto-generated method stub
        
    }

    public void dispatchDrag(ProcessingView view, int x, int y) {
        // TODO Auto-generated method stub
        
    }

    public void dispatchIn(ProcessingView view, int x, int y) {
        // TODO Auto-generated method stub
        
    }
    
    public void dispatchOut(ProcessingView view, int x, int y) {
        
    }

    public void dispatchUp(ProcessingView view, int x, int y) {
//        view.addAnimation(new ShrinkBubbleAnimation(bubble, 100, width, height));
        view.shrinkBubble(bubble);
    }

    public boolean hits(int x, int y) {
        return true;
    }


}
