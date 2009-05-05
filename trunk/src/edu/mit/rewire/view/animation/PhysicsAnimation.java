package edu.mit.rewire.view.animation;

import java.util.ArrayList;
import java.util.List;

import edu.mit.rewire.view.Bubble;
import edu.mit.rewire.view.Bubble.State;

import processing.core.PVector;

public class PhysicsAnimation implements Animation {

	List<Bubble> bubbles;

	private final float width, height;

	private float repulsionConstant = 800f;
	private float wallRepulsionConstant = 600f;

	private final float frictionConstant = 0.9f;
	
	private final float springConstant = 0.005f;

	public PhysicsAnimation(float width, float height) {
		this.bubbles = new ArrayList<Bubble>();
		this.width = width;
		this.height = height;
	}

	public PhysicsAnimation(float width, float height, List<Bubble> bubbles) {
		this(height, width);
		this.bubbles.addAll(bubbles);
	}

	public void add(Bubble p) {
		this.bubbles.add(p);
	}

	public void init() {

	}

	public boolean step() {
		for (int i = 0; i < bubbles.size(); i++) {
			for (int j = i + 1; j < bubbles.size(); j++) {
				handleRepulsion(bubbles.get(i), bubbles.get(j));
				if (testCollision(bubbles.get(i), bubbles.get(j))) {
					handleCollision(bubbles.get(i), bubbles.get(j));
				}
			}
		}

		for (Bubble p : bubbles) {
			p.setX(p.getX() + p.getDx());
			p.setY(p.getY() + p.getDy());
			p.setDx(p.getDx() * frictionConstant);
			p.setDy(p.getDy() * frictionConstant);
			handleWalls(p);
			handleWallRepulsion(p);
		}
		
		return false;
	}

	protected void handleWalls(Bubble p) {
		float x = p.getX();
		float y = p.getY();
		float r = p.getR();

		if (x + r > width) {
			p.setX(width - r);
			p.setDx(p.getDx() * frictionConstant);
		} else if (x - r < 0) {
			p.setX(r);
			p.setDx(p.getDx() * frictionConstant);
		}

		if (y + r > height) {
			p.setY(height - r);
			p.setDy(p.getDy() * frictionConstant);
		} else if (y - r < 0) {
			p.setY(r);
			p.setDy(p.getDy() * frictionConstant);
		}
	}

	protected void handleWallRepulsion(Bubble b1) {
		PVector north = new PVector();
		PVector south = new PVector();
		PVector east = new PVector();
		PVector west = new PVector();
		
		north.y = b1.getY();
		south.y = height - b1.getY();
		east.x = width - b1.getX();
		west.x = b1.getX();
		
		float forceN = (float) (wallRepulsionConstant / Math.pow(north.mag(), 2));
		float forceS = (float) (wallRepulsionConstant / Math.pow(south.mag(), 2));
		float forceE = (float) (wallRepulsionConstant / Math.pow(east.mag(), 2));
		float forceW = (float) (wallRepulsionConstant / Math.pow(west.mag(), 2));
		
		north.normalize();
		south.normalize();
		east.normalize();
		west.normalize();
		
		north.mult(forceN);
		south.mult(forceS);
		east.mult(forceE);
		west.mult(forceW);
		
		if (b1.getState() == State.MEDIUM || b1.getState() == State.SMALL) {
			b1.setDx(b1.getDx() - east.x + (2 * west.x));
			b1.setDy(b1.getDy() + (2 * north.y) - (2 * south.y));
		}
		
	}
	
	protected void handleRepulsion(Bubble b1, Bubble b2) {
		
		PVector offset = new PVector();
		offset.x = b2.getX() - b1.getX();
		offset.y = b2.getY() - b1.getY();

		float force = (float) (repulsionConstant / Math.pow(offset.mag(), 2));
		offset.normalize();
		offset.mult(force);

		if (b1.getState() == State.MEDIUM || b1.getState() == State.SMALL) {
			b1.setDx(b1.getDx() - offset.x);
			b1.setDy(b1.getDy() - offset.y);
		}
		if (b2.getState() == State.MEDIUM || b2.getState() == State.SMALL) {
			b2.setDx(b2.getDx() + offset.x);
			b2.setDy(b2.getDy() + offset.y);
		}
	}
	
	protected boolean testCollision(Bubble b1, Bubble b2) {
		PVector collisionVector = new PVector();
		float minDist = b1.getR() + b2.getR();
		
		collisionVector.x = b1.getX() - b2.getX();
		collisionVector.y = b1.getY() - b2.getY();
		
		if (collisionVector.mag() < minDist) {
			return true;
		}		
		
		return false;
	}
	
	protected void handleCollision(Bubble b1, Bubble b2) {
		if ((b1.getState() != State.EXPANDED && b1.getState() != State.EXPANDING) &&
				(b2.getState() != State.EXPANDED && b2.getState() != State.EXPANDING)) {
			float xDist = b2.getX() - b1.getX();
			float yDist = b2.getY() - b1.getY();
			float angle = (float) Math.atan2(yDist, xDist);
			float minDist = b1.getR() + b2.getR();
			float targetX = (float) (b1.getX() + Math.cos(angle) * minDist);
			float targetY = (float) (b1.getY() + Math.sin(angle) * minDist);
			float ax = (targetX - b2.getX()) * springConstant;
			float ay = (targetY - b2.getY()) * springConstant;
			b1.setDx(b1.getDx() - ax);
			b1.setDy(b1.getDy() - ay);
			b2.setDx(b2.getDx() + ax);
			b2.setDy(b2.getDy() + ay);
		}
	}
}
