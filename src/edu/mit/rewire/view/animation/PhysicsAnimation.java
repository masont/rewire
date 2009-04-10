package edu.mit.rewire.view.animation;

import java.util.ArrayList;
import java.util.List;

import edu.mit.rewire.model.Item;
import edu.mit.rewire.model.MockItem;
import edu.mit.rewire.view.Bubble;
import edu.mit.rewire.view.Particle;

import processing.core.PVector;

public class PhysicsAnimation implements Animation {

	List<Particle> particles;

	private final float width, height;

	private final float springConstant = 0.05f;

	private final float repulsionConstant = 200f;

	private final float frictionConstant = -0.9f;

	public PhysicsAnimation(float width, float height) {
		this.particles = new ArrayList<Particle>();
		this.width = width;
		this.height = height;
	}

	public PhysicsAnimation(float width, float height, List<Particle> particles) {
		this(height, width);
		this.particles.addAll(particles);
	}

	public void add(Particle p) {
		this.particles.add(p);
	}

	public void init() {

	}

	public boolean step() {
		for (int i = 0; i < particles.size(); i++) {
			for (int j = i + 1; j < particles.size(); j++) {
				handleRepulsion(particles.get(i), particles.get(j));
//				handleCollision(particles.get(i), particles.get(j));
			}
		}

		for (Particle p : particles) {
			p.setX(p.getX() + p.getDx());
			p.setY(p.getY() + p.getDy());
			handleWalls(p);
			p.setChanged(true);
		}
		
		return false;
	}

