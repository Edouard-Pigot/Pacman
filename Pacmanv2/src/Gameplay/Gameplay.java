package Gameplay;

import Engine.CoreKernel;
import Entity.Entity;
import Entity.MovingEntity;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Gameplay extends Application {

    private Point2D newDirection = new Point2D(0,0);
    private Point2D oldDirection = new Point2D(0,0);
    AnimationTimer gameTimer;
    CoreKernel coreKernel;
    Pacman pacman;

    public int nbOfLives = 3;
    public int score = 0;
    public int time = 0;

    public int nbOfLives = 3;
    public int score = 0;
    public int time = 0;

    @Override
    public void start(Stage stage) throws Exception {
        coreKernel = new CoreKernel();
        coreKernel.startEngines(this, stage);

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
        this.oldDirection = this.newDirection;
        this.newDirection = direction;
    }

    public void spawnPacman(){
        pacman = new Pacman(new Point2D(200,200), 8, Color.RED);
        coreKernel.spawnEntity(pacman);
    }

    private void createGameLoop(){
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                moveEntity(pacman);
                changeNextFrameDirection();
            }
        };
        gameTimer.start();
    }

    private void moveEntity(MovingEntity entity){
        checkCollision(entity);
        checkPrediction(entity);
        System.out.println(newDirection + "/" + oldDirection);
        coreKernel.moveEntity(newDirection, entity);
        System.out.println(newDirection + "/" + oldDirection);
    }

    private void checkCollision(MovingEntity entity){
        ArrayList<Entity> collidingEntities = coreKernel.checkCollision(entity);
        for (Entity collidingEntity : collidingEntities) {
            //QUOI FAIRE POUR QUEL TYPE D'ENTITE
        }
    }

    private void checkPrediction(MovingEntity entity){
        System.out.println("G = " + pacman.graphicalPosition);
        ArrayList<Entity> collidingEntities = coreKernel.checkPrediction(entity, newDirection);
        for (Entity collidingEntity : collidingEntities) {
            if(collidingEntity instanceof Wall){
                ((Wall) collidingEntity).setFill(Color.GREEN);
                ArrayList<Entity> collidingEntitiesOld = coreKernel.checkPrediction(entity, oldDirection);
                for (Entity collidingEntityOld : collidingEntitiesOld) {
                    if(collidingEntityOld instanceof Wall) {
                        setDirection(new Point2D(0, 0));
                        return;
                    }
                }
                setDirection(oldDirection);
                return;
            }
        }
    }

    private void changeNextFrameDirection(){
        oldDirection = newDirection;
    }
}
