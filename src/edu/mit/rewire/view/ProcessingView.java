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
        this.controller = new Controller(this);
        
        size(screen.width, screen.height, OPENGL);
        background(0);
        smooth();
        frameRate(30);
        
        physicsEngine = new PhysicsAnimation(this.width, this.height);
        Item item = new MockItem();
//      ArrayList<Bubble> bubbles = new ArrayList<Bubble>();
        for (int i = 0; i < 20; i++) {
            float x = (float) (Math.random() * screen.width);
            float y = (float) (Math.random() * screen.height);
            float r = (float) ((Math.random() * 50) + 50);

// Sad attempt to get bubbles to NOT place over other existing bubbles
//            for (Bubble b : bubbles) {
//            	float xTest = Math.abs(x - b.getX());
//            	float yTest = Math.abs(y - b.getY());
//            	float mag = (float) Math.sqrt(xTest * xTest + yTest * yTest);
//            	                     	
//            	while (mag < (b.getR() + r)) {
//            		            		
//            		x = (float) (Math.random() * screen.width);
//            		y = (float) (Math.random() * screen.height);
//            		
//            		xTest = Math.abs(x - b.getX());
//            		yTest = Math.abs(y - b.getY());
//                	mag = (float) Math.sqrt(xTest * xTest + yTest * yTest);
//            	}            	
//            }
            
            Bubble bubble = new Bubble(item, x, y, r);
//          bubbles.add(bubble);
            this.elements.add(bubble);
            this.physicsEngine.add(bubble);
            this.controller.add(bubble);
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
    
    public void add(Animation animation) {
        this.animations.add(animation);
    }
    
    @Override
    public void mouseClicked() {
        int x = mouseX;
        int y = mouseY;
        
        this.controller.doClick(x, y);
    }
    
    @Override
    public void mouseMoved() {
        int x = mouseX;
        int y = mouseY;
        
        this.controller.doMove(x, y);
    }


}