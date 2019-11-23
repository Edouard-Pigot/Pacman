package Gameplay;

import Entity.StaticEntity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Empty extends Rectangle implements StaticEntity  {
    public Empty(double x, double y, double size, Color color) {
        super(x, y, size, size);
        super.setFill(color);
    }

    @Override
    public String toString() {
        Class c = getClass();
        return "<" + c.getSimpleName() + ">";
    }
}
