package objects;

import tools.KeyboardInput;

public interface Moveable {
    public void initialize(KeyboardInput keyboardInput);
    public void move(int x, int y);
}
