package edu.mit.rewire.view;

import edu.mit.rewire.controller.Controller;

public interface MouseAware {
    
    boolean hits(int x, int y);
    
    void dispatchClick(Controller controller);
    
    void dispatchOver(Controller controller);

    int getZ();

}
