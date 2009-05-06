package edu.mit.rewire.view.animation;

import edu.mit.rewire.view.ChristopherWalken;

public class ChristopherWalkenAnimation implements Animation {

	private ChristopherWalken walken;
	
	public ChristopherWalkenAnimation(ChristopherWalken walken) {
		this.walken = walken;
	}
	
	public boolean step() {
		if (walken.getX() < -405) {
			System.out.println("walkenTrue");
			return true;
		} else {
			walken.setX(walken.getX() - 5);
			System.out.println("walkenFalse");
			return false;	
		}
		
	}

}
