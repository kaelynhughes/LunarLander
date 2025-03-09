package objects;

import org.joml.Vector3f;
import tools.KeyboardInput;

public interface Moveable {
    void initialize(KeyboardInput keyboardInput);
    Vector3f getCenter();
    void update(double elapsedTime);
}
