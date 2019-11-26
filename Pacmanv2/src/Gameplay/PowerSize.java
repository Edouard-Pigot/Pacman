package Gameplay;

import Engine.CoreKernel;
import Entity.StaticEntity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static java.lang.Thread.sleep;

public class PowerSize extends Circle implements StaticEntity {
    public PowerSize(double centerX, double centerY, double radius, Color color) {
        super(centerX, centerY, radius);
        super.setFill(color);
    }
}
