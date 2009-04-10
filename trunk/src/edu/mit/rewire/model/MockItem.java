package edu.mit.rewire.model;

public class MockItem implements Item {

    //@Override
    public String getType() {
        return "mock";
    }
    
    //@Override
    public String getBody() {
        return "This is the body of the mock item.  Here is a bunch of text.";
    }

    //@Override
    public String getTitle() {
        return "Mock Item";
    }

    //@Override
    public String getHeader() {
        return "nowish";
    }

}
