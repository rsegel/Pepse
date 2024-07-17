package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;
import pepse.world.trees.Fruit;
import pepse.world.trees.Leaf;
import pepse.world.trees.Tree;

import java.util.List;

public class PepseGameManager extends GameManager {

    private static final int skyLayer = Layer.BACKGROUND;
    private static final int NIGHT_LAYER = Layer.UI + 1; //TODO - consider
    private static final int CYCLE_DEFAULT = 30;
    private static final float OFFSIDE_AVATAR_Y = 50;

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, skyLayer);
        Terrain t = new Terrain(windowController.getWindowDimensions(), 0);
        // TODO - decide if ok, needed Terrain to be before Sun
        GameObject sun = Sun.create(windowController.getWindowDimensions(), CYCLE_DEFAULT);
        gameObjects().addGameObject(sun, skyLayer);
        GameObject night = Night.create(windowController.getWindowDimensions(), CYCLE_DEFAULT);
        gameObjects().addGameObject(night, NIGHT_LAYER);
        List<Block> blockList = t.createInRange(1,1600);
        // TODO: add functionality to add only the top blocks in each column to a layer that supports collisions
        for (Block block : blockList) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS); // TODO: add to correct layer
        }
        Flora flora = new Flora();
        List<Tree> treesList = flora.createInRange(0, (int) windowController.getWindowDimensions().x());
        for (Tree tree : treesList){
            gameObjects().addGameObject(tree, Layer.STATIC_OBJECTS);
            List<Leaf> leaves = tree.getLeaves();
            for (Leaf leaf : leaves){
                gameObjects().addGameObject(leaf, Layer.UI);
            }
            List<Fruit> fruits = tree.getFruits();
            for (Fruit fruit : fruits){
                gameObjects().addGameObject(fruit, Layer.UI);
            }
        }
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, skyLayer);
        // TODO: smart formula for the OFFSIDE_AVATAR_Y
        Vector2 avatarPositionTopLeft = new Vector2(0, Terrain.getGroundHeightAtX0()-OFFSIDE_AVATAR_Y);
        Avatar avatar = new Avatar(avatarPositionTopLeft, inputListener, imageReader);
        gameObjects().addGameObject(avatar);
        EnergyRenderer energyRenderer = new EnergyRenderer(avatar::getEnergy);
        gameObjects().addGameObject(energyRenderer, Layer.UI);





    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
