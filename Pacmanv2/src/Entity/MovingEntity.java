package Entity;

import Engine.BoxCollider;
import javafx.geometry.Point2D;

public interface MovingEntity extends Entity {
    Point2D physicalPosition = new Point2D(0,0);
    Point2D graphicalPosition = new Point2D(0,0);

    public void setPhysicalPosition(Point2D position);

    public void setGraphicalPosition(Point2D position);

    public Point2D getPhysicalPosition();

    public Point2D getGraphicalPosition();

    public BoxCollider boxCollider();

    public boolean isColliding(Entity other);

    public Point2D convertGraphicalPositionToPhysicalPosition(Point2D position, double size);
}
