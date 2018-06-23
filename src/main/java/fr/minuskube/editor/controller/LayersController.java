package fr.minuskube.editor.controller;

import com.google.inject.Inject;
import fr.minuskube.editor.model.Layer;
import fr.minuskube.editor.model.Scene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class LayersController implements Initializable {

    @Inject
    private Scene scene;

    @FXML
    private ListView<Layer> list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MultipleSelectionModel<Layer> selectionModel = this.list.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        this.list.setItems(this.scene.getLayers());

        this.list.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if(event.getCode() != KeyCode.DELETE)
                return;

            selectionModel.getSelectedIndices().stream()
                    .sorted(Comparator.reverseOrder())
                    .mapToInt(Integer::intValue)
                    .forEachOrdered(this.list.getItems()::remove);
        });
    }

}
