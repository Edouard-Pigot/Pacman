package Gameplay;

import Engine.CoreKernel;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import static java.lang.Thread.sleep;

public class Gameplay extends Application {

    private Point2D newDirection = new Point2D(0,0);
    private Point2D oldDirection = new Point2D(0,0);
    AnimationTimer gameTimer;
    CoreKernel coreKernel;
    Pacman pacman;
    Circle circle = new Circle();

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
        debug();
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
        pacman = new Pacman(new Point2D(200,200));
        coreKernel.spawnEntity(pacman);
    }

    public void debug(){
        coreKernel.spawnEntity(circle);
        circle.setFill(Color.CYAN);
        circle.setRadius(6);
        circle.setCenterX(0);
        circle.setLayoutX(200);
        circle.setCenterY(0);
        circle.setLayoutY(200);
    }

    private void createGameLoop(){
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                setDirection(checkCollision());
                movePacman();
            }
        };
        gameTimer.start();
    }

    private Point2D checkCollision(){
        return coreKernel.checkCollision(pacman, newDirection, oldDirection, circle);
    }

    private void movePacman(){
        coreKernel.movePacman(newDirection, pacman);
    }
}
