package fr.minuskube.editor.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Scene {

    private ObservableList<Layer> layers = FXCollections.observableArrayList();

    public Scene() {
        for(int i = 0; i < 10; i++) {
            Layer layer = new Layer();
            layer.nameProperty().set("Layer " + i);

            this.layers.add(layer);
        }
    }

    public ObservableList<Layer> getLayers() { return this.layers; }

}
