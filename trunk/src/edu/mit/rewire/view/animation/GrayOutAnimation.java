package edu.mit.rewire.view.animation;

import edu.mit.rewire.view.BackgroundOverlay;

public class GrayOutAnimation implements Animation {

	private BackgroundOverlay bg;
	
	public GrayOutAnimation(BackgroundOverlay bg, boolean direction) {
		this.bg = bg;
	}
	
	@Override
	public boolean step() {
		while (this.bg.getAlpha() < 90) {
			this.bg.setAlpha(this.bg.getAlpha() + 10);
			System.out.println(this.bg.getAlpha());
			return false;
		}
		return true;
	}

}
