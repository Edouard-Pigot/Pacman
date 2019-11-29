package Gameplay;

import Engine.BoxCollider;
import Entity.*;
import Entity.StaticEntity;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Bonus extends Rectangle implements StaticEntity, ScoreEntity {
    Point2D physicalPosition = new Point2D(0,0);
    Point2D graphicalPosition = new Point2D(0,0);

    private final int value = 100;
    //A modifier en fonction du niveau...

    public Bonus(Point2D graphicalPosition, Point2D physicalPosition, double size, Color color) {
        super.setFill(color);
        setPhysicalPosition(physicalPosition);
        setGraphicalPosition(graphicalPosition);
        setLayoutX(graphicalPosition.getX());
        setLayoutY(graphicalPosition.getY());
        setWidth(size);
        setHeight(size);
        setFill(color);
    }

    @Override
    public void setPhysicalPosition(Point2D position) {
        this.physicalPosition = position;
    }

    @Override
    public void setGraphicalPosition(Point2D position) {
        this.graphicalPosition = position;
        setLayoutX(position.getX());
        setLayoutY(position.getY());
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
        return new BoxCollider((int) graphicalPosition.getX(), (int) graphicalPosition.getX() + (int) getWidth(), (int) graphicalPosition.getY(), (int) graphicalPosition.getY() + (int) getHeight());
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