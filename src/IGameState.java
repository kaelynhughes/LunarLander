import edu.usu.graphics.Graphics2D;
import enums.GameStateEnum;

public interface IGameState {
    void initialize(Graphics2D graphics);

    void initializeSession();

    GameStateEnum processInput(double elapsedTime);

    void update(double elapsedTime);

    void render(double elapsedTime);
}
