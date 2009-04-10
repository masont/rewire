package edu.mit.rewire.controller;

import java.util.LinkedList;
import java.util.List;

import edu.mit.rewire.view.Bubble;
import edu.mit.rewire.view.MouseAware;
import edu.mit.rewire.view.ProcessingView;
import edu.mit.rewire.view.animation.ExpandBubbleAnimation;
import edu.mit.rewire.view.animation.FixedAnimation;
import edu.mit.rewire.view.animation.PopBubbleAnimation;
import edu.mit.rewire.view.animation.SequentialAnimation;

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
        for (MouseAware component : components) {
            if (component.hits(x, y)) {
                component.dispatchClick(this, x, y);
            }
        }
    }
    
    public void doMove(int x, int y) {
        for (MouseAware component : components) {
            if (component.hits(x, y)) {
                component.dispatchOver(this, x, y);
            }
        }
    }

    public void handleBubbleClick(Bubble bubble) {
        SequentialAnimation animation = new SequentialAnimation();
        animation.add(new ExpandBubbleAnimation(bubble, 100, width, height));
        animation.add(new FixedAnimation(bubble));
        this.view.add(animation);
    }
    
    public void handleMarkReadClick(Bubble bubble) {
        this.view.add(new PopBubbleAnimation(bubble, view));
        this.view.remove(bubble);
//        this.remove(bubble);
    }
    
    public void handleStarClick(Bubble bubble) {
        this.view.add(new PopBubbleAnimation(bubble, view));
        this.view.remove(bubble);
    }
    
    public void handleOpenClick(Bubble bubble) {
        this.view.add(new PopBubbleAnimation(bubble, view));
        this.view.remove(bubble);
    }
    
    public void handleTrashClick(Bubble bubble) {
        this.view.add(new PopBubbleAnimation(bubble, view));
        this.view.remove(bubble);
    }

    public void remove(MouseAware component) {
        this.components.remove(component);        
    }

}