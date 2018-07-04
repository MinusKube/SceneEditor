package fr.minuskube.editor.model;

import fr.minuskube.editor.model.objects.SceneObject;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Layer {

    private StringProperty name = new SimpleStringProperty(this, "name");
    private ReadOnlyObjectWrapper<SceneObject> object = new ReadOnlyObjectWrapper<>(this, "object");
    private ReadOnlyObjectWrapper<Image> thumbnail = new ReadOnlyObjectWrapper<>(this, "thumbnail");

    public Layer(SceneObject object) {
        this.object.set(object);

        Canvas canvas = new Canvas(30, 30);
        GraphicsContext context = canvas.getGraphicsContext2D();

        object.render(context);

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        this.thumbnail.set(canvas.snapshot(parameters, null));
    }

    public StringProperty nameProperty() { return this.name; }
    public String getName() { return this.name.get(); }
    public void setName(String name) { this.name.set(name); }

    public ReadOnlyObjectProperty<SceneObject> objectProperty() { return this.object.getReadOnlyProperty(); }
    public SceneObject getObject() { return this.object.get(); }

    public ReadOnlyObjectProperty<Image> thumbnailProperty() { return this.thumbnail.getReadOnlyProperty(); }
    public Image getThumbnail() { return this.thumbnail.get(); }

}
