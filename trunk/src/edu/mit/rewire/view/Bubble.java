package edu.mit.rewire.view;

import processing.core.PGraphics;

public class Bubble implements Drawable {
    
    /** Position in pixels */
    private float x, y;
    
    /** Radius in pixels */
    private float r;
    
    /** Velocity in pixels / second */
    private float dx = 0, dy = 0;
    
    private boolean changed;
    
    public Bubble(float x, float y, float r) {
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
        graphics.fill(150);
        graphics.ellipse(x, y, 2 * r, 2 * r);
        this.x += dx;
        this.y += dy;
    }

    @Override
    public boolean isChanged() {
        return this.changed;
    }

}
