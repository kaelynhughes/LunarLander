package objects;

import enums.RenderLayerEnum;
import org.joml.Vector3f;

public class TerrainPoint implements Comparable<TerrainPoint> {
    final private Vector3f point;

    public TerrainPoint(Vector3f point) {
        this.point = point;
    }

    public TerrainPoint(float x, float y, RenderLayerEnum layer) {
        this.point = new Vector3f(x, y, layer.getValue());
    }

    public float getX() {
        return point.x;
    }

    public float getY() {
        return point.y;
    }

    public Vector3f getPoint() {
        return point;
    }

    @Override
    public int compareTo(TerrainPoint o) {
        return Float.compare(this.getX(), o.getX());
    }
}
