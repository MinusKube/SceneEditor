package fr.minuskube.editor.control;

import javafx.scene.layout.Pane;

public class ResizableCanvas extends Pane {

    private DrawableCanvas canvas;

    public ResizableCanvas() {}
    public ResizableCanvas(DrawableCanvas canvas) {
        this.canvas = canvas;

        this.getChildren().add(canvas);
    }

    @Override
    protected void layoutChildren() {
        int top = (int) snappedTopInset();
        int right = (int) snappedRightInset();
        int bottom = (int) snappedBottomInset();
        int left = (int) snappedLeftInset();

        int width = (int) getWidth() - left - right;
        int height = (int) getHeight() - top - bottom;

        canvas.setLayoutX(left);
        canvas.setLayoutY(top);

        if(width != canvas.getWidth() || height != canvas.getHeight()) {
            canvas.setWidth(width);
            canvas.setHeight(height);

            canvas.redraw();
        }
    }

    public DrawableCanvas getCanvas() { return canvas; }
    public void setCanvas(DrawableCanvas canvas) {
        this.canvas = canvas;
        this.getChildren().add(canvas);
    }

}
