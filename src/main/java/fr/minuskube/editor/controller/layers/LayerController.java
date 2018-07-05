package fr.minuskube.editor.controller.layers;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import fr.minuskube.editor.model.Layer;
import fr.minuskube.editor.model.Scene;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LayerController implements Initializable {

    @Inject
    private ListView<HBox> layers;
    @Inject @Named("index")
    private int index;

    @Inject
    private Injector injector;
    @Inject
    private Scene scene;

    @FXML
    private HBox root;
    @FXML
    private ImageView thumbnail;

    @FXML
    private Label label;
    @FXML
    private TextField field;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Layer layer = this.scene.getLayers().get(this.index);
        MultipleSelectionModel<HBox> selectionModel = this.layers.getSelectionModel();

        this.thumbnail.imageProperty().bind(layer.thumbnailProperty());

        this.label.setVisible(true);
        this.label.textProperty().bind(layer.nameProperty());

        this.field.setVisible(false);
        this.field.textProperty().bindBidirectional(layer.nameProperty());

        this.root.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() != MouseButton.SECONDARY)
                return;

            this.showContextMenu(event.getScreenX(), event.getScreenY());
        });

        this.layers.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if(selectionModel.getSelectedIndex() != this.index)
                return;

            if(event.getCode() == KeyCode.F2)
                this.startEditingLayerName();
        });

        this.label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
                this.startEditingLayerName();
        });

        this.field.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.ESCAPE)
                this.stopEditingLayerName();
        });

        this.field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue)
                this.stopEditingLayerName();
        });

        this.layers.getFocusModel().focusedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() != this.index)
                this.stopEditingLayerName();
        });

        this.layers.addEventHandler(KeyEvent.ANY, event -> {
            if(this.field.isVisible())
                event.consume();
        });
    }

    public void startEditingLayerName() {
        if(!this.label.isVisible())
            return;

        this.label.setVisible(false);

        this.field.setVisible(true);
        this.field.requestFocus();
    }

    public void stopEditingLayerName() {
        if(!this.field.isVisible())
            return;

        this.field.setVisible(false);
        this.label.setVisible(true);

        this.layers.requestFocus();
    }

    private void showContextMenu(double x, double y) {
        Injector contextInjector = this.injector.createChildInjector(new LayerContextModule(this));

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/layerContext.fxml"));
        loader.setControllerFactory(contextInjector::getInstance);

        try {
            ContextMenu menu = loader.load();
            menu.show(this.root, x, y);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
