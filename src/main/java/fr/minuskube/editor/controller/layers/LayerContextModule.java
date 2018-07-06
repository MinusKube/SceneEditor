package fr.minuskube.editor.controller.layers;

import com.google.inject.name.Names;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class LayerContextModule extends LayerModule {

    private LayerController controller;

    public LayerContextModule(ListView<HBox> layers, int index, LayerController controller) {
        super(layers, index);

        this.controller = controller;
    }

    @Override
    protected void configure() {
        super.configure();

        this.bind(LayerController.class)
                .annotatedWith(Names.named("parent"))
                .toInstance(this.controller);
    }

}
