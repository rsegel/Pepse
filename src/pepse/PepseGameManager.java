package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import pepse.world.Sky;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;

public class PepseGameManager extends GameManager {

    private static final int skyLayer = 1;
    private static final int CYCLE_DEFAULT = 30;

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, skyLayer);
        GameObject sun = Sun.create(windowController.getWindowDimensions(), CYCLE_DEFAULT);
        gameObjects().addGameObject(sun, skyLayer);
        GameObject night = Night.create(windowController.getWindowDimensions(), CYCLE_DEFAULT);
        gameObjects().addGameObject(night, skyLayer);
    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
