package Gameplay;

import Engine.BoxCollider;
import Entity.Entity;
import Entity.MovingEntity;
import Entity.StaticEntity;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Ghost extends Circle implements MovingEntity {
    Point2D physicalPosition = new Point2D(0,0);
    Point2D graphicalPosition = new Point2D(0,0);

    Point2D homePosition = new Point2D(0,0);
    Point2D gateExitPosition = new Point2D(0,0);
    Point2D cornerPosition = new Point2D(0,0);

    Color originalColor;

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

    public void setColor(Color color){
        setFill(color);
    }

    public Ghost(Point2D graphicalPosition, double size, Color color, int id, int status) {
        originalColor = color;
        this.status = status;
        setGraphicalPosition(graphicalPosition);
        setCenterX(0);
        setLayoutX(graphicalPosition.getX());
        setCenterY(0);
        setLayoutY(graphicalPosition.getY());
        setRadius(size);
        setFill(color);
        setPhysicalPosition(size/2);
        this.id = id;
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
        setLayoutX(graphicalPosition.getX());
        setLayoutY(graphicalPosition.getY());
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
        return new BoxCollider((int) graphicalPosition.getX() - (int) getRadius(), (int) graphicalPosition.getX() + (int) getRadius(), (int) graphicalPosition.getY() - (int) getRadius(), (int) graphicalPosition.getY() + (int) getRadius());
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

    public void levelRestart(){
        setGraphicalPosition(new Point2D(((gateExitPosition.getX()-2) * (getRadius()*2))-getRadius(), (gateExitPosition.getY() * (getRadius()*2))-getRadius()));
        setPhysicalPosition(getRadius());
    }
}
