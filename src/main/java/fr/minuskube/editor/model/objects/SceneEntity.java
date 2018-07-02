package fr.minuskube.editor.model.objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.canvas.GraphicsContext;

import java.util.Map;

public class SceneEntity extends SceneObject {

    private ObservableMap<String, SceneAnimation> animations = FXCollections.observableHashMap();

    public SceneEntity() {}

    public SceneEntity(Map<String, SceneAnimation> animations) {
        this.animations.putAll(animations);
    }

    @Override
    public SceneObject clone() {
        return new SceneEntity(this.animations);
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(GraphicsContext context) {

    }

    public ObservableMap<String, SceneAnimation> getAnimations() { return this.animations; }

}