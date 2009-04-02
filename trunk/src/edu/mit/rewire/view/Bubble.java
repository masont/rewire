package edu.mit.rewire.view;

import processing.core.PGraphics;
import edu.mit.rewire.model.Item;

public class Bubble implements Drawable, Particle {
    
    /** Model instance that this bubble represents */
    private final Item item;
    
    /** Position in pixels */
    private float x, y;

    /** Radius in pixels */
    private float r;
    
    /** Velocity in pixels / second */
    private float dx = 0, dy = 0;
    
    /** Flag for whether or not this bubble needs to be redrawn */
    private boolean changed;
    
    public Bubble(Item item, float x, float y, float r) {
        this.item = item;
        
        this.x = x;
        this.y = y;
        this.r = r;
        
        this.dx = (float) ((0.5 - Math.random()) * 1);
        this.dy = (float) ((0.5 - Math.random()) * 1);
        
        this.changed = true;
    }

    @Override
    public void draw(PGraphics graphics) {
        graphics.noStroke();
        graphics.fill(150, 200);
        graphics.ellipse(x, y, 2 * r, 2 * r);
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

    
}
