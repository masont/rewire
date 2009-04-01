package edu.mit.rewire;

import java.util.ArrayList;
import java.util.List;

import edu.mit.rewire.model.Item;
import edu.mit.rewire.model.MockItem;
import edu.mit.rewire.view.Bubble;
import edu.mit.rewire.view.Drawable;
import edu.mit.rewire.view.PhysicsEngine;

import processing.core.PApplet;

public class Main extends PApplet {
    
    private static final long serialVersionUID = -4669039170458943974L;
    
    private final List<Drawable> elements = new ArrayList<Drawable>();
    private PhysicsEngine physicsEngine;
    
    @Override
    public void setup() {
        size(screen.width, screen.height);
        background(0);
        smooth();
        
        physicsEngine = new PhysicsEngine(this.width, this.height);
        Item item = new MockItem();
        for (int i = 0; i < 40; i++) {
            float x = (float) (Math.random() * screen.width);
            float y = (float) (Math.random() * screen.height);
            float r = (float) (Math.random() * 100);
            Bubble bubble = new Bubble(item, x, y, r);
            this.elements.add(bubble);
            this.physicsEngine.add(bubble);
        }
        this.physicsEngine.init();
    }
    
    @Override
    public void draw() {
        this.physicsEngine.step();
        background(0);
        for (Drawable element : elements) {
            if (element.isChanged()) {
                element.draw(this.g);
            }
        }
    }

}
