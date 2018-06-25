package fr.minuskube.editor.controller;

import com.google.inject.Inject;
import fr.minuskube.editor.model.Layer;
import fr.minuskube.editor.model.Scene;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class LayersController implements Initializable {

    @Inject
    private Scene scene;

    @FXML
    private ListView<HBox> list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MultipleSelectionModel<HBox> selectionModel = this.list.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.MULTIPLE);

        this.updateListItems(this.scene.getLayers());

        this.scene.getLayers().addListener((ListChangeListener<Layer>) change ->
                this.updateListItems(change.getList())
        );

        this.list.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if(event.getCode() != KeyCode.DELETE)
                return;

            selectionModel.getSelectedIndices().stream()
                    .sorted(Comparator.reverseOrder())
                    .mapToInt(Integer::intValue)
                    .forEachOrdered(this.list.getItems()::remove);
        });
    }

    private void updateListItems(List<? extends Layer> layers) {
        // TODO: This needs a bit of cleaning and more MVC

        ObservableList<HBox> list = FXCollections.observableArrayList();

        for(int i = 0; i < layers.size(); i++) {
            final int index = i;
            Layer layer = layers.get(i);

            HBox box = new HBox();
            box.setSpacing(10);
            box.setAlignment(Pos.CENTER_LEFT);
            box.getStyleClass().add("layer");

            ImageView imageView = new ImageView("http://minuskube.fr/logo.png");
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);

            Label label = new Label();
            label.textProperty().bind(layer.nameProperty());

            TextField field = new TextField();
            field.textProperty().bindBidirectional(layer.nameProperty());

            // FIXME: When we are writing on the field and hit Shift + Space, the field gets unfocused

            label.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    box.getChildren().set(1, field);

                    field.requestFocus();
                }
            });

            field.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
                if(event.getCode() == KeyCode.ENTER)
                    box.getChildren().set(1, label);
            });

            ChangeListener<Boolean> focusListener = ((observable, oldValue, newValue) -> {
                if(!newValue)
                    box.getChildren().set(1, label);
            });

            field.focusedProperty().addListener(focusListener);

            this.list.getSelectionModel().getSelectedIndices().addListener((ListChangeListener<Integer>) change -> {
                if(!change.getList().contains(index))
                    box.getChildren().set(1, label);
            });

            box.getChildren().addAll(imageView, label);

            list.add(box);
        }

        this.list.setItems(list);
    }

}
