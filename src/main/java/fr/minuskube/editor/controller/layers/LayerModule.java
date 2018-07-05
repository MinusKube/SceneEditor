package fr.minuskube.editor.controller.layers;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class LayerModule extends AbstractModule {

    private ListView<HBox> layers;
    private int index;

    public LayerModule(ListView<HBox> layers, int index) {
        this.layers = layers;
        this.index = index;
    }

    @Override
    protected void configure() {
        this.bind(Integer.class)
                .annotatedWith(Names.named("index"))
                .toInstance(this.index);

        this.bind(new TypeLiteral<ListView<HBox>>() {})
                .toInstance(this.layers);
    }

}
