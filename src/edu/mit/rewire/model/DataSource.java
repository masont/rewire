package edu.mit.rewire.model;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class DataSource {
	
	private List<Item> items;

	public DataSource() {
		this.items = new ArrayList<Item>();
		Twitter twitter = new Twitter("claytonrules","superawesome");
		List<Status> timeline = new ArrayList<Status>();
		try {
			timeline = twitter.getFriendsTimeline();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i=0; i<5; i++) {
			Status status = timeline.get(i);
			this.addItem(new TwitterItem(status));
		}
		this.addItem(new WeatherItem());

//		try {
//			SAXBuilder parser = new SAXBuilder();
//			Document doc = parser.build("http://www.google.com/calendar/m");
//		} catch (JDOMException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		this.addItem(new DummyItem("mail",
                "Subject: Next Friday: Computation Lecture Series: Shane Burger @ 12:30 in 3-133",
                "From: Daniela Smith",
                "All are invited to this talk in the Computation Group Lecture Series,\n"+
                "Department of Architecture, MIT:\n\n"+

                "Embedded Intelligence: Design-Driven Computation*\n\n"+

                "Friday, April 17th *\n" +
                "12:30 - 2:00pm\n" +
        "Room 3-133*\n"));
        this.addItem(new DummyItem("mail",
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
        this.addItem(new DummyItem("mail",
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
        this.addItem(new DummyItem("mail",
                "Subject: [Listit] How to synchronize 2 computers",
                "From: Eleanor Smith",
                "Every time I try to synchronize it says 'sync success  yet none of my "+
                "notes which I typed in previously at my other computer appear.  Whassup?\n\n"+

                "I love this product, but haven't quite got the hang of it yet.  Help.\n\n"+

        "Eleanor Smith"));
        this.addItem(new DummyItem("mail",
                "Subject: !!!!!",
                "From: James Smith",
        "http://www.ustream.tv/channel/snoop-dogg-live"));
        this.addItem(new DummyItem("mail",
                "Subject: !!!!!",
                "From: James Smith",
        "http://www.ustream.tv/channel/snoop-dogg-live"));
        this.addItem(new DummyItem("rss",
                "Heiko Smith",
                "6 hours ago",
        "accepted your friend request."));
        this.addItem(new DummyItem("rss",
                "Anna Smith",
                "20 hours ago",
        "knows a secret about you."));
        this.addItem(new DummyItem("rss",
                "Greg Smith",
                "on Thursday",
        "wrote on your Wall."));
        this.addItem(new DummyItem("rss",
                "John Smith",
                "9:28am",
        "commented on your status"));
        this.addItem(new DummyItem("rss",
                "Amy Smith",
                "7:48pm",
        "made a comment about your photo."));
        this.addItem(new DummyItem("weather",
                "Weather",
                "4/10/09",
                "Friday: Rain likely. Low 42F. S winds shifting to ENE at 10 to 15 mph. Chance of rain 80%. Rainfall near a quarter of an inch.\n\n"+

                "Saturday: Rain showers early with overcast skies later in the day. High around 45F. Winds NNE at 10 to 20 mph. Chance of rain 70%.\n\n"+

        "Sunday: Partly cloudy. Highs in the mid 40s and lows in the low 30s."));
        this.addItem(new DummyItem("todo",
                "Agenda",
                "4/10/09",
                "- talk to adam"+
                "- finish pset\n"+
                "- read papers\n"+
                "- tech support emails\n"+
                "- fill out reimbursement forms\n"+
        "- taxes"));
		
	}
	
	public DataSource(List<Item> items) {
		this.items = items;
	}

	public List<Item> getItems() {
		return items;
	}
	
	public void addItem(Item item) {
		this.items.add(item);
	}

}
