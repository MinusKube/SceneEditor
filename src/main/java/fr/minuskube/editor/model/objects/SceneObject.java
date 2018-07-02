package fr.minuskube.editor.model.objects;

import javafx.scene.canvas.GraphicsContext;

public abstract class SceneObject {

    protected int x, y;

    public abstract SceneObject clone();

    public abstract void update(float deltaTime);
    public abstract void render(GraphicsContext context);

    public int getX() { return this.x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return this.y; }
    public void setY(int y) { this.y = y; }

}
