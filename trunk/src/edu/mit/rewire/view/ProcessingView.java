package edu.mit.rewire.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import processing.core.PApplet;
import edu.mit.rewire.controller.Controller;
import edu.mit.rewire.model.DataSource;
import edu.mit.rewire.model.Item;
import edu.mit.rewire.view.animation.Animation;
import edu.mit.rewire.view.animation.PhysicsAnimation;

public class ProcessingView extends PApplet {

	private static final long serialVersionUID = -4669039170458943974L;

	private final List<Drawable> elements = new ArrayList<Drawable>();
	private final List<Animation> animations = new LinkedList<Animation>();
	private final List<Bubble> bubbles = new ArrayList<Bubble>();				// JARED CHANGE

	private PhysicsAnimation physicsEngine;

	private Controller controller;
	
	private DataSource dataSource;

	// Variables for placement of bubbles on screen
	private final boolean randomPlacement = false;
	private float r = 75;
	private float x;
	private float y;
	private int cellRows = (int) Math.floor(screen.height/(2*r));
	private int cellColumns = (int) Math.floor(screen.width/(2*r));
	private boolean[][] testArray = new boolean[cellRows][cellColumns];

	@Override
	public void setup() {
		this.controller = new Controller(this, screen.width, screen.height);

		size(screen.width, screen.height);
		background(0);
		smooth();

		physicsEngine = new PhysicsAnimation(this.width, this.height);
	    this.dataSource = new DataSource();       
		this.loadItems();
		this.physicsEngine.init();
		this.animations.add(physicsEngine);
	}
	
	private void loadItems() {
        List<Item> items = dataSource.getItems();
        //System.out.println(cellRows + " rows and " + cellColumns + " columns.");

        for (Item item : items) {

            if (randomPlacement) {
                x = (float) ((Math.random() * (screen.width - r)) + r);
                y = (float) ((Math.random() * (screen.height - r)) + r);

                // Choose x and y in such a way so that no overlap occurs

                // Set checker to be equal to the number of existing bubbles on the screen
                int checker = bubbles.size();
                
                /* while loop checks for any overlap with existing bubbles,
                 * if it finds one it will reassign x and y and try again until
                 * it finds acceptable values
                 */
                while (checker != 0) {

                    checker = bubbles.size();

                    x = (float) ((Math.random() * (screen.width - r)) + r);
                    y = (float) ((Math.random() * (screen.height - r)) + r);

                    for (Bubble b : bubbles) {

                        float xTest = x - b.getX();
                        float yTest = y - b.getY();

                        float mag = (float) Math.sqrt(xTest * xTest + yTest * yTest);

                        if (mag > (b.getR() + r)) {
                            checker -= 1;
                        } else {
                            break;
                        }
                    }
                }

            } else {
                
                // Attempt to place bubbles into predefined 'cells'
                // --Currently not finished feature.
                
                int rowChoice = (int) Math.floor(Math.random() * cellRows);
                int columnChoice = (int) Math.floor(Math.random() * cellColumns);

                x = (columnChoice * (2*r)) + r;
                y = (rowChoice * (2*r)) + r;

                while (testArray[rowChoice][columnChoice]) {
                    rowChoice = (int) Math.floor(Math.random() * cellRows);
                    columnChoice = (int) Math.floor(Math.random() * cellColumns);

                    x = (columnChoice * (2*r)) + r;
                    y = (rowChoice * (2*r)) + r;
                }

                testArray[rowChoice][columnChoice] = true;
            }

            // ugly if-else statement
            Bubble bubble;
            if (item.getType() == "rss") {
                bubble = new Bubble(item, x, y, r, ViewResources.loadShape("orangeBubble"),  ViewResources.loadShape("rssIcon"));
            } else if (item.getType() == "twitter") {
                bubble = new Bubble(item, x, y, r, ViewResources.loadShape("twitterBlueBubble"), ViewResources.loadShape("twitterIcon"));
            } else if (item.getType() == "nyt") {
                bubble = new Bubble(item, x, y, r, ViewResources.loadShape("pinkBubble"), ViewResources.loadShape("nytIcon"));
            } else if (item.getType() == "todo") {
                bubble = new Bubble(item, x, y, r, ViewResources.loadShape("seafoamBubble"), ViewResources.loadShape("todoIcon"));
            } else if (item.getType() == "weather") {
                bubble = new Bubble(item, x, y, r, ViewResources.loadShape("yellowBubble"), ViewResources.loadShape("weatherIcon"));
            } else if (item.getType() == "mail") {
                bubble = new Bubble(item, x, y, r, ViewResources.loadShape("redBubble"), ViewResources.loadShape("mailIcon"));
            } else {
                bubble = new Bubble(item, x, y, r, ViewResources.loadShape("redBubble"), ViewResources.loadShape("mailIcon"));
            }

            this.elements.add(bubble);
            this.physicsEngine.add(bubble);
            this.controller.add(bubble);
            this.bubbles.add(bubble);
            //System.out.println(bubbles);
        }
	}

	@Override
	public void draw() {
		background(0);

		for (Drawable element : elements) {
			element.draw(this.g);
		}

		Iterator<Animation> iterator = animations.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().step())
				iterator.remove();
		}

		image(ViewResources.loadImage("logo"),20,40);
		
		shape(ViewResources.loadShape("redToggle"),20,600,60,60);
		shape(ViewResources.loadShape("twitterBlueToggle"),100,600,60,60);
		shape(ViewResources.loadShape("orangeToggle"),180,600,60,60);
		shape(ViewResources.loadShape("seafoamToggle"),260,600,60,60);
		shape(ViewResources.loadShape("yellowToggle"),340,600,60,60);

	}

	public void add(Animation animation) {
		this.animations.add(animation);
	}

	public void add(Bubble bubble) {
		this.elements.add(bubble);
	}
	
	public void add(BackgroundOverlay bg) {
		this.elements.add(bg);
	}

	public void remove(Bubble bubble) {
		this.elements.remove(bubble);
	}
	
	private MouseAware hitComponent() {
        for (MouseAware component : bubbles) {
            if (component.hits(mouseX, mouseY)) {
                return component;
            }
        }
        return null;
	}
	
	public void mousePressed() {
	    MouseAware component = hitComponent();
	    if (component == null) return;
	    component.dispatchDown(controller, mouseX, mouseY);
	}
	
	public void mouseReleased() {
        MouseAware component = hitComponent();
        if (component == null) return;
        component.dispatchUp(controller, mouseX, mouseY);
    }
	
//	public void mouseMoved() {
//        MouseAware component = hitComponent();
//        if (component == null) return;
//        component.dispatchDown(controller, mouseX, mouseY);
//    }

	public void remove(BackgroundOverlay bg) {
		this.elements.remove(bg);
	}

	public void update(int x, int y) {
		this.controller.update(x, y, mousePressed);
	}
	//    
	// @Override
	// public void mouseMoved() {
	// int x = mouseX;
	// int y = mouseY;
	//        
	// this.controller.doMove(x, y);
	// }

}
