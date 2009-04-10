package edu.mit.rewire.view.animation;

public class StepFunctions {
    
    public static float linear(float start, float end, int frames, int i) {
        return start + (end - start) * linear(frames, i);
    }
    
    public static float linear(int frames, int i) {
        if (i == 0 || frames == 0) return 0;
        return ((float) i) / ((float) frames);
    }
    
    public static float quadratic(float start, float end, int frames, int i) {
        return start + (end - start) * quadratic(frames, i);
    }

    public static float quadratic(int frames, int i) {
        return 0;
    }

}
