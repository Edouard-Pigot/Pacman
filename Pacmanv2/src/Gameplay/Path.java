package Gameplay;

import Engine.BoxCollider;
import Entity.Entity;
import Entity.StaticEntity;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Path extends Rectangle implements StaticEntity {
    Point2D physicalPosition = new Point2D(0,0);
    Point2D graphicalPosition = new Point2D(0,0);

    int id = 0;
    String use = "";

    public void setId(int id){
        this.id = id;
    }

    public void setUse(String use){
        this.use = use;
    }

    public int get_Id() {
        return id;
    }

    public String get_Use() {
        return use;
    }

    public Path(Point2D graphicalPosition, Point2D physicalPosition, double size, Color color) {
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

    public void setColor(Color color){
        setFill(color);
    }
}
