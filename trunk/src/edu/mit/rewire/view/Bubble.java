package edu.mit.rewire.view;

import java.awt.geom.Point2D;

import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PShape;
import edu.mit.rewire.model.Item;

public class Bubble implements Drawable, MouseAware {

    public enum State {
        SMALL, MEDIUM, EXPANDED, TRANSITIONING
    }

    /** Model instance that this bubble represents */
    private final Item item;

    /** State of this bubble */
    private State state;

    /** Default state of the bubble */
    private State defaultState;
    private float originalX, originalY;

    /** Position in pixels */
    private float x, y;

    /** Radius in pixels */
    private float r;

    /** Velocity in pixels / second */
    private float dx = 0, dy = 0;

    /** Background image for the bubble */
    private PShape bubble;
    private final PShape defaultBubble;
    private final PShape hoverBubble;

    /** Fonts for the title and body */
    private PFont titleFont;
    private PFont bodyFont;

    /** Icon representing the type of item */
    private PShape favIcon;

    private PShape popButton;
    private PShape bubbleButton;
    private PShape starButton;
    //private PShape openButton;
    private PShape trashButton;

    public Bubble(float x, float y, float r) {
        this(null, x, y, r, null, null, null);
    }

    public Bubble(Item item, float x, float y, float r, PShape backImage, PShape hoverImage, PShape icon) {
        this.item = item;

        this.x = x;
        this.y = y;

//        this.dx = (float) ((0.5 - Math.random()) * 1);
//        this.dy = (float) ((0.5 - Math.random()) * 1);
        
        this.dx = 0;
        this.dy = 0;

        this.bubble = backImage;
        this.defaultBubble = backImage;
        this.hoverBubble = hoverImage;
        this.titleFont = ViewResources.loadFont("titleFont");
        this.bodyFont = ViewResources.loadFont("bodyFont");
        this.favIcon = icon;
        this.state = State.SMALL;
//        this.state = Math.random() > .4 ? State.SMALL : State.MEDIUM;
        this.defaultState = state;
        this.r = state == State.SMALL ? 75 : 150;
        
        this.popButton = ViewResources.loadShape("popButton");
        this.bubbleButton = ViewResources.loadShape("bubbleButton");
        this.starButton = ViewResources.loadShape("starButton");
        //this.openButton = ViewResources.loadShape("openButton");
        this.trashButton = ViewResources.loadShape("trashButton");
    }

    //@Override
    public void draw(PGraphics graphics) {
    	
        graphics.shape(bubble, x - r, y - r, 2 * r, 2 * r);
        graphics.fill(136);
        //graphics.textAlign(CENTER);
        switch (state) {
        case SMALL:
            graphics.shape(favIcon, x - r * 3 / 10, y - r * 3 / 5, r * 3 / 5,
                    r * 3 / 5);
            graphics.textFont(bodyFont,10);
            graphics.text(item.getTitle(), x - r * 3 / 5, y,
            		r * 4 / 3, r * 4 / 5);
            break;
        case MEDIUM:
            graphics.shape(favIcon, x - r * 1 / 4, y - r * 4 / 5, r * 1 / 2,
                    r * 1 / 2);
            graphics.textFont(titleFont,20);
            graphics.text(item.getTitle(), x - r * 3 / 5, y - r * 1 / 5,
            	r * 7 / 5, r * 3 / 5);
            graphics.textFont(bodyFont);
            graphics.text(item.getHeader(), x - r * 2 / 5, y + r * 2 / 5, r * 5 / 5, r * 2 / 5);
            break;
        case EXPANDED:
            graphics.shape(favIcon, x - r * 1 / 4, y - r * 4 / 5, r * 1 / 2,
                    r * 1 / 2);
            graphics.textFont(titleFont,20);
            graphics.text(item.getTitle(), x - r * 3 / 5, y - r * 2 / 5,
            	r, r * 2 / 10);
            graphics.textFont(bodyFont);
            graphics.text(item.getHeader(), x - r * 1 / 2, y - r * 2 / 10, r * 9 / 10, r * 4 / 5);
            graphics.textFont(bodyFont, 12);
            graphics.text(item.getBody(), x - r * 3 / 5, y - r * 1 / 10, r * 10 / 10, r * 3 / 5);
            
            //final float BUTTON_WIDTH = 54f;
            graphics.shape(popButton, x + r * 1 / 2, y - r * 2 / 5);
            graphics.shape(bubbleButton, x + r * 1 / 2, y - r * 2 / 5 + 50);
            graphics.shape(starButton, x + r * 1 / 2, y - r * 2 / 5 + 100);
            //graphics.shape(openButton, x + r * 1 / 2, y - r * 2 / 5 + 165);
            graphics.shape(trashButton, x + r * 1 / 2, y - r * 2 / 5 + 150);

            break;
        }
    }

    public Item getItem() {
        return item;
    }

    //@Override
    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    //@Override
    public float getX() {
        return x;
    }

    //@Override
    public void setX(float x) {
        this.x = x;
    }
    
    public void setOriginalX(float x) {
    	this.originalX = x;
    }
    
    public float getOriginalX() {
    	return originalX;
    }

    //@Override
    public float getY() {
        return y;
    }

    //@Override
    public void setY(float y) {
        this.y = y;
    }
    
    public void setOriginalY(float y) {
    	this.originalY = y;
    }
    
    public float getOriginalY() {
    	return originalY;
    }

    //@Override
    public float getDx() {
        return dx;
    }

    //@Override
    public void setDx(float dx) {
        this.dx = dx;
    }

    //@Override
    public float getDy() {
        return dy;
    }

    //@Override
    public void setDy(float dy) {
        this.dy = dy;
    }
    
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
    public State getDefaultState() {
    	return defaultState;
    }
    
    public void setDefaultState(State state) {
    	this.defaultState = state;
    }

    public void clearState() {
        this.state = defaultState;
    }
     
    public void dispatchDown(ProcessingView view, int x, int y) {
        this.r -= 4;
    }
    
    public void dispatchUp(ProcessingView view, int x, int y) {
        this.r += 4;
        if (this.state != State.EXPANDED && this.state != State.TRANSITIONING) {
            this.bubble = defaultBubble;
        	view.maximizeBubble(this);
        }
    }

    public void dispatchIn(ProcessingView view, int x, int y) {
    	
    	if (this.state != State.EXPANDED && this.state != State.TRANSITIONING) {
    		this.bubble = hoverBubble;
    	}

    }
    
    public void dispatchOut(ProcessingView view, int x, int y) {
    	
    	if (this.state != State.EXPANDED && this.state != State.TRANSITIONING) {
    		this.bubble = defaultBubble;
    	}

    }
    
    public void dispatchDrag(ProcessingView view, int x, int y) {

    }

    public boolean hits(int x, int y) {
        return Point2D.distance(x, y, this.x, this.y) <= this.r;
    }



}
