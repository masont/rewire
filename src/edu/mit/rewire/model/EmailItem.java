package edu.mit.rewire.model;


public class EmailItem implements Item {
	
	private String body;
	private String header;
	private String title;
	
	public EmailItem(String title, String header, String body) {
		this.title = title;
		this.header = header;
		this.body = body;
		
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
		return "email";
	}

}
