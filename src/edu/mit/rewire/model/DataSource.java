package edu.mit.rewire.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class DataSource {
	
	private List<Item> items;

	@SuppressWarnings("unchecked")
	public DataSource() {
		this.items = new ArrayList<Item>();
		// WEATHER
		this.addItem(new WeatherItem());
		// TWITTER 
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
		// EMAIL
		Inbox inbox = new Inbox();
		try {
			for (int i=0; i<5; i++) {
				this.addItem(new EmailItem(inbox.getEmail(i)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// RSS
        try {
        	SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(new URL("http://feeds.boingboing.net/boingboing/iBag")));
			List<SyndEntry> entries = feed.getEntries();

			for (int i=0; i<5; i++) {
				this.addItem(new RSSItem(entries.get(i)));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FeedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// AGENDA
		this.addItem(new AgendaItem());
		
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
