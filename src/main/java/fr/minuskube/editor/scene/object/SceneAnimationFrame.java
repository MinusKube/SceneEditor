package fr.minuskube.editor.scene.object;

import java.io.File;
import java.io.FileNotFoundException;

public class SceneAnimationFrame extends SceneImage {

    private float duration;
    private int offsetX, offsetY;

    public SceneAnimationFrame(File source) throws FileNotFoundException {
        super(source);
    }

    public float getDuration() { return duration; }
    public void setDuration(float duration) { this.duration = duration; }

    public int getOffsetX() { return offsetX; }
    public void setOffsetX(int offsetX) { this.offsetX = offsetX; }

    public int getOffsetY() { return offsetY; }
    public void setOffsetY(int offsetY) { this.offsetY = offsetY; }

}
