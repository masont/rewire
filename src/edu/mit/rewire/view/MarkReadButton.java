package edu.mit.rewire.view;

public class MarkReadButton extends Button {
    
    private final Bubble bubble;
    
    public MarkReadButton(Bubble bubble, float x, float y, float height, float width) {
        super(x, y, height, width);
        this.bubble = bubble;        
        this.upImage = this.overImage = new Shape("popButton", x, y, height, width);
        this.downImage = new Shape("popButtonClick", x, y, height, width);
    }

    public void execute(ProcessingView view, int x, int y) {
        view.markRead(bubble);
    }

}
