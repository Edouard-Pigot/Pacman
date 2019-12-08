package Gameplay;

import Engine.BoxCollider;
import Entity.*;
import Entity.StaticEntity;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bonus extends ImageView implements StaticEntity, ScoreEntity {
    Point2D physicalPosition = new Point2D(0,0);
    Point2D graphicalPosition = new Point2D(0,0);

    int identifiant = 0;
    String use = "";

    private Image image = new Image("file:Images/APPLE.png");

    private String fruit = "cherry";

    public void set_Id(int id){
        this.identifiant = id;
    }

    public void set_Use(String use){
        this.use = use;
    }

    public int get_Id() {
        return identifiant;
    }

    public String get_Use() {
        return use;
    }

    public Bonus(Point2D graphicalPosition, Point2D physicalPosition, double size, int level) {

        setImage(image);

        setPhysicalPosition(physicalPosition);
        setGraphicalPosition(graphicalPosition);
        setLayoutX(graphicalPosition.getX());
        setLayoutY(graphicalPosition.getY());

        setFitWidth(size);
        setFitHeight(size);
        toFront();
        setFruit(level);

    }

    @Override
    public void setPhysicalPosition(Point2D position) {
        this.physicalPosition = position;
    }

    @Override
    public void setGraphicalPosition(Point2D position) {
        this.graphicalPosition = position;
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
        return new BoxCollider((int) graphicalPosition.getX(), (int) graphicalPosition.getX() + (int) getFitWidth(), (int) graphicalPosition.getY(), (int) graphicalPosition.getY() + (int) getFitWidth());
    }

    @Override
    public boolean isColliding(Entity other) {
        return boxCollider().isColliding(other.boxCollider());
    }

    public void setColor(Color color) {
        setColor(color);
    }

    @Override
    public int getValue() {
        if(fruit.equals("cherry"))
            return 100;
        else if(fruit.equals("strawberry"))
            return 300;
        else if(fruit.equals("peach"))
            return 500;
        else if(fruit.equals("apple"))
            return 700;
        else if(fruit.equals("grapes"))
            return 1000;
        else if(fruit.equals("galaxian"))
            return 2000;
        else if(fruit.equals("bell"))
            return 3000;
        else if(fruit.equals("key"))
            return 5000;
        else
            return -10000;
    }

    public void setFruit(int level){
        if(level<=1)
            fruit = "cherry";
        else if(level <= 2)
            fruit = "strawberry";
        else if(level <=4)
            fruit = "peach";
        else if(level <=6)
            fruit = "apple";
        else if(level <=8)
            fruit = "grapes";
        else if(level <=10)
            fruit = "galaxian";
        else if(level <=12)
            fruit = "bell";
        else
            fruit = "key";
    }
}
