package edu.mit.rewire.model;

public class DummyItem implements Item {
	
	private String type;
	private String body;
	private String title;
	private String header;
	

    public DummyItem(String type, String title, String header, String body) {
		this.type = type;
		this.body = body;
		this.title = title;
		this.header = header;
	}

	//@Override
    public String getType() {
        return type;
    }
    
    //@Override
    public String getBody() {
        return body;
    }

    //@Override
    public String getTitle() {
        return title;
    }
    
    //@Override
    public String getHeader() {
        return header;
    }

}
