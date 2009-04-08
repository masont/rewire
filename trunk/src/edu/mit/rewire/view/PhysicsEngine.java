package edu.mit.rewire.view;

import java.util.ArrayList;
import java.util.List;

import edu.mit.rewire.model.Item;
import edu.mit.rewire.model.MockItem;

import processing.core.PVector;

public class PhysicsEngine {

	List<Particle> particles;

	private final float width, height;

	private final float springConstant = 0.05f;

	private final float repulsionConstant = 10f;

	private final float frictionConstant = -0.9f;

	public PhysicsEngine(float width, float height) {
		this.particles = new ArrayList<Particle>();
		this.width = width;
		this.height = height;
	}

	public PhysicsEngine(float width, float height, List<Particle> particles) {
		this(height, width);
		this.particles.addAll(particles);
	}

	public void add(Particle p) {
		this.particles.add(p);
	}

	public void init() {

	}

	public void step() {

		for (int i = 0; i < particles.size(); i++) {
			for (int j = i + 1; j < particles.size(); j++) {
				handleRepulsion(particles.get(i), particles.get(j));
				handleCollision(particles.get(i), particles.get(j));
			}
		}

		for (Particle p : particles) {
			p.setX(p.getX() + p.getDx());
			p.setY(p.getY() + p.getDy());
			handleWalls(p);
			p.setChanged(true);
		}
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

	    protected void handleCollision(Particle p1, Particle p2) {
	
	        // get distances between the balls components
	        PVector offset = new PVector();
	        offset.x = p2.getX() - p1.getX();
	        offset.y = p2.getY() - p1.getY();
	
	        // calculate magnitude of the vector separating the balls
	        float d = offset.mag();
	        float minDist = p1.getR() + p2.getR();
	
	        if (d < minDist) {
	        	
	        	offset.normalize();
	            offset.mult(minDist);
	            p2.setX(p1.getX() + offset.x);
	            p2.setY(p1.getY() + offset.y);
	
	            float dx = p1.getDx(), dy = p2.getDy();
	            p1.setDx(p2.getDx());
	            p1.setDy(p2.getDy());
	            p2.setDx(dx);
	            p2.setDy(dy);
	
	        }
	
	    }

//	protected void handleCollision(Particle p1, Particle p2) {
//		PVector bVect = new PVector();
//		bVect.x = p2.getX() - p1.getX();
//		bVect.y = p2.getY() - p1.getY();
//
//		float bVectMag = (float) Math.sqrt(bVect.x * bVect.x + bVect.y * bVect.y);
//		if (bVectMag < p1.getR() + p2.getR()) {
//			float theta = (float) Math.atan2(bVect.y, bVect.x);
//			float sine = (float) Math.sin(theta);
//			float cosine = (float) Math.cos(theta);
//
//
//			Item item = new MockItem();
//			Bubble[] bTemp = {
//					new Bubble(item, 0, 0, 0), new Bubble(item, 0, 0, 0)
//			};
//
//			bTemp[1].setX(cosine * bVect.x + sine * bVect.y);
//			bTemp[1].setY(cosine * bVect.y - sine * bVect.x);
//
//			PVector[] vTemp = {
//					new PVector(), new PVector()
//			};
//			vTemp[0].x = cosine * p1.getDx() + sine * p1.getDy();
//			vTemp[0].y = cosine * p1.getDy() - sine * p1.getDx();
//			vTemp[1].x = cosine * p2.getDx() + sine * p2.getDy();
//			vTemp[1].y = cosine * p2.getDy() - sine * p2.getDx();
//
//			PVector[] vFinal = {
//					new PVector(), new PVector()
//			};
//			vFinal[0].x = (float) (((p1.getR()*.1 - p2.getR()*.1) * vTemp[0].x + 2 * p2.getR()*.1 *
//					vTemp[1].x) / (p1.getR()*.1 + p2.getR()*.1));
//			vFinal[0].y = vTemp[0].y;
//			vFinal[1].x = (float) (((p2.getR()*.1 - p1.getR()*.1) * vTemp[1].x + 2 * p1.getR()*.1 *
//					vTemp[0].x) / (p1.getR()*.1 + p2.getR()*.1));
//			vFinal[1].y = vTemp[1].y;
//
//			bTemp[0].setX(bTemp[0].getX() + vFinal[0].x);
//			bTemp[1].setX(bTemp[1].getY() + vFinal[1].y);
//
//			Bubble[] bFinal = {
//					new Bubble(item, 0, 0, 0), new Bubble(item, 0, 0, 0)
//			};
//			bFinal[0].setX(cosine * bTemp[0].getX()- sine * bTemp[0].getY());
//			bFinal[0].setY(cosine * bTemp[0].getY() + sine * bTemp[0].getX());
//			bFinal[1].setX(cosine * bTemp[1].getX() - sine * bTemp[1].getY());
//			bFinal[1].setY(cosine * bTemp[1].getY() + sine * bTemp[1].getX());
//
//			// update balls to screen position
//			p2.setX(p1.getX() + bFinal[1].getX());
//			p2.setY(p1.getY() + bFinal[1].getY());
//			p1.setX(p1.getX() + bFinal[0].getX());
//			p1.setY(p1.getY() + bFinal[0].getY());
//
//			// update velocities
//			p1.setDx(cosine * vFinal[0].x - sine * vFinal[0].y);
//			p1.setDy(cosine * vFinal[0].y + sine * vFinal[0].x);
//			p2.setDx(cosine * vFinal[1].x - sine * vFinal[1].y);
//			p2.setDy(cosine * vFinal[1].y + sine * vFinal[1].x);
//		}
//	}
}
