package xcon.stackoverflow.shapes;

import java.util.Scanner;

public class App {

    private Scanner sc;

    public App() {
        sc = new Scanner(System.in);
    }

    private void run() {
        ShapeFactory shapeFactory = readShapeFactory();
        Shape shape = readShape(shapeFactory);
        System.out.printf("Area of %s is: %s%n", shapeFactory.name(), shape.area());
    }

    private ShapeFactory readShapeFactory() {
        ShapeFactory result = null;
        while (result == null) {
            try {
                result = ShapeFactoryFactory.createShapeFactory(readName());
            } catch (IllegalArgumentException e) {
                System.out.printf("That shape is not known.%nAvailable shapes are: %s%n", ShapeFactoryFactory.shapesDisplay());
            }
        }
        return result;
    }

    private String readName() {
        System.out.printf("Type the name of the shape:%n");
        return sc.next();
    }

    private Shape readShape(ShapeFactory shapeFactory) {
        System.out.printf("Creating a %s:%n", shapeFactory.name());
        Point[] points = readPoints(shapeFactory.numberOfPoints());
        return shapeFactory.createShape(points);
    }

    private Point[] readPoints(int numberOfPoints) {
        Point[] points = new Point[numberOfPoints];
        for (int i = 0; i < points.length; i++) {
            points[i] = readPoint(i + 1);
        }
        return points;
    }

    private Point readPoint(int ordinal) {
        System.out.printf("Enter x%s:%n", ordinal);
        int x = sc.nextInt();
        System.out.printf("Enter y%s:%n", ordinal);
        int y = sc.nextInt();
        return new Point(x, y);
    }

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
}
