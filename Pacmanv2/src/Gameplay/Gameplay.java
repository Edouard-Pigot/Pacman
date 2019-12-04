package Gameplay;

import Engine.CoreKernel;
import Entity.*;
import Entity.MovingEntity;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Gameplay extends Application {

    AnimationTimer gameTimer;
    CoreKernel coreKernel;

    Pacman pacman;
    Ghost blinky;
    Ghost inky;
    Ghost clyde;
    Ghost pinky;
    ArrayList<Ghost> ghosts = new ArrayList<Ghost>();

    public Stage stage;

    public int nbOfLives = 3;
    public int score = 0;
    public int time = 0;

    public boolean powerSize = false;
    public boolean powerPassThrough = false;

    public int cpt;

    private int tick = 0;
    private int chaseCpt = 0;
    private int scatterCpt = 0;
    private int frightCpt = 0;

    private int phase = 0;
    private int[] phaseTimes = {7,20,7,20,5,20,-1};

    private int nbScoreEntity = 0;

    public boolean canExitHouse = false;


    @Override
    public void start(Stage stage) throws Exception {
        coreKernel = new CoreKernel();
        coreKernel.startEngines(this,stage);
        stage.setTitle("Pacman 10.0");
        home(stage);
        this.stage = stage;
    }

    public void home(Stage stage) throws MalformedURLException {
        AnchorPane home = coreKernel.home();
        Scene scene = new Scene(home,448,576);
        stage.setScene(scene);
        stage.show();
    }

    public void gameOver(Stage stage) throws MalformedURLException {
        AnchorPane gameOver = coreKernel.gameOver();
        Scene scene = new Scene(gameOver,448,576);
        stage.setScene(scene);
        stage.show();
    }

    public void rules(Stage stage) throws MalformedURLException {
        AnchorPane rules = coreKernel.rules();
        Scene scene = new Scene(rules,448,576);
        stage.setScene(scene);
        stage.show();
    }

    public void play(Stage stage) throws FileNotFoundException {
        Scene scene = coreKernel.scene;
        stage.setScene(scene);
        scene.setOnKeyPressed(coreKernel.inputEngine);
        spawnPacman();
        spawnGhosts();
        createGameLoop();
        stage.show();
        coreKernel.updateScoreText(score);
        coreKernel.updateLivesText(nbOfLives);
        coreKernel.updateTimeText(time);
        stage.setScene(scene);
        stage.show();
    }

    public void setPacmanDirection(Point2D direction){
        pacman.setWantedDirection(direction);
    }

    public void openDoor(){
        if(nbScoreEntity == 0){
            exitHouse(blinky);
        }else if(nbScoreEntity == 7){
            exitHouse(pinky);
        }else if(nbScoreEntity == 17){
            exitHouse(inky);
        }else if(nbScoreEntity == 32){
            exitHouse(clyde);
        }
    }

    public void exitHouse(Ghost ghost){
        ghost.setStatus(0);
    }

    public void spawnGhosts(){
        blinky = new Ghost(new Point2D(12.5*16,17.5*16),8, Color.RED,1,0);
        inky = new Ghost(new Point2D(13.5*16,17.5*16),8, Color.CYAN,2,0);
        clyde = new Ghost(new Point2D(15.5*16,17.5*16),8, Color.ORANGE,3,0);
        pinky = new Ghost(new Point2D(16.5*16,17.5*16),8, Color.PINK,4,0);
        ghosts.add(blinky);
        ghosts.add(inky);
        ghosts.add(clyde);
        ghosts.add(pinky);
        spawnEntity(blinky);
        spawnEntity(inky);
        spawnEntity(clyde);
        spawnEntity(pinky);
        for(Ghost ghost : ghosts){
            ghost.setHomePosition(coreKernel.getGhostHomePosition(ghost));
            ghost.setCornerPosition(coreKernel.getGhostCornerPosition(ghost));
            ghost.setGateExitPosition(coreKernel.getGhostGateExitPosition(ghost));
            ghost.setStatus(1);
        }
    }

    public void checkExitedHouse(){
        for(Ghost ghost : ghosts){
            if((int) ghost.getPhysicalPosition().getX() == (int) ghost.getTarget().getX() && (int) ghost.getPhysicalPosition().getY() == (int) ghost.getTarget().getY() && (int) ghost.getStatus() == 0){
                ghost.setStatus(2);
                System.out.println("CHANGE STATUS !!!");
            }
        }
    }

    public void spawnPacman(){
        pacman = new Pacman(new Point2D(14.5*16,26.5*16), 8, Color.YELLOW);
        spawnEntity(pacman);
    }

    public void resetPacman(){
        coreKernel.removeEntity(pacman);
        spawnPacman();
        moveEntity(pacman);
    }

    public void resetGhosts(){
        for(Ghost ghost : ghosts)
            coreKernel.removeEntity(ghost);
        ghosts = new ArrayList<>();
        spawnGhosts();
        moveGhosts();
    }

    public void resetMap() throws FileNotFoundException {
        coreKernel.reloadMap();
    }

    public void spawnEntity(Entity entity){
        coreKernel.spawnEntity(entity);
    }

    public void removeEntity(Entity entity){
        coreKernel.removeEntity(entity);
    }

    public void respawnPacman(){
        removeEntity(pacman);
        pacman.setWantedDirection( new Point2D(0,0));
        nbOfLives -= 1;
        coreKernel.updateLivesText(nbOfLives);
        spawnPacman();
    }

    public void power(){
        if(powerSize && cpt >= 300){
            coreKernel.center(pacman);
            coreKernel.biggerPacman(pacman);
            powerSize=false;
        }
        if(powerPassThrough && cpt >= 300){
            coreKernel.center(pacman);
            powerPassThrough=false;
            checkCollision(pacman);
        }
    }

    private void createGameLoop(){
        coreKernel.playBeginningSound();
        tick = 0;
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                moveEntity(pacman);
                moveGhosts();
                power();
                openDoor();
                checkExitedHouse();
                cpt++;
                ++tick;

                if(tick %60==0)
                    coreKernel.updateTimeText(tick /60);

                if(!coreKernel.map.containsScoreEntity()) {
                    System.out.println("GOING TO NEXT LEVEL");
                    coreKernel.playBeginningSound();

                    try {
                        resetMap();
                        resetPacman();
                        resetGhosts();
                        coreKernel.updateScoreText(score);
                        coreKernel.updateLivesText(nbOfLives);
                        coreKernel.updateTimeText(time);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if(nbOfLives==0) {
                    try {
                        gameOver(stage);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        gameTimer.start();
    }

    private void moveGhosts(){
        for (Ghost ghost : ghosts) {
            Point2D targetCoordinate = ghost.calculateTarget(pacman, ghosts);
            Point2D direction = coreKernel.calculateMove(targetCoordinate, ghost);
            if(direction.getX() == 0 && direction.getY() == 0){
                direction = ghost.getOldDirection();
            }
            ghost.setDirection(direction);
            moveEntity(ghost);
        }
    }

    private void moveEntity(MovingEntity entity){
        checkCollision(entity);
        checkPrediction(entity);
        coreKernel.moveEntity(entity.getNewDirection(), entity);
    }

    private void checkCollision(MovingEntity entity){
        ArrayList<Entity> collidingEntities = coreKernel.checkCollision(entity);
        if(entity instanceof Pacman) {
            for (Entity collidingEntity : collidingEntities) {
                if (collidingEntity instanceof ScoreEntity) {
                    removeEntity(collidingEntity);
                    nbScoreEntity +=1;
                    System.out.println(nbScoreEntity);
                    score += ((ScoreEntity) collidingEntity).getValue();
                    coreKernel.updateScoreText(score);
                    if (collidingEntity instanceof PowerSize) {
                        powerSize = true;
                        coreKernel.smallerPacman(pacman);
                        cpt = 0;
                    } else if (collidingEntity instanceof PowerPassThrough) {
                        powerPassThrough = true;
                        cpt = 0;
                    }
                } else if (collidingEntity instanceof Wall && !powerPassThrough) {
                    respawnPacman();
                }
                else if (collidingEntity instanceof Ghost){
                    respawnPacman();
                }

                if (collidingEntity instanceof PacGum)
                    coreKernel.playChompSound();
                else if (collidingEntity instanceof SuperPacGum)
                    coreKernel.playChompSound();
                else if (collidingEntity instanceof PowerSize)
                    coreKernel.playChompSound();
                else if (collidingEntity instanceof PowerPassThrough)
                    coreKernel.playChompSound();
                else if (collidingEntity instanceof Bonus)
                    coreKernel.playChompSound();
                //Ajouter les sons des fantômes en conséquence
            }
        }
    }

    private void checkPrediction(MovingEntity entity){
        ArrayList<Entity> collidingEntities = coreKernel.checkGraphicalPrediction(entity, entity.getWantedDirection());
        for (Entity collidingEntity : collidingEntities) {
            if(collidingEntity instanceof Empty){
                entity.changeDirection(new Point2D(0, 0));
                return;
            }
            boolean canExitHouse = false;
            if(entity instanceof Ghost){
                if (((Ghost) entity).getStatus() == 0){
                    canExitHouse = true;
                }
            }
            if((collidingEntity instanceof Wall  || (collidingEntity instanceof Door && !canExitHouse)) && powerPassThrough == false){
                Entity tile = coreKernel.checkPhysicalPrediction(entity,  entity.getWantedDirection());
                if(!(tile instanceof Wall || (tile instanceof Door && !canExitHouse))){
                    checkPixelOffset(entity,  entity.getWantedDirection());
                    entity.changeDirection( entity.getOldDirection());
                    return;
                }
                ArrayList<Entity> collidingEntitiesOld = coreKernel.checkGraphicalPrediction(entity,  entity.getOldDirection());
                for (Entity collidingEntityOld : collidingEntitiesOld) {
                    if(collidingEntityOld instanceof Wall|| (collidingEntityOld instanceof Door && !canExitHouse)) {
                        entity.changeDirection(new Point2D(0, 0));
                        return;
                    }
                }
                entity.changeDirection(entity.getOldDirection());
                return;
            }
        }
        entity.changeDirection(entity.getWantedDirection());
    }

    private void checkPixelOffset(MovingEntity entity, Point2D direction){
        int activationAreaFactor = 2;
        if(entity.getNewDirection().getX() == 1){           //RIGHT
            if(direction.getX() == 1){              //RIGHT
                entity.changeDirection(new Point2D(1, 0));   //RIGHT
//                System.out.println("NEW RIGHT WANTED RIGHT CHANGE RIGHT");
                return;
            } else if(direction.getX() == -1){      //LEFT
                entity.changeDirection(new Point2D(-1, 0));   //LEFT
//                System.out.println("NEW RIGHT WANTED LEFT CHANGE LEFT");
                return;
            } else if(direction.getY() == 1){       //DOWN
                if(entity.getGraphicalPosition().getX() < entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(1, 0));   //RIGHT
//                    System.out.println("NEW RIGHT WANTED DOWN CHANGE RIGHT");
                    return;
                } else if(entity.getGraphicalPosition().getX() > entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(-1, 0));   //LEFT
//                    System.out.println("NEW RIGHT WANTED DOWN CHANGE LEFT");
                    return;
                }
            } else if(direction.getY() == -1){      //UP
                if(entity.getGraphicalPosition().getX() < entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(1, 0));   //RIGHT
//                    System.out.println("NEW RIGHT WANTED UP CHANGE RIGHT");
                    return;
                } else if(entity.getGraphicalPosition().getX() > entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(-1, 0));   //LEFT
//                    System.out.println("NEW RIGHT WANTED UP CHANGE LEFT");
                    return;
                }
            }
        } else if(entity.getNewDirection().getX() == -1){   //LEFT
            if(direction.getX() == 1){              //RIGHT
                entity.changeDirection(new Point2D(1, 0));   //RIGHT
//                System.out.println("NEW LEFT WANTED RIGHT CHANGE RIGHT");
                return;
            } else if(direction.getX() == -1){      //LEFT
                entity.changeDirection(new Point2D(-1, 0));   //LEFT
//                System.out.println("NEW LEFT WANTED LEFT CHANGE LEFT");
                return;
            } else if(direction.getY() == 1){       //DOWN
                if(entity.getGraphicalPosition().getX() < entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(-1, 0));   //LEFT
//                    System.out.println("NEW LEFT WANTED DOWN CHANGE LEFT");
                    return;
                } else if(entity.getGraphicalPosition().getX() > entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(1, 0));   //RIGHT
//                    System.out.println("NEW LEFT WANTED DOWN CHANGE RIGHT");
                    return;
                }
            } else if(direction.getY() == -1){      //UP
                if(entity.getGraphicalPosition().getX() < entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(-1, 0));   //LEFT
//                    System.out.println("NEW LEFT WANTED UP CHANGE LEFT");
                    return;
                } else if(entity.getGraphicalPosition().getX() > entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(1, 0));   //RIGHT
//                    System.out.println("NEW LEFT WANTED UP CHANGE RIGHT");
                    return;
                }
            }
        } else if(entity.getNewDirection().getY() == 1){    //DOWN
            if(direction.getX() == 1){              //RIGHT
                if(entity.getGraphicalPosition().getY() > entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(0, -1));   //UP
//                    System.out.println("NEW DOWN WANTED RIGHT CHANGE UP");
                    return;
                } else if(entity.getGraphicalPosition().getY() < entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(0, 1));   //DOWN
//                    System.out.println("NEW DOWN WANTED RIGHT CHANGE DOWN");
                    return;
                }
            } else if(direction.getX() == -1){      //LEFT
                if(entity.getGraphicalPosition().getY() > entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(0, -1));   //UP
//                    System.out.println("NEW DOWN WANTED LEFT CHANGE UP");
                    return;
                } else if(entity.getGraphicalPosition().getY() < entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(0, 1));   //DOWN
