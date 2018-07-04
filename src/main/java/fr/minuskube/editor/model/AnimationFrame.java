package fr.minuskube.editor.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

public class AnimationFrame {

    private FloatProperty duration = new SimpleFloatProperty(this, "duration");
    private ObjectProperty<Image> image = new SimpleObjectProperty<>(this, "image");

    public AnimationFrame(float duration, Image image) {
        this.duration.set(duration);
        this.image.set(image);
    }

    public FloatProperty durationProperty() { return this.duration; }
    public float getDuration() { return this.duration.get(); }
    public void setDuration(float duration) { this.duration.set(duration); }

    public ObjectProperty<Image> imageProperty() { return this.image; }
    public Image getImage() { return this.image.get(); }
    public void setImage(Image image) { this.image.set(image); }

}
