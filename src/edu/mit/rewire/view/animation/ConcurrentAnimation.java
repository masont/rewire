package edu.mit.rewire.view.animation;

import java.util.LinkedList;
import java.util.List;

public class ConcurrentAnimation implements Animation {
    
    private final List<Animation> animations;
    
    public ConcurrentAnimation() {
        this(new LinkedList<Animation>());
    }
    
    public ConcurrentAnimation(List<Animation> animations) {
        this.animations = new LinkedList<Animation>(animations);
    }
    
    public void add(Animation animation) {
        this.animations.add(animation);
    }

    public boolean step() {
        // TODO Auto-generated method stub
        return false;
    }

}
