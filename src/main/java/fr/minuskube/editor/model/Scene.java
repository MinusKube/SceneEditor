package fr.minuskube.editor.model;

import fr.minuskube.editor.model.objects.SceneImage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class Scene {

    private ObservableList<Layer> layers = FXCollections.observableArrayList();

    public Scene() {
        Image placeholder = new Image("http://via.placeholder.com/30x30");

        for(int i = 0; i < 10; i++) {
            System.out.println("Load layer " + (i + 1));

            SceneImage image = new SceneImage(placeholder);

            Layer layer = new Layer(image);
            layer.nameProperty().set("Layer " + (i + 1));

            this.layers.add(layer);
        }
    }

    public ObservableList<Layer> getLayers() { return this.layers; }

}
