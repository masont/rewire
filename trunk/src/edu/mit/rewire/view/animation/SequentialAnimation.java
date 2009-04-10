package edu.mit.rewire.view.animation;

import java.util.LinkedList;
import java.util.List;

public class SequentialAnimation implements Animation {
	
	private final List<Animation> animations;
	
	public SequentialAnimation() {
		this(new LinkedList<Animation>());
	}
	
	public SequentialAnimation(List<Animation> animations) {
		this.animations = new LinkedList<Animation>(animations);
	}
	
	public void add(Animation animation) {
		this.animations.add(animation);
	}

	//@Override
	public boolean step() {
		if (animations.isEmpty()) return true;
		if (animations.get(0).step()) animations.remove(0);
		return false;
	}

}
