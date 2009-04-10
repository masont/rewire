package edu.mit.rewire.controller;

import java.util.LinkedList;
import java.util.List;

import edu.mit.rewire.view.Bubble;
import edu.mit.rewire.view.MouseAware;
import edu.mit.rewire.view.ProcessingView;
import edu.mit.rewire.view.animation.ExpandBubbleAnimation;

public class Controller {
    
    private final List<MouseAware> components;
    
    private final ProcessingView view;
    
    private final float width, height;
    
    public Controller(ProcessingView view, float width, float height) {
        this.components = new LinkedList<MouseAware>();
        this.view = view;
        this.width = width;
        this.height = height;
    }
    
    public void add(MouseAware clickable) {
        this.components.add(clickable);
    }
    
    public void doClick(int x, int y) {
        for (MouseAware clickable : components) {
            if (clickable.hits(x, y)) {
                clickable.dispatchClick(this);
            }
        }
    }
    
    public void doMove(int x, int y) {
        for (MouseAware clickable : components) {
            if (clickable.hits(x, y)) {
                clickable.dispatchOver(this);
            }
        }
    }

    public void handleBubbleClick(Bubble bubble) {
        this.view.add(new ExpandBubbleAnimation(bubble, 100, width, height));
    }

}