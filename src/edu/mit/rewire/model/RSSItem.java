package edu.mit.rewire.model;

import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;

public class RSSItem implements Item {
	
	private SyndEntry entry;
	private String title;
	private String header;
	private String body;

	public RSSItem(SyndEntry entry) {
		this.entry = entry;
		
		this.title = entry.getTitle();
		String date = entry.getPublishedDate().toString(); 
		this.header = entry.getAuthor() + " "  + date.substring(0,date.length() - 12);
		this.body = ((SyndContentImpl) entry.getContents().get(0)).getValue()
		.replaceAll("\\<br\\>","\n").replaceAll("\\<.*?\\>", "");
	}
	
	public String getBody() {
		return body;
	}

	public String getHeader() {
		return header;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return "rss";
	}

	public SyndEntry getEntry() {
		return entry;
	}

}
