package fr.minuskube.editor.view;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class SceneCanvas extends Canvas {

    public SceneCanvas() {
        AnimationTimer updateTimer = new AnimationTimer() {
            private long lastFrame = System.nanoTime();

            @Override
            public void handle(long now) {
                float deltaTime = (now - this.lastFrame) / 1000000000f;

                SceneCanvas.this.update(deltaTime);
                SceneCanvas.this.render();
            }
        };

        updateTimer.start();
    }

    private void update(float deltaTime) {

    }

    private void render() {
        GraphicsContext context = this.getGraphicsContext2D();
        context.clearRect(0, 0, this.getWidth(), this.getHeight());
    }

}
