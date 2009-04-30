package edu.mit.rewire.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PShape;
import edu.mit.rewire.controller.Controller;
import edu.mit.rewire.model.DataSource;
import edu.mit.rewire.model.DummyItem;
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

	// Variables for placement of bubbles on screen
	private final boolean randomPlacement = false;
	private float r = 75;
	private float x;
	private float y;
	private int cellRows = (int) Math.floor(screen.height/(2*r));
	private int cellColumns = (int) Math.floor(screen.width/(2*r));
	private boolean[][] testArray = new boolean[cellRows][cellColumns];


	private PImage rewire;

	private PShape bubble_red;
	private PShape bubble_twitterblue;
	private PShape bubble_pink;
	private PShape bubble_seafoam;
	private PShape bubble_yellow;
	private PShape bubble_orange;
	
	private PShape fav_rss;
	private PShape fav_twitter;
	private PShape fav_nyt;
	private PShape fav_todo;
	private PShape fav_weather;
	private PShape fav_mail;

	private PShape button_pop;
	private PShape button_star;
	private PShape button_open;
	private PShape button_trash;

	private PShape toggle_red;
	private PShape toggle_twitterblue;
	private PShape toggle_pink;
	private PShape toggle_seafoam;
	private PShape toggle_yellow;
	private PShape toggle_orange;
	
	private PFont font_title;
	private PFont font_body;

	@Override
	public void setup() {
		this.controller = new Controller(this, screen.width, screen.height);

		rewire = loadImage("logo.png");

		bubble_red = loadShape("bubble-red.svg");
		bubble_twitterblue = loadShape("bubble-twitterblue.svg");
		bubble_pink = loadShape("bubble-pink.svg");
		bubble_seafoam = loadShape("bubble-seafoam.svg");
		bubble_yellow = loadShape("bubble-yellow.svg");
		bubble_orange = loadShape("bubble-orange.svg");

		fav_rss = loadShape("rss-orange.svg");
		fav_twitter = loadShape("fav-twitter.svg");
		fav_nyt = loadShape("fav-nyt.svg");
		fav_todo = loadShape("fav-todo.svg");
		fav_weather = loadShape("fav-weather.svg");
		fav_mail = loadShape("fav-mail.svg");

		button_pop = loadShape("button-pop.svg");
		button_star = loadShape("button-star.svg");
		button_open = loadShape("button-open.svg");
		button_trash = loadShape("button-trash.svg");

		toggle_red = loadShape("toggle-red-on.svg");
		toggle_twitterblue = loadShape("toggle-twitter-on.svg");
		toggle_pink = loadShape("toggle-pink-on.svg");
		toggle_seafoam = loadShape("toggle-seafoam-on.svg");
		toggle_yellow = loadShape("toggle-yellow-on.svg");
		toggle_orange = loadShape("toggle-orange-on.svg");
		
		font_title = loadFont("HelveticaNeue-Light-36.vlw");
		font_body = loadFont("HelveticaNeue-Light-14.vlw");

		size(screen.width, screen.height);
		background(0);
		smooth();
		frameRate(30);

		physicsEngine = new PhysicsAnimation(this.width, this.height);

		DataSource dataSource = new DataSource();
		dataSource.addItem(new DummyItem("mail",
				"Subject: Next Friday: Computation Lecture Series: Shane Burger @ 12:30 in 3-133",
				"From: Daniela Smith",
				"All are invited to this talk in the Computation Group Lecture Series,\n"+
				"Department of Architecture, MIT:\n\n"+

				"Embedded Intelligence: Design-Driven Computation*\n\n"+

				"Friday, April 17th *\n" +
				"12:30 - 2:00pm\n" +
		"Room 3-133*\n"));
		dataSource.addItem(new DummyItem("mail",
				"Subject: 6.863 Project Proposal",
				"From: Professor Robert C Smith",
				"Hi all,\n\n"+

				"I think parsing 'pidgin' English is probably hard - because often the syntax "+
				"is reduced, you are right.\n"+
				"By classification - I didn't know what sorts of tasks you had in mind, "+
				"but i meant: did you want to *bin* the documents into different groups "+
				"(clustering) eg, spam, some sort of talk announcement, etc. -- for this, there are many sorts of classifications methods. "+
				"But again, I didn't know what your ultimate functional objective was --\n\n"+

		"best bob"));
		dataSource.addItem(new DummyItem("mail",
				"Subject: My name is Ekaterina.",
				"From: Yesenia Valenzuela",
				"Hi my new friend!\n\n"+
				"My name is Ekaterina I hope my letter will find  you in good mood. II "+
				"for the first time try such way of dialogue, and I really do not know "+
				"what to tell right now even that I understand that this first message "+
				"is of great importance. But I  have decided to write to you and maybe "+
				"you will answer. I sincerely  hope that you are looking for the same "+
				"as I.\n\n"+
				"Please reply only to my personal e-mail:  ekaterina2129@gmail.com\n\n"+

		"Bye."));
		dataSource.addItem(new DummyItem("mail",
				"Subject: [Listit] How to synchronize 2 computers",
				"From: Eleanor Smith",
				"Every time I try to synchronize it says 'sync success  yet none of my "+
				"notes which I typed in previously at my other computer appear.  Whassup?\n\n"+

				"I love this product, but haven't quite got the hang of it yet.  Help.\n\n"+

		"Eleanor Smith"));
		dataSource.addItem(new DummyItem("mail",
				"Subject: !!!!!",
				"From: James Smith",
		"http://www.ustream.tv/channel/snoop-dogg-live"));
		dataSource.addItem(new DummyItem("mail",
				"Subject: !!!!!",
				"From: James Smith",
		"http://www.ustream.tv/channel/snoop-dogg-live"));
		dataSource.addItem(new DummyItem("rss",
				"Heiko Smith",
				"6 hours ago",
		"accepted your friend request."));
		dataSource.addItem(new DummyItem("rss",
				"Anna Smith",
				"20 hours ago",
		"knows a secret about you."));
		dataSource.addItem(new DummyItem("rss",
				"Greg Smith",
				"on Thursday",
		"wrote on your Wall."));
		dataSource.addItem(new DummyItem("rss",
				"John Smith",
				"9:28am",
		"commented on your status"));
		dataSource.addItem(new DummyItem("rss",
				"Amy Smith",
				"7:48pm",
		"made a comment about your photo."));
		dataSource.addItem(new DummyItem("weather",
				"Weather",
				"4/10/09",
				"Friday: Rain likely. Low 42F. S winds shifting to ENE at 10 to 15 mph. Chance of rain 80%. Rainfall near a quarter of an inch.\n\n"+

				"Saturday: Rain showers early with overcast skies later in the day. High around 45F. Winds NNE at 10 to 20 mph. Chance of rain 70%.\n\n"+

		"Sunday: Partly cloudy. Highs in the mid 40s and lows in the low 30s."));
		dataSource.addItem(new DummyItem("todo",
				"Agenda",
				"4/10/09",
				"- talk to adam"+
				"- finish pset\n"+
				"- read papers\n"+
				"- tech support emails\n"+
				"- fill out reimbursement forms\n"+
		"- taxes"));

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
				bubble = new Bubble(item, x, y, r, bubble_orange, font_title,
						font_body, fav_rss, button_pop, button_star,
						button_open, button_trash);
			} else if (item.getType() == "twitter") {
				bubble = new Bubble(item, x, y, r, bubble_twitterblue, font_title,
						font_body, fav_twitter, button_pop, button_star,
						button_open, button_trash);
			} else if (item.getType() == "nyt") {
				bubble = new Bubble(item, x, y, r, bubble_pink, font_title,
						font_body, fav_nyt, button_pop, button_star,
						button_open, button_trash);
			} else if (item.getType() == "todo") {
				bubble = new Bubble(item, x, y, r, bubble_seafoam, font_title,
						font_body, fav_todo, button_pop, button_star,
						button_open, button_trash);
			} else if (item.getType() == "weather") {
				bubble = new Bubble(item, x, y, r, bubble_yellow, font_title,
						font_body, fav_weather, button_pop, button_star,
						button_open, button_trash);
			} else if (item.getType() == "mail") {
				bubble = new Bubble(item, x, y, r, bubble_red, font_title,
						font_body, fav_mail, button_pop, button_star,
						button_open, button_trash);
			} else {
				bubble = new Bubble(item, x, y, r, bubble_red, font_title,
						font_body, fav_mail, button_pop, button_star,
						button_open, button_trash);
			}

			this.elements.add(bubble);
			this.physicsEngine.add(bubble);
			this.controller.add(bubble);
			this.bubbles.add(bubble);
			//System.out.println(bubbles);
		}

		this.physicsEngine.init();
		this.animations.add(physicsEngine);
	}

	@Override
	public void draw() {
		background(0);

		update(mouseX, mouseY);


		for (Drawable element : elements) {
			element.draw(this.g);
		}

		Iterator<Animation> iterator = animations.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().step())
				iterator.remove();
		}


		image(rewire,20,40);
		shape(toggle_red,20,600,60,60);
		shape(toggle_twitterblue,100,600,60,60);
		shape(toggle_orange,180,600,60,60);
		shape(toggle_seafoam,260,600,60,60);
		shape(toggle_yellow,340,600,60,60);

	}

	public void add(Animation animation) {
		this.animations.add(animation);
	}

	public void add(Bubble bubble) {
		this.elements.add(bubble);
	}

	public void remove(Bubble bubble) {
		this.elements.remove(bubble);
	}

	public void update(int x, int y) {
		if (mousePressed) {
			this.controller.doClick(x, y);
		}
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
