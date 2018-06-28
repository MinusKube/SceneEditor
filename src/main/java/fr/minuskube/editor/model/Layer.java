package fr.minuskube.editor.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Layer {

    private StringProperty name = new SimpleStringProperty(this, "name");
    private ObjectProperty<Image> thumbnail = new SimpleObjectProperty<>(this, "thumbnail");

    public Layer() {
        this.thumbnail.set(new Image("https://picsum.photos/30/30/?random"));
    }

    public StringProperty nameProperty() { return this.name; }
    public ObjectProperty<Image> thumbnailProperty() { return this.thumbnail; }

}
