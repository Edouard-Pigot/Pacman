package Gameplay;

import Engine.BoxCollider;
import Entity.Entity;
import Entity.MovingEntity;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pacman extends Circle implements MovingEntity {
    Point2D physicalPosition = new Point2D(0,0);
    Point2D graphicalPosition = new Point2D(0,0);

    public Pacman(Point2D graphicalPosition, double size, Color color) {
        Point2D physicalPosition = convertGraphicalPositionToPhysicalPosition(graphicalPosition, size);
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
        setLayoutX(graphicalPosition.getX());
        setLayoutY(graphicalPosition.getY());
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
        return new BoxCollider((int) graphicalPosition.getX() - (int) getRadius(), (int) graphicalPosition.getX() + (int) getRadius(), (int) graphicalPosition.getY() - (int) getRadius(), (int) graphicalPosition.getY() + (int) getRadius());
    }

    @Override
    public boolean isColliding(Entity other) {
        return boxCollider().isColliding(other.boxCollider());
    }

    public Point2D convertGraphicalPositionToPhysicalPosition(Point2D position, double size){
        return new Point2D(Math.floor(position.getX()/(size*2)), Math.floor(position.getY()/(size*2)));
    }
}
