package fr.minuskube.editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/window.fxml"));
        Parent root = loader.load();

        Controller controller = loader.getController();
        SceneEditor editor = new SceneEditor(primaryStage, controller);

        controller.initListeners(editor);

        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add("file:///" + new File("src/main/resources/window.css")
                .getAbsolutePath().replace('\\', '/'));

        primaryStage.setTitle("Game Scene Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
