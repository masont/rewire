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
		this.addItem(new WeatherItem());
		Twitter twitter = new Twitter("claytonrules","superawesome");
		List<Status> timeline = new ArrayList<Status>();
		try {
			timeline = twitter.getFriendsTimeline();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		for (int i=0; i<5; i++) {
			Status status = timeline.get(i);
			this.addItem(new TwitterItem(status));
		}
		
		Inbox inbox = new Inbox();

		try {
			for (int i=0; i<5; i++) {
				this.addItem(inbox.getEmail(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

//		try {
//			SAXBuilder parser = new SAXBuilder();
//			Document doc = parser.build("http://www.google.com/calendar/m");
//		} catch (JDOMException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
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
