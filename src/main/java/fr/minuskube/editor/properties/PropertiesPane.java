package fr.minuskube.editor.properties;

import fr.minuskube.editor.SceneEditor;
import fr.minuskube.editor.scene.object.SceneImage;
import fr.minuskube.editor.scene.object.SceneObject;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class PropertiesPane extends ScrollPane {

    private Controller controller;

    public void init(SceneEditor editor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pane/properties.fxml"));
            VBox root = loader.load();

            root.getStylesheets().add("file:///" + new File("src/main/resources/window.css")
                    .getAbsolutePath().replace('\\', '/'));

            this.controller = loader.getController();
            this.controller.initListeners(this);

            editor.getScene().getObjects().addListener((ListChangeListener<SceneObject>) change -> {
                System.out.println("Change.");

                while(change.next()) {
                    for(SceneObject added : change.getAddedSubList()) {
                        System.out.println("Add item.");

                        List<HBox> items = controller.getList().getItems();
                        items.add(newImage((SceneImage) added));
                    }
                }
            });
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private HBox newImage(SceneImage image) {
        HBox box = new HBox();

        ImageView thumb = new ImageView(image.getImage());
        thumb.setPreserveRatio(true);

        Label label = new Label(Integer.toHexString(new Random().nextInt(Integer.MAX_VALUE)));

        box.getChildren().addAll(thumb, label);

        return box;
    }

    public static class Controller {

        @FXML
        private ListView<HBox> list;

        public void initListeners(PropertiesPane pane) {

        }

        public ListView<HBox> getList() { return list; }

    }

}
