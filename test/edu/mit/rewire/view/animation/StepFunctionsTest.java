package edu.mit.rewire.view.animation;

import static org.junit.Assert.*;
import static edu.mit.rewire.view.animation.StepFunctions.*;

import org.junit.Test;

public class StepFunctionsTest {

    @Test
    public void testLinear() {
        assertEquals(0, linear(1, 0));
        assertEquals(1, linear(1, 1));
        
        assertEquals(0, linear(2, 0));
        assertEquals(0.5f, linear(2, 1));
        assertEquals(1, linear(2, 2));
        
        assertEquals(50, linear(50, 200, 1, 0));
        assertEquals(200, linear(50, 200, 1, 1));
        assertEquals(50, linear(0, 100, 2, 1));
    }

}
