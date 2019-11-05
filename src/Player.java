import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.concurrent.TimeUnit;

public class Player extends Rectangle {
    private double xTile;
    private double yTile;

    private double xSprite;
    private double ySprite;

    MapCreator map;

    Player(double _xTile, double _yTile, double _xSprite, double _ySprite, MapCreator _map) {
        xTile = _xTile;
        yTile = _yTile;
        xSprite = _xSprite;
        ySprite = _ySprite;
        map = _map;
        setWidth((double) map.x/ map.map.length);
        setHeight((double) map.y/ map.map.length);
        setX(xSprite - getWidth()/2);
        setY(ySprite - getHeight()/2);
        setFill(Color.YELLOW);
    }

    public void setTile(double _x, double _y){
        xTile = _x;
        yTile = _y;
    }

    public double[] getTile(){
        return new double[]{xTile, yTile};
    }

    public void setSprite(double _x, double _y){
        xSprite = _x;
        ySprite = _y;
        setX(xSprite - getWidth()/2);
        setY(ySprite - getHeight()/2);
    }

    public  double[] getSprite() {
        return new double[]{xSprite, ySprite};
    }
}
