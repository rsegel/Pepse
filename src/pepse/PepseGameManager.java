package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.Terrain;
import pepse.world.daynight.SunHalo;
import pepse.world.Avatar;

import java.util.List;

public class PepseGameManager extends GameManager {

    private static final int skyLayer = -101;
    private static final int CYCLE_DEFAULT = 30;
    private static final float OFFSIDE_AVATAR_Y = 50;

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, skyLayer);
        GameObject sun = Sun.create(windowController.getWindowDimensions(), CYCLE_DEFAULT);
        gameObjects().addGameObject(sun, skyLayer);
        GameObject night = Night.create(windowController.getWindowDimensions(), CYCLE_DEFAULT);
        gameObjects().addGameObject(night, skyLayer);
        Terrain t = new Terrain(windowController.getWindowDimensions(), 0);
        List<Block> blockList = t.createInRange(1,1600);
        // TODO: add functionality to add only the top blocks in each column to a layer that supports collisions
        for (Block block : blockList) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS); // TODO: add to correct layer
        }
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, skyLayer);
        // TODO: smart formula for the OFFSIDE_AVATAR_Y
        Vector2 avatarPositionTopLeft = new Vector2(0, Terrain.getGroundHeightAtX0()-OFFSIDE_AVATAR_Y);
        Avatar avatar = new Avatar(avatarPositionTopLeft, inputListener, imageReader);
        gameObjects().addGameObject(avatar);



    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
