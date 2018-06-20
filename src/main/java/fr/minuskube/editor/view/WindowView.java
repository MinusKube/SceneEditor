package fr.minuskube.editor.view;

import fr.minuskube.editor.controller.WindowController;
import fr.minuskube.editor.model.Window;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowView extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Window window = new Window();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/window.fxml"));
        Parent root = loader.load();

        WindowController controller = loader.getController();
        controller.init(this.window);

        Scene scene = new Scene(root, 1280, 720);

        stage.setScene(scene);
        stage.show();
    }

}
