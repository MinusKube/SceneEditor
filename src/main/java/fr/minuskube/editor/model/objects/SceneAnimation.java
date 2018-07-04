package fr.minuskube.editor.model.objects;

import fr.minuskube.editor.model.AnimationFrame;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class SceneAnimation extends SceneObject {

    private ObservableList<AnimationFrame> frames = FXCollections.observableArrayList();
    private IntegerProperty currentFrameIndex = new SimpleIntegerProperty(this, "currentFrameIndex");
    private ReadOnlyObjectWrapper<AnimationFrame> currentFrame = new ReadOnlyObjectWrapper<>(this, "currentFrame");

    private BooleanProperty loop = new SimpleBooleanProperty(this, "loop");

    private float currentFrameState = 0;

    public SceneAnimation() {
        this.currentFrameIndex.addListener((observable, oldValue, newValue) -> {
            int newIndex = newValue.intValue();

            if(newIndex < 0 || newIndex >= this.frames.size())
                newIndex = 0;

            this.currentFrame.set(this.frames.get(newIndex));
            this.currentFrameState = 0;
        });
    }

    public SceneAnimation(List<AnimationFrame> frames) {
        this();

        this.frames.addAll(frames);
    }

    @Override
    public SceneObject clone() {
        return new SceneAnimation(this.frames);
    }

    @Override
    public void update(float deltaTime) {
        AnimationFrame frame = this.currentFrame.get();

        this.currentFrameState += deltaTime;

        if(this.currentFrameState >= frame.getDuration())
            this.currentFrameIndex.add(1);
    }

    @Override
    public void render(GraphicsContext context) {
        AnimationFrame frame = this.currentFrame.get();

        context.drawImage(frame.getImage(), this.x, this.y);
    }

    @Override
    public void renderThumbnail(GraphicsContext context) {
        AnimationFrame frame = this.currentFrame.get();

        context.drawImage(frame.getImage(), 0, 0);
    }

    public ObservableList<AnimationFrame> getFrames() { return this.frames; }

    public IntegerProperty currentFrameIndexProperty() { return this.currentFrameIndex; }
    public int getCurrentFrameIndex() { return this.currentFrameIndex.get(); }
    public void setCurrentFrameIndex(int currentFrameIndex) { this.currentFrameIndex.set(currentFrameIndex); }

    public ReadOnlyObjectProperty<AnimationFrame> currentFrameProperty() {
        return this.currentFrame.getReadOnlyProperty();
    }
    public AnimationFrame getCurrentFrame() { return this.currentFrame.get(); }

    public BooleanProperty loopProperty() { return this.loop; }
    public boolean isLoop() { return this.loop.get(); }
    public void setLoop(boolean loop) { this.loop.set(loop); }

}