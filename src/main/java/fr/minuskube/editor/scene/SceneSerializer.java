package fr.minuskube.editor.scene;

import fr.minuskube.editor.scene.object.SceneImage;
import fr.minuskube.editor.scene.object.SceneObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SceneSerializer {

    // XXX: This class is temporary! (Yes it's ugly)

    public static void save(File file, Scene scene) {
        try(FileOutputStream fileOut = new FileOutputStream(file);
            DataOutputStream out = new DataOutputStream(fileOut)) {

            out.writeInt(scene.getWidth());
            out.writeInt(scene.getHeight());

            out.writeInt(scene.getScrollX());
            out.writeInt(scene.getScrollY());

            out.writeInt(scene.getObjects().size());

            for(SceneObject object : scene.getObjects()) {
                out.writeInt(object.getX());
                out.writeInt(object.getY());

                if(object instanceof SceneImage)
                    out.writeUTF(((SceneImage) object).getSource().getAbsolutePath());
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void load(File file, Scene scene) {
        try(FileInputStream fileIn = new FileInputStream(file);
             DataInputStream in = new DataInputStream(fileIn)) {

            scene.setWidth(in.readInt());
            scene.setHeight(in.readInt());

            scene.setScrollX(in.readInt());
            scene.setScrollY(in.readInt());

            int size = in.readInt();
            for(int i = 0; i < size; i++) {
                int x = in.readInt();
                int y = in.readInt();
                File source = new File(in.readUTF());

                SceneImage object = new SceneImage(source);
                object.setX(x);
                object.setY(y);

                scene.getObjects().add(object);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
