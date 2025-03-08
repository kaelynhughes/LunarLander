package objects;

import enums.RenderLayerEnum;
import org.joml.Vector3f;

public class SafeZone implements Comparable<SafeZone> {
    private final Vector3f left;
    private final Vector3f right;

        public SafeZone(float width, float center, float height, RenderLayerEnum layer) {
        float halfWidth = width / 2;
        this.left = new Vector3f(center - halfWidth, height, layer.getValue());
        this.right = new Vector3f(center + halfWidth, height, layer.getValue());
    }

    public Vector3f getLeft() {
        return left;
    }
    public Vector3f getRight() {
        return right;
    }

    public boolean overlaps(SafeZone other) {
            if (other.getLeft().x >= this.left.x && other.getLeft().x <= this.right.x) {
                return true;
            }
            if (other.getRight().x >= this.left.x && other.getRight().x <= this.right.x) {
                return true;
            }
            return false;
    }

    @Override
    public String toString() {
            return "Safe zone: (" + left.x + ", " + left.y + ") to (" + right.x + ", " + right.y + ")";
    }

    @Override
    public int compareTo(SafeZone o) {
        return Float.compare(this.getLeft().x, o.getLeft().x);
    }
}
