package fr.minuskube.editor.view;

import fr.minuskube.editor.SceneEditorApp;
import javafx.scene.control.ScrollPane;

import java.io.IOException;

public class LayersPane extends ScrollPane {

    public LayersPane() throws IOException {
        SceneEditorApp app = SceneEditorApp.instance();

        this.setContent(app.load("layers.fxml"));
    }

}
