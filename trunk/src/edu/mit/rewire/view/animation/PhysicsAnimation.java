package edu.mit.rewire.view.animation;

import java.util.ArrayList;
import java.util.List;

import edu.mit.rewire.view.Bubble;
import edu.mit.rewire.view.Bubble.State;

import processing.core.PVector;

public class PhysicsAnimation implements Animation {

	List<Bubble> bubbles;

	private final float width, height;

	private final float repulsionConstant = 500f;

	private final float frictionConstant = 0.95f;
	
	private final float springConstant = 0.01f;

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
	
//	protected void handleCollision(Bubble b1, Bubble b2) {
//		PVector unitNormal = new PVector();
//		PVector unitTangent = new PVector();
//		PVector initialVelocityb1 = new PVector();
//		PVector initialVelocityb2 = new PVector();
//		PVector newNormalVelocityb1 = new PVector();
//		PVector newNormalVelocityb2 = new PVector();
//		PVector newTangentialVelocityb1 = new PVector();
//		PVector newTangentialVelocityb2 = new PVector();
//		
//		PVector finalVelocityb1 = new PVector();
//		PVector finalVelocityb2 = new PVector();
//		
//		float v1n, v1t, v2n, v2t;
//		float v1np, v1tp, v2np, v2tp;
//		float massb1 = (float) (Math.PI * b1.getR() * b1.getR());
//		float massb2 = (float) (Math.PI * b2.getR() * b2.getR());
//		
//		
//		unitNormal.set((b1.getX() - b2.getX()), (b1.getY() - b2.getY()), 0);
//		unitNormal.normalize();
//		
//		unitTangent.set(-unitNormal.y, unitNormal.x, 0);
//		
//		initialVelocityb1.set(b1.getDx(), b1.getDy(), 0);
//		initialVelocityb2.set(b2.getDx(), b2.getDy(), 0);
//		
//		v1n = unitNormal.dot(initialVelocityb1);
//		v1t = unitTangent.dot(initialVelocityb1);
//		v2n = unitNormal.dot(initialVelocityb2);
//		v2t = unitTangent.dot(initialVelocityb2);
//		
//		v1tp = v1t;
//		v2tp = v2t;
//		
//		v1np = (v1n * (massb1 - massb2) + (2 * massb2 * v2n)) / (massb1 + massb2);
//		v2np = (v2n * (massb2 - massb1) + (2 * massb1 * v1n)) / (massb1 + massb2);
//		
//		newNormalVelocityb1 = PVector.mult(unitNormal, v1np);
//		newTangentialVelocityb1 = PVector.mult(unitTangent, v1tp);
//		newNormalVelocityb2 = PVector.mult(unitNormal, v2np);
//		newTangentialVelocityb2 = PVector.mult(unitTangent, v2tp);
//		
//		finalVelocityb1 = PVector.add(newNormalVelocityb1, newTangentialVelocityb1);
//		finalVelocityb2 = PVector.add(newNormalVelocityb2, newTangentialVelocityb2);
//		
//		
//		b1.setDx(finalVelocityb1.x);
//		b1.setDy(finalVelocityb1.y);
//		
//		b2.setDx(finalVelocityb2.x);
//		b2.setDy(finalVelocityb2.y);
//	}
	
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
