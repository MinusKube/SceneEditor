package fr.minuskube.editor.layer;

import fr.minuskube.editor.SceneEditor;
import fr.minuskube.editor.control.DraggableListCell;
import fr.minuskube.editor.scene.Scene;
import fr.minuskube.editor.scene.object.SceneImage;
import fr.minuskube.editor.scene.object.SceneObject;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LayersPane extends ScrollPane {

    private SceneEditor editor;
    private Controller controller;

    private Map<SceneObject, HBox> objectBoxes = new HashMap<>();

    public void init(SceneEditor editor) {
        this.editor = editor;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pane/layers.fxml"));
            VBox root = loader.load();

            root.getStylesheets().add("file:///" + new File("src/main/resources/window.css")
                    .getAbsolutePath().replace('\\', '/'));

            this.controller = loader.getController();
            this.controller.initListeners(this);

            editor.getScene().getObjects().addListener((ListChangeListener<SceneObject>) change -> {
                if(controller.selfChanging)
                    return;

                controller.selfChanging = true;

                while(change.next()) {
                    for(SceneObject added : change.getAddedSubList()) {
                        List<HBox> items = controller.getList().getItems();
                        HBox box = newImage((SceneImage) added);

                        items.add(box);
                        objectBoxes.put(added, box);
                    }

                    for(SceneObject removed : change.getRemoved()) {
                        List<HBox> items = controller.getList().getItems();

                        items.remove(objectBoxes.get(removed));
                        objectBoxes.remove(removed);
                    }
                }

                controller.selfChanging = false;
            });

            this.setContent(root);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private HBox newImage(SceneImage image) {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_LEFT);
        box.getStyleClass().add("layer-box");

        ImageView thumb = new ImageView(image.getImage());
        thumb.setFitWidth(50);
        thumb.setFitHeight(50);
        thumb.setPreserveRatio(true);

        BorderPane thumbPane = new BorderPane(thumb);
        thumbPane.getStyleClass().add("pane");

        TextField field = new TextField(Integer.toHexString(new Random().nextInt(Integer.MAX_VALUE)));
        field.setEditable(false);

        field.setOnMouseClicked(event -> {
            if(event.getClickCount() >= 2)
                field.setEditable(true);
            else if(event.getClickCount() == 1) {
                SelectionModel model = controller.getList().getSelectionModel();
                model.clearSelection();
                model.select(controller.getList().getItems().indexOf(box));
            }
        });

        field.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER)
                field.setEditable(false);
            else if(event.getCode() == KeyCode.F2)
                field.setEditable(true);
        });

        field.focusedProperty().addListener(((observable, oldValue, newValue) -> field.setEditable(false)));

        box.getChildren().addAll(thumbPane, field);

        return box;
    }

    public SceneEditor getEditor() { return editor; }

    public SceneObject getObjectFromBox(HBox box) {
        for(Map.Entry<SceneObject, HBox> entry : objectBoxes.entrySet()) {
            if(entry.getValue() == box)
                return entry.getKey();
        }

        return null;
    }

    public HBox getBoxFromObject(SceneObject object) {
        return objectBoxes.get(object);
    }

    public Map<SceneObject, HBox> getObjectBoxes() { return objectBoxes; }

    public static class Controller {

        @FXML
        private ListView<HBox> list;

        public boolean selfChanging = false;

        public void initListeners(LayersPane pane) {
            Scene scene = pane.getEditor().getScene();

            list.setCellFactory(list -> new DraggableListCell<>());
            list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            list.getItems().addListener((ListChangeListener<? super HBox>) change -> {
                if(selfChanging)
                    return;

                selfChanging = true;

                List<SceneObject> objects = scene.getObjects();
                objects.clear();

                for(HBox box : change.getList())
                    objects.add(pane.getObjectFromBox(box));

                selfChanging = false;
            });

            scene.getSelectedObjects().addListener((ListChangeListener<? super SceneObject>) change -> {
                if(selfChanging)
                    return;

                selfChanging = true;

                MultipleSelectionModel<HBox> model = list.getSelectionModel();
                model.clearSelection();

                for(SceneObject object : change.getList())
                    model.select(pane.getBoxFromObject(object));

                selfChanging = false;
            });

            list.getSelectionModel().getSelectedIndices().addListener((ListChangeListener<? super Integer>) change -> {
                if(selfChanging)
                    return;

                selfChanging = true;

                ObservableList<SceneObject> objects = scene.getObjects();

                for(int i = 0; i < objects.size(); i++) {
                    SceneObject object = objects.get(i);

                    if(change.getList().contains(i)) {
                        object.setSelected(true);
                        scene.getSelectedObjects().add(object);
                    }
                    else {
                        object.setSelected(false);
                        scene.getSelectedObjects().remove(object);
                    }
                }

                scene.getCanvas().redraw();

                selfChanging = false;
            });
        }

        public ListView<HBox> getList() { return list; }

    }

}
