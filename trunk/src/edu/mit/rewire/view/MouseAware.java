package edu.mit.rewire.view;

import edu.mit.rewire.controller.Controller;

public interface MouseAware {
    
    boolean hits(int x, int y);
    
    void dispatchDown(Controller controller, int x, int y);
    
    void dispatchUp(Controller controller, int x, int y);
    
    void dispatchIn(Controller controller, int x, int y);
    
    void dispatchDrag(Controller controller, int x, int y);

}
