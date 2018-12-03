package xcon.stackoverflow.shapes;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ShapeFactoryFactory {

    private static final ShapeFactory[] SHAPE_FACTORIES = {new TriangleFactory(), new RectangleFactory()};

    static ShapeFactory createShapeFactory(String name) {
        return findShapeFactory(name);
    }

    static String shapesDisplay() {
        return Arrays.stream(SHAPE_FACTORIES).map(ShapeFactory::name).collect(Collectors.joining(", "));
    }

    private static ShapeFactory findShapeFactory(String name) {
        for (ShapeFactory shapeFactory : SHAPE_FACTORIES) {
            if (shapeFactory.name().equalsIgnoreCase(name)) {
                return shapeFactory;
            }
        }
        throw new IllegalArgumentException("Unknown ShapeFactory: " + name);
    }
}
