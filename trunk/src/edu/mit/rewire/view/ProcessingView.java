package edu.mit.rewire.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PImage;
import processing.core.PFont;
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

	private float x;
	private float y;
	
	private PImage rewire;

	private PShape bluebubble;
	private PShape lavenderbubble;
	private PShape pinkbubble;
	private PShape seafoambubble;
	private PShape yellowbubble;
	private PShape orangebubble;

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

		rewire = loadImage("logo.png");

		bluebubble = loadShape("blue.svg");
		lavenderbubble = loadShape("lavender.svg");
		pinkbubble = loadShape("pink.svg");
		seafoambubble = loadShape("seafoam.svg");
		yellowbubble = loadShape("yellow.svg");
		orangebubble = loadShape("orange.svg");

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
		dataSource.addItem(new DummyItem("orange",
				"Subject: Next Friday: Computation Lecture Series: Shane Burger @ 12:30 in 3-133",
				"From: Daniela Smith",
				"All are invited to this talk in the Computation Group Lecture Series,\n"+
				"Department of Architecture, MIT:\n\n"+

				"Embedded Intelligence: Design-Driven Computation*\n\n"+

				"Friday, April 17th *\n" +
				"12:30 - 2:00pm\n" +
		"Room 3-133*\n"));
		dataSource.addItem(new DummyItem("orange",
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
		dataSource.addItem(new DummyItem("orange",
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
		dataSource.addItem(new DummyItem("orange",
				"Subject: [Listit] How to synchronize 2 computers",
				"From: Eleanor Smith",
				"Every time I try to synchronize it says 'sync success  yet none of my "+
				"notes which I typed in previously at my other computer appear.  Whassup?\n\n"+

				"I love this product, but haven't quite got the hang of it yet.  Help.\n\n"+

		"Eleanor Smith"));
		dataSource.addItem(new DummyItem("orange",
				"Subject: !!!!!",
				"From: James Smith",
		"http://www.ustream.tv/channel/snoop-dogg-live"));
		dataSource.addItem(new DummyItem("orange",
				"Subject: !!!!!",
				"From: James Smith",
		"http://www.ustream.tv/channel/snoop-dogg-live"));
		dataSource.addItem(new DummyItem("blue",
				"Heiko Smith",
				"6 hours ago",
		"accepted your friend request."));
		dataSource.addItem(new DummyItem("blue",
				"Anna Smith",
				"20 hours ago",
		"knows a secret about you."));
		dataSource.addItem(new DummyItem("blue",
				"Greg Smith",
				"on Thursday",
		"wrote on your Wall."));
		dataSource.addItem(new DummyItem("blue",
				"John Smith",
				"9:28am",
		"commented on your status"));
		dataSource.addItem(new DummyItem("blue",
				"Amy Smith",
				"7:48pm",
		"made a comment about your photo."));
		dataSource.addItem(new DummyItem("lavender",
				"fimoculous",
				"6:36 PM Apr 7th from web",
		"Do social media experts have any purpose whatsoever? http://tinyurl.com/ct3e96"));
		dataSource.addItem(new DummyItem("lavender",
				"evan",
				"3:06 PM Mar 30th from web",
		"Mo' tweets, mo' problems."));
		dataSource.addItem(new DummyItem("lavender",
				"nick",
				"9:27 PM Mar 24th from web",
		"It's been a fun couple of years, Twitter, but when are we gonna start a second thread?"));
		dataSource.addItem(new DummyItem("lavender",
				"JSCarroll",
				"5:13 AM Mar 24th from web",
		"Twitter teaches brevity, but being brief doesn't necessarily mean an observer is either insightful or interesting."));
		dataSource.addItem(new DummyItem("lavender",
				"livejamie",
				"5:02 PM Mar 18th from Ping.fm",
		"The only reason people pick the White Album or Pet Sounds as best album of all time is because it's hard to narrow it to one Jock Jams CD."));
		dataSource.addItem(new DummyItem("yellow",
				"Weather",
				"4/10/09",
				"Friday: Rain likely. Low 42F. S winds shifting to ENE at 10 to 15 mph. Chance of rain 80%. Rainfall near a quarter of an inch.\n\n"+

				"Saturday: Rain showers early with overcast skies later in the day. High around 45F. Winds NNE at 10 to 20 mph. Chance of rain 70%.\n\n"+

		"Sunday: Partly cloudy. Highs in the mid 40s and lows in the low 30s."));
		dataSource.addItem(new DummyItem("seafoam",
				"Agenda",
				"4/10/09",
				"- talk to adam"+
				"- finish pset\n"+
				"- read papers\n"+
				"- tech support emails\n"+
				"- fill out reimbursement forms\n"+
		"- taxes"));

		List<Item> items = dataSource.getItems();

		for (Item item : items) {

			float r = 75;
			float x = (float) ((Math.random() * (screen.width - r)) + r);
			float y = (float) ((Math.random() * (screen.height - r)) + r);

			// Choose x and y in such a way so that no overlap occurs

			// Set checker to be equal to the number of existing bubbles on the screen
			int checker = bubbles.size();

			System.out.println("external " + checker);
			
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
			this.bubbles.add(bubble);
			System.out.println(bubbles);
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
