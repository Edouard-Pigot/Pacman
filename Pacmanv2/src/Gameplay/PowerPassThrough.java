package Gameplay;

import Engine.*;
import Entity.*;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PowerPassThrough extends Circle implements StaticEntity,ScoreEntity {
    Point2D physicalPosition = new Point2D(0,0);
    Point2D graphicalPosition = new Point2D(0,0);
    private final int value = 100;

    public PowerPassThrough(Point2D graphicalPosition, Point2D physicalPosition, double size, Color color) {
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

    @Override
    public int getValue() {
        return value;
    }
}
