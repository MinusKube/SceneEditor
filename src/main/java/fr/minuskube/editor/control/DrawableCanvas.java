package fr.minuskube.editor.control;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.function.Consumer;

public class DrawableCanvas extends Canvas {

    private int mouseX;
    private int mouseY;

    private Consumer<GraphicsContext> drawConsumer;

    public void redraw() {
        GraphicsContext context = getGraphicsContext2D();
        context.clearRect(0, 0, getWidth(), getHeight());

        if(drawConsumer != null)
            drawConsumer.accept(context);
    }

    public Consumer<GraphicsContext> getDrawConsumer() { return drawConsumer; }
    public void setDrawConsumer(Consumer<GraphicsContext> drawConsumer) { this.drawConsumer = drawConsumer; }

    public int getMouseX() { return mouseX; }
    public void setMouseX(int mouseX) { this.mouseX = mouseX; }

    public int getMouseY() { return mouseY; }
    public void setMouseY(int mouseY) { this.mouseY = mouseY; }

}
