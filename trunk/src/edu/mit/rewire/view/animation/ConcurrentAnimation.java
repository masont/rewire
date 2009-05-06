package edu.mit.rewire.view.animation;

import java.util.Iterator;
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
        Iterator<Animation> iterator = animations.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().step())
                iterator.remove();
        }
        return animations.isEmpty();
    }

}
