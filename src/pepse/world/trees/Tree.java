package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.Tags;
import pepse.util.ColorSupplier;
import pepse.world.TagsToNames;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Represents a tree in the game
 */
public class Tree extends GameObject{

    private static final float TREE_HEIGHT = 150;
    private static final float TREE_WIDTH = 25;
    private static final int CANOPY_SIZE = 8;
    private static final float PROBABILITY_FOR_LEAF = 0.7f;
    private static final float PROBABILITY_FOR_FRUIT = 0.2f;
    private static final Color BASE_TREE_COLOR = new Color(100, 50, 20);
    private static final int TWO = 2;
    private final ArrayList<Leaf> leaves;
    private final ArrayList<Fruit> fruits;
    private final Random random;
    /**
     * Creates a tree at the given location
     * @param location the location of the tree
     */
    public Tree(Vector2 location, int randomSeed){
        super(new Vector2(location.x(),location.y() - TREE_HEIGHT),
                new Vector2(TREE_WIDTH, TREE_HEIGHT),
                new RectangleRenderable(ColorSupplier.approximateColor(BASE_TREE_COLOR)));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        this.random = new Random(Objects.hash(randomSeed, location.x()));
        this.leaves = new ArrayList<>();
        this.fruits = new ArrayList<>();
        int leafSize = Leaf.getLeafSize();
        int xOffset = (int) (leafSize * CANOPY_SIZE - TREE_WIDTH) / TWO;
        int yOffset = (int) (leafSize * CANOPY_SIZE) / TWO;
        setLeavesAndFruits(leafSize, xOffset, yOffset, location);
        this.setTag(TagsToNames.getTagName(Tags.TREE));
    }

    private void setLeavesAndFruits(int leafSize, int xOffset, int yOffset, Vector2 location) {
        for (int i = 0; i < CANOPY_SIZE; i++){
            for (int j = 0; j < CANOPY_SIZE; j++){
                Vector2 canopyLocation = new Vector2(
                        location.x() + i * leafSize - xOffset,
                        location.y() - TREE_HEIGHT + j * leafSize - yOffset
                );
                if (this.random.nextFloat() < PROBABILITY_FOR_LEAF) {
                    this.leaves.add(new Leaf(canopyLocation));
                }
                else if (this.random.nextFloat() < PROBABILITY_FOR_FRUIT){
                    this.fruits.add(new Fruit(canopyLocation));
                }
            }
        }
    }
    /**
     * getter for the leaves of the tree
     * @return the leaves of the tree
     */
    public ArrayList<Leaf> getLeaves(){
        return this.leaves;
    }
    /**
     * getter for the fruits of the tree
     * @return the fruits of the tree
     */
    public ArrayList<Fruit> getFruits(){
        return this.fruits;
    }
    /**
     * Observer for the avatar jump
     * @return the observer for the avatar jump
     */
    public Runnable avatarJumped() {
        return () -> {
            this.renderer().setRenderable(new RectangleRenderable(
                    ColorSupplier.approximateColor(BASE_TREE_COLOR)));
            for (Leaf leaf : this.leaves){
                leaf.avatarJumped();
            }
            for (Fruit fruit : this.fruits){
                fruit.avatarJumped();
            }
        };
    }
}
