package edu.mit.rewire.view;

import processing.core.PGraphics;

public interface Drawable {
    
    boolean isChanged();
    
    void draw(PGraphics graphics);

}
