package Gameplay;

import Entity.MovingEntity;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pacman extends Circle implements MovingEntity {
    private Point2D position;

    public Pacman(Point2D position) {
        this.position = position;
        setCenterX(0);
        setLayoutX(position.getX());
        setCenterY(0);
        setLayoutY(position.getY());
        setRadius(8);
        setFill(Color.RED);
    }

    public void setPosition(Point2D position){
        this.position = position;
        setCenterX(position.getX());
        setCenterY(position.getY());
    }
}
