package xcon.stackoverflow.shapes;

public abstract class Shape {

    private final Point[] points;

    Shape(Point... points) {
        this.points = points;
    }

    public Point[] getPoints() {
        return points;
    }

    abstract public double area();

    @Override
    public String toString() {
        return String.format("%s: %s", this.getClass().getSimpleName(), displayPoints());
    }

    private StringBuilder displayPoints() {
        StringBuilder pointsDisplay = new StringBuilder();
        for (Point point : points) {
            pointsDisplay.append(point).append(",");
        }
        pointsDisplay.setLength(pointsDisplay.length() - 1);
        return pointsDisplay;
    }
}
