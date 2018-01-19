package fr.minuskube.editor.layers;

import fr.minuskube.editor.SceneEditor;
import fr.minuskube.editor.scene.Scene;
import fr.minuskube.editor.scene.object.SceneImage;
import fr.minuskube.editor.scene.object.SceneObject;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
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

        ImageView thumb = new ImageView(image.getImage());
        thumb.setFitWidth(50);
        thumb.setFitHeight(50);
        thumb.setPreserveRatio(true);

        Label label = new Label(Integer.toHexString(new Random().nextInt(Integer.MAX_VALUE)));

        box.getChildren().addAll(thumb, label);

        return box;
    }

    public SceneEditor getEditor() { return editor; }
    public Map<SceneObject, HBox> getObjectBoxes() { return objectBoxes; }

    public static class Controller {

        @FXML
        private ListView<HBox> list;

        public boolean selfChanging = false;

        public void initListeners(LayersPane pane) {
            Scene scene = pane.getEditor().getScene();

            list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            scene.getSelectedObjects().addListener((ListChangeListener<? super SceneObject>) change -> {
                if(selfChanging)
                    return;

                selfChanging = true;

                MultipleSelectionModel<HBox> model = list.getSelectionModel();
                model.clearSelection();

                for(SceneObject object : change.getList())
                    model.select(pane.getObjectBoxes().get(object));

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
