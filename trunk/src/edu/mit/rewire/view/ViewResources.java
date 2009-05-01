package edu.mit.rewire.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PShape;

public class ViewResources {
    
    private static final String fileName = "view.properties";
    
    private static final Properties properties;
    
    private static final Map<String, PShape> shapeMap = new HashMap<String, PShape>();
    
    private static final Map<String, PImage> imageMap = new HashMap<String, PImage>();
    
    private static final Map<String, PFont> fontMap = new HashMap<String, PFont>();
    
    private static final PApplet pApplet;
    
    static {
        pApplet = new PApplet();
        InputStream inStream = ViewResources.class.getResourceAsStream(fileName);
        properties = new Properties();
        try {
            properties.load(inStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static PShape loadShape(String name) {
        if (!shapeMap.containsKey(name)) { 
            String fileName = properties.getProperty(name);
            shapeMap.put(name, pApplet.loadShape(fileName));
        }        
        return shapeMap.get(name);
    }
    
    public static PImage loadImage(String name) {
        if (!imageMap.containsKey(name)) { 
            String fileName = properties.getProperty(name);
            imageMap.put(name, pApplet.loadImage(fileName));
        }        
        return imageMap.get(name);
    }
    
    public static PFont loadFont(String name) {
        if (!fontMap.containsKey(name)) { 
            String fileName = properties.getProperty(name);
            fontMap.put(name, pApplet.loadFont(fileName));
        }        
        return fontMap.get(name);
    }

}
