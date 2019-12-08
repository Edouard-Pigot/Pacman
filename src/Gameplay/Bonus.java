package Gameplay;

import Engine.BoxCollider;
import Engine.GraphicsEngine;
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

    private Image image;

    private int value = -10000;

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
        setFruit(level);
        setImage(image);

        setPhysicalPosition(physicalPosition);
        setGraphicalPosition(graphicalPosition);
        setLayoutX(graphicalPosition.getX());
        setLayoutY(graphicalPosition.getY());

        setFitWidth(size*3);
        setFitHeight(size*3);

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
        return value;
    }

    private void setFruit(int level){
        if(level<=1) {
            value = 100;
            image = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/CHERRY.png")));
        }
        else if(level <= 2) {
            value = 300;
            image = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/STRAWBERRY.png")));
        }
        else if(level <=4) {
            value = 500;
            image = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/PEACH.png")));
        }
        else if(level <=6) {
            value = 700;
            image = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/APPLE.png")));
        }
        else if(level <=8) {
            value = 1000;
            image = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/MELON.png")));
        }
        else if(level <=10) {
            value = 2000;
            image = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/GALAXIAN.png")));
        }
        else if(level <=12) {
            value = 3000;
            image = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/BELL.png")));
        }
        else {
            value = 5000;
            image = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/KEY.png")));
        }
    }
}
