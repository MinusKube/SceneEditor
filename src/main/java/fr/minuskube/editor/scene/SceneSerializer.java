package fr.minuskube.editor.scene;

import java.io.File;

public class SceneSerializer {

    public static void save(File file, Scene scene) {
        /*try(FileOutputStream fileOut = new FileOutputStream(file);
            DataOutputStream out = new DataOutputStream(fileOut)) {

            out.writeInt(scene.getScrollX());
            out.writeInt(scene.getScrollY());

            out.writeInt(scene.getObjects().size());

            for(SceneObject object : scene.getObjects()) {
                out.writeInt(object.getX());
                out.writeInt(object.getY());
                out.writeUTF(object.getSource().getAbsolutePath());
            }
        } catch(IOException e) {
            e.printStackTrace();
        }*/
    }

    public static void load(File file, Scene scene) {
        /*try(FileInputStream fileIn = new FileInputStream(file);
            DataInputStream in = new DataInputStream(fileIn)) {

            scene.setScrollX(in.readInt());
            scene.setScrollY(in.readInt());

            int size = in.readInt();
            for(int i = 0; i < size; i++) {
                int x = in.readInt();
                int y = in.readInt();
                File source = new File(in.readUTF());

                SceneObject object = new SceneObject(source);
                object.setX(x);
                object.setY(y);

                scene.getObjects().add(object);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }*/
    }

}
