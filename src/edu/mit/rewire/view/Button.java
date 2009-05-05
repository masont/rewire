package edu.mit.rewire.view;

import processing.core.PGraphics;

/**
 * Class that implements a basic button with a rectangular region.
 * @author masont
 */
public abstract class Button implements Drawable, MouseAware {
    
    private enum State { UP, OVER, DOWN }
    
    private float x, y;
    private float height, width;
    
    private State state;
    
    private Drawable upImage;
    private Drawable overImage;
    private Drawable downImage;

    public void draw(PGraphics graphics) {
        switch (state) {
        case UP:
            upImage.draw(graphics);
            break;
        case OVER:
            overImage.draw(graphics);
            break;
        case DOWN:
            downImage.draw(graphics);
            break;
        }
    }

    public void dispatchDown(ProcessingView view, int x, int y) {
        this.state = State.DOWN;
    }

    public void dispatchDrag(ProcessingView view, int x, int y) {

    }

    public void dispatchIn(ProcessingView view, int x, int y) {
        this.state = State.OVER;
        execute(view, x, y);
    }
    
    public void dispatchOut(ProcessingView view, int x, int y) {
        this.state = State.UP;
    }
    

    public void dispatchUp(ProcessingView view, int x, int y) {
        this.state = State.UP;
        execute(view, x, y);
    }
    
    public abstract void execute(ProcessingView view, int x, int y);

    public boolean hits(int x, int y) {
        return !(x < this.x || x > this.x + width ||
                 y < this.y || y > this.y + height);
    }

}
