package Gameplay;

import Engine.BoxCollider;
import Engine.GraphicsEngine;
import Entity.Entity;
import Entity.MovingEntity;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;



public class Pacman extends ImageView implements MovingEntity {
    Point2D physicalPosition = new Point2D(0,0);
    Point2D graphicalPosition = new Point2D(0,0);

    private Point2D newDirection = new Point2D(0,0);
    private Point2D oldDirection = new Point2D(0,0);
    private Point2D wantedDirection = new Point2D(0,0);

    private Image normal1 = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/PAC1.png")));
    private Image normal2 = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/PAC2.png")));

    public Pacman(Point2D graphicalPosition, double size) {
        setImage(normal1);
        setPhysicalPosition(size);
        setGraphicalPosition(graphicalPosition);
        setLayoutX(graphicalPosition.getX());
        setLayoutY(graphicalPosition.getY());
        setFitWidth(size*2);
        setFitHeight(size*2);
    }

    public void refreshImage(int frame){
        switch (frame){
            case 0:
                setImage(normal1);
            break;
            case 1:
                setImage(normal2);
            break;
        }
        if (newDirection.equals(new Point2D(0, 1))) {
            setRotate(90);
//            eyes.setImage(eyesDown);
        } else if (newDirection.equals(new Point2D(0, -1))) {
            setRotate(270);
//            eyes.setImage(eyesUp);
        } else if (newDirection.equals(new Point2D(1, 0))) {
            setRotate(0);
//            eyes.setImage(eyesRight);
        } else if (newDirection.equals(new Point2D(-1, 0))) {
            setRotate(180);
//            eyes.setImage(eyesLeft);
        }
    }

    @Override
    public void setPhysicalPosition(double size) {
        this.physicalPosition = convertGraphicalPositionToPhysicalPosition(graphicalPosition, size);
    }

    @Override
    public void setGraphicalPosition(Point2D position) {
        this.graphicalPosition = position;
        setLayoutX(graphicalPosition.getX()-getFitWidth()/2);
        setLayoutY(graphicalPosition.getY()-getFitWidth()/2);
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
        return new BoxCollider((int) graphicalPosition.getX() - (int) getFitWidth()/2, (int) graphicalPosition.getX() + (int) getFitWidth()/2, (int) graphicalPosition.getY() - (int) getFitHeight()/2, (int) graphicalPosition.getY() + (int) getFitHeight()/2);
    }

    @Override
    public boolean isColliding(Entity other) {
        return boxCollider().isColliding(other.boxCollider());
    }

    @Override
    public Point2D convertGraphicalPositionToPhysicalPosition(Point2D position, double size){
        return new Point2D(Math.floor(position.getX()/size), Math.floor(position.getY()/size));
    }

    @Override
    public void changeDirection(Point2D direction){
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

    @Override
    public Point2D convertPhysicalPositionToGraphicalPosition(){
        return new Point2D(Math.floor(physicalPosition.getX()*16), Math.floor(physicalPosition.getY()*16));
    }
}
