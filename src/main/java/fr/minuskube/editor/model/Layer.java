package fr.minuskube.editor.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Layer {

    private StringProperty name = new SimpleStringProperty(this, "name");

    public StringProperty nameProperty() { return this.name; }

}