//                    System.out.println("NEW DOWN WANTED LEFT CHANGE DOWN");
                    return;
                }
            } else if(direction.getY() == 1){       //DOWN
                entity.changeDirection(new Point2D(0, 1));   //DOWN
//                System.out.println("NEW DOWN WANTED DOWN CHANGE DOWN");
                return;
            } else if(direction.getY() == -1){      //UP
                entity.changeDirection(new Point2D(0, -1));   //UP
//                System.out.println("NEW DOWN WANTED UP CHANGE UP");
                return;
            }
        } else if(entity.getNewDirection().getY() == -1){   //UP
            if(direction.getX() == 1){              //RIGHT
                if(entity.getGraphicalPosition().getY() > entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(0, 1));   //DOWN
//                    System.out.println("NEW UP WANTED RIGHT CHANGE DOWN");
                    return;
                } else if(entity.getGraphicalPosition().getY() < entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(0, -1));   //UP
//                    System.out.println("NEW UP WANTED RIGHT CHANGE UP");
                    return;
                }
            } else if(direction.getX() == -1){      //LEFT
                if(entity.getGraphicalPosition().getY() > entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(0, 1));   //DOWN
//                    System.out.println("NEW UP WANTED LEFT CHANGE DOWN");
                    return;
                } else if(entity.getGraphicalPosition().getY() < entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(0, -1));   //UP
//                    System.out.println("NEW UP WANTED LEFT CHANGE UP");
                    return;
                }
            } else if(direction.getY() == 1){       //DOWN
                entity.changeDirection(new Point2D(0, 1));   //DOWN
//                System.out.println("NEW UP WANTED DOWN CHANGE DOWN");
                return;
            } else if(direction.getY() == -1){      //UP
                entity.changeDirection(new Point2D(0, -1));   //UP
//                System.out.println("NEW UP WANTED UP CHANGE UP");
                return;
            }
        }
    }
}
