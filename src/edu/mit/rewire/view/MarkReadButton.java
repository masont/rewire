package edu.mit.rewire.view;

public class MarkReadButton extends Button {
    
    private final Bubble bubble;
    
    private Drawable currentImage;
    
    public MarkReadButton(Bubble bubble, float x, float y, float height, float width) {
        super(x, y, height, width);
        this.bubble = bubble;
        this.upImage = new Shape("popButton", x, y, height, width);
        this.downImage = new Shape("popButtonClick", x, y, height, width);
        this.overImage = new Shape("popButtonOver", x, y, height, width);
        this.currentImage = this.bubble.isStarred() ? this.downImage : this.upImage;
    }

    public void execute(ProcessingView view, int x, int y) {
        view.markRead(bubble);
    }

}
