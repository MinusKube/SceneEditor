package fr.minuskube.editor.controller.layers;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class LayerContextModule extends AbstractModule {

    private LayerController controller;

    public LayerContextModule(LayerController controller) {
        this.controller = controller;
    }

    @Override
    protected void configure() {
        this.bind(LayerController.class)
                .annotatedWith(Names.named("parent"))
                .toInstance(this.controller);
    }

}
