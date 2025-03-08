package ecs.Components;

import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;


public class Position extends Component {
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
