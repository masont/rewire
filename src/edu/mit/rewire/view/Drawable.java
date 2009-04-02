package edu.mit.rewire.view;

import processing.core.PGraphics;

public interface Drawable {
    
    boolean isChanged();
    
    void setChanged(boolean changed);
    
    void draw(PGraphics graphics);

}
