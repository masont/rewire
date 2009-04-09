package edu.mit.rewire.view;

import edu.mit.rewire.controller.Controller;

public interface Clickable {
    
    boolean isClicked(int x, int y);
    
    void dispatchClick(Controller controller);
    
    int getZ();

}
