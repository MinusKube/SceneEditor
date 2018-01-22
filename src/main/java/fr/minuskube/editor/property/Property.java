package fr.minuskube.editor.property;

import javafx.scene.Node;

public class Property {

    private String key;
    private String name;

    private Node value;

    public Property(String key, String name, Node value) {
        this.key = key;
        this.name = name;

        this.value = value;
    }

    public String getKey() { return key; }
    public String getName() { return name; }

    public Node getValue() { return value; }
    public void setValue(Node value) { this.value = value; }

}
