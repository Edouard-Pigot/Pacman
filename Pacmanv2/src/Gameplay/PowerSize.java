
package Gameplay;

import Engine.BoxCollider;
import Engine.CoreKernel;
import Entity.*;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static java.lang.Thread.sleep;

public class PowerSize extends Circle implements StaticEntity, ScoreEntity {
    static final int value = 100;

    public PowerSize(double centerX, double centerY, double radius, Color color) {
        super(centerX, centerY, radius);
        super.setFill(color);
    }

    @Override
    public void setPhysicalPosition(Point2D position) {

    }

    @Override
    public void setGraphicalPosition(Point2D position) {

    }

    @Override
    public Point2D getPhysicalPosition() {
        return null;
    }

    @Override
    public Point2D getGraphicalPosition() {
        return null;
    }

    @Override
    public BoxCollider boxCollider() {
        return null;
    }

    @Override
    public boolean isColliding(Entity other) {
        return false;
    }

    @Override
    public int getValue() {
        return value;
    }
}