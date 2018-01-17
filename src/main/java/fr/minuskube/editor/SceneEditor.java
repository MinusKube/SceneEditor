package fr.minuskube.editor;

import fr.minuskube.editor.scene.Scene;
import javafx.stage.Stage;


public class SceneEditor {

    private Stage stage;
    private Scene scene;

    public SceneEditor(Stage stage, Controller controller) {
        this.stage = stage;

        this.scene = new Scene(stage, controller.getSceneCanvas(), 0, 0);
        this.scene.init();

        controller.getSceneCanvas().init(scene);
    }

    public Stage getStage() { return stage; }
    public Scene getScene() { return scene; }

}
