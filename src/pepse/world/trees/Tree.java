package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Tree extends GameObject{

    private static final float TREE_HEIGHT = 100;
    private static final float TREE_WIDTH = 20;
    private static final Color TREE_COLOR = new Color(100, 50, 20);


    public Tree(Vector2 location){
        super(new Vector2(location.x(),location.y() + TREE_HEIGHT),
                new Vector2(TREE_WIDTH, TREE_HEIGHT),
                new RectangleRenderable(TREE_COLOR));
    }
}
