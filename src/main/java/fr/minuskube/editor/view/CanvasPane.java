package fr.minuskube.editor.view;

import fr.minuskube.editor.SceneEditorApp;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Region;

import java.io.IOException;

public class CanvasPane extends Region {

    private Canvas canvas;

    public CanvasPane() throws IOException {
        SceneEditorApp app = SceneEditorApp.instance();

        this.canvas = app.load("canvas.fxml");
        this.canvas.setWidth(this.getWidth());
        this.canvas.setHeight(this.getHeight());

        this.getChildren().add(this.canvas);
    }

    @Override
    protected void layoutChildren() {
        int top = (int) this.snappedTopInset();
        int right = (int) this.snappedRightInset();
        int bottom = (int) this.snappedBottomInset();
        int left = (int) this.snappedLeftInset();

        int width = (int) this.getWidth() - left - right;
        int height = (int) this.getHeight() - top - bottom;

        this.canvas.setLayoutX(left);
        this.canvas.setLayoutY(top);

        if(width != this.canvas.getWidth() || height != this.canvas.getHeight()) {
            this.canvas.setWidth(width);
            this.canvas.setHeight(height);
        }
    }

}
