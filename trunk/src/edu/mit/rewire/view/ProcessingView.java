package edu.mit.rewire.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import processing.core.PApplet;
import edu.mit.rewire.controller.Controller;
import edu.mit.rewire.model.Item;
import edu.mit.rewire.model.MockItem;
import edu.mit.rewire.view.animation.Animation;
import edu.mit.rewire.view.animation.PhysicsAnimation;

public class ProcessingView extends PApplet {
    
    private static final long serialVersionUID = -4669039170458943974L;
    
    private final List<Drawable> elements = new ArrayList<Drawable>();
    private final List<Animation> animations = new LinkedList<Animation>();
    
    private PhysicsAnimation physicsEngine;
    
    private Controller controller;
    
    @Override
    public void setup() {
        size(screen.width, screen.height);
        background(0);
        smooth();
        
        physicsEngine = new PhysicsAnimation(this.width, this.height);
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
        this.animations.add(physicsEngine);
    }
    
    @Override
    public void draw() {
        background(0);
        
        Iterator<Animation> iterator = animations.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().step()) iterator.remove();
        }
        
        for (Drawable element : elements) {
            if (element.isChanged()) {
                element.draw(this.g);
                element.setChanged(false);
            }
        }
    }
    
    @Override
    public void mouseClicked() {
        int x = mouseX;
        int y = mouseY;
        
        
    }

}
