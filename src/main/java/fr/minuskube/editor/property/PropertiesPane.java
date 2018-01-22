package fr.minuskube.editor.property;

import fr.minuskube.editor.SceneEditor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;

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

            this.setContent(root);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static class Controller {

        @FXML
        private ListView<HBox> list;

        public void initListeners(PropertiesPane pane) {

        }

        public ListView<HBox> getList() { return list; }

    }

}
