package fr.minuskube.editor.scene.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SceneImage extends SceneObject {

    private Image image;
    private File source;

    private int width, height;

    public SceneImage(File source) throws FileNotFoundException {
        this.image = new Image(new FileInputStream(source));
        this.source = source;

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
