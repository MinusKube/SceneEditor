package fr.minuskube.editor;

import com.google.inject.AbstractModule;
import fr.minuskube.editor.model.Scene;
import fr.minuskube.editor.model.Window;

public class SceneEditorModule extends AbstractModule {

    private SceneEditorApp app;

    public SceneEditorModule(SceneEditorApp app) {
        this.app = app;
    }

    @Override
    protected void configure() {
        Window window = new Window();

        this.bind(SceneEditorApp.class).toInstance(this.app);

        this.bind(Window.class).toInstance(window);
        this.bind(Scene.class).toInstance(window.currentSceneProperty().get());
    }

}
