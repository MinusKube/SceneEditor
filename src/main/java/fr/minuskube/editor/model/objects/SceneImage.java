package fr.minuskube.editor.model.objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class SceneImage extends SceneObject {

    private ObjectProperty<Image> image = new SimpleObjectProperty<>(this, "image");

    public SceneImage(Image image) {
        this.image.set(image);
    }

    @Override
    public SceneObject clone() {
        return new SceneImage(this.image.get());
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(GraphicsContext context) {

    }

    public ObjectProperty<Image> imageProperty() { return this.image; }

}