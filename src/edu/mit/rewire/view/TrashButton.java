package edu.mit.rewire.view;

public class TrashButton extends Button {
    
    private final Bubble bubble;

    public TrashButton(Bubble bubble, float x, float y, float height, float width) {
        super(x, y, height, width);
        this.bubble = bubble;        
        this.upImage = new Shape("trashButton", x, y, height, width);
        this.downImage = new Shape("trashButtonClick", x, y, height, width);
        this.overImage = new Shape("trashButtonOver", x, y, height, width);
    }

    public void execute(ProcessingView view, int x, int y) {
        view.trashBubble(bubble);
    }


}
