import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.concurrent.TimeUnit;

public class Pacman extends ImageView {
    private double xTile;
    private double yTile;

    private double xSprite;
    private double ySprite;

    MapCreator map;

    Pacman(double _xTile, double _yTile, double _xSprite, double _ySprite, MapCreator _map, Image image) {
        xTile = _xTile;
        yTile = _yTile;
        xSprite = _xSprite;
        ySprite = _ySprite;
        map = _map;
        setImage(image);
        setFitWidth((double) map.x/ map.map.length);
        setFitHeight((double) map.y/ map.map.length);
        setX(xSprite - getFitWidth()/2);
        setY(ySprite - getFitHeight()/2);
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
        setX(xSprite - getFitWidth()/2);
        setY(ySprite - getFitHeight()/2);
    }

    public  double[] getSprite() {
        return new double[]{xSprite, ySprite};
    }
}
