package edu.mit.rewire.controller;

import java.util.LinkedList;
import java.util.List;

import edu.mit.rewire.view.Bubble;
import edu.mit.rewire.view.Clickable;
import edu.mit.rewire.view.ProcessingView;
import edu.mit.rewire.view.animation.ExpandBubbleAnimation;

public class Controller {
    
    private final List<Clickable> clickables;
    
    private final ProcessingView view;
    
    public Controller(ProcessingView view) {
        this.clickables = new LinkedList<Clickable>();
        this.view = view;
    }
    
    public void add(Clickable clickable) {
        this.clickables.add(clickable);
    }
    
    public void doClick(int x, int y) {
        for (Clickable clickable : clickables) {
            if (clickable.isClicked(x, y)) {
                clickable.dispatchClick(this);
            }
        }
    }

    public void handleBubbleClick(Bubble bubble) {
        this.view.add(new ExpandBubbleAnimation(bubble, 100));
    }

}
