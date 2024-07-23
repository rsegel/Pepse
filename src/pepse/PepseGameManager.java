package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;
import pepse.world.trees.Fruit;
import pepse.world.trees.Leaf;
import pepse.world.trees.Tree;

import static pepse.LayerGetter.getLayer;
import static pepse.world.Block.SIZE;


import java.util.List;


/**
 * The main class for the game.
 * This class is responsible for initializing the game and updating it.
 */
public class PepseGameManager extends GameManager {

    /**
     * The default cycle length for the game.
     */
    public static final int CYCLE_DEFAULT = 30;
    private static final int MIN_INIT_RANGE = 0;
    private static final float HALF_FLOAT_FACTOR = 0.5f;
    private static final int TWO = 2;
    private static final int SEED = 10;
    private static final float AVATAR_Y_FACTOR = 1.5f;
    private static final float FRUIT_ENERGY = 10;
    private Vector2 windowDimensions;
    private float minLegitX;
    private float maxLegitX;
    private static final int NUM_OF_BLCK_FOR_BUFFER = 3;
    private static final float WORLD_BUFFER_FACTOR =  SIZE * NUM_OF_BLCK_FOR_BUFFER;
    private Terrain t;
    private Flora f;
    private Avatar avatar;


    /**
     * Initializes the game.
     * @param imageReader The image reader to use.
     * @param soundReader The sound reader to use.
     * @param inputListener The input listener to use.
     * @param windowController The window controller to use.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowDimensions = windowController.getWindowDimensions();
        minLegitX = - WORLD_BUFFER_FACTOR;
        maxLegitX = windowDimensions.x() + WORLD_BUFFER_FACTOR;
        Vector2 avatarPositionTopLeft = createAndInsertObjects(windowController, imageReader, inputListener);
        Vector2 dist_vector = new Vector2(
                windowController.getWindowDimensions().x() * HALF_FLOAT_FACTOR - avatarPositionTopLeft.x(),
                windowController.getWindowDimensions().y() * HALF_FLOAT_FACTOR - avatarPositionTopLeft.y()
        );
        setCamera(new Camera(avatar, dist_vector,
                windowController.getWindowDimensions(),
                windowController.getWindowDimensions()));
    }
    // TODO: create a factory for the objects
    private Vector2 createAndInsertObjects(WindowController windowController,
                                        ImageReader imageReader, UserInputListener inputListener) {
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        GameObject night = Night.create(windowController.getWindowDimensions(), CYCLE_DEFAULT);
        t = new Terrain(windowController.getWindowDimensions(), SEED);
        useTerrainToCreateGround(MIN_INIT_RANGE, (int) windowDimensions.x());
        Vector2 avatarPositionTopLeft = new Vector2(windowController.getWindowDimensions().x()/ TWO,
                Terrain.groundHeightAt(windowController.getWindowDimensions().x()) / AVATAR_Y_FACTOR);
        GameObject sun = Sun.create(windowController.getWindowDimensions(), CYCLE_DEFAULT);
        GameObject sunHalo = SunHalo.create(sun);
        avatar = new Avatar(avatarPositionTopLeft, inputListener, imageReader);
        gameObjects().addGameObject(sky, getLayer(TagsToNames.getTag(sky.getTag())));
        gameObjects().addGameObject(sun, getLayer(TagsToNames.getTag(sun.getTag())));
        gameObjects().addGameObject(night, getLayer(TagsToNames.getTag(night.getTag())));
        EnergyRenderer energyRenderer = new EnergyRenderer(avatar::getEnergy);
        gameObjects().addGameObject(avatar, getLayer(TagsToNames.getTag(avatar.getTag())));
        f = new Flora(Terrain::groundHeightAt, SEED,
                (eatenFruit, other) -> {
                    if (other.equals(avatar)){
                        avatar.addEnergy(FRUIT_ENERGY);
                        this.gameObjects().removeGameObject(eatenFruit,
                                getLayer(TagsToNames.getTag(eatenFruit.getTag())));
                        new ScheduledTask(avatar, CYCLE_DEFAULT, false,
                                () -> this.gameObjects().addGameObject(eatenFruit,
                                        getLayer(TagsToNames.getTag(eatenFruit.getTag()))));
                    }
                });
        useFloraToCreateTrees(MIN_INIT_RANGE, (int) windowDimensions.x());
        gameObjects().layers().shouldLayersCollide(LayerGetter.getLayer(Tags.AVATAR),
                LayerGetter.getLayer(Tags.FRUIT), true);
        gameObjects().addGameObject(sunHalo, getLayer(TagsToNames.getTag(sunHalo.getTag())));
        gameObjects().addGameObject(energyRenderer, getLayer(TagsToNames.getTag(energyRenderer.getTag())));
        return avatarPositionTopLeft;
    }


    private void useFloraToCreateTrees(int minInitRange, int maxInitRange) {
        List<GameObject> treesList = f.createInRange(minInitRange, maxInitRange);
        for (GameObject objectTree : treesList){
            Tree tree = (Tree) objectTree;
            gameObjects().addGameObject(tree, getLayer(TagsToNames.getTag(tree.getTag())));
            avatar.addJumpObserver(tree.avatarJumped());
            List<Leaf> leaves = tree.getLeaves();
            for (Leaf leaf : leaves){
                gameObjects().addGameObject(leaf, getLayer(TagsToNames.getTag(leaf.getTag())));
            }
            List<Fruit> fruits = tree.getFruits();
            for (Fruit fruit : fruits){
                gameObjects().addGameObject(fruit, getLayer(TagsToNames.getTag(fruit.getTag())));
            }
        }
    }

    private void useTerrainToCreateGround(int minRange,int maxRange) {
        List<GameObject> blockList = t.createInRange(minRange, maxRange);
        for (GameObject objectBlock : blockList) {
            Block block = (Block) objectBlock;
            if (block.getTag().equals(TagsToNames.getTagName(Tags.TOP_LAYER_BLOCK))) {
                gameObjects().addGameObject(block, getLayer(TagsToNames.getTag(block.getTag())));
            }
            else {
                gameObjects().addGameObject(block, getLayer(TagsToNames.getTag(block.getTag())));
            }
        }
    }
    /**
     * The main method of the game.
     * @param args The arguments to the main method.
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        infinityScrolling();
    }

    private void infinityScrolling() {
        float maxDisplayedX = camera().screenToWorldCoords(windowDimensions).x();
        float minDisplayedX = maxDisplayedX - windowDimensions.x();
        if (minDisplayedX < minLegitX) {
            changeObjsInRange((int) (minDisplayedX - WORLD_BUFFER_FACTOR), (int) minDisplayedX);
            minLegitX = minLegitX - WORLD_BUFFER_FACTOR;
            maxLegitX = maxLegitX - WORLD_BUFFER_FACTOR;
        }
        if (maxDisplayedX > maxLegitX) {
            changeObjsInRange((int) maxDisplayedX, (int) (maxDisplayedX + WORLD_BUFFER_FACTOR));
            maxLegitX = maxLegitX + WORLD_BUFFER_FACTOR;
            minLegitX = minLegitX + WORLD_BUFFER_FACTOR;
        }

    }

    private void changeObjsInRange(int min, int max) {
        deleteNonVisibleObjects();
        useTerrainToCreateGround(min, max);
        useFloraToCreateTrees(min, max);
    }

    private void deleteNonVisibleObjects() {
        for (GameObject gameObject : gameObjects()) {
            // remove all objects that are not visible
            if (gameObject.getCenter().x() < minLegitX || gameObject.getCenter().x() > maxLegitX) {
                if(gameObject.getTag().equals(TagsToNames.getTagName(Tags.TREE)) ||
                        gameObject.getTag().equals(TagsToNames.getTagName(Tags.BLOCK)) ||
                        gameObject.getTag().equals(TagsToNames.getTagName(Tags.TOP_LAYER_BLOCK))){
                    removeFromRightLayer(gameObject);
                    if (gameObject.getTag().equals(TagsToNames.getTagName(Tags.TREE))){
                        avatar.removeJumpObserver(((Tree) gameObject).avatarJumped());}
                }
            }
        }
    }

    private void removeFromRightLayer(GameObject gameObject) {
        String tag = gameObject.getTag();
        Tags tagEnum = TagsToNames.getTag(tag);
        int layer = getLayer(tagEnum);
        if (gameObject.getTag().equals(TagsToNames.getTagName(Tags.TREE))) {
            Tree tree = (Tree) gameObject;
            for (Leaf leaf : tree.getLeaves()) {
                gameObjects().removeGameObject(leaf, layer);
            }
            for (Fruit fruit : tree.getFruits()) {
                gameObjects().removeGameObject(fruit, layer);
            }
        }
        gameObjects().removeGameObject(gameObject, layer);

    }
}