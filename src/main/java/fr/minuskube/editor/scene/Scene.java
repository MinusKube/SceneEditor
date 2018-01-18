package fr.minuskube.editor.scene;

import fr.minuskube.editor.control.DrawableCanvas;
import fr.minuskube.editor.scene.object.SceneImage;
import fr.minuskube.editor.scene.object.SceneObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scene {

    private Stage stage;
    private Canvas canvas;

    private int width, height;
    private ObservableList<SceneObject> objects;
    private ObservableList<SceneObject> selectedObjects;

    private int scrollX, scrollY;
    private double zoom = 1;

    private File saveLocation;

    public Scene(Stage stage, Canvas canvas, int width, int height) {
        this.stage = stage;
        this.canvas = canvas;

        this.width = width;
        this.height = height;

        this.objects = FXCollections.observableArrayList();
        this.selectedObjects = FXCollections.observableArrayList();
    }

    public void init() {
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            int distance = event.isShiftDown() ? 10 : 1;

            if(event.getCode() == KeyCode.Z)
                scrollY -= distance;
            else if(event.getCode() == KeyCode.S)
                scrollY += distance;
            else if(event.getCode() == KeyCode.Q)
                scrollX -= distance;
            else if(event.getCode() == KeyCode.D)
                scrollX += distance;

            else if(event.getCode() == KeyCode.UP)
                moveSelectedObjects(0, -distance);
            else if(event.getCode() == KeyCode.DOWN)
                moveSelectedObjects(0, distance);
            else if(event.getCode() == KeyCode.LEFT)
                moveSelectedObjects(-distance, 0);
            else if(event.getCode() == KeyCode.RIGHT)
                moveSelectedObjects(distance, 0);

            else if(event.getCode() == KeyCode.DELETE) {
                objects.removeIf(SceneObject::isSelected);
                selectedObjects.clear();
            }

            else if(event.isControlDown() && event.getCode() == KeyCode.C) {
                ClipboardContent content = new ClipboardContent();

                List<SceneObject> selected = new ArrayList<>();
                List<File> files = new ArrayList<>();

                for(SceneObject object : selectedObjects) {
                    if(object instanceof SceneImage)
                        files.add(((SceneImage) object).getSource());

                    selected.add(object);
                }

                content.put(SceneImage.DATA_FORMAT, selected);
                content.putFiles(files);

                Clipboard clipboard = Clipboard.getSystemClipboard();
                clipboard.setContent(content);
            }

            else if(event.isControlDown() && event.getCode() == KeyCode.V) {
                Clipboard clipboard = Clipboard.getSystemClipboard();

                Object list = clipboard.getContent(SceneImage.DATA_FORMAT);

                if(list instanceof List<?>) {
                    for(Object obj : (List<?>) list) {
                        if(obj instanceof SceneObject)
                            objects.add((SceneObject) obj);
                    }
                }
                else {
                    for(File file : clipboard.getFiles()) {
                        try {
                            ImageIO.read(file);

                            SceneImage object = new SceneImage(file);
                            objects.add(object);
                        } catch(IOException ignored) {}
                    }
                }
            }

            canvas.redraw();
        });
    }

    public void reset() {
        saveLocation = null;

        scrollX = (int) (canvas.getWidth() - width) / 2;
        scrollY = (int) (canvas.getHeight() - height) / 2;
        zoom = 1;

        objects.clear();
    }

    private void moveSelectedObjects(int x, int y) {
        for(SceneObject object : selectedObjects) {
            object.setX(object.getX() + x);
            object.setY(object.getY() + y);
        }
    }

    public Canvas getCanvas() { return canvas; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public ObservableList<SceneObject> getSelectedObjects() { return selectedObjects; }
    public ObservableList<SceneObject> getObjects() { return objects; }

    public int getScrollX() { return scrollX; }
    public void setScrollX(int scrollX) { this.scrollX = scrollX; }

    public int getScrollY() { return scrollY; }
    public void setScrollY(int scrollY) { this.scrollY = scrollY; }

    public double getZoom() { return zoom; }
    public void setZoom(double zoom) { this.zoom = zoom; }

    public File getSaveLocation() { return saveLocation; }
    public void setSaveLocation(File saveLocation) { this.saveLocation = saveLocation; }

    public static class Canvas extends DrawableCanvas {

        private int mouseX, mouseY;

        private boolean wasPrimaryButtonDown;

        public Canvas() {}

        public void init(Scene scene) {
            Color selectColor = Color.rgb(0, 100, 0, 0.5);
            Color hoverColor = Color.rgb(0, 100, 100, 0.5);

            RadialGradient gradient = new RadialGradient(
                    0, 0, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE,
                    new Stop(1, Color.gray(0.08)),
                    new Stop(0, Color.gray(0.15))
            );

            setDrawConsumer(context -> {
                context.setFill(gradient);
                context.fillRect(0, 0, getWidth(), getHeight());

                Affine affine = new Affine();
                affine.append(new Translate(scene.getScrollX(), scene.getScrollY()));
                affine.append(new Scale(scene.getZoom(), scene.getZoom()));

                context.setTransform(affine);
                context.save();

                if(scene.getWidth() != 0 && scene.getHeight() != 0) {
                    context.setFill(Color.BLACK);
                    context.fillRect(-2, -2, scene.getWidth() + 4, scene.getHeight() + 4);

                    context.setFill(Color.DARKGRAY);

                    context.beginPath();
                    context.rect(0, 0, scene.getWidth(), scene.getHeight());
                    context.fill();
                    context.closePath();

                    context.clip();
                }

                scene.getObjects().forEach(object -> {
                    context.save();
                    context.translate(object.getX(), object.getY());

                    object.draw(context);

                    context.restore();
                });

                context.restore();

                scene.getObjects().forEach(object -> {
                    if(object.isHovered() || object.isSelected()) {
                        if(object.isSelected())
                            context.setStroke(selectColor);
                        else
                            context.setStroke(hoverColor);

                        context.setLineWidth(4);

                        context.strokeRect(object.getX() - 2, object.getY() - 2,
                                object.getWidth() + 4, object.getHeight() + 4);
                    }
                });

                context.setTransform(new Affine());
            });

            setEventHandler(ScrollEvent.SCROLL, event -> {
                double deltaY = event.getDeltaY() / 500;

                double zoom = scene.getZoom() + deltaY;
                zoom = Math.max(zoom, 0.1);
                zoom = Math.min(zoom, 5);

                scene.setZoom(zoom);

                redraw();
            });

            setEventHandler(MouseEvent.ANY, event -> {
                int oldMouseX = mouseX;
                int oldMouseY = mouseY;

                mouseX = (int) event.getX();
                mouseY = (int) event.getY();

                int screenX = (int) ((mouseX - scene.getScrollX()) / scene.getZoom());
                int screenY = (int) ((mouseY - scene.getScrollY()) / scene.getZoom());

                int distMouseX = mouseX - oldMouseX;
                int distMouseY = mouseY - oldMouseY;

                if(event.isMiddleButtonDown()) {
                    scene.setScrollX(scene.getScrollX() + distMouseX);
                    scene.setScrollY(scene.getScrollY() + distMouseY);
                }

                boolean hovered = false;
                ObservableList<SceneObject> objects = scene.getObjects();

                for(int i = objects.size() - 1; i >= 0; i--) {
                    SceneObject object = objects.get(i);

                    if(!hovered && !event.isPrimaryButtonDown()) {
                        object.setHovered(
                                screenX >= object.getX() && screenY >= object.getY()
                                        && screenX <= object.getX() + object.getWidth()
                                        && screenY <= object.getY() + object.getHeight()
                        );
                    }
                    else if(hovered)
                        object.setHovered(false);

                    if(object.isHovered())
                        hovered = true;

                    if(!this.wasPrimaryButtonDown && event.isPrimaryButtonDown()) {
                        if(object.isHovered() || !event.isControlDown()) {
                            if(object.isHovered()) {
                                object.setSelected(true);
                                scene.getSelectedObjects().add(object);
                            }
                            else {
                                object.setSelected(false);
                                scene.getSelectedObjects().remove(object);
                            }
                        }
                    }

                    if(event.isPrimaryButtonDown() && object.isSelected()) {
                        object.setX(object.getX() + (int) (distMouseX / scene.getZoom()));
                        object.setY(object.getY() + (int) (distMouseY / scene.getZoom()));
                    }
                }

                redraw();

                this.wasPrimaryButtonDown = event.isPrimaryButtonDown();
            });
        }

        public int getMouseX() { return mouseX; }
        public int getMouseY() { return mouseY; }

    }

}
