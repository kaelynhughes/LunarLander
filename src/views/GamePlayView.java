package views;

import enums.DifficultyEnum;
import enums.GameStateEnum;
import enums.RenderLayerEnum;
import objects.*;
import edu.usu.graphics.Graphics2D;
import tools.KeyboardInput;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class GamePlayView extends GameStateView {

    private KeyboardInput inputKeyboard;
    private GameStateEnum nextGameState = GameStateEnum.GamePlay;
    // private GameModel gameModel;
    private final List<Moveable> moveables = new ArrayList<>();
    private final List<Rendered> rendered = new ArrayList<>();
    private final float windowSize = 1f;

    @Override
    public void initialize(Graphics2D graphics) {
        super.initialize(graphics);

        inputKeyboard = new KeyboardInput(graphics.getWindow());
        // When ESC is pressed, set the appropriate new game state
        inputKeyboard.registerCommand(GLFW_KEY_ESCAPE, true, (double elapsedTime) -> {
            nextGameState = GameStateEnum.MainMenu;
        });

        rendered.add(new Terrain(graphics, windowSize, RenderLayerEnum.MIDDLE, DifficultyEnum.LEVEL1));
        rendered.add(new Background(graphics, windowSize, RenderLayerEnum.BOTTOM));
        rendered.add(new ShipStatusMenu(graphics, windowSize, RenderLayerEnum.TOP));
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
