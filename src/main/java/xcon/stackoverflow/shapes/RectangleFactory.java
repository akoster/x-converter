package xcon.stackoverflow.shapes;

public class RectangleFactory extends ShapeFactory {

    private static final String NAME = "Rectangle";

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public int numberOfPoints() {
        return 2;
    }

    @Override
    public Shape createShape(Point... points) {
        validatePoints(points);
        return new Rectangle(points);
    }
}

