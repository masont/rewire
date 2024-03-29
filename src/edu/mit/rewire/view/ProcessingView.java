package edu.mit.rewire.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import processing.core.PApplet;
import edu.mit.rewire.model.DataSource;
import edu.mit.rewire.model.Item;
import edu.mit.rewire.view.Bubble.State;
import edu.mit.rewire.view.animation.Animation;
import edu.mit.rewire.view.animation.ExpandBubbleAnimation;
import edu.mit.rewire.view.animation.FixedAnimation;
import edu.mit.rewire.view.animation.GrayOutAnimation;
import edu.mit.rewire.view.animation.PhysicsAnimation;
import edu.mit.rewire.view.animation.PopBubbleAnimation;
import edu.mit.rewire.view.animation.ScaleBubbleAnimation;
import edu.mit.rewire.view.animation.SequentialAnimation;
import edu.mit.rewire.view.animation.ShrinkBubbleAnimation;

public class ProcessingView extends PApplet {

	private static final long serialVersionUID = -4669039170458943974L;

	private final List<Drawable> elements = new ArrayList<Drawable>();
	private final List<Animation> animations = new LinkedList<Animation>();

	private final List<Button> bubbleButtons = new LinkedList<Button>();

	private PhysicsAnimation physicsEngine;

	private final List<MouseAware> components = new LinkedList<MouseAware>();

	private final float width = screen.width;
	private final float height = screen.height;

	private MouseAware mouseOver = null;

	private MouseAware mouseDown = null;

	private BackgroundOverlay bg = new BackgroundOverlay(null, width, height);;

	private DataSource dataSource;
	
	private UndoCommand lastCommand = null;

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

