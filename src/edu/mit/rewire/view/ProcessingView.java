package edu.mit.rewire.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PFont;
import edu.mit.rewire.controller.Controller;
import edu.mit.rewire.model.DataSource;
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

    private PImage bluebubble;
    private PImage lavenderbubble;
    private PImage pinkbubble;
    private PImage seafoambubble;
    private PImage yellowbubble;
    private PImage orangebubble;

    private PImage blueicon;
    private PImage lavendericon;
    private PImage pinkicon;
    private PImage seafoamicon;
    private PImage yellowicon;
    private PImage orangeicon;

    private PImage markReadButton;
    private PImage starButton;
    private PImage openButton;
    private PImage trashButton;

    private PFont titleFont;
    private PFont bodyFont;

    @Override
    public void setup() {
        this.controller = new Controller(this, screen.width, screen.height);

        bluebubble = loadImage("blue.png");
        lavenderbubble = loadImage("lavender.png");
        pinkbubble = loadImage("pink.png");
        seafoambubble = loadImage("seafoam.png");
        yellowbubble = loadImage("yellow.png");
        orangebubble = loadImage("bluebubble.png");

        blueicon = loadImage("facebook-rounded.png");
        lavendericon = loadImage("twitter.png");
        pinkicon = loadImage("times.png");
        seafoamicon = loadImage("todo.png");
        yellowicon = loadImage("weather.png");
        orangeicon = loadImage("gmail.png");

        markReadButton = loadImage("markread.png");
        starButton = loadImage("star.png");
        openButton = loadImage("open.png");
        trashButton = loadImage("trash.png");

        titleFont = loadFont("HelveticaNeue-Light-36.vlw");
        bodyFont = loadFont("HelveticaNeue-Light-14.vlw");

        size(screen.width, screen.height);
        background(0);
        smooth();
        frameRate(30);

        physicsEngine = new PhysicsAnimation(this.width, this.height);

        DataSource dataSource = new DataSource();
        dataSource.addItem(new MockItem());
        dataSource.addItem(new MockItem());
        dataSource.addItem(new MockItem());
        dataSource.addItem(new MockItem());
        dataSource.addItem(new MockItem());

        List<Item> items = dataSource.getItems();

        for (Item item : items) {

            float r = (float) ((Math.random() * 50) + 50);
            float x = (float) ((Math.random() * (screen.width - r)) + r);
            float y = (float) ((Math.random() * (screen.height - r)) + r);

            // Choose x and y in such a way so that no overlap occurs

            // // Set checker to be equal to the number of existing bubbles on
            // the screen
            // int checker = bubbles.size();
            //        	
            // /* while loop checks for any overlap with existing bubbles,
            // * if it finds one it will reassign x and y and try again until
            // * it finds acceptable values
            // */
            // while (checker != 0) {
            // checker = bubbles.size();
            //        		
            // x = (float) ((Math.random() * (screen.width - r)) + r);
            // y = (float) ((Math.random() * (screen.height - r)) + r);
            //                
            // for (Bubble b : bubbles) {
            //        			                  
            // float xTest = x - b.getX();
            // float yTest = y - b.getY();
            //                    
            // float mag = (float) Math.sqrt(xTest * xTest + yTest * yTest);
            //                    
            // if (mag > (b.getR() + r)) {
            // checker -= 1;
            // } else {
            // break;
            // }
            // }
            // }

            // ugly if-else statement
            Bubble bubble;
            if (item.getType() == "blue") {
                bubble = new Bubble(item, x, y, r, bluebubble, titleFont,
                        bodyFont, blueicon, markReadButton, starButton,
                        openButton, trashButton);
            } else if (item.getType() == "lavender") {
                bubble = new Bubble(item, x, y, r, lavenderbubble, titleFont,
                        bodyFont, lavendericon, markReadButton, starButton,
                        openButton, trashButton);
            } else if (item.getType() == "pink") {
                bubble = new Bubble(item, x, y, r, pinkbubble, titleFont,
                        bodyFont, pinkicon, markReadButton, starButton,
                        openButton, trashButton);
            } else if (item.getType() == "seafoam") {
                bubble = new Bubble(item, x, y, r, seafoambubble, titleFont,
                        bodyFont, seafoamicon, markReadButton, starButton,
                        openButton, trashButton);
            } else if (item.getType() == "yellow") {
                bubble = new Bubble(item, x, y, r, yellowbubble, titleFont,
                        bodyFont, yellowicon, markReadButton, starButton,
                        openButton, trashButton);
            } else if (item.getType() == "orange") {
                bubble = new Bubble(item, x, y, r, orangebubble, titleFont,
                        bodyFont, orangeicon, markReadButton, starButton,
                        openButton, trashButton);
            } else {
                bubble = new Bubble(item, x, y, r, orangebubble, titleFont,
                        bodyFont, orangeicon, markReadButton, starButton,
                        openButton, trashButton);
            }

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
            if (iterator.next().step())
                iterator.remove();
        }

        for (Drawable element : elements) {
            element.draw(this.g);
        }

    }

    public void add(Animation animation) {
        this.animations.add(animation);
    }
    
    public void remove(Bubble bubble) {
        this.elements.remove(bubble);
    }

    @Override
    public void mouseClicked() {
        int x = mouseX;
        int y = mouseY;

        this.controller.doClick(x, y);
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
