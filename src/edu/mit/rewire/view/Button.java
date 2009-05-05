package edu.mit.rewire.view;

import processing.core.PGraphics;

/**
 * Class that implements a basic button with a rectangular region.
 * 
 * @author masont
 */
public abstract class Button implements Drawable, MouseAware {

    private enum State {
        UP, OVER, DOWN
    }

    private float x, y;
    private float height, width;

    private State state;

    protected Drawable upImage;
    protected Drawable overImage;
    protected Drawable downImage;

    public Button(float x, float y, float height, float width,
            Drawable upImage, Drawable overImage, Drawable downImage) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.upImage = upImage;
        this.overImage = overImage;
        this.downImage = downImage;
        this.state = State.UP;
    }

    public Button(float x, float y, float height, float width) {
        this(x, y, height, width, null, null, null);
    }

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
        return !(x < this.x || x > this.x + width || y < this.y || y > this.y
                + height);
    }

    /**
     * @return the upImage
     */
    public Drawable getUpImage() {
        return upImage;
    }

    /**
     * @param upImage the upImage to set
     */
    public void setUpImage(Drawable upImage) {
        this.upImage = upImage;
    }

    /**
     * @return the overImage
     */
    public Drawable getOverImage() {
        return overImage;
    }

    /**
     * @param overImage the overImage to set
     */
    public void setOverImage(Drawable overImage) {
        this.overImage = overImage;
    }

    /**
     * @return the downImage
     */
    public Drawable getDownImage() {
        return downImage;
    }

    /**
     * @param downImage the downImage to set
     */
    public void setDownImage(Drawable downImage) {
        this.downImage = downImage;
    }

}
