package Gameplay;

import Entity.StaticEntity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends Rectangle implements StaticEntity {
    public Wall(double x, double y, double size, Color color) {
        super(x, y, size, size);
        super.setFill(color);
    }
}
