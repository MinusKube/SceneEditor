package fr.minuskube.editor.control;

import javafx.scene.control.TextField;

public class IntegerTextField extends TextField {

    public IntegerTextField() {
        this.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("\\d*"))
                this.setText(newValue.replaceAll("\\D", ""));
        });
    }

    public int getValue() {
        try {
            return Integer.parseInt(this.getText());
        } catch(NumberFormatException e) {
            return 0;
        }
    }

}
