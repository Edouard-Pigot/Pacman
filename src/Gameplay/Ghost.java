package Gameplay;

import Engine.BoxCollider;
import Engine.GraphicsEngine;
import Entity.Entity;
import Entity.MovingEntity;
import Entity.StaticEntity;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javax.imageio.ImageIO;
import java.util.ArrayList;

public class Ghost extends ImageView implements MovingEntity {
    Point2D physicalPosition = new Point2D(0,0);
    Point2D graphicalPosition = new Point2D(0,0);

    Point2D homePosition = new Point2D(0,0);
    Point2D gateExitPosition = new Point2D(0,0);
    Point2D cornerPosition = new Point2D(0,0);

    String originalColor;

    public boolean exitedHouse;

    Point2D target;
    int id;
    int status;

    //0 = sortie maison
    //1 = chasse
    //2 = dispersion
    //3 = retour maison
    //4 = vulnérable

    private Point2D newDirection = new Point2D(0,0);
    private Point2D oldDirection = new Point2D(1,0);
    private Point2D wantedDirection = new Point2D(0,0);
    private Point2D tileFirstDirection = new Point2D(0,0);

    private ImageView eyes = new ImageView();

    private Image eyesUp = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/EYE_UP.png")));
    private Image eyesLeft = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/EYE_LEFT.png")));
    private Image eyesDown = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/EYE_DOWN.png")));
    private Image eyesRight = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/EYE_RIGHT.png")));
    private Image eyesFright1 = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/EYE_WHITE.png")));
    private Image eyesFright2 = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/EYE_RED.png")));

    private Image normal1;
    private Image normal2;
    private Image fright11 = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/BLUE1.png")));
    private Image fright12 = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/BLUE2.png")));
    private Image fright21 = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/WHITE1.png")));
    private Image fright22 = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/WHITE2.png")));

    public void refreshImage(int frame){
        switch (status){
            case 0:
                switch (frame){
                    case 0:
                        setImage(normal1);
                        break;
                    case 1:
                        setImage(normal2);
                        break;
                }
                break;
            case 1:
                switch (frame){
                    case 0:
                        setImage(normal1);
                        break;
                    case 1:
                        setImage(normal2);
                        break;
                }
                break;
            case 2:
                switch (frame){
                    case 0:
                        setImage(normal1);
                        break;
                    case 1:
                        setImage(normal2);
                        break;
                }
                break;
            case 3:
                setImage(null);
                break;
            case 4:
                switch (frame){
                    case 0:
                        setImage(fright11);
                        break;
                    case 1:
                        setImage(fright12);
                        break;
                }
                break;
        }
        if(status != 4) {
            if (newDirection.equals(new Point2D(0, 1))) {
                eyes.setImage(eyesDown);
            } else if (newDirection.equals(new Point2D(0, -1))) {
                eyes.setImage(eyesUp);
            } else if (newDirection.equals(new Point2D(1, 0))) {
                eyes.setImage(eyesRight);
            } else if (newDirection.equals(new Point2D(-1, 0))) {
                eyes.setImage(eyesLeft);
            }
        } else {
            eyes.setImage(eyesFright1);
        }
        changeEyesPosition();
    }

    public ImageView getEyes(){
        return eyes;
    }

    public void setTileFirstDirection(Point2D tileFirstDirection) {
        this.tileFirstDirection = tileFirstDirection;
    }

