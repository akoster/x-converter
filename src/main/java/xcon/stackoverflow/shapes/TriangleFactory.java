package xcon.stackoverflow.shapes;

public class TriangleFactory extends ShapeFactory {

    private static final String NAME = "Triangle";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public int numberOfPoints() {
        return 3;
    }

    @Override
    public Shape createShape(Point... points) {
        validatePoints(points);
        return new Triangle(points);
    }
}
