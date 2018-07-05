package fr.minuskube.editor.controller.layers;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import fr.minuskube.editor.model.Scene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LayerContextController implements Initializable {

    @Inject @Named("index")
    private int index;

    @Inject
    private Scene scene;
    @Inject @Named("parent")
    private LayerController parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @FXML
    private void rename() {
        this.parentController.startEditingLayerName();
    }

    @FXML
    private void delete() {
        this.scene.getLayers().remove(this.index);
    }


}
