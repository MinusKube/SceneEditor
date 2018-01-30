package fr.minuskube.editor.scene.object;

import java.io.File;
import java.io.FileNotFoundException;

public class SceneAnimationFrame extends SceneImage {

    private float duration;

    public SceneAnimationFrame(File source) throws FileNotFoundException {
        super(source);
    }

    public float getDuration() { return duration; }
    public void setDuration(float duration) { this.duration = duration; }

}
