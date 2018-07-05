package fr.minuskube.editor.view;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class SceneCanvas extends Canvas {

    public SceneCanvas() {
        AnimationTimer updateTimer = new AnimationTimer() {
            private long lastFrame = System.nanoTime();

            @Override
            public void handle(long now) {
                float deltaTime = (now - this.lastFrame) / 1_000_000_000f;

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


        WritableImage test = new WritableImage(30, 30);

        for(int x = 0; x < 30; x++) {
            for(int y = 0; y < 30; y++) {
                test.getPixelWriter().setColor(x, y, Color.RED);
            }
        }

        context.drawImage(test, 50, 50);

        context.setFill(Color.RED);
        context.fillOval(0, 0, 50, 50);
    }

}
