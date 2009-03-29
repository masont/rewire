package edu.mit.rewire.view;

import java.util.ArrayList;
import java.util.List;

import edu.mit.rewire.model.Item;
import edu.mit.rewire.model.MockItem;

import processing.core.PApplet;

public class Main extends PApplet {
    
    private static final long serialVersionUID = -4669039170458943974L;
    
    private final List<Drawable> elements = new ArrayList<Drawable>();
    private final PhysicsEngine physicsEngine = new PhysicsEngine();
    
    @Override
    public void setup() {
        size(screen.width, screen.height);
        background(0);
        smooth();
        Item item = new MockItem();
        for (int i = 0; i < 40; i++) {
            float x = (float) (Math.random() * screen.width);
            float y = (float) (Math.random() * screen.height);
            float r = (float) (Math.random() * 100);
            Bubble bubble = new Bubble(item, x, y, r);
            this.elements.add(bubble);
            this.physicsEngine.add(bubble);
        }
        (new Thread(physicsEngine)).start();
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
