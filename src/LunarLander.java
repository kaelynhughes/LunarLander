import edu.usu.graphics.Color;
import edu.usu.graphics.Graphics2D;
import objects.MyRandom;

public class LunarLander {

    public static void main(String[] args) {
        try (Graphics2D graphics = new Graphics2D(1920, 1080, "ECS - Snake Game")) {
            graphics.initialize(Color.BLACK);
            Game game = new Game(graphics);
            game.initialize();
            game.run();
            game.shutdown();
        }
    }
}
