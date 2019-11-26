package Gameplay;

import Engine.CoreKernel;
import Entity.StaticEntity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PowerPassThrough extends Circle implements StaticEntity, SuperPower{
    public PowerPassThrough(double centerX, double centerY, double radius, Color color) {
        super(centerX, centerY, radius);
        super.setFill(color);
    }

    @Override
    public void useSuperPower(CoreKernel ck) {

    }

}