		this.addDrawable(bg);
	}

	private void loadItems() {
		List<Item> items = dataSource.getItems();

		Toggle redToggle = new Toggle("email", 20, screen.height - 100, 60, 80,
				ViewResources.loadShape("redToggleOff"), ViewResources
				.loadShape("redToggleHover"), ViewResources
				.loadShape("redToggleOn"));
		Toggle twitterBlueToggle = new Toggle("twitter", 100,
				screen.height - 100, 60, 80, ViewResources
				.loadShape("twitterBlueToggleOff"), ViewResources
				.loadShape("twitterBlueToggleHover"), ViewResources
				.loadShape("twitterBlueToggleOn"));
		Toggle orangeToggle = new Toggle("rss", 180, screen.height - 100, 60,
				80, ViewResources.loadShape("orangeToggleOff"), ViewResources
				.loadShape("orangeToggleHover"), ViewResources
				.loadShape("orangeToggleOn"));
		Toggle kellyToggle = new Toggle("todo", 260, screen.height - 100, 60,
				80, ViewResources.loadShape("kellyToggleOff"), ViewResources
				.loadShape("kellyToggleHover"), ViewResources
				.loadShape("kellyToggleOn"));
		Toggle yellowToggle = new Toggle("weather", 340, screen.height - 100,
				60, 80, ViewResources.loadShape("yellowToggleOff"),
				ViewResources.loadShape("yellowToggleHover"), ViewResources
				.loadShape("yellowToggleOn"));

		this.elements.add(redToggle);
		this.components.add(redToggle);
		this.elements.add(twitterBlueToggle);
		this.components.add(twitterBlueToggle);
		this.elements.add(orangeToggle);
		this.components.add(orangeToggle);
		this.elements.add(kellyToggle);
		this.components.add(kellyToggle);
		this.elements.add(yellowToggle);
		this.components.add(yellowToggle);

		for (Item item : items) {

			// Place bubbles randomly near center of screen - PhysicsAnimation
			// handles spreading bubbles out on screen
			x = (float) ((Math.random() * (0 - r) + r)) + (screen.width / 2);
			y = (float) ((Math.random() * (0 - r) + r)) + (screen.height / 2);

			// ugly if-else statement
			Bubble bubble;
			if (item.getType() == "rss") {
				bubble = new Bubble(item, x, y, r, ViewResources
						.loadShape("orangeBubble"), ViewResources
						.loadShape("orangeBubbleHover"), ViewResources
						.loadShape("rssIcon"));
			} else if (item.getType() == "twitter") {
				bubble = new Bubble(item, x, y, r, ViewResources
						.loadShape("twitterBlueBubble"), ViewResources
						.loadShape("twitterBlueBubbleHover"), ViewResources
						.loadShape("twitterIcon"));
			} else if (item.getType() == "nyt") {
				bubble = new Bubble(item, x, y, r, ViewResources
						.loadShape("pinkBubble"), ViewResources
						.loadShape("pinkBubbleHover"), ViewResources
						.loadShape("nytIcon"));
			} else if (item.getType() == "todo") {
				bubble = new Bubble(item, x, y, r, ViewResources
						.loadShape("kellyBubble"), ViewResources
						.loadShape("kellyBubbleHover"), ViewResources
						.loadShape("todoIcon"));
			} else if (item.getType() == "weather") {
				bubble = new Bubble(item, x, y, r, ViewResources
						.loadShape("yellowBubble"), ViewResources
						.loadShape("yellowBubbleHover"), ViewResources
						.loadShape("weatherIcon"));
			} else if (item.getType() == "email") {
				bubble = new Bubble(item, x, y, r, ViewResources
						.loadShape("redBubble"), ViewResources
						.loadShape("redBubbleHover"), ViewResources
						.loadShape("mailIcon"));
			} else {
				bubble = new Bubble(item, x, y, r, ViewResources
						.loadShape("redBubble"), ViewResources
						.loadShape("redBubbleHover"), ViewResources
						.loadShape("mailIcon"));
			}

			this.elements.add(bubble);
			this.components.add(bubble);
			this.physicsEngine.add(bubble);
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

		image(ViewResources.loadImage("logo"), 20, 40);

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

	public void addBubbleButton(Button button) {
		this.bubbleButtons.add(button);
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
		if (component == null)
			return;
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
		if (component == null)
			return;
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
	
    public void keyPressed() {
        if (keyEvent.getKeyChar() == 'Z' || keyEvent.getKeyChar() == 'z') {
            if (this.lastCommand != null) {
                this.lastCommand.undo();
                this.lastCommand = null;
            }
        }
    }

	public void showBubbleButtons(Bubble bubble) {

		float buttonX = bubble.getX() + bubble.getR() / 2;
		float baseY = bubble.getY() - bubble.getR() * 2 / 4 - 10;
		float spacing = 100;

		Button markReadButton = new MarkReadButton(bubble, buttonX, baseY, 100,
				100);
		this.addMouseAware(markReadButton);
		this.addDrawable(markReadButton);
		this.addBubbleButton(markReadButton);

		Button bubbleButton = new BubbleButton(bubble, buttonX,
				baseY + spacing, 100, 100);
		this.addMouseAware(bubbleButton);
		this.addDrawable(bubbleButton);
		this.addBubbleButton(bubbleButton);

		StarButton starButton = new StarButton(bubble, buttonX, baseY + spacing
				* 2, 100, 100);
		this.addMouseAware(starButton);
		this.addDrawable(starButton);
		this.addBubbleButton(starButton);

		Button trashButton = new TrashButton(bubble, buttonX, baseY + spacing
				* 3, 100, 100);
		this.addMouseAware(trashButton);
		this.addDrawable(trashButton);
		this.addBubbleButton(trashButton);

	}

	public void maximizeBubble(Bubble bubble) {
		bubble.setOriginalX(bubble.getX());
		bubble.setOriginalY(bubble.getY());

		bg.setBubble(bubble);

		SequentialAnimation animation = new SequentialAnimation();
		animation.add(new ExpandBubbleAnimation(bubble, this, 100, width,
				height));
		animation.add(new FixedAnimation(bubble));

		this.addAnimation(new GrayOutAnimation(bg, true));
		this.addAnimation(animation);

		this.removeDrawable(bubble);
		this.removeMouseAware(bubble);
		this.removeDrawable(bg);

		this.addMouseAware(bg);
		this.addDrawable(bg);

		this.addMouseAware(bubble);
		this.addDrawable(bubble);

	}

	public void minimizeBubble(Bubble bubble) {
		this.addAnimation(new GrayOutAnimation(bg, false));
		this.addAnimation(new ShrinkBubbleAnimation(bubble, 100, width,
				height));

		this.removeMouseAware(bg);

		for (Button button : this.bubbleButtons) {
			this.removeMouseAware(button);
			this.removeDrawable(button);
		}
		this.bubbleButtons.clear();
	}

	public void filterBubbles(String type, boolean direction) {

		for (Bubble b : this.physicsEngine.getBubbles()) {
			if (b.getItem().getType() == type) {
				this.addAnimation(new ScaleBubbleAnimation(b, 100,
						direction ? State.MEDIUM : State.SMALL));
			}
		}
	}

	public void popBubble(final Bubble bubble) {
		this.addAnimation(new PopBubbleAnimation(bubble, this));
		this.removeDrawable(bubble);
		this.removeMouseAware(bubble);
		this.physicsEngine.remove(bubble);

		this.addAnimation(new GrayOutAnimation(bg, false));
		this.removeMouseAware(bg);

		for (Button button : this.bubbleButtons) {
			this.removeMouseAware(button);
			this.removeDrawable(button);
		}
		this.bubbleButtons.clear();
		
        final ProcessingView self = this;
        this.lastCommand = new UndoCommand() {
            public void undo() {
                bubble.setState(bubble.getDefaultState());
                bubble.setR(bubble.getState() == State.SMALL ? 75 : 150);
                self.elements.add(bubble);
                self.components.add(bubble);
                self.physicsEngine.add(bubble);
            }
        };
	}

	public void trashBubble(Bubble bubble) {
		this.popBubble(bubble);
	}

	public void markRead(Bubble bubble) {
		this.popBubble(bubble);
	}

	public void star(Bubble bubble) {
		bubble.setStarred(!bubble.isStarred());
	}

	public void handleStarClick(Bubble bubble) {
		this.addAnimation(new PopBubbleAnimation(bubble, this));
		this.removeDrawable(bubble);
	}

	public void handleOpenClick(Bubble bubble) {
		this.addAnimation(new PopBubbleAnimation(bubble, this));
		this.removeDrawable(bubble);
	}

}
