package fr.minuskube.editor.scene.object;

import fr.minuskube.editor.animation.FrameRepeat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class SceneAnimation extends SceneObject {

    private ObservableList<SceneAnimationFrame> frames;
    private List<FrameRepeat> repeats;

    private boolean playing = true;
    private boolean looping = true;

    private int width, height;
    private boolean autoCrop;
    private float speed = 1;

    private int currentFrame = 0;
    private float currentFrameState = 0;

    public SceneAnimation() {
        this.frames = FXCollections.observableArrayList();
        this.repeats = new ArrayList<>();
    }

    @Override
    public void draw(GraphicsContext context) {
        update(); // XXX: TEMPORARY

        if(!this.frames.isEmpty()) {
            this.frames.get(currentFrame).draw(context);
        }
    }

    public void update() {
        if(!playing)
            return;

        SceneAnimationFrame frame = frames.get(currentFrame);

        currentFrameState += (1 / 60f) * speed; // TODO: Replace by deltaTime

        if(currentFrameState >= frame.getDuration()) {
            currentFrame++;

            for(FrameRepeat repeat : repeats) {
                if(repeat.getTimes() != -1 && repeat.getTimes() >= repeat.getCurrentTimes())
                    continue;

                if(currentFrame > repeat.getEnd())
                    currentFrame = repeat.getStart();

                repeat.setCurrentTimes(repeat.getCurrentTimes() + 1);
            }

            currentFrameState = 0;

            if(currentFrame >= frames.size()) {
                if(looping)
                    currentFrame = 0;
                else {
                    currentFrame--;
                    playing = false;
                }
            }
        }
    }

    public ObservableList<SceneAnimationFrame> getFrames() { return frames; }
    public List<FrameRepeat> getRepeats() { return repeats; }

    public boolean isPlaying() { return playing; }

    public boolean isLooping() { return looping; }
    public void setLooping(boolean looping) { this.looping = looping; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public float getSpeed() { return speed; }
    public void setSpeed(float speed) { this.speed = speed; }

}
