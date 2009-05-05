package edu.mit.rewire.view;

public interface MouseAware {
    
    boolean hits(int x, int y);
    
    void dispatchDown(ProcessingView view, int x, int y);
    
    void dispatchUp(ProcessingView view, int x, int y);
    
    void dispatchIn(ProcessingView view, int x, int y);
    
    void dispatchOut(ProcessingView view, int x, int y);
    
    void dispatchDrag(ProcessingView view, int x, int y);

}
