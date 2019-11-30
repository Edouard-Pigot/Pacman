package Gameplay;

import Engine.*;
import Entity.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Gameplay extends Application {

    private Point2D newDirection = new Point2D(0,0);
    private Point2D oldDirection = new Point2D(0,0);
    private Point2D wantedDirection = new Point2D(0,0);
    AnimationTimer gameTimer;
    CoreKernel coreKernel;
    Pacman pacman;

    public int nbOfLives = 3;
    public int score = 0;
    public int time = 0;

    public boolean powerSize = false;
    public boolean powerPassThrough = false;

    public int cpt;

    @Override
    public void start(Stage stage) throws Exception {
        coreKernel = new CoreKernel();
        coreKernel.startEngines(this,stage);
        stage.setTitle("Pacman 10.0");
        AnchorPane home = coreKernel.home();
        Scene scene = new Scene(home,448,576);
        stage.setScene(scene);
        stage.show();
    }

    public void play(Stage stage) throws FileNotFoundException {
        Scene scene = coreKernel.scene;
        stage.setScene(scene);
        scene.setOnKeyPressed(coreKernel.inputEngine);
        spawnPacman();
        createGameLoop();
        stage.show();
        coreKernel.updateScoreText(score);
        coreKernel.updateLivesText(nbOfLives);
        coreKernel.updateTimeText(time);
        Thread timeHandlerThread = new Thread(new TimeHandler());
        timeHandlerThread.start();
        stage.setScene(scene);
        stage.show();
    }


    public class TimeHandler implements Runnable{

        @Override
        public void run() {
            while(true){
                try {
                    sleep(1000);
                    ++time;
                    coreKernel.updateTimeText(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setDirection(Point2D direction){
        wantedDirection = direction;
    }

    public void changeDirection(Point2D direction){
        oldDirection = newDirection;
        newDirection = direction;
    }

    public void spawnPacman(){
        pacman = new Pacman(new Point2D(200,200), 8, Color.RED);
        coreKernel.spawnEntity(pacman);
    }

    public void removeEntity(Entity entity){
        coreKernel.removeEntity(entity);
    }

    public void power(){
        if(powerSize == true && cpt >= 300){
            coreKernel.biggerPacman(pacman);
            powerSize=false;
        }
        if(powerPassThrough== true && cpt >= 300){
            powerPassThrough=false;
            checkCollision(pacman);
        }
    }

    public void reSpanwPacman(){
        removeEntity(pacman);
        wantedDirection = new Point2D(0,0);
        nbOfLives -= 1;
        coreKernel.updateLivesText(nbOfLives);
        spawnPacman();
    }

    private void createGameLoop(){
        coreKernel.playBeginningSound();
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                cpt++;
                moveEntity(pacman);
                power();
            }
        };
        gameTimer.start();
    }

    private void moveEntity(MovingEntity entity){
        checkCollision(entity);
        checkPrediction(entity);
        coreKernel.moveEntity(newDirection, entity);
    }

    private void checkCollision(MovingEntity entity){
        ArrayList<Entity> collidingEntities = coreKernel.checkCollision(entity);
        for (Entity collidingEntity : collidingEntities) {
            if(collidingEntity instanceof ScoreEntity){
                removeEntity(collidingEntity);
                score += ((ScoreEntity) collidingEntity).getValue();
                coreKernel.updateScoreText(score);
                if(collidingEntity instanceof PowerSize){
                    powerSize=true;
                    coreKernel.smallerPacman(pacman);
                    cpt=0;
                }else if (collidingEntity instanceof PowerPassThrough){
                    powerPassThrough = true;
                    cpt=0;
                }
            } else if(collidingEntity instanceof Wall && powerPassThrough == false){
                reSpanwPacman();
            }
        }
    }

    private void checkPrediction(MovingEntity entity){
        ArrayList<Entity> collidingEntities = coreKernel.checkGraphicalPrediction(entity, wantedDirection);
        for (Entity collidingEntity : collidingEntities) {
            if(collidingEntity instanceof Empty){
                changeDirection(new Point2D(0, 0));
                return;
            }
            if(collidingEntity instanceof Wall && powerPassThrough == false){
                Entity tile = coreKernel.checkPhysicalPrediction(entity, wantedDirection);
                if(!(tile instanceof Wall)){
                    checkPixelOffset(entity, wantedDirection);
                    changeDirection(oldDirection);
                    return;
                }
                ArrayList<Entity> collidingEntitiesOld = coreKernel.checkGraphicalPrediction(entity, oldDirection);
                for (Entity collidingEntityOld : collidingEntitiesOld) {
                    if(collidingEntityOld instanceof Wall) {
                        changeDirection(new Point2D(0, 0));
                        return;
                    }
                }
                changeDirection(oldDirection);
                return;
            }
        }
        changeDirection(wantedDirection);
    }

    private void checkPixelOffset(MovingEntity entity, Point2D direction){
        int activationAreaFactor = 3;
        if(newDirection.getX() == 1){           //RIGHT
            //System.out.println("RIGHT");
            if(direction.getX() == 1){              //RIGHT
                //System.out.println("RIGHT");
                changeDirection(new Point2D(1, 0));   //RIGHT
                return;
            } else if(direction.getX() == -1){      //LEFT
                //System.out.println("LEFT");
                changeDirection(new Point2D(-1, 0));   //LEFT
                return;
            } else if(direction.getY() == 1){       //DOWN
                if(entity.getGraphicalPosition().getX() < entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    //System.out.println("RIGHT");
                    changeDirection(new Point2D(1, 0));   //RIGHT
                    return;
                } else if(entity.getGraphicalPosition().getX() > entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    //System.out.println("LEFT");
                    changeDirection(new Point2D(-1, 0));   //LEFT
                    return;
                }
            } else if(direction.getY() == -1){      //UP
                if(entity.getGraphicalPosition().getX() < entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    //System.out.println("RIGHT");
                    changeDirection(new Point2D(1, 0));   //RIGHT
                    return;
                } else if(entity.getGraphicalPosition().getX() > entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    //System.out.println("LEFT");
                    changeDirection(new Point2D(-1, 0));   //LEFT
                    return;
                }
            }
        } else if(newDirection.getX() == -1){   //LEFT
            if(direction.getX() == 1){              //RIGHT
                //System.out.println("RIGHT");
                changeDirection(new Point2D(1, 0));   //RIGHT
                return;
            } else if(direction.getX() == -1){      //LEFT
                //System.out.println("LEFT");
                changeDirection(new Point2D(-1, 0));   //LEFT
                return;
            } else if(direction.getY() == 1){       //DOWN
                if(entity.getGraphicalPosition().getX() < entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    //System.out.println("LEFT");
                    changeDirection(new Point2D(-1, 0));   //LEFT
                    return;
                } else if(entity.getGraphicalPosition().getX() > entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    //System.out.println("RIGHT");
                    changeDirection(new Point2D(1, 0));   //RIGHT
                    return;
                }
            } else if(direction.getY() == -1){      //UP
                if(entity.getGraphicalPosition().getX() < entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    //System.out.println("LEFT");
                    changeDirection(new Point2D(-1, 0));   //LEFT
                    return;
                } else if(entity.getGraphicalPosition().getX() > entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    //System.out.println("RIGHT");
                    changeDirection(new Point2D(1, 0));   //RIGHT
                    return;
                }
            }
        } else if(newDirection.getY() == 1){    //DOWN
            if(direction.getX() == 1){              //RIGHT
                //System.out.println("RIGHT");
                if(entity.getGraphicalPosition().getY() > entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    //System.out.println("UP");
                    changeDirection(new Point2D(0, -1));   //UP
                    return;
                } else if(entity.getGraphicalPosition().getY() < entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    //System.out.println("DOWN");
                    changeDirection(new Point2D(0, 1));   //DOWN
                    return;
                }
            } else if(direction.getX() == -1){      //LEFT
                if(entity.getGraphicalPosition().getY() > entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    //System.out.println("UP");
                    changeDirection(new Point2D(0, -1));   //UP
                    return;
                } else if(entity.getGraphicalPosition().getY() < entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    //System.out.println("DOWN");
                    changeDirection(new Point2D(0, 1));   //DOWN
                    return;
                }
            } else if(direction.getY() == 1){       //DOWN
                //System.out.println("DOWN");
                changeDirection(new Point2D(0, 1));   //DOWN
                return;
            } else if(direction.getY() == -1){      //UP
                //System.out.println("UP");
                changeDirection(new Point2D(0, -1));   //UP
                return;
            }
        } else if(newDirection.getY() == -1){   //UP
            if(direction.getX() == 1){              //RIGHT
                if(entity.getGraphicalPosition().getY() > entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    //System.out.println("DOWN");
                    changeDirection(new Point2D(0, 1));   //DOWN
                    return;
                } else if(entity.getGraphicalPosition().getY() < entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    //System.out.println("UP");
                    changeDirection(new Point2D(0, -1));   //UP
                    return;
                }
            } else if(direction.getX() == -1){      //LEFT
                if(entity.getGraphicalPosition().getY() > entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    //System.out.println("DOWN");
                    changeDirection(new Point2D(0, 1));   //DOWN
                    return;
                } else if(entity.getGraphicalPosition().getY() < entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    //System.out.println("UP");
                    changeDirection(new Point2D(0, -1));   //UP
                    return;
                }
            } else if(direction.getY() == 1){       //DOWN
                //System.out.println("DOWN");
                changeDirection(new Point2D(0, 1));   //DOWN
                return;
            } else if(direction.getY() == -1){      //UP
                //System.out.println("UP");
                changeDirection(new Point2D(0, -1));   //UP
                return;
            }
        }
    }
}
