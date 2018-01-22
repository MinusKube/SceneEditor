package fr.minuskube.editor.control;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class DraggableListCell<T> extends ListCell<T> {

    private static final DataFormat DATA_FORMAT = new DataFormat("s-editor/drag");

    private boolean dragging = false;

    @SuppressWarnings("unchecked")
    public DraggableListCell() {
        setOnDragDetected(event -> {
            if(getItem() == null)
                return;

            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.put(DATA_FORMAT, getIndex());

            dragboard.setContent(content);
            event.consume();
        });

        setOnDragOver(event -> {
            if(getItem() == null)
                return;

            Dragboard dragboard = event.getDragboard();

            if(event.getGestureSource() != this && dragboard.hasContent(DATA_FORMAT))
                event.acceptTransferModes(TransferMode.MOVE);

            event.consume();
        });

        setOnDragEntered(event -> {
            if(getItem() == null)
                return;

            Dragboard dragboard = event.getDragboard();

            if(event.getGestureSource() != this && dragboard.hasContent(DATA_FORMAT)) {
                getStyleClass().remove("cell-drag-over-top");
                getStyleClass().remove("cell-drag-over-bottom");

                if(getIndex() <= (int) dragboard.getContent(DATA_FORMAT))
                    getStyleClass().add("cell-drag-over-top");
                else
                    getStyleClass().add("cell-drag-over-bottom");
            }
        });

        setOnDragExited(event -> {
            if(getItem() == null)
                return;

            Dragboard dragboard = event.getDragboard();

            if(event.getGestureSource() != this && dragboard.hasContent(DATA_FORMAT)) {
                getStyleClass().remove("cell-drag-over-top");
                getStyleClass().remove("cell-drag-over-bottom");
            }
        });

        setOnDragDropped(event -> {
            if(getItem() == null)
                return;

            Dragboard dragboard = event.getDragboard();
            boolean success = false;

            if(dragboard.hasContent(DATA_FORMAT)) {
                ObservableList<T> items = getListView().getItems();

                int draggedIndex = (int) dragboard.getContent(DATA_FORMAT);
                int thisIndex = items.indexOf(getItem());

                T draggedItem = items.get(draggedIndex);

                items.remove(draggedIndex);
                items.add(thisIndex, draggedItem);

                success = true;
            }

            event.setDropCompleted(success);
            event.consume();
        });

        setOnDragDone(DragEvent::consume);
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if(empty) {
            this.setText(null);
            this.setGraphic(null);
        }
        else if(item instanceof Node) {
            this.setText(null);

            Node graphic = this.getGraphic();
            Node nodeItem = (Node) item;

            if(graphic == null || !graphic.equals(nodeItem))
                this.setGraphic(nodeItem);
        }
        else {
            this.setText(String.valueOf(item));
            this.setGraphic(null);
        }

    }

}
