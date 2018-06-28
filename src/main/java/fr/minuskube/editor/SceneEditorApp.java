package fr.minuskube.editor;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.fxmisc.cssfx.CSSFX;
import org.fxmisc.cssfx.api.URIToPathConverter;

import java.io.IOException;
import java.nio.file.Paths;

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
        Parent root = this.load("window.fxml");

        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Scene Editor");
        primaryStage.show();

        URIToPathConverter converter = uri -> Paths.get(
                uri.replace("out/production", "src/main")
                        .replace("file:/", "")
        );

        CSSFX.addConverter(converter).start();
    }

    public <T> T load(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/" + path));
        loader.setControllerFactory(this.injector::getInstance);

        return loader.load();
    }

    public Injector getInjector() { return this.injector; }

    public static SceneEditorApp instance() { return instance; }

}
