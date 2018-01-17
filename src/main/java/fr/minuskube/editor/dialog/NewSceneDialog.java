package fr.minuskube.editor.dialog;

import fr.minuskube.editor.control.IntegerTextField;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Dimension2D;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.io.File;
import java.io.IOException;

public class NewSceneDialog extends Dialog<Dimension2D> {

    public NewSceneDialog() {
        this.setTitle("New Scene...");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dialog/newScene.fxml"));
            DialogPane root = loader.load();

            root.getStylesheets().add("file:///" + new File("src/main/resources/dialog/newScene.css")
                    .getAbsolutePath().replace('\\', '/'));

            this.setDialogPane(root);

            Controller controller = loader.getController();
            controller.initListeners(this);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static class Controller {

        @FXML private IntegerTextField widthField;
        @FXML private IntegerTextField heightField;

        public void initListeners(NewSceneDialog dialog) {
            Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
            Node cancelButton = dialog.getDialogPane().lookupButton(ButtonType.CANCEL);

            ChangeListener<String> changeListener = ((observable, oldValue, newValue) -> {
                boolean empty = newValue.isEmpty()
                        || widthField.getText().isEmpty()
                        || heightField.getText().isEmpty();

                okButton.setDisable(empty);
                cancelButton.setDisable(empty);
            });

            widthField.textProperty().addListener(changeListener);
            heightField.textProperty().addListener(changeListener);

            dialog.setResultConverter(buttonType -> {
                if(buttonType == ButtonType.OK) {
                    int width = widthField.getValue();
                    int height = heightField.getValue();

                    if(width <= 0 || height <= 0)
                        return null;

                    return new Dimension2D(width, height);
                }

                return null;
            });
        }

    }

}
