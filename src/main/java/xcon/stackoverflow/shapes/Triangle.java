package xcon.stackoverflow.shapes;

public class Triangle extends Shape {

    Triangle(Point... points) {
        super(points);
    }

    @Override
    public double area() {
        Point point1 = getPoints()[0];
        Point point2 = getPoints()[1];
        Point point3 = getPoints()[2];
        return (point1.getX() * point2.getY() + point2.getX() * point3.getY() + point3.getX() * point1.getY()
                - point1.getX() * point3.getY() - point2.getX() * point1.getY() - point3.getX() * point2.getY()) / 2.0;
    }
}
