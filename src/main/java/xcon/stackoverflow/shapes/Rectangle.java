package xcon.stackoverflow.shapes;

public class Rectangle extends Shape {

    Rectangle(Point... points) {
        super(points);
    }

    @Override
    public double area() {
        Point point1 = getPoints()[0];
        Point point2 = getPoints()[1];
        double width = Math.abs(point1.getX() - point2.getX());
        double height = Math.abs(point1.getY() - point2.getY());
        return width * height;
    }
}

