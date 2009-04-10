package edu.mit.rewire.view;

import java.awt.geom.Point2D;

import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PFont;
import edu.mit.rewire.controller.Controller;
import edu.mit.rewire.model.Item;

public class Bubble implements Drawable, Particle, MouseAware {
    
    public enum State {
        SMALL,
        MEDIUM,
        EXPANDED
    }
    
    /** Model instance that this bubble represents */
    private final Item item;
    
    /** State of this bubble */
    private State state;
    
	/** Default state of the bubble */
    private State defaultState;
    
    /** Position in pixels */
    private float x, y;

    /** Radius in pixels */
    private float r;
    
    /** Velocity in pixels / second */
    private float dx = 0, dy = 0;
    
    /** Background image for the bubble */
    private PImage backimage;
    
    /** Fonts for the title and body */
    private PFont titleFont;
    private PFont bodyFont;
    
    public Bubble(Item item, float x, float y, float r, PImage backimage,
    		PFont titleFont, PFont bodyFont) {
        this.item = item;
        
        this.x = x;
        this.y = y;
        
        this.dx = (float) ((0.5 - Math.random()) * 1);
        this.dy = (float) ((0.5 - Math.random()) * 1);
        
        this.backimage = backimage;
        this.titleFont = titleFont;
        this.bodyFont = bodyFont;
        this.state = Math.random() > .5 ? State.SMALL : State.MEDIUM;
        this.defaultState = state;
        this.r = state == State.SMALL ? 75 : 150;
    }

    @Override
    public void draw(PGraphics graphics) {
    	graphics.image(backimage, x - r, y - r, 2 * r, 2 * r);
    	graphics.fill(136);
    	switch (state) {
    		case SMALL:
    	    	graphics.textFont(bodyFont);
    	    	graphics.text(item.getTitle(), x - r * 3 / 5, y - r / 5);
    	    	break;
    		case MEDIUM:
    	    	graphics.textFont(bodyFont);
    	    	graphics.text(item.getTitle(), x - r * 3 / 5, y - r / 5);
    	    	break;
    		case EXPANDED:
    	    	graphics.textFont(titleFont);
    	    	graphics.text(item.getTitle(), x - r * 3 / 5, y - r / 5);
    	    	break;
    	}
    }

    public Item getItem() {
        return item;
    }
    
    @Override
    public float getR() {
        return r;
    }
    
    public void setR(float r) {
        this.r = r;
    }

    @Override
    public float getX() {
        return x;
    }
    
    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }
    
    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public float getDx() {
        return dx;
    }
    
    @Override
    public void setDx(float dx) {
        this.dx = dx;
    }

    @Override    
    public float getDy() {
        return dy;
    }
    
    @Override
    public void setDy(float dy) {
        this.dy = dy;
    }

    @Override
    public void dispatchClick(Controller controller) {
        controller.handleBubbleClick(this);        
    }
    
    @Override
    public void dispatchOver(Controller controller) {
    	
    }

    @Override
    public boolean hits(int x, int y) {
        return Point2D.distance(x, y, this.x, this.y) <= this.r;
    }
    
    @Override
    public int getZ() {
        return 0;
    }

    public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public void clearState() {
		this.state = defaultState;
	}
    
}
