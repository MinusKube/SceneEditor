package fr.minuskube.editor.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Scene {

    private ObservableList<Layer> layers = FXCollections.observableArrayList();

    public ObservableList<Layer> getLayers() { return layers; }

}
