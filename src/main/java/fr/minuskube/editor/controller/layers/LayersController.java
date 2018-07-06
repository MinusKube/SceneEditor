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
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class LayersController implements Initializable {

    @Inject
    private Injector injector;
    @Inject
    private Scene scene;

    @FXML
    private ListView<HBox> list;

    private Map<HBox, LayerController> controllers = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MultipleSelectionModel<HBox> selectionModel = this.list.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        this.list.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if(event.getCode() == KeyCode.DELETE) {
                selectionModel.getSelectedIndices().stream()
                        .sorted(Comparator.reverseOrder())
                        .mapToInt(Integer::intValue)
                        .forEachOrdered(this.scene.getLayers()::remove);
            }
        });

        this.list.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if(event.isShiftDown())
                return;
            if(event.isControlDown())
                return;

            HBox selected = selectionModel.getSelectedItem();

            if(selected == null)
                return;

            Bounds bounds = selected.localToScene(selected.getBoundsInLocal());

            if(!bounds.contains(event.getSceneX(), event.getSceneY()))
                selectionModel.clearSelection();
        });

        this.updateListItems(this.scene.getLayers());

        this.scene.getLayers().addListener((ListChangeListener<Layer>) change ->
                this.updateListItems(change.getList())
        );

        this.list.setCellFactory(param -> {
            ListCell<HBox> cell = new ListCell<>();
            cell.graphicProperty().bind(cell.itemProperty());

            cell.emptyProperty().addListener(((observable, oldValue, newValue) -> {
                if(!newValue) {
                    LayerContextModule module = new LayerContextModule(
                            this.list, cell.getIndex(),
                            this.controllers.get(cell.getItem())
                    );

                    Injector contextInjector = this.injector.createChildInjector(module);

                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/layerContext.fxml"));
                    loader.setControllerFactory(contextInjector::getInstance);

                    try {
                        ContextMenu contextMenu = loader.load();
                        cell.setContextMenu(contextMenu);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                    cell.setContextMenu(null);
            }));

            return cell;
        });
    }

    private void updateListItems(List<? extends Layer> layers) {
        this.controllers.clear();

        ObservableList<HBox> list = FXCollections.observableArrayList();

        for(int i = 0; i < layers.size(); i++) {
            Layer layer = layers.get(i);

            Injector layerInjector = this.injector.createChildInjector(new LayerModule(this.list, i));

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/layer.fxml"));
            loader.setControllerFactory(layerInjector::getInstance);

            try {
                HBox box = loader.load();

                this.controllers.put(box, loader.getController());
                list.add(box);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        this.list.setItems(list);
    }

}
