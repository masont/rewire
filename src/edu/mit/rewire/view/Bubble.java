package edu.mit.rewire.view;

import java.awt.Color;
import java.awt.geom.Point2D;

import processing.core.PGraphics;
import processing.core.PImage;
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
    
    /** Position in pixels */
    private float x, y;

    /** Radius in pixels */
    private float r;
    
    /** Velocity in pixels / second */
    private float dx = 0, dy = 0;
    
    /** Background color */
    private Color background;
    
    /** Flag for whether or not this bubble needs to be redrawn */
    private boolean changed;
    
    /** Background image for the bubble */
    private PImage backimage;
    
    public Bubble(Item item, float x, float y, float r, PImage backimage) {
        this.item = item;
        
        this.x = x;
        this.y = y;
        this.r = r;
        
        this.dx = (float) ((0.5 - Math.random()) * 1);
        this.dy = (float) ((0.5 - Math.random()) * 1);
        
        this.backimage = backimage;
        this.background = Color.GRAY;
        this.changed = true;
    }

    @Override
    public void draw(PGraphics graphics) {
    	graphics.image(backimage, x, y, 2 * r, 2 * r);
        /*graphics.noStroke();
        graphics.fill(background.getRed(), background.getGreen(), background.getBlue(), background.getAlpha());
        graphics.ellipse(x, y, 2 * r, 2 * r);*/
    }

    @Override
    public boolean isChanged() {
        return changed;
    }
    
    @Override
    public void setChanged(boolean changed) {
        this.changed = changed;
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
    
    public Color getBackground() {
        return this.background;
    }

    @Override
    public void setBackground(Color background) {
        this.background = background;
    }
    
    @Override
    public void dispatchClick(Controller controller) {
        controller.handleBubbleClick(this);        
    }
    
    @Override
    public void dispatchOver(Controller controller) {
        this.background = Color.DARK_GRAY;
    }

    @Override
    public boolean hits(int x, int y) {
        return Point2D.distance(x, y, this.x, this.y) <= this.r;
    }
    
    @Override
    public int getZ() {
        return 0;
    }
    
}
