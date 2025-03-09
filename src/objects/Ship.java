package objects;

import edu.usu.graphics.Graphics2D;
import edu.usu.graphics.Rectangle;
import edu.usu.graphics.Texture;
import enums.ColorsEnum;
import enums.RenderLayerEnum;
import org.joml.Vector2f;
import org.joml.Vector3f;
import tools.KeyboardInput;

import static org.lwjgl.glfw.GLFW.*;

public class Ship implements Moveable, Rendered {
    private Graphics2D graphics;
    private float windowSize;
    private RenderLayerEnum layer;

    private float rotation;
    private Vector3f center;
    private double fuel = 100;
    private Vector2f velocity = new Vector2f(0, 0);

    private boolean paused = false;
    private boolean crashed = false;
    private boolean landed = false;

    private Texture sprite;
    private float shipSize;
    private ParticleSystem particleSystem;

    private float upperLeftBound;
    private float lowerRightBound;

    private static final float ROTATE_RATE = (float) (Math.PI / 2); // radians per second
    private static final float THRUST_RATE = -0.004f; // world coordinates per second
    private static final float FUEL_LOSS_RATE = 5f; // per second
    private static final float GRAVITY_RATE = 0.0015f;

    public Ship(KeyboardInput keyboardInput, Graphics2D graphics, float windowSize, RenderLayerEnum layer, ParticleSystem particleSystem) {
        this.rotation = 0f;
        initialize(keyboardInput);
        initialize(graphics, windowSize, layer);
        this.sprite = new Texture("resources/images/spaceship.png");
        this.particleSystem = particleSystem;
    }

    // initialize interfaces
    @Override
    public void initialize(KeyboardInput keyboardInput) {
        keyboardInput.registerCommand(GLFW_KEY_LEFT, false, (double elapsedTime) -> rotateLeft((float) elapsedTime));
        keyboardInput.registerCommand(GLFW_KEY_RIGHT, false, (double elapsedTime) -> rotateRight((float) elapsedTime));
        keyboardInput.registerCommand(GLFW_KEY_UP, false, (double elapsedTime) -> propel((float) elapsedTime));
    }
    @Override
    public void initialize(Graphics2D graphics, float windowSize, RenderLayerEnum layer) {
        this.graphics = graphics;
        this.windowSize = windowSize;
        this.layer = layer;

        this.shipSize = windowSize * 0.05f;
        this.center = new Vector3f(0f, 0f - (windowSize / 2) + (shipSize / 2), layer.getValue());

        upperLeftBound = (0f - windowSize / 2) + (shipSize / 2);
        lowerRightBound = (0f + windowSize / 2) - (shipSize / 2);
    }

    @Override
    public void renderObject() {
        if (crashed) return;
        Rectangle destination = new Rectangle(center.x - (shipSize / 2), center.y - (shipSize / 2), shipSize, shipSize, layer.getValue());
        Vector2f center = new Vector2f(this.center.x, this.center.y);
        graphics.draw(sprite, destination, rotation, center, ColorsEnum.WHITE.getColor());
    }

    public void update(double elapsedTime) {
        if (paused) return;
        velocity.y += GRAVITY_RATE * elapsedTime;
        center.x += velocity.x;
        center.y += velocity.y;
        if (center.y < upperLeftBound) {
            velocity.y = 0;
            center.y = upperLeftBound;
        }
        if (center.y > lowerRightBound) {
            velocity.y = 0;
            center.y = lowerRightBound;
        }
        if (center.x < upperLeftBound) {
            velocity.x = 0;
            center.x = upperLeftBound;
        }
        if (center.x > lowerRightBound) {
            velocity.x = 0;
            center.x = lowerRightBound;
        }
        particleSystem.move(new Vector2f(center.x, center.y));
    }

    // input processing
    private void rotateRight(float elapsedTime) {
        if (fuel <= 0 || paused) return;
        float angle = elapsedTime * ROTATE_RATE;
        rotation += angle;
        if (rotation > Math.toRadians(360)) {
            rotation = rotation - (float) Math.toRadians(360);
        }
        fuel -= elapsedTime * FUEL_LOSS_RATE;
        if (fuel < 0) {
            fuel = 0;
        }
    }

    private void rotateLeft(float elapsedTime) {
        if (fuel <= 0 || paused) return;
        fuel -= elapsedTime * FUEL_LOSS_RATE;
        if (fuel < 0) {
            fuel = 0;
        }

        float angle = elapsedTime * ROTATE_RATE;
        rotation -= angle;
        if (rotation < Math.toRadians(0)) {
            rotation = (float) Math.toRadians(360) + rotation;
        }
    }

    private void propel(float elapsedTime) {
        if (paused) return;
        if (fuel <= 0) {
            fuel = 0;
        }
        if (fuel <= 0) return;

        float fuelConsumed = FUEL_LOSS_RATE * elapsedTime;
        fuel -= fuelConsumed;

        float thrustPower = THRUST_RATE * elapsedTime;
        velocity.x += (float) Math.cos(rotation + ROTATE_RATE) * thrustPower;
        velocity.y += (float) Math.sin(rotation + ROTATE_RATE) * thrustPower;

        particleSystem.createParticles(5);

    }

    public void crash() {
        paused = true;
        crashed = true;
    }

    public void land() {
        paused = true;
        landed = true;
    }

    // getters
    @Override
    public Vector3f getCenter() {
        return center;
    }

    public float getShipWidth() {
        return shipSize;
    }

    public float getRotation() {
        return rotation;
    }

    public double getFuel() {
        return fuel;
    }

    public double getSpeed() {
        return Math.sqrt((velocity.x * velocity.x) + (velocity.y * velocity.y)) * 9;
    }

    // can safely land?
    public boolean isSafeAngle() {
        double degrees = Math.toDegrees(rotation);
        return degrees < 5 || degrees > 355;
    }

    public boolean isSafeSpeed() {
        return getSpeed() < 2;
    }

    public boolean isFuelLeft() {
        return fuel > 0;
    }
}
