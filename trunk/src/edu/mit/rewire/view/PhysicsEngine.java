package edu.mit.rewire.view;

import java.util.ArrayList;
import java.util.List;

public class PhysicsEngine implements Runnable {
    
    List<Particle> particles;
    
    public PhysicsEngine() {
        this.particles = new ArrayList<Particle>();
    }
    
    public PhysicsEngine(List<Particle> particles) {
        this();
        this.particles.addAll(particles);
    }
    
    public void add(Particle p) {
        this.particles.add(p);
    }

    @Override
    public void run() {
        while (true) {
            
        }        
    }

}
