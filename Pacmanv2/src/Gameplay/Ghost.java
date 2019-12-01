package Gameplay;

import Engine.BoxCollider;
import Entity.Entity;
import Entity.MovingEntity;
import Entity.StaticEntity;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Ghost extends Circle implements MovingEntity {
    Point2D physicalPosition = new Point2D(0,0);
    Point2D graphicalPosition = new Point2D(0,0);
    StaticEntity target;
    int id;

    private Point2D newDirection = new Point2D(0,0);
    private Point2D oldDirection = new Point2D(1,0);
    private Point2D wantedDirection = new Point2D(0,0);

    public Ghost(Point2D graphicalPosition, double size, Color color, int id) {
        setPhysicalPosition(size);
        setGraphicalPosition(graphicalPosition);
        setCenterX(0);
        setLayoutX(graphicalPosition.getX());
        setCenterY(0);
        setLayoutY(graphicalPosition.getY());
        setRadius(size);
        setFill(color);
        this.id = id;
    }

    @Override
    public void setPhysicalPosition(double size) {
        this.physicalPosition = convertGraphicalPositionToPhysicalPosition(graphicalPosition, size);
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

    @Override
    public Point2D convertGraphicalPositionToPhysicalPosition(Point2D position, double size){
        return new Point2D(Math.floor(position.getX()/size), Math.floor(position.getY()/size));
    }

    public void setTarget(StaticEntity targetTile){
        this.target = targetTile;
    }

    public Point2D getTarget(Pacman pacman, ArrayList<Ghost> ghosts){
        switch (id){
            case 1:
                return pacman.getPhysicalPosition();
        }
        return null;
    }

    @Override
    public void changeDirection(Point2D direction){
        if(((newDirection.getX() == 1 && direction.getX() == -1) && (newDirection.getY() == 0 && direction.getY() == 0)) ||
                ((newDirection.getX() == -1 && direction.getX() == 1) && (newDirection.getY() == 0 && direction.getY() == 0)) ||
                ((newDirection.getX() == 0 && direction.getX() == 0) && (newDirection.getY() == 1 && direction.getY() == -1)) ||
                ((newDirection.getX() == 0 && direction.getX() == 0) && (newDirection.getY() == -1 && direction.getY() == 1))){
            return;
        }
        oldDirection = newDirection;
        newDirection = direction;
    }

    @Override
    public void setDirection(Point2D direction){
        wantedDirection = direction;
    }

    @Override
    public Point2D getNewDirection() {
        return newDirection;
    }

    @Override
    public Point2D getOldDirection() {
        return oldDirection;
    }

    @Override
    public Point2D getWantedDirection() {
        return wantedDirection;
    }

    @Override
    public void setNewDirection(Point2D newDirection) {
        this.newDirection = newDirection;
    }

    @Override
    public void setOldDirection(Point2D oldDirection) {
        this.oldDirection = oldDirection;
    }

    @Override
    public void setWantedDirection(Point2D wantedDirection) {
        this.wantedDirection = wantedDirection;
    }

}
