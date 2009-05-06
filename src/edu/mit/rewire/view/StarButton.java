package edu.mit.rewire.view;

public class StarButton extends Button {
    
    private final Bubble bubble;
    
    public StarButton(Bubble bubble, float x, float y, float height, float width) {
        super(x, y, height, width);
        this.bubble = bubble;        
        this.upImage = new Shape("starButton", x, y, height, width);
        this.downImage = new Shape("starButtonClick", x, y, height, width);
        this.overImage = new Shape("starButtonOver", x, y, height, width);
    }

    @Override
    public void execute(ProcessingView view, int x, int y) {
        // TODO Auto-generated method stub

    }

}