	protected void handleWalls(Particle p) {
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

	protected void handleRepulsion(Particle p1, Particle p2) {
		PVector offset = new PVector();
		offset.x = p2.getX() - p1.getX();
		offset.y = p2.getY() - p1.getY();

		float force = (float) (repulsionConstant / Math.pow(offset.mag(), 2));
		offset.normalize();
		offset.mult(force);

		p1.setDx(p1.getDx() - offset.x);
		p1.setDy(p1.getDy() - offset.y);
		p2.setDx(p2.getDx() + offset.x);
		p2.setDy(p2.getDy() + offset.y);
	}
	
	protected void handleInitialCollision(Particle p1, Particle p2) {
		
	}
	
	protected void handleCollision(Particle p1, Particle p2) {
		// get distances between the balls components
		PVector bVect = new PVector();
		bVect.x = p2.getX() - p1.getX();
		bVect.y = p2.getY() - p1.getY();
		float minDist = p1.getR() + p2.getR();
		
		// calculate magnitude of the vector separating the balls
		float bVectMagnitude = (float) Math.sqrt((bVect.x * bVect.x) + (bVect.y * bVect.y));
		if (bVectMagnitude < minDist) {		
			
			// get angle of bVect
			float theta = (float) Math.atan2(bVect.y, bVect.x);
			
			// precalculate trig values
			float sine = (float) Math.sin(theta);
			float cosine = (float) Math.cos(theta);
			
			// bTemps will hold rotated ball positions. 
			Item item = new MockItem();
			Bubble bTemp1 = new Bubble(item, 0, 0, 0, null);
			Bubble bTemp2 = new Bubble(item, 0, 0, 0, null);
			
			/* Bubble 2's position is relative to Bubble 1's
			 * so you can use the vector between them (bVect) as the
			 * reference point in the rotation expressions.
			 * since bubble 2 will rotate around bubble 1 */
			bTemp2.setX(cosine * bVect.x + sine * bVect.y);
			bTemp2.setY(cosine * bVect.y - sine * bVect.x);
			
			// rotate Temporary velocities
		 	
			PVector vTemp1 = new PVector();
			PVector vTemp2 = new PVector();
			
			vTemp1.x = cosine * p1.getDx() + sine * p1.getDy();
			vTemp1.y = cosine * p1.getDy() + sine * p1.getDx();
			vTemp2.x = cosine * p2.getDx() + sine * p2.getDy();
			vTemp2.y = cosine * p2.getDy() - sine * p2.getDx();
		
			/* Now that velocities are rotated, you can use 1D
		 	 * conservation of momentum equations to calculate
		 	 * the final velocity along the x-axis. */
			
			PVector vFinal1 = new PVector();
			PVector vFinal2 = new PVector();
			
			vFinal1.x = (float) (((.1 * (p1.getR() - p2.getR()) * vTemp1.x) + (.2 * p2.getR() * vTemp2.x)) / (.1 * (p1.getR() + p2.getR())));
			vFinal1.y = vTemp1.y;
			vFinal2.x = (float) (((.1 * (p2.getR() - p1.getR()) * vTemp2.x) + (.2 * p1.getR() * vTemp1.x)) / (.1 * (p1.getR() + p2.getR())));
			vFinal2.y = vTemp2.y; 
			
			// hack to avoid clumping - CAUSES PROBLEMS
//			bTemp1.setX(bTemp1.getX() + vFinal1.x);
//			bTemp2.setX(bTemp2.getX() + vFinal2.x);
//			float targetX = bTemp1.getX() + cosine * minDist;
//			float targetY = bTemp1.getY() + sine * minDist;
//			float ax = (targetX - bTemp2.getX()) * springConstant;
//			float ay = (targetY - bTemp2.getY()) * springConstant;
//			bTemp1.setDx(bTemp1.getDx() - ax);
//			bTemp1.setDy(bTemp1.getDy() - ay);
//			bTemp2.setDx(bTemp2.getDx() + ax);
//			bTemp2.setDy(bTemp2.getDy() + ay);
		
			/* Rotate ball positions and velocities back
			 * Reverse signs in trig expressions to rotate
			 * in the opposite direction */
			// rotate balls
			Bubble bFinal1 = new Bubble(item, 0, 0, 0, null);
			Bubble bFinal2 = new Bubble(item, 0, 0, 0, null);
			
			bFinal1.setX(cosine * bTemp1.getX() - sine * bTemp1.getY());
			bFinal1.setY(cosine * bTemp1.getY() + sine * bTemp1.getX());
			bFinal2.setX(cosine * bTemp2.getX() - sine * bTemp2.getY());
			bFinal2.setY(cosine * bTemp2.getY() + sine * bTemp2.getX());
						
			// update balls to screen position
			p2.setX(p1.getX() + bFinal2.getX());
			p2.setY(p1.getY() + bFinal2.getY());
			p1.setX(p1.getX() + bFinal1.getX());
			p1.setY(p1.getY() + bFinal1.getY());
			
			// update velocities
			p1.setDx(cosine * vFinal1.x - sine * vFinal1.y);
			p1.setDy(cosine * vFinal1.y + sine * vFinal1.x);
			p2.setDx(cosine * vFinal2.x - sine * vFinal2.y);
			p2.setDy(cosine * vFinal2.y + sine * vFinal2.x);
		}

	}
	
// 		OLD SORT OF WORKING handleCollision METHOD
//	    protected void handleCollision(Particle p1, Particle p2) {
//	
//	        // get distances between the balls components
//	        PVector offset = new PVector();
//	        offset.x = p2.getX() - p1.getX();
//	        offset.y = p2.getY() - p1.getY();
//	
//	        // calculate magnitude of the vector separating the balls
//	        float d = offset.mag();
//	        float minDist = p1.getR() + p2.getR();
//	
//	        if (d < minDist * 1.1) {
//	        	
//	        	offset.normalize();
//	            offset.mult(minDist);
//	            p2.setX(p1.getX() + offset.x);
//	            p2.setY(p1.getY() + offset.y);
//	
//	            float dx = p1.getDx(), dy = p2.getDy();
//	            p1.setDx(p2.getDx());
//	            p1.setDy(p2.getDy());
//	            p2.setDx(dx);
//	            p2.setDy(dy);
//	
//	        }
//	
//	    }

}
