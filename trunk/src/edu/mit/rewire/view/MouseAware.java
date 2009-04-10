package edu.mit.rewire.view;

import edu.mit.rewire.controller.Controller;

public interface MouseAware {
    
    boolean hits(int x, int y);
    
    void dispatchClick(Controller controller, int x, int y);
    
    void dispatchOver(Controller controller, int x, int y);

    int getZ();

}
