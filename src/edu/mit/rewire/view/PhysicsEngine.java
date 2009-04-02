package edu.mit.rewire.view;

import java.util.ArrayList;
import java.util.List;

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

//        for (int i = 0; i < particles.size(); i++) {
//            for (int j = i + 1; j < particles.size(); j++) {
//                handleRepulsion(particles.get(i), particles.get(j));
//                handleCollision(particles.get(i), particles.get(j));
//            }
//        }

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

}