    public Point2D getTileFirstDirection() {
        return tileFirstDirection;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus(){return status;}

    public Point2D getTarget(){return target;}

    public int getGhostId() {
        return id;
    }

    public void setCornerPosition(Point2D cornerPosition) {
        this.cornerPosition = cornerPosition;
    }

    public void setHomePosition(Point2D homePosition) {
        this.homePosition = homePosition;
    }

    public void setGateExitPosition(Point2D gateExitPosition) {
        this.gateExitPosition = gateExitPosition;
    }

    public Ghost(Point2D graphicalPosition, double size, String color, int id, int status) {
        originalColor = color;
        normal1 = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/" + color + "1.png")));
        normal2 = new Image(String.valueOf(GraphicsEngine.class.getClassLoader().getResource("Images/" + color + "2.png")));
        setImage(normal1);
        changeEyesPosition();
        eyes.setFitHeight(size*2);
        eyes.setFitWidth(size*2);
        this.status = status;
        setGraphicalPosition(graphicalPosition);
        setLayoutX(graphicalPosition.getX()-getFitWidth()/2);
        setLayoutY(graphicalPosition.getY()-getFitWidth()/2);
        setFitWidth(size*2);
        setFitHeight(size*2);
        setPhysicalPosition(size);
        this.id = id;
    }

    private void changeEyesPosition(){
        eyes.setLayoutX(this.getLayoutX());
        eyes.setLayoutY(this.getLayoutY());
    }

    @Override
    public void setPhysicalPosition(double size) {
        Point2D newPhysicalPosition = convertGraphicalPositionToPhysicalPosition(graphicalPosition, size);
        if(newPhysicalPosition.getX() != physicalPosition.getX() && newPhysicalPosition.getY() != physicalPosition.getY()){
            tileFirstDirection = wantedDirection;
        }
        this.physicalPosition = newPhysicalPosition;
    }

    @Override
    public void setGraphicalPosition(Point2D position) {
        this.graphicalPosition = position;
        setLayoutX(graphicalPosition.getX()-getFitWidth()/2);
        setLayoutY(graphicalPosition.getY()-getFitWidth()/2);
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
        return new BoxCollider((int) graphicalPosition.getX() - (int) getFitWidth()/2, (int) graphicalPosition.getX() + (int) getFitWidth()/2, (int) graphicalPosition.getY() - (int) getFitHeight()/2, (int) graphicalPosition.getY() + (int) getFitHeight()/2);
    }

    @Override
    public boolean isColliding(Entity other) {
        return boxCollider().isColliding(other.boxCollider());
    }

    @Override
    public Point2D convertGraphicalPositionToPhysicalPosition(Point2D position, double size){
        return new Point2D(Math.floor(position.getX()/size), Math.floor(position.getY()/size));
    }

    public void setTarget(Point2D targetTile){
        this.target = targetTile;
    }

    @Override
    public Point2D convertPhysicalPositionToGraphicalPosition() {
        return new Point2D(Math.floor(physicalPosition.getX()*16), Math.floor(physicalPosition.getY()*16));
    }

    public Point2D calculateTarget(Pacman pacman, ArrayList<Ghost> ghosts){
        Point2D calculatedTarget = new Point2D(0,0);
        switch (id){
            case 1:
                switch (status){
                    case 0:
                        calculatedTarget = gateExitPosition;
                        break;
                    case 1:
                        // case de pacman
                        calculatedTarget = pacman.getPhysicalPosition();
                        break;
                    case 2:
                        calculatedTarget = cornerPosition;
                        break;
                    case 3:
                        calculatedTarget = homePosition;
                        break;
                    case 4:
                        int rand = (int) (Math.random() * (4 - 1));
                        switch (rand){
                            case 0:
                                calculatedTarget = new Point2D(getPhysicalPosition().getX(), getPhysicalPosition().getY() - 1);
                                break;
                            case 1:
                                calculatedTarget = new Point2D(getPhysicalPosition().getX() + 1, getPhysicalPosition().getY());
                                break;
                            case 2:
                                calculatedTarget = new Point2D(getPhysicalPosition().getX(), getPhysicalPosition().getY() + 1);
                                break;
                            case 3:
                                calculatedTarget = new Point2D(getPhysicalPosition().getX() - 1, getPhysicalPosition().getY());
                                break;
                        }
                        break;
                }
            break;
            case 2:
                switch (status){
                    case 0:
                        calculatedTarget = gateExitPosition;
                        break;
                    case 1:
                        // 4 cases devant pacman
                        Point2D direction = pacman.getNewDirection();
                        Point2D position = pacman.getPhysicalPosition();
                        calculatedTarget = new Point2D(position.getX() + (direction.getX() * 4), position.getY() + (direction.getY() * 4));
                        break;
                    case 2:
                        calculatedTarget = cornerPosition;
                        break;
                    case 3:
                        calculatedTarget = homePosition;
                        break;
                    case 4:
                        int rand = (int) (Math.random() * (4 - 1));
                        switch (rand){
                            case 0:
                                calculatedTarget = new Point2D(getPhysicalPosition().getX(), getPhysicalPosition().getY() - 1);
                                break;
                            case 1:
                                calculatedTarget = new Point2D(getPhysicalPosition().getX() + 1, getPhysicalPosition().getY());
                                break;
                            case 2:
                                calculatedTarget = new Point2D(getPhysicalPosition().getX(), getPhysicalPosition().getY() + 1);
                                break;
                            case 3:
                                calculatedTarget = new Point2D(getPhysicalPosition().getX() - 1, getPhysicalPosition().getY());
                                break;
                        }
                        break;
                }
            break;
            case 3:
                switch (status){
                    case 0:
                        calculatedTarget = gateExitPosition;
                        break;
                    case 1:
                        // double du vecteur blinky / pacman
                        Point2D direction = pacman.getNewDirection();
                        Point2D position = pacman.getPhysicalPosition();
                        Point2D ahead = new Point2D(position.getX() + (direction.getX() * 2), position.getY() + (direction.getY() * 2));
                        Point2D blinkyPos = ghosts.get(0).getPhysicalPosition();
                        int xB = (int) blinkyPos.getX();
                        int yB = (int) blinkyPos.getY();
                        int xA = (int) ahead.getX();
                        int yA = (int) ahead.getY();
                        int targetX = xA - xB;
                        int targetY = yA - yB;
                        calculatedTarget = new Point2D(xA + targetX, yA + targetY);
                        break;
                    case 2:
                        calculatedTarget = cornerPosition;
                        break;
                    case 3:
                        calculatedTarget = homePosition;
                        break;
                    case 4:
                        int rand = (int) (Math.random() * (4 - 1));
                        switch (rand){
                            case 0:
                                calculatedTarget = new Point2D(getPhysicalPosition().getX(), getPhysicalPosition().getY() - 1);
                                break;
                            case 1:
                                calculatedTarget = new Point2D(getPhysicalPosition().getX() + 1, getPhysicalPosition().getY());
                                break;
                            case 2:
                                calculatedTarget = new Point2D(getPhysicalPosition().getX(), getPhysicalPosition().getY() + 1);
                                break;
                            case 3:
                                calculatedTarget = new Point2D(getPhysicalPosition().getX() - 1, getPhysicalPosition().getY());
                                break;
                        }
                        break;
                }
            break;
            case 4:
                switch (status){
                    case 0:
                        calculatedTarget = gateExitPosition;
                        break;
                    case 1:
                        // si a plus de 8 cases alors pacman sinon scatter
                        int xB = (int) pacman.getPhysicalPosition().getX();
                        int yB = (int) pacman.getPhysicalPosition().getY();
                        int xA = (int) getPhysicalPosition().getX();
                        int yA = (int) getPhysicalPosition().getY();
                        double distance = Math.sqrt(Math.pow(xB - xA, 2) + Math.pow(yB - yA,2));
                        if(distance >= 8){
                            calculatedTarget = pacman.getPhysicalPosition();
                        } else {
                            calculatedTarget = cornerPosition;
                        }
                        break;
                    case 2:
                        calculatedTarget = cornerPosition;
                        break;
                    case 3:
                        calculatedTarget = homePosition;
                        break;
                    case 4:
                        int rand = (int) (Math.random() * (4 - 1));
                        switch (rand){
                            case 0:
                                calculatedTarget = new Point2D(getPhysicalPosition().getX(), getPhysicalPosition().getY() - 1);
                                break;
                            case 1:
                                calculatedTarget = new Point2D(getPhysicalPosition().getX() + 1, getPhysicalPosition().getY());
                                break;
                            case 2:
                                calculatedTarget = new Point2D(getPhysicalPosition().getX(), getPhysicalPosition().getY() + 1);
                                break;
                            case 3:
                                calculatedTarget = new Point2D(getPhysicalPosition().getX() - 1, getPhysicalPosition().getY());
                                break;
                        }
                        break;
                }
            break;
        }
        setTarget(calculatedTarget);
        return calculatedTarget;
    }

    @Override
    public void changeDirection(Point2D direction){
        if(((wantedDirection.getX() == 1 && direction.getX() == -1) && (wantedDirection.getY() == 0 && direction.getY() == 0)) ||
                ((wantedDirection.getX() == -1 && direction.getX() == 1) && (wantedDirection.getY() == 0 && direction.getY() == 0)) ||
                ((wantedDirection.getX() == 0 && direction.getX() == 0) && (wantedDirection.getY() == 1 && direction.getY() == -1)) ||
                ((wantedDirection.getX() == 0 && direction.getX() == 0) && (wantedDirection.getY() == -1 && direction.getY() == 1))){
            return;
        }
        oldDirection = newDirection;
        newDirection = direction;
    }

    @Override
    public void setDirection(Point2D direction){
        wantedDirection = direction;
    }

    @Override
    public Point2D getNewDirection() {
        return newDirection;
    }

    @Override
    public Point2D getOldDirection() {
        return oldDirection;
    }

    @Override
    public Point2D getWantedDirection() {
        return wantedDirection;
    }

    @Override
    public void setNewDirection(Point2D newDirection) {
        this.newDirection = newDirection;
    }

    @Override
    public void setOldDirection(Point2D oldDirection) {
        this.oldDirection = oldDirection;
    }

    @Override
    public void setWantedDirection(Point2D wantedDirection) {
        this.wantedDirection = wantedDirection;
    }

    public void levelRestart(int cpt){
        setGraphicalPosition(new Point2D(((gateExitPosition.getX()-2 +cpt) * (getFitWidth()))-(getFitWidth()/2), ((gateExitPosition.getY()+7) * (getFitHeight()))-(getFitHeight()/2)));
        setPhysicalPosition(getFitWidth()/2);
        setWantedDirection(new Point2D(0,0));
        setOldDirection(new Point2D(0,0));
        setNewDirection(new Point2D(0,0));
        setTileFirstDirection(new Point2D(0,0));
    }
}
