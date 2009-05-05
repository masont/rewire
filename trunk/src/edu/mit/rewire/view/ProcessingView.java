package edu.mit.rewire.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import processing.core.PApplet;
import edu.mit.rewire.model.DataSource;
import edu.mit.rewire.model.Item;
import edu.mit.rewire.view.animation.Animation;
import edu.mit.rewire.view.animation.ExpandBubbleAnimation;
import edu.mit.rewire.view.animation.FixedAnimation;
import edu.mit.rewire.view.animation.GrayOutAnimation;
import edu.mit.rewire.view.animation.PhysicsAnimation;
import edu.mit.rewire.view.animation.PopBubbleAnimation;
import edu.mit.rewire.view.animation.SequentialAnimation;

public class ProcessingView extends PApplet {

	private static final long serialVersionUID = -4669039170458943974L;

	private final List<Drawable> elements = new ArrayList<Drawable>();
	private final List<Animation> animations = new LinkedList<Animation>();
	private final List<Bubble> bubbles = new ArrayList<Bubble>();

	private PhysicsAnimation physicsEngine;

	private final List<MouseAware> components = new LinkedList<MouseAware>();
    
    private final List<MouseAware> toRemove = new LinkedList<MouseAware>();
    
    private final float width = screen.width;
    private final float height = screen.height;
    
    private MouseAware mouseOver = null;
    
    private BackgroundOverlay bg;
	
	private DataSource dataSource;

	private float r = 75;
	private float x;
	private float y;

	@Override
	public void setup() {
		
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

		for (Item item : items) {

			// Place bubbles randomly near center of screen - PhysicsAnimation handles spreading bubbles out on screen
			x = (float) ((Math.random() * (0 - r) + r)) + (screen.width/2);
			y = (float) ((Math.random() * (0 - r) + r)) + (screen.height/2);

			// ugly if-else statement
			Bubble bubble;
			if (item.getType() == "rss") {
				bubble = new Bubble(item, x, y, r, ViewResources.loadShape("orangeBubble"),  ViewResources.loadShape("rssIcon"));
			} else if (item.getType() == "twitter") {
				bubble = new Bubble(item, x, y, r, ViewResources.loadShape("twitterBlueBubble"), ViewResources.loadShape("twitterIcon"));
			} else if (item.getType() == "nyt") {
				bubble = new Bubble(item, x, y, r, ViewResources.loadShape("pinkBubble"), ViewResources.loadShape("nytIcon"));
			} else if (item.getType() == "todo") {
				bubble = new Bubble(item, x, y, r, ViewResources.loadShape("kellyBubble"), ViewResources.loadShape("todoIcon"));
			} else if (item.getType() == "weather") {
				bubble = new Bubble(item, x, y, r, ViewResources.loadShape("yellowBubble"), ViewResources.loadShape("weatherIcon"));
			} else if (item.getType() == "email") {
				bubble = new Bubble(item, x, y, r, ViewResources.loadShape("redBubble"), ViewResources.loadShape("mailIcon"));
			} else {
				bubble = new Bubble(item, x, y, r, ViewResources.loadShape("redBubble"), ViewResources.loadShape("mailIcon"));
			}

			this.elements.add(bubble);
			this.physicsEngine.add(bubble);
			this.add(bubble);
			this.bubbles.add(bubble);
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
		component.dispatchDown(this, mouseX, mouseY);
	}

	public void mouseReleased() {
		MouseAware component = hitComponent();
		if (component == null) return;
		component.dispatchUp(this, mouseX, mouseY);
	}
	
	public void mouseMoved() {
	    MouseAware component = hitComponent();
        if (component == null) {
            if (mouseOver != null) {
                this.mouseOver.dispatchOut(this, mouseX, mouseY);
                this.mouseOver = null;
            }
        } else {
            if (mouseOver == null) {
                component.dispatchIn(this, mouseX, mouseY);
                this.mouseOver = component;
            } else if (mouseOver == component) {
                component.dispatchIn(this, mouseX, mouseY);
                this.mouseOver.dispatchOut(this, mouseX, mouseY);
                this.mouseOver = component;
            }
        }
	    
	}

	public void remove(BackgroundOverlay bg) {
		this.elements.remove(bg);
	}

    public void expandBubble(Bubble bubble) {
        bg = new BackgroundOverlay(width, height);
    	
    	SequentialAnimation animation = new SequentialAnimation();
        animation.add(new GrayOutAnimation(bg, true));
    	animation.add(new ExpandBubbleAnimation(bubble, 100, width, height));
        animation.add(new FixedAnimation(bubble));
        
        this.add(animation);
        this.remove(bubble);
        this.add(bg);
        this.add(bubble);
    }
    
    public void handleMarkReadClick(Bubble bubble) {
        this.add(new PopBubbleAnimation(bubble, this));
        this.remove(bubble);
        this.add(new GrayOutAnimation(bg, false));
        this.toRemove.add(bubble);
        this.toRemove.add(bg);
    }
    
    public void handleStarClick(Bubble bubble) {
        this.add(new PopBubbleAnimation(bubble, this));
        this.remove(bubble);
    }
    
    public void handleOpenClick(Bubble bubble) {
        this.add(new PopBubbleAnimation(bubble, this));
        this.remove(bubble);
    }
    
    public void handleTrashClick(Bubble bubble) {
        this.add(new PopBubbleAnimation(bubble, this));
        this.remove(bubble);
    }

    public void remove(MouseAware component) {
        this.components.remove(component);        
    }

}
