package fr.minuskube.editor;

import fr.minuskube.editor.dialog.NewSceneDialog;
import fr.minuskube.editor.layer.LayersPane;
import fr.minuskube.editor.property.PropertiesPane;
import fr.minuskube.editor.scene.Scene;
import fr.minuskube.editor.scene.SceneSerializer;
import fr.minuskube.editor.scene.object.SceneImage;
import fr.minuskube.editor.scene.object.SceneObject;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Dimension2D;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public class Controller {

    @FXML private MenuItem menuFileNew;
    @FXML private MenuItem menuFileOpen;
    @FXML private MenuItem menuFileSave;
    @FXML private MenuItem menuFileSaveAs;
    @FXML private MenuItem menuFileExport;
    @FXML private MenuItem menuFileImport;
    @FXML private MenuItem menuFileExit;

    @FXML private LayersPane layersPane;
    @FXML private PropertiesPane propertiesPane;
    @FXML private Scene.Canvas sceneCanvas;

    public void initialize() {}

    public void initListeners(SceneEditor editor) {
        menuFileNew.setOnAction(event -> {
            NewSceneDialog dialog = new NewSceneDialog();
            Optional<Dimension2D> opDimension = dialog.showAndWait();

            if(opDimension.isPresent()) {
                Dimension2D dimension = opDimension.get();
                Scene scene = editor.getScene();

                scene.setWidth((int) dimension.getWidth());
                scene.setHeight((int) dimension.getHeight());
                scene.reset();
            }
        });

        menuFileOpen.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Please select a scene...");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Scene", "*.scn"));

            File file = chooser.showOpenDialog(editor.getStage());

            if(file != null) {
                Scene scene = editor.getScene();

                scene.reset();
                scene.setSaveLocation(file);

                SceneSerializer.load(file, scene);

                scene.resetPosition();
            }
        });

        menuFileSave.setOnAction(event -> {
            Scene scene = editor.getScene();
            File file;

            if(scene.getSaveLocation() == null) {
                FileChooser chooser = new FileChooser();
                chooser.setTitle("Please select a location for the scene...");
                chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Scene", "*.scn"));

                file = chooser.showSaveDialog(editor.getStage());
            }
            else
                file = scene.getSaveLocation();

            if(file != null) {
                scene.setSaveLocation(file);

                SceneSerializer.save(file, scene);
            }
        });

        menuFileSaveAs.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Please select a location for the scene...");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Scene", "*.scn"));

            File file = chooser.showSaveDialog(editor.getStage());

            if(file != null)
                SceneSerializer.save(file, editor.getScene());
        });

        menuFileExport.setOnAction(event -> {

        });

        menuFileImport.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Please select an animation or an image...");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Animation", "*.anim"));
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All", "*"));

            List<File> files = chooser.showOpenMultipleDialog(editor.getStage());

            if(files != null) {
                for(File file : files) {
                    try {
                        editor.getScene().getObjects().add(new SceneImage(file));
                    } catch(FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        menuFileExit.setOnAction(event -> Platform.exit());

        layersPane.init(editor);
        propertiesPane.init(editor);

        editor.getScene().getObjects()
                .addListener((ListChangeListener<SceneObject>) c -> sceneCanvas.redraw());
    }

    public Scene.Canvas getSceneCanvas() { return sceneCanvas; }

}
