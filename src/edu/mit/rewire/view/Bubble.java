package edu.mit.rewire.view;

import java.awt.geom.Point2D;

import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PShape;
import edu.mit.rewire.controller.Controller;
import edu.mit.rewire.model.Item;

public class Bubble implements Drawable, Particle, MouseAware {

    public enum State {
        SMALL, MEDIUM, EXPANDED, EXPANDING
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
    private PShape bubble;

    /** Fonts for the title and body */
    private PFont titleFont;
    private PFont bodyFont;

    /** Icon representing the type of item */
    private PShape favIcon;

    private PShape popButton;
    private PShape starButton;
    private PShape openButton;
    private PShape trashButton;

    public Bubble(float x, float y, float r) {
        this(null, x, y, r, null, null);
    }

    public Bubble(Item item, float x, float y, float r, PShape backImage, PShape icon) {
        this.item = item;

        this.x = x;
        this.y = y;

        this.dx = (float) ((0.5 - Math.random()) * 1);
        this.dy = (float) ((0.5 - Math.random()) * 1);

        this.bubble = backImage;
        this.titleFont = ViewResources.loadFont("titleFont");
        this.bodyFont = ViewResources.loadFont("bodyFont");
        this.favIcon = icon;
        this.state = Math.random() > .33 ? State.SMALL : State.MEDIUM;
        this.defaultState = state;
        this.r = state == State.SMALL ? 75 : 150;
        
        this.popButton = ViewResources.loadShape("popButton");
        this.starButton = ViewResources.loadShape("starButton");
        this.openButton = ViewResources.loadShape("openButton");
        this.trashButton = ViewResources.loadShape("trashButton");
    }

    //@Override
    public void draw(PGraphics graphics) {
    	
        graphics.shape(bubble, x - r, y - r, 2 * r, 2 * r);
        graphics.fill(136);
        switch (state) {
        case SMALL:
            graphics.shape(favIcon, x - r * 3 / 10, y - r * 3 / 5, r * 3 / 5,
                    r * 3 / 5);
            graphics.textFont(bodyFont,10);
            graphics.text(item.getTitle(), x - r * 4 / 5, y,
            		2*r,2*r);
            break;
        case MEDIUM:
            graphics.shape(favIcon, x - r * 1 / 4, y - r * 4 / 5, r * 1 / 2,
                    r * 1 / 2);
            graphics.textFont(titleFont,20);
            graphics.text(item.getTitle(), x - r * 4 / 5, y - r * 1 / 5,
            	r * 9/5,2*r);
            graphics.textFont(bodyFont);
            graphics.text(item.getHeader(), x - r * 3 / 5, y + r * 1 / 2);
            break;
        case EXPANDED:
            graphics.shape(favIcon, x - r * 1 / 4, y - r * 4 / 5, r * 1 / 2,
                    r * 1 / 2);
            graphics.textFont(titleFont,20);
            graphics.text(item.getTitle(), x - r * 4 / 5, y - r * 1 / 5,
            	r,2*r);
            graphics.textFont(bodyFont);
            graphics.text(item.getHeader(), x - r * 4 / 5, y + r * 1 / 20);
            graphics.textFont(bodyFont);
            graphics.text(item.getBody(), x - r * 3 / 5, y + r * 1 / 10, r, r);
            
            //final float BUTTON_WIDTH = 54f;
            graphics.shape(popButton, x + r * 2 / 5, y - r / 2);
            graphics.shape(starButton, x + r * 2 / 5, y - r / 2 + 100);
            graphics.shape(openButton, x + r * 2 / 5, y - r / 2 + 165);
            graphics.shape(trashButton, x + r * 2 / 5, y - r / 2 + 230);

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

    //@Override
    public float getY() {
        return y;
    }

    //@Override
    public void setY(float y) {
        this.y = y;
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

    public void clearState() {
        this.state = defaultState;
    }
     
    public void dispatchDown(Controller controller, int x, int y) {

    }
    
    public void dispatchUp(Controller controller, int x, int y) {
        if (this.state == State.EXPANDED) {
            controller.handleMarkReadClick(this);
//            if (x >= this.x + this.r / 5 && x <= this.x + this.r / 5 + 100) {
//                if (y >= this.y - this.r / 2 + 230) {
//                    controller.handleTrashClick(this);
//                } else if (y >= this.y - this.r / 2 + 165) {
//                    controller.handleOpenClick(this);
//                } else if (y >=this.y - this.r / 2 + 100) {
//                    controller.handleStarClick(this);
//                } else if (y >= this.y - this.r / 2) {
//                    controller.handleMarkReadClick(this);
//                }
//            }
        } else {
            controller.handleBubbleClick(this);
        }
    }

    public void dispatchIn(Controller controller, int x, int y) {

    }
    
    public void dispatchDrag(Controller controller, int x, int y) {

    }

    public boolean hits(int x, int y) {
        return Point2D.distance(x, y, this.x, this.y) <= this.r;
    }



}
