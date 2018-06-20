package fr.minuskube.editor.controller;

import fr.minuskube.editor.model.Window;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class WindowController {

    @FXML
    private ListView<String> list;
    @FXML
    private Button button;

    public void init(Window window) {}

}
