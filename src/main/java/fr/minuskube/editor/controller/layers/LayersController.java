package fr.minuskube.editor.controller.layers;

import com.google.inject.Inject;
import com.google.inject.Injector;
import fr.minuskube.editor.model.Layer;
import fr.minuskube.editor.model.Scene;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class LayersController implements Initializable {

    @Inject
    private Injector injector;
    @Inject
    private Scene scene;

    @FXML
    private ListView<HBox> list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MultipleSelectionModel<HBox> selectionModel = this.list.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        this.list.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if(event.getCode() != KeyCode.DELETE)
                return;

            selectionModel.getSelectedIndices().stream()
                    .sorted(Comparator.reverseOrder())
                    .mapToInt(Integer::intValue)
                    .forEachOrdered(this.scene.getLayers()::remove);
        });

        this.updateListItems(this.scene.getLayers());

        this.scene.getLayers().addListener((ListChangeListener<Layer>) change ->
                this.updateListItems(change.getList())
        );
    }

    private void updateListItems(List<? extends Layer> layers) {
        ObservableList<HBox> list = FXCollections.observableArrayList();

        for(int i = 0; i < layers.size(); i++) {
            Layer layer = layers.get(i);

            Injector layerInjector = this.injector.createChildInjector(new LayerModule(this.list, i));

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/layer.fxml"));
            loader.setControllerFactory(layerInjector::getInstance);

            try {
                list.add(loader.load());
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        this.list.setItems(list);
    }

}
