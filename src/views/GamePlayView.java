package views;

import enums.DifficultyEnum;
import enums.GameStateEnum;
import enums.RenderLayerEnum;
import objects.*;
import edu.usu.graphics.Graphics2D;
import tools.CollisionDetector;
import tools.KeyboardInput;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class GamePlayView extends GameStateView {

    private KeyboardInput inputKeyboard;
    private GameStateEnum nextGameState = GameStateEnum.GamePlay;
    private CollisionDetector collisionDetector;
    private final List<Moveable> moveables = new ArrayList<>();
    private final List<Rendered> rendered = new ArrayList<>();
    private final float windowSize = 1f;
    private Ship ship;
    private PauseMenu pauseMenu;

    @Override
    public void initialize(Graphics2D graphics) {
        super.initialize(graphics);

        inputKeyboard = new KeyboardInput(graphics.getWindow());
        // When ESC is pressed, set the appropriate new game state
        inputKeyboard.registerCommand(GLFW_KEY_ESCAPE, true, (double elapsedTime) -> {
            nextGameState = GameStateEnum.MainMenu;
        });

        Terrain terrain = new Terrain(graphics, windowSize, RenderLayerEnum.MIDDLE, DifficultyEnum.LEVEL1);
        rendered.add(terrain);
        rendered.add(new Background(graphics, windowSize, RenderLayerEnum.BOTTOM));
        ShipStatusMenu menu = new ShipStatusMenu(graphics, windowSize, RenderLayerEnum.ALRIGHT);
        rendered.add(menu);

        ship = new Ship(inputKeyboard, graphics, windowSize, RenderLayerEnum.MIDDLE);
        rendered.add(ship);
        moveables.add(ship);
        menu.registerShip(ship);

        collisionDetector = new CollisionDetector(terrain, ship);
        pauseMenu = new PauseMenu(graphics, windowSize, RenderLayerEnum.TOP);

    }

    @Override
    public void initializeSession() {
        // gameModel = new GameModel();
        // gameModel.initialize(graphics);
        nextGameState = GameStateEnum.GamePlay;
    }

    @Override
    public GameStateEnum processInput(double elapsedTime) {
        // Updating the keyboard can change the nextGameState
        inputKeyboard.update(elapsedTime);
        return nextGameState;
    }

    @Override
    public void update(double elapsedTime) {
        for (Moveable object: moveables) {
            object.update(elapsedTime);
        }
        if (collisionDetector.getCollision()) {
            if (collisionDetector.getSafeCollision()) {
                ship.land();
                pauseMenu.land();
            } else {
                ship.crash();
                pauseMenu.crash();
            }
        }
        // gameModel.update(elapsedTime);
    }

    @Override
    public void render(double elapsedTime) {
        for (Rendered object: rendered) {
            object.renderObject();
        }
        // Nothing to do because the render now occurs in the update of the game model
    }
}
