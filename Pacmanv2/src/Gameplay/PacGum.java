package Gameplay;

import Engine.BoxCollider;
import Entity.Entity;
import Entity.StaticEntity;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PacGum extends Circle implements StaticEntity {
    Point2D physicalPosition = new Point2D(0,0);
    Point2D graphicalPosition = new Point2D(0,0);

    public PacGum(Point2D graphicalPosition, Point2D physicalPosition, double size, Color color) {
        setPhysicalPosition(physicalPosition);
        setGraphicalPosition(graphicalPosition);
        setCenterX(0);
        setLayoutX(graphicalPosition.getX());
        setCenterY(0);
        setLayoutY(graphicalPosition.getY());
        setRadius(size);
        setFill(color);
    }

    @Override
    public void setPhysicalPosition(Point2D position) {
        this.physicalPosition = position;
    }

    @Override
    public void setGraphicalPosition(Point2D position) {
        this.graphicalPosition = position;
    }

    @Override
    public Point2D getPhysicalPosition() {
        return physicalPosition;
    }

    @Override
    public Point2D getGraphicalPosition() {
        return graphicalPosition;
    }

    @Override
    public BoxCollider boxCollider() {
        return new BoxCollider((int) graphicalPosition.getX(), (int) graphicalPosition.getX() + (int) getRadius(), (int) graphicalPosition.getY(), (int) graphicalPosition.getY() + (int) getRadius());
    }

    @Override
    public boolean isColliding(Entity other) {
        return boxCollider().isColliding(other.boxCollider());
    }
}
