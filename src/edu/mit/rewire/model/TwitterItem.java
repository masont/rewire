package edu.mit.rewire.model;

import twitter4j.Status;

public class TwitterItem implements Item {
	
	private Status status;
	
	private String body;
	private String title;
	private String header;
	
    public TwitterItem(Status status) {
    	this.status = status;
    	
		this.body = status.getText();
		this.title = status.getUser().getName();
		this.header = status.getCreatedAt().toString();
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
		return "twitter";
	}

	public Status getStatus() {
		return status;
	}
	
}
