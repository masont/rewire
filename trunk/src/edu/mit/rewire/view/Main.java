package edu.mit.rewire.view;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Main extends PApplet {
    
    private static final long serialVersionUID = -4669039170458943974L;
    
    private final List<Drawable> elements = new ArrayList<Drawable>();
    
    @Override
    public void setup() {
        size(screen.width, screen.height);
        background(0);
        smooth();
        for (int i = 0; i < 40; i++) {
            float x = (float) (Math.random() * screen.width);
            float y = (float) (Math.random() * screen.height);
            float r = (float) (Math.random() * 100);
            this.elements.add(new Bubble(x, y, r));
        }
    }
    
    @Override
    public void draw() {
        background(0);
        for (Drawable element : elements) {
            if (element.isChanged()) {
                element.draw(this.g);
            }
        }
    }

}
