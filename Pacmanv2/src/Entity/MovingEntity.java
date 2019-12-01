package Entity;

import Engine.BoxCollider;
import javafx.geometry.Point2D;

public interface MovingEntity extends Entity {
    Point2D physicalPosition = new Point2D(0,0);
    Point2D graphicalPosition = new Point2D(0,0);

    Point2D newDirection = new Point2D(0,0);
    Point2D oldDirection = new Point2D(0,0);
    Point2D wantedDirection = new Point2D(0,0);

    public void setPhysicalPosition(double size);

    public void setGraphicalPosition(Point2D position);

    public Point2D getPhysicalPosition();

    public Point2D getGraphicalPosition();

    public BoxCollider boxCollider();

    public boolean isColliding(Entity other);

    public Point2D convertGraphicalPositionToPhysicalPosition(Point2D position, double size);

    public void changeDirection(Point2D direction);

    public void setDirection(Point2D direction);

    public Point2D getNewDirection();

    public Point2D getOldDirection();

    public Point2D getWantedDirection();

    public void setNewDirection(Point2D newDirection);

    public void setOldDirection(Point2D oldDirection);

    public void setWantedDirection(Point2D wantedDirection);
}
