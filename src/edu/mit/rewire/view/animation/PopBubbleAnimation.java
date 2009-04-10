package edu.mit.rewire.view.animation;

import processing.core.PImage;
import edu.mit.rewire.view.Bubble;
import edu.mit.rewire.view.ProcessingView;

public class PopBubbleAnimation implements Animation {
    
    private final Bubble bubble;
    private final ProcessingView view;
    
    private int frames = 4;
    
    private static PImage popImage;

	public PopBubbleAnimation(Bubble bubble, ProcessingView view) {
        this.bubble = bubble;
        this.view = view;
        if (popImage == null) {
            popImage = view.loadImage("pop.png");
        }
    }

    //@Override
	public boolean step() {
        if (frames == 0) return true;
		view.image(popImage, bubble.getX() - bubble.getR() + 100, bubble.getY() - bubble.getR() + 100);
		frames--;
		return false;
	}

}
