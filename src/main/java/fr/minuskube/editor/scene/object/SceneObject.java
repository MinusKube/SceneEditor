package fr.minuskube.editor.scene.object;

import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

public abstract class SceneObject implements Serializable {

    private String name;
    private int x, y;

    private transient boolean hovered;
    private transient boolean selected;

    public void update(float deltaTime) {}
    public abstract void draw(GraphicsContext context);

    public abstract int getWidth();
    public abstract int getHeight();

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public boolean isHovered() { return hovered; }
    public void setHovered(boolean hovered) { this.hovered = hovered; }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { System.out.println("Selected: " + selected + ", " + hashCode()); this.selected = selected; }

}
