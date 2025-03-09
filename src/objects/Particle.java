package objects;

import org.joml.Vector2f;
import edu.usu.graphics.Rectangle;
import org.joml.Vector3f;
import tools.KeyboardInput;

public class Particle implements Moveable {
    public Particle(Vector2f center, Vector2f direction, float speed, Vector2f size, double lifetime) {
        this.name = nextName++;
        this.center = center;
        this.direction = direction;
        this.speed = speed;
        this.size = size;
        this.area = new Rectangle(center.x - size.x / 2, center.y - size.y / 2, size.x, size.y);
        this.lifetime = lifetime;

        this.rotation = 0;
    }

    @Override
    public void initialize(KeyboardInput keyboardInput) {
    }

    @Override
    public Vector3f getCenter() {
        return new Vector3f(center.x, center.y, 0);
    }

    @Override
    public void update(double elapsedTime) {
        // Update how long it has been alive
        alive += elapsedTime;

        // Update its center
        center.x += (float) (elapsedTime * speed * direction.x);
        center.y += (float) (elapsedTime * speed * direction.y);
        area.left += (float) (elapsedTime * speed * direction.x);
        area.top += (float) (elapsedTime * speed * direction.y);

        // Rotate proportional to its speed
        rotation += (speed / 0.5f);
    }
    public boolean isAlive() {
        return alive < lifetime;
    }

    public long name;
    public Vector2f size;
    public Vector2f center;
    public Rectangle area;
    public float rotation;
    private Vector2f direction;
    private float speed;
    private double lifetime;
    private double alive = 0;
    private static long nextName = 0;
}
