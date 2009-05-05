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
import edu.mit.rewire.view.animation.ShrinkBubbleAnimation;

public class ProcessingView extends PApplet {

	private static final long serialVersionUID = -4669039170458943974L;

	private final List<Drawable> elements = new ArrayList<Drawable>();
	private final List<Animation> animations = new LinkedList<Animation>();

	private PhysicsAnimation physicsEngine;

	private final List<MouseAware> components = new LinkedList<MouseAware>();
    
    private final List<MouseAware> toRemove = new LinkedList<MouseAware>();
    
    private final float width = screen.width;
    private final float height = screen.height;
    
    private MouseAware mouseOver = null;
    
    private MouseAware mouseDown = null;
    
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
			this.components.add(bubble);
			this.physicsEngine.add(bubble);
			this.addDrawable(bubble);
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

	public void addAnimation(Animation animation) {
		this.animations.add(animation);
	}

	public void addDrawable(Drawable drawable) {
		this.elements.add(drawable);
	}

	public void addMouseAware(MouseAware component) {
		this.components.add(component);
	}

	public void removeDrawable(Drawable drawable) {
		this.elements.remove(drawable);
	}
	
	public void removeMouseAware(MouseAware component) {
	    this.components.remove(component);
	}

	private MouseAware hitComponent() {
	    MouseAware target = null;
		for (MouseAware component : components) {
			if (component.hits(mouseX, mouseY)) {
				target = component;
			}
		}
		return target;
	}

	public void mousePressed() {
		MouseAware component = hitComponent();
		if (component == null) return;
	    this.mouseDown = component;
		component.dispatchDown(this, mouseX, mouseY);
	}

	public void mouseReleased() {
	    // FIXME releasing mouse outside of region registers as a click
	    if (this.mouseDown != null) {
	        this.mouseDown.dispatchUp(this, mouseX, mouseY);
	        this.mouseDown = null;
	        return;
	    }
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
            } else if (mouseOver != component) {
                component.dispatchIn(this, mouseX, mouseY);
                this.mouseOver.dispatchOut(this, mouseX, mouseY);
                this.mouseOver = component;
            }
        }
	    
	}

    public void expandBubble(Bubble bubble) {
    	bubble.setOriginalX(bubble.getX());
    	bubble.setOriginalY(bubble.getY());
    	
        bg = new BackgroundOverlay(bubble, width, height);
    	
    	SequentialAnimation animation = new SequentialAnimation();
        animation.add(new GrayOutAnimation(bg, true));
    	animation.add(new ExpandBubbleAnimation(bubble, 100, width, height));
        animation.add(new FixedAnimation(bubble));
        
        this.addAnimation(animation);
        
        this.removeDrawable(bubble);
        this.removeMouseAware(bubble);
        
        this.addMouseAware(bg);
        this.addDrawable(bg);
        
        this.addMouseAware(bubble);
        this.addDrawable(bubble);
    }
    
    public void shrinkBubble(Bubble bubble) {
    	SequentialAnimation animation = new SequentialAnimation();
    	animation.add(new GrayOutAnimation(bg, false));
    	animation.add(new ShrinkBubbleAnimation(bubble, 100, width, height));
    	
    	this.addAnimation(animation);
        
        this.removeDrawable(bg);
        this.removeMouseAware(bg);
    }
    
    
    public void handleMarkReadClick(Bubble bubble) {
        this.addAnimation(new PopBubbleAnimation(bubble, this));
        this.removeDrawable(bubble);
        this.addAnimation(new GrayOutAnimation(bg, false));
        this.toRemove.add(bubble);
        this.toRemove.add(bg);
    }
    
    public void handleStarClick(Bubble bubble) {
        this.addAnimation(new PopBubbleAnimation(bubble, this));
        this.removeDrawable(bubble);
    }
    
    public void handleOpenClick(Bubble bubble) {
        this.addAnimation(new PopBubbleAnimation(bubble, this));
        this.removeDrawable(bubble);
    }
    
    public void handleTrashClick(Bubble bubble) {
        this.addAnimation(new PopBubbleAnimation(bubble, this));
        this.removeDrawable(bubble);
    }

    public void remove(MouseAware component) {
        this.components.remove(component);        
    }

}
