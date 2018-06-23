package fr.minuskube.editor.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class WindowController implements Initializable {

    @FXML
    private ListView<String> list;
    @FXML
    private Button button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
