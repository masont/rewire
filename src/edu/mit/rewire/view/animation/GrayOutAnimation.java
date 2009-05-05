package edu.mit.rewire.view.animation;

import edu.mit.rewire.view.BackgroundOverlay;

public class GrayOutAnimation implements Animation {

	private BackgroundOverlay bg;
	private boolean direction;
	
	public GrayOutAnimation(BackgroundOverlay bg, boolean direction) {
		this.bg = bg;
		this.direction = direction;
	}
	
	//@Override
	public boolean step() {
		if (direction) {
			while (this.bg.getAlpha() < 90) {
				this.bg.setAlpha(this.bg.getAlpha() + 10);
				return false;
			}
			return true;
		} else {
			while(this.bg.getAlpha() > 0) {
				this.bg.setAlpha(this.bg.getAlpha() - 10);
				return false;
			}
			return true;
		}
	}
}
