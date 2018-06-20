package fr.minuskube.editor.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Window {

    private ObjectProperty<Scene> currentScene = new SimpleObjectProperty<>(this, "currentScene");

    public ObjectProperty<Scene> currentSceneProperty() { return currentScene; }

}
