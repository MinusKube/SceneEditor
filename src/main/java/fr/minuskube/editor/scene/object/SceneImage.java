package fr.minuskube.editor.scene.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.DataFormat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SceneImage extends SceneObject {

    public static final DataFormat DATA_FORMAT = new DataFormat("s-editor/img");

    private File source;

    private transient Image image;
    private transient int width, height;

    public SceneImage(File source) throws FileNotFoundException {
        this.source = source;
        this.image = new Image(new FileInputStream(source));

        this.width = (int) image.getWidth();
        this.height = (int) image.getHeight();
    }

    public SceneImage(SceneImage image) {
        this.setX(image.getX());
        this.setY(image.getY());

        try {
            this.source = image.getSource();
            this.image = new Image(new FileInputStream(source));

            this.width = (int) this.image.getWidth();
            this.height = (int) this.image.getHeight();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        this.image = new Image(new FileInputStream(source));

        this.width = (int) image.getWidth();
        this.height = (int) image.getHeight();
    }

    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(image, 0, 0);
    }

    public Image getImage() { return image; }
    public File getSource() { return source; }

    @Override
    public int getWidth() { return width; }
    @Override
    public int getHeight() { return height; }

}
