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
    public void update(float deltaTime) {}

    @Override
    public void render(GraphicsContext context) {
        context.drawImage(this.image.get(), this.x, this.y);
    }

    @Override
    public void renderThumbnail(GraphicsContext context) {
        context.drawImage(this.image.get(), 0, 0);
    }

    public ObjectProperty<Image> imageProperty() { return this.image; }
    public Image getImage() { return this.image.get(); }
    public void setImage(Image image) { this.image.set(image); }

}