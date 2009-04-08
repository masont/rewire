package edu.mit.rewire;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import processing.core.PApplet;
import edu.mit.rewire.model.Item;
import edu.mit.rewire.model.MockItem;
import edu.mit.rewire.view.Bubble;
import edu.mit.rewire.view.Drawable;
import edu.mit.rewire.view.PhysicsEngine;
import edu.mit.rewire.view.animation.Animation;

public class Main extends PApplet {
    
    private static final long serialVersionUID = -4669039170458943974L;
    
    private final List<Drawable> elements = new ArrayList<Drawable>();
    private final List<Animation> animations = new LinkedList<Animation>();
    
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

// EPILEPSY MODE        
//        
//        this.animations.add(new Animation() {
//
//			@Override
//			public boolean step() {
//				if (Math.random() > 0.5) {
//					background(255);
//				} else {
//					background(0);
//				}
//				return false;
//			}
//        	
//        });
        
        this.physicsEngine.init();
    }
    
    @Override
    public void draw() {
        this.physicsEngine.step();
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
