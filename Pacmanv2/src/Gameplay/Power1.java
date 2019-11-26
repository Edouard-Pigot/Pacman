package Gameplay;

import Entity.StaticEntity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Power1 extends Circle implements StaticEntity {
    public Power1(double centerX, double centerY, double radius, Color color) {
        super(centerX, centerY, radius);
        super.setFill(color);
    }
}
