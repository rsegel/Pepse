package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class Tree extends GameObject{

    private static final float TREE_HEIGHT = 150;
    private static final float TREE_WIDTH = 25;
    private static final Color TREE_COLOR = new Color(100, 50, 20);
    private static final int CANOPY_SIZE = 8;
    private static final float PROBABILITY_FOR_LEAF = 0.7f;
    private static final float PROBABILITY_FOR_FRUIT = 0.2f;
    private static final float TURN_WHEN_JUMPS = 90;
    private ArrayList<Leaf> leaves;
    private ArrayList<Fruit> fruits;

    public Tree(Vector2 location){
        super(new Vector2(location.x(),location.y() - TREE_HEIGHT),
                new Vector2(TREE_WIDTH, TREE_HEIGHT),
                new RectangleRenderable(TREE_COLOR));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        this.leaves = new ArrayList<>();
        this.fruits = new ArrayList<>();
        int leafSize = Leaf.getLeafSize();
        int xOffset = (int) (leafSize * CANOPY_SIZE - TREE_WIDTH) / 2;
        int yOffset = (int) (leafSize * CANOPY_SIZE) / 2;
        for (int i = 0; i < CANOPY_SIZE; i++){
            for (int j = 0; j < CANOPY_SIZE; j++){
                Vector2 canopyLocation = new Vector2(
                        location.x() + i * leafSize - xOffset,
                        location.y() - TREE_HEIGHT + j * leafSize - yOffset
                );
                if (Math.random() < PROBABILITY_FOR_LEAF) {
                    this.leaves.add(new Leaf(canopyLocation));
                }
                else if (Math.random() < PROBABILITY_FOR_FRUIT){
                    this.fruits.add(new Fruit(canopyLocation));
                }
            }
        }
    }

    public ArrayList<Leaf> getLeaves(){
        return this.leaves;
    }

    public ArrayList<Fruit> getFruits(){
        return this.fruits;
    }

    public Runnable avatarJumped() {
        return () -> {
            for (Leaf leaf : this.leaves){
                leaf.avatarJumped();
            }
            for (Fruit fruit : this.fruits){
                fruit.avatarJumped();
            }
        };
    }
}
