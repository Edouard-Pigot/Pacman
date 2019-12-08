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
    ArrayList<Ghost> ghosts;

    Bonus bonus;


    public Stage stage;

    public int nbOfLives;
    public int score;
    public int time;

    public boolean powerSize = false;
    public boolean powerPassThrough = false;

    public int cpt;
    public int nbGhostEaten;

    private int tick;
    private int chaseCpt;
    private int scatterCpt;
    private int frightCpt;

    private boolean frightModeOn;
    private int actualMode;
    private int phase;
    private int[] phaseTimes;

    private int nbScoreEntity ;

    private int spawn;

    private int frame;

    private int level = 1;

    @Override
    public void start(Stage stage) throws Exception {
        init(stage);
        this.stage = stage;
    }

    public void init(Stage stage)throws Exception{
        coreKernel = new CoreKernel();
        coreKernel.startEngines(this,stage);
        stage.setTitle("Pacman 10.0");
        coreKernel.home(stage);
    }

    public void play(Stage stage) throws FileNotFoundException {
        nbOfLives = 3;
        score = 0;
        time = 0;

        powerSize = false;
        powerPassThrough = false;

        tick = 0;
        chaseCpt = 0;
        scatterCpt = 0;
        frightCpt = 0;

        frightModeOn = false;
        actualMode = 2;

        phase = 0;
        phaseTimes = new int[]{7, 20, 7, 20, 5, 20, -1};

        nbScoreEntity = 0;

        spawn = 0;

        ghosts = new ArrayList<Ghost>();

        Scene scene = coreKernel.scene;
        stage.setScene(scene);
        scene.setOnKeyPressed(coreKernel.inputEngine);
        spawnBonus();
        spawnPacman();
        spawnGhosts();
        System.out.println(coreKernel.map.findBonus());
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
        blinky = new Ghost(new Point2D(12.5*16,17.5*16),8, "RED",1, 1);
        inky = new Ghost(new Point2D(13.5*16,17.5*16),8, "CYAN",2, 1);
        clyde = new Ghost(new Point2D(15.5*16,17.5*16),8, "ORANGE",3, 1);
        pinky = new Ghost(new Point2D(16.5*16,17.5*16),8, "PINK",4, 1);
        ghosts.add(blinky);
        ghosts.add(clyde);
        ghosts.add(inky);

        ghosts.add(pinky);
        spawnEntity(blinky);
        spawnEntity(inky);
        spawnEntity(clyde);
        spawnEntity(pinky);
        for(Ghost ghost : ghosts){
            ghost.setHomePosition(coreKernel.getGhostHomePosition(ghost));
            ghost.setCornerPosition(coreKernel.getGhostCornerPosition(ghost));
            ghost.setGateExitPosition(coreKernel.getGhostGateExitPosition());
            coreKernel.refreshImage(ghost, frame);
        }
    }


    public void spawnPacman(){
        pacman = new Pacman(new Point2D(14.5*16,26.5*16), 8);
        spawnEntity(pacman);
    }

    public void spawnBonus(){
        bonus = new Bonus(new Point2D(14.5*16,24.5*16), new Point2D(13,20), 8, level);
        spawnEntity(bonus);
    }

    public void checkExitedHouse(){
        for(Ghost ghost : ghosts){
            if((int) ghost.getPhysicalPosition().getX() == (int) ghost.getTarget().getX() && (int) ghost.getPhysicalPosition().getY() == (int) ghost.getTarget().getY() && ghost.getStatus() == 0){
                ghost.exitedHouse = true;
            }
            if((int) ghost.getPhysicalPosition().getX() == (int) ghost.getTarget().getX() && (int) ghost.getPhysicalPosition().getY() == (int) ghost.getTarget().getY() && ghost.getStatus() == 0 && ghost.exitedHouse) {
                ghost.setStatus(actualMode);
            }
        }
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
        spawnBonus();
    }

    public void spawnEntity(Entity entity){
        coreKernel.spawnEntity(entity);
    }

    public void removeEntity(Entity entity){
        coreKernel.removeEntity(entity);
    }

    public void respawnPacman(){
        coreKernel.playDeathSound();
        removeEntity(pacman);
        powerPassThrough=false;
        pacman.setWantedDirection( new Point2D(0,0));
        nbOfLives -= 1;
        for (Ghost ghost: ghosts){
            ghost.levelRestart(spawn);
            spawn++;
        }
        spawn = 0;
        coreKernel.updateLivesText(nbOfLives);
        spawnPacman();
    }

    public void power(){
        if(frightModeOn  && cpt>=300){
            changeGhostsStatus(actualMode);
            frightModeOn = false;
        }
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
                tick++;


                if(tick %5==0) {
                    if (frame == 1) {
                        frame = 0;
                    } else if (frame == 0) {
                        frame = 1;
                    }
                }

                if(tick %60==0) {
                    coreKernel.updateTimeText(tick / 60);
                    if(phaseTimes[phase] != -1) {
                        switch (actualMode) {
                            case 1:
                                if (!frightModeOn) {
                                    chaseCpt++;
                                    if (chaseCpt % phaseTimes[phase] == 0) {
                                        actualMode = 2;
                                        phase++;
                                        changeGhostsStatus(actualMode);
                                    }
                                }
                                break;
                            case 2:
                                if (!frightModeOn) {
                                    scatterCpt++;
                                    if (scatterCpt % phaseTimes[phase] == 0) {
                                        actualMode = 1;
                                        phase++;
                                        changeGhostsStatus(actualMode);
                                    }
                                }
                                break;
                            case 4:
                                if (frightModeOn) {
                                    frightCpt++;
                                    if (frightCpt % 6 == 0) {
                                        frightModeOn = false;
                                        changeGhostsStatus(actualMode);
                                    }
                                }
                                break;
                        }
                    }
                }

                if(!coreKernel.map.containsScoreEntity()) {
                    ++level;
                    coreKernel.playBeginningSound();
                    try {
                        resetMap();
                        resetPacman();
                        resetGhosts();
                        coreKernel.updateScoreText(score);
                        coreKernel.updateLivesText(nbOfLives);
                        coreKernel.updateTimeText(time);
                        nbScoreEntity =0;

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                if(nbOfLives <=0) {
                    try {
                        coreKernel.gameOver(stage);
                        nbOfLives = 3;
                        gameTimer.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        gameTimer.start();
    }

    public void changeGhostsStatus(int status){
        System.out.println("CHANGE TO " + status);
        for(Ghost ghost : ghosts){
            if(ghost.exitedHouse) {
                ghost.setStatus(status);
            }
            coreKernel.refreshImage(ghost, frame);
        }
    }

    public void changeStatus(int status,Ghost ghost){
        if(ghost.exitedHouse) {
            ghost.setStatus(status);
        }
        coreKernel.refreshImage(ghost, frame);
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
        coreKernel.refreshImage(entity, frame);
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
                    if(collidingEntity instanceof Bonus){

                    }
                    score += ((ScoreEntity) collidingEntity).getValue();
                    coreKernel.updateScoreText(score);
                    if(collidingEntity instanceof SuperPacGum){
                        frightModeOn = true;
                        changeGhostsStatus(4);
                        cpt = 0;
                        nbGhostEaten = 1;
                    }
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
                } else if(collidingEntity instanceof Ghost && !frightModeOn ){
                    respawnPacman();
                } else if(collidingEntity instanceof Ghost && frightModeOn ){
                    changeStatus(0,(Ghost) collidingEntity);
                    nbGhostEaten*=2;
                    score+=(100*nbGhostEaten);
                    System.out.println(score);
                    coreKernel.updateScoreText(score);
                    ((Ghost) collidingEntity).levelRestart(spawn);
                }
                if (collidingEntity instanceof PacGum)
                    coreKernel.playChompSound();
                else if (collidingEntity instanceof SuperPacGum)
                    coreKernel.playChompSound();
                else if (collidingEntity instanceof PowerSize)
                    coreKernel.playEatFruitSound();
                else if (collidingEntity instanceof PowerPassThrough)
                    coreKernel.playEatFruitSound();
                else if (collidingEntity instanceof Bonus)
                    coreKernel.playEatFruitSound();
                else if(collidingEntity instanceof Ghost && frightModeOn)
                    coreKernel.playEatGhostSound();
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
            if(entity instanceof Ghost) {
                if (((Ghost) entity).getStatus() == 0) {
                    canExitHouse = true;
                }
            }

            boolean powerPass = false;
            if(entity instanceof Pacman) {
                if(powerPassThrough){
                    powerPass = true;
                }
            }

            if((collidingEntity instanceof Wall  || (collidingEntity instanceof Door && !canExitHouse)) && !powerPass){
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
