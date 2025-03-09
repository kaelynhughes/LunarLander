package objects;

import edu.usu.graphics.Graphics2D;
import edu.usu.graphics.Texture;
import enums.ColorsEnum;
import enums.RenderLayerEnum;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.joml.Vector3f;
import tools.KeyboardInput;
import tools.MyRandom;

public class ParticleSystem implements Rendered, Moveable {
    private final HashMap<Long, Particle> particles = new HashMap<>();
    private final MyRandom random = new MyRandom();

    private Vector2f center;
    private final float sizeMean;
    private final float sizeStdDev;
    private final float speedMean;
    private final float speedStdDev;
    private final float lifetimeMean;
    private final float lifetimeStdDev;
    private float direction;

    private Graphics2D graphics;
    private float windowSize;
    private RenderLayerEnum layer;

    private Texture texParticle;

    public ParticleSystem(Graphics2D graphics, float windowSize, RenderLayerEnum layer) {
        this.center = new Vector2f(0, 0);
        this.sizeMean = 0.001f;
        this.sizeStdDev = 0.005f;
        this.speedMean = 0.12f;
        this.speedStdDev = 0.05f;
        this.lifetimeMean = 2;
        this.lifetimeStdDev = 0.5f;
        this.direction = 0f;

        texParticle = new Texture("resources/images/fire.png");

        initialize(graphics, windowSize, layer);
    }

    @Override
    public void initialize(Graphics2D graphics, float windowSize, RenderLayerEnum layer) {
        this.graphics = graphics;
        this.windowSize = windowSize;
        this.layer = layer;
    }

    @Override
    public void initialize(KeyboardInput keyboardInput) {
    }

    @Override
    public Vector3f getCenter() {
        return new Vector3f(this.center.x, this.center.y, 0);
    }

    public void update(double elapsedTime) {
        // Update existing particles
        List<Long> removeMe = new ArrayList<>();
        for (Particle p : particles.values()) {
            p.update(elapsedTime);
            if (!p.isAlive()) {
                removeMe.add(p.name);
            }
        }

        // Remove dead particles
        for (Long key : removeMe) {
            particles.remove(key);
        }

        // Generate some new particles
    }

    public void updateDirection(float direction) {
        this.direction = direction;
    }

    public Collection<Particle> getParticles() {
        return this.particles.values();
    }

    private Particle create() {
        float size = (float) this.random.nextGaussian(this.sizeMean, this.sizeStdDev);
        var p = new Particle(
                new Vector2f(this.center.x, this.center.y),
                this.random.nextHalfCircleVector(direction),
                (float) this.random.nextGaussian(this.speedMean, this.speedStdDev),
                new Vector2f(size, size),
                this.random.nextGaussian(this.lifetimeMean, this.lifetimeStdDev));

        return p;
    }

    public void move(Vector2f newPosition) {
        this.center = newPosition;
    }

    public void createParticles(int numParticles) {
        for (int i = 1; i < numParticles; i++) {
            var particle = create();
            particles.put(particle.name, particle);
        }
    }

    public void renderObject() {
        for (var particle : getParticles()) {
            if (particle.isAlive()) {
                graphics.draw(texParticle, particle.area, particle.rotation, particle.center, ColorsEnum.WHITE.getColor());
            }
        }
    }
}
