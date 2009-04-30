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
    private PShape bubble_image;

    /** Fonts for the title and body */
    private PFont font_title;
    private PFont font_body;

    /** Icon representing the type of item */
    private PShape fav_icon;

    private PShape button_pop;
    private PShape button_star;
    private PShape button_open;
    private PShape button_trash;

    public Bubble(float x, float y, float r) {
        this(null, x, y, r, null, null, null, null, null, null, null, null);
    }

    public Bubble(Item item, float x, float y, float r, PShape backimage,
            PFont titleFont, PFont bodyFont, PShape icon,
            PShape markReadButton, PShape starButton, PShape openButton,
            PShape trashButton) {
        this.item = item;

        this.x = x;
        this.y = y;

        this.dx = (float) ((0.5 - Math.random()) * 1);
        this.dy = (float) ((0.5 - Math.random()) * 1);

        this.bubble_image = backimage;
        this.font_title = titleFont;
        this.font_body = bodyFont;
        this.fav_icon = icon;
        this.state = Math.random() > .33 ? State.SMALL : State.MEDIUM;
        this.defaultState = state;
        this.r = state == State.SMALL ? 75 : 150;
        
        this.button_pop = markReadButton;
        this.button_star = starButton;
        this.button_open = openButton;
        this.button_trash = trashButton;
    }

    //@Override
    public void draw(PGraphics graphics) {
    	
        graphics.shape(bubble_image, x - r, y - r, 2 * r, 2 * r);
        graphics.fill(136);
        switch (state) {
        case SMALL:
            graphics.shape(fav_icon, x - r * 3 / 10, y - r * 3 / 5, r * 3 / 5,
                    r * 3 / 5);
            graphics.textFont(font_body,10);
            graphics.text(item.getTitle(), x - r * 4 / 5, y,
            		2*r,2*r);
            break;
        case MEDIUM:
            graphics.shape(fav_icon, x - r * 1 / 4, y - r * 4 / 5, r * 1 / 2,
                    r * 1 / 2);
            graphics.textFont(font_title,20);
            graphics.text(item.getTitle(), x - r * 4 / 5, y - r * 1 / 5,
            	r * 9/5,2*r);
            graphics.textFont(font_body);
            graphics.text(item.getHeader(), x - r * 3 / 5, y + r * 1 / 2);
            break;
        case EXPANDED:
            graphics.shape(fav_icon, x - r * 1 / 4, y - r * 4 / 5, r * 1 / 2,
                    r * 1 / 2);
            graphics.textFont(font_title,20);
            graphics.text(item.getTitle(), x - r * 4 / 5, y - r * 1 / 5,
            	r,2*r);
            graphics.textFont(font_body);
            graphics.text(item.getHeader(), x - r * 4 / 5, y + r * 1 / 20);
            graphics.textFont(font_body);
            graphics.text(item.getBody(), x - r * 3 / 5, y + r * 1 / 10, r, r);
            
            //final float BUTTON_WIDTH = 54f;
            graphics.shape(button_pop, x + r * 2 / 5, y - r / 2);
            graphics.shape(button_star, x + r * 2 / 5, y - r / 2 + 100);
            graphics.shape(button_open, x + r * 2 / 5, y - r / 2 + 165);
            graphics.shape(button_trash, x + r * 2 / 5, y - r / 2 + 230);

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
     
    //@Override
    public void dispatchClick(Controller controller, int x, int y) {
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

    //@Override
    public void dispatchOver(Controller controller, int x, int y) {

    }

    //@Override
    public boolean hits(int x, int y) {
        return Point2D.distance(x, y, this.x, this.y) <= this.r;
    }

    //@Override
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
