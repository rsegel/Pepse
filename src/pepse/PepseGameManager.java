package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
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
import static pepse.world.TagsToNames.getTag;
import pepse.Tags;

import java.util.List;


/**
 * The main class for the game.
 * This class is responsible for initializing the game and updating it.
 */
public class PepseGameManager extends GameManager {

    private static final int skyLayer = Layer.BACKGROUND - 1;
    private static final int NIGHT_LAYER = Layer.UI + 1; //TODO - consider
    private static final int CYCLE_DEFAULT = 30;
    private static final float OFFSIDE_AVATAR_Y = 200;
    private static final int MIN_INIT_RANGE = 0;
    private static final int MAX_INIT_RANGE = 1600;
    private static final float HALF_FLOAT_FACTOR = 0.5f;
    private static final int TWO = 2;
    private Vector2 windowDimensions;
    private float minLegitX;
    private float maxLegitX;
    private static final int NUM_OF_BLCK_FOR_BUFFER = 5;
    private static final float WORLD_BUFFER_FACTOR =  SIZE * NUM_OF_BLCK_FOR_BUFFER;
    private Terrain t;
    private Flora flora;
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
//        windowController.setTargetFramerate(10);
        windowDimensions = windowController.getWindowDimensions();
        minLegitX = - WORLD_BUFFER_FACTOR;
        maxLegitX = windowDimensions.x() + WORLD_BUFFER_FACTOR;
        Vector2 avatarPositionTopLeft = createAndInsertObjects(windowController, imageReader, inputListener);
        // windowController.getWindowDimensions().mult(0.5f) - initialAvatarLocation
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
        GameObject sun = Sun.create(windowController.getWindowDimensions(), CYCLE_DEFAULT);
        GameObject night = Night.create(windowController.getWindowDimensions(), CYCLE_DEFAULT);
        GameObject sunHalo = SunHalo.create(sun);
        Vector2 avatarPositionTopLeft = new Vector2(windowController.getWindowDimensions().x()/ TWO,
                Terrain.getGroundHeightAtX0()-OFFSIDE_AVATAR_Y);
        avatar = new Avatar(avatarPositionTopLeft, inputListener, imageReader);
        gameObjects().addGameObject(sky, skyLayer);
        t = new Terrain(windowController.getWindowDimensions(), 0);
        // TODO - decide if ok, needed Terrain to be before Sun
        gameObjects().addGameObject(sun, skyLayer);
        gameObjects().addGameObject(night, NIGHT_LAYER);
        EnergyRenderer energyRenderer = new EnergyRenderer(avatar::getEnergy);
        gameObjects().addGameObject(avatar);
        useTerrainToCreateGround(MIN_INIT_RANGE, MAX_INIT_RANGE);
        flora = new Flora();
        useFloraToCreateTrees(MIN_INIT_RANGE, MAX_INIT_RANGE);
        gameObjects().addGameObject(sunHalo, skyLayer);
        // TODO: smart formula for the OFFSIDE_AVATAR_Y
        gameObjects().addGameObject(energyRenderer, Layer.UI);
        return avatarPositionTopLeft;
    }

    private void useFloraToCreateTrees(int minInitRange, int maxInitRange) {
        List<Tree> treesList = flora.createInRange(minInitRange, maxInitRange);
        for (Tree tree : treesList){
            gameObjects().addGameObject(tree, Layer.STATIC_OBJECTS);
            avatar.addJumpObserver(tree.avatarJumped());
            List<Leaf> leaves = tree.getLeaves();
            for (Leaf leaf : leaves){
                gameObjects().addGameObject(leaf, Layer.STATIC_OBJECTS);
            }
            List<Fruit> fruits = tree.getFruits();
            for (Fruit fruit : fruits){
                gameObjects().addGameObject(fruit, Layer.STATIC_OBJECTS);
            }
        }
    }

    private void useTerrainToCreateGround(int minRange,int maxRange) {
        List<Block> blockList = t.createInRange(minRange, maxRange);
        for (Block block : blockList) {
            if (block.getTag().equals(TagsToNames.getTagName(Tags.TOP_LAYER_BLOCK)))
                gameObjects().addGameObject(block, Layer.STATIC_OBJECTS); // TODO: add to correct layer
            else gameObjects().addGameObject(block, Layer.BACKGROUND);
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
        useTerrainToCreateGround((int) min, (int) max);
        useFloraToCreateTrees((int) min, (int) max);
    }

    private void deleteNonVisibleObjects() {
        for (GameObject gameObject : gameObjects()) {
            // remove all objects that are not visible
            if (gameObject.getCenter().x() < minLegitX || gameObject.getCenter().x() > maxLegitX) {
                removeFromRightLayer(gameObject);
            }
        }
    }

    private void removeFromRightLayer(GameObject gameObject) {
        String tag = gameObject.getTag();
        Tags tagEnum = TagsToNames.getTag(tag);
        int layer = getLayer(tagEnum);
        gameObjects().removeGameObject(gameObject, layer);

    }
}