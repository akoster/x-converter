package xcon.stackoverflow.shapes;

public abstract class ShapeFactory {

    abstract public String name();

    abstract public int numberOfPoints();

    abstract public Shape createShape(Point... points);

    void validatePoints(Point[] points) {
        if (points.length != numberOfPoints()) {
            throw new IllegalArgumentException(String.format("Cannot create a %s with %s points", name(), points.length));
        }
    }
}
