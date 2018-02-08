package fr.minuskube.editor.scene.object;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;

public class SceneEntity extends SceneObject {

    private int width;
    private int height;

    private ObservableList<SceneAnimation> animations;
    private int currentAnimation;

    public SceneEntity() {
        this.animations = FXCollections.observableArrayList();
    }

    @Override
    public void update(float deltaTime) {
        /*if(!animations.isEmpty()) {
            animations.get(currentAnimation).update();
        }*/
    }

    @Override
    public void draw(GraphicsContext context) {
        if(!animations.isEmpty())
            animations.get(currentAnimation).draw(context);
    }

    @Override
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    @Override
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public ObservableList<SceneAnimation> getAnimations() { return animations; }

    public int getCurrentAnimation() { return currentAnimation; }
    public void setCurrentAnimation(int currentAnimation) { this.currentAnimation = currentAnimation; }

}
