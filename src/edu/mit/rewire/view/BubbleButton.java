package edu.mit.rewire.view;

public class BubbleButton extends Button {
    
    private final Bubble bubble;

    public BubbleButton(Bubble bubble, float x, float y, float height, float width) {
        super(x, y, height, width);
        this.bubble = bubble;
        this.upImage = new Shape("bubbleButton", x, y, height, width);
        this.downImage = new Shape("bubbleButtonClick", x, y, height, width);
        this.overImage = new Shape("bubbleButtonOver", x, y, height, width);
    }

    @Override
    public void execute(ProcessingView view, int x, int y) {
        view.minimizeBubble(bubble);
    }

}
