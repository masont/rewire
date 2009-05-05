package edu.mit.rewire.view;

import processing.core.PGraphics;
import processing.core.PShape;

public class Shape implements Drawable {
    
    private final String shapeName;
    private final PShape pShape;
    
    private float x, y, height, width;

    public Shape(String shapeName, float x, float y, float height, float width) {
        this.shapeName = shapeName;
        this.pShape = ViewResources.loadShape(shapeName);
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public void draw(PGraphics graphics) {
        graphics.shape(this.pShape, x, y, height, width);
    }

}
