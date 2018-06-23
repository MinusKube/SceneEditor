package fr.minuskube.editor;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneEditorApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static SceneEditorApp instance;

    private Injector injector;

    public SceneEditorApp() {
        instance = this;

        this.injector = Guice.createInjector(new SceneEditorModule(this));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage stage = new Stage();

        Parent root = this.load("window.fxml");

        stage.setScene(new Scene(root));
        stage.show();
    }

    public <T> T load(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/" + path));
        loader.setControllerFactory(this.injector::getInstance);

        return loader.load();
    }

    public Injector getInjector() { return this.injector; }

    public static SceneEditorApp instance() { return instance; }

}
