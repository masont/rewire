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
