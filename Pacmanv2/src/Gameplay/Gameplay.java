package Gameplay;

import Engine.CoreKernel;
import Entity.Entity;
import Entity.MovingEntity;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Gameplay extends Application {

    AnimationTimer gameTimer;
    CoreKernel coreKernel;

    Pacman pacman;
    Ghost blinky;
    ArrayList<Ghost> ghosts = new ArrayList<Ghost>();

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
        spawnGhosts();
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

    public void setPacmanDirection(Point2D direction){
        pacman.setWantedDirection(direction);
    }

    public void spawnGhosts(){
        blinky = new Ghost(new Point2D(12.5*16,17.5*16),8, Color.CYAN,1);
        ghosts.add(blinky);
        spawnEntity(blinky);
    }

    public void spawnPacman(){
        pacman = new Pacman(new Point2D(14.5*16,26.5*16), 8, Color.RED);
        spawnEntity(pacman);
    }

    public void spawnEntity(Entity entity){
        coreKernel.spawnEntity(entity);
    }

    public void removeEntity(Entity entity){
        coreKernel.removeEntity(entity);
    }

    private void createGameLoop(){//////////////////////////////////////////////////////////////////////////////////////
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                moveEntity(pacman);
                moveGhosts();
            }
        };
        gameTimer.start();
    }

    private void moveGhosts(){
        for (Ghost ghost : ghosts) {
            Point2D targetCoordinate = ghost.getTarget(pacman, ghosts);
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
        for (Entity collidingEntity : collidingEntities) {
            if(collidingEntity instanceof PacGum){
                removeEntity(collidingEntity);
            }
        }
    }

    private void checkPrediction(MovingEntity entity){
        ArrayList<Entity> collidingEntities = coreKernel.checkGraphicalPrediction(entity, entity.getWantedDirection());
        for (Entity collidingEntity : collidingEntities) {
            if(collidingEntity instanceof Wall){
                Entity tile = coreKernel.checkPhysicalPrediction(entity,  entity.getWantedDirection());
                if(!(tile instanceof Wall)){
                    checkPixelOffset(entity,  entity.getWantedDirection());
                    entity.changeDirection( entity.getOldDirection());
                    return;
                }
                ArrayList<Entity> collidingEntitiesOld = coreKernel.checkGraphicalPrediction(entity,  entity.getOldDirection());
                for (Entity collidingEntityOld : collidingEntitiesOld) {
                    if(collidingEntityOld instanceof Wall) {
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
                System.out.println("NEW RIGHT WANTED RIGHT CHANGE RIGHT");
                return;
            } else if(direction.getX() == -1){      //LEFT
                entity.changeDirection(new Point2D(-1, 0));   //LEFT
                System.out.println("NEW RIGHT WANTED LEFT CHANGE LEFT");
                return;
            } else if(direction.getY() == 1){       //DOWN
                if(entity.getGraphicalPosition().getX() < entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(1, 0));   //RIGHT
                    System.out.println("NEW RIGHT WANTED DOWN CHANGE RIGHT");
                    return;
                } else if(entity.getGraphicalPosition().getX() > entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(-1, 0));   //LEFT
                    System.out.println("NEW RIGHT WANTED DOWN CHANGE LEFT");
                    return;
                }
            } else if(direction.getY() == -1){      //UP
                if(entity.getGraphicalPosition().getX() < entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(1, 0));   //RIGHT
                    System.out.println("NEW RIGHT WANTED UP CHANGE RIGHT");
                    return;
                } else if(entity.getGraphicalPosition().getX() > entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(-1, 0));   //LEFT
                    System.out.println("NEW RIGHT WANTED UP CHANGE LEFT");
                    return;
                }
            }
        } else if(entity.getNewDirection().getX() == -1){   //LEFT
            if(direction.getX() == 1){              //RIGHT
                entity.changeDirection(new Point2D(1, 0));   //RIGHT
                System.out.println("NEW LEFT WANTED RIGHT CHANGE RIGHT");
                return;
            } else if(direction.getX() == -1){      //LEFT
                entity.changeDirection(new Point2D(-1, 0));   //LEFT
                System.out.println("NEW LEFT WANTED LEFT CHANGE LEFT");
                return;
            } else if(direction.getY() == 1){       //DOWN
                if(entity.getGraphicalPosition().getX() < entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(-1, 0));   //LEFT
                    System.out.println("NEW LEFT WANTED DOWN CHANGE LEFT");
                    return;
                } else if(entity.getGraphicalPosition().getX() > entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(1, 0));   //RIGHT
                    System.out.println("NEW LEFT WANTED DOWN CHANGE RIGHT");
                    return;
                }
            } else if(direction.getY() == -1){      //UP
                if(entity.getGraphicalPosition().getX() < entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(-1, 0));   //LEFT
                    System.out.println("NEW LEFT WANTED UP CHANGE LEFT");
                    return;
                } else if(entity.getGraphicalPosition().getX() > entity.getPhysicalPosition().getX() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(1, 0));   //RIGHT
                    System.out.println("NEW LEFT WANTED UP CHANGE RIGHT");
                    return;
                }
            }
        } else if(entity.getNewDirection().getY() == 1){    //DOWN
            if(direction.getX() == 1){              //RIGHT
                if(entity.getGraphicalPosition().getY() > entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(0, -1));   //UP
                    System.out.println("NEW DOWN WANTED RIGHT CHANGE UP");
                    return;
                } else if(entity.getGraphicalPosition().getY() < entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(0, 1));   //DOWN
                    System.out.println("NEW DOWN WANTED RIGHT CHANGE DOWN");
                    return;
                }
            } else if(direction.getX() == -1){      //LEFT
                if(entity.getGraphicalPosition().getY() > entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(0, -1));   //UP
                    System.out.println("NEW DOWN WANTED LEFT CHANGE UP");
                    return;
                } else if(entity.getGraphicalPosition().getY() < entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(0, 1));   //DOWN
                    System.out.println("NEW DOWN WANTED LEFT CHANGE DOWN");
                    return;
                }
            } else if(direction.getY() == 1){       //DOWN
                entity.changeDirection(new Point2D(0, 1));   //DOWN
                System.out.println("NEW DOWN WANTED DOWN CHANGE DOWN");
                return;
            } else if(direction.getY() == -1){      //UP
                entity.changeDirection(new Point2D(0, -1));   //UP
                System.out.println("NEW DOWN WANTED UP CHANGE UP");
                return;
            }
        } else if(entity.getNewDirection().getY() == -1){   //UP
            if(direction.getX() == 1){              //RIGHT
                if(entity.getGraphicalPosition().getY() > entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(0, 1));   //DOWN
                    System.out.println("NEW UP WANTED RIGHT CHANGE DOWN");
                    return;
                } else if(entity.getGraphicalPosition().getY() < entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(0, -1));   //UP
                    System.out.println("NEW UP WANTED RIGHT CHANGE UP");
                    return;
                }
            } else if(direction.getX() == -1){      //LEFT
                if(entity.getGraphicalPosition().getY() > entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(0, 1));   //DOWN
                    System.out.println("NEW UP WANTED LEFT CHANGE DOWN");
                    return;
                } else if(entity.getGraphicalPosition().getY() < entity.getPhysicalPosition().getY() * (coreKernel.map.getStaticEntitySize()/activationAreaFactor)){
                    entity.changeDirection(new Point2D(0, -1));   //UP
                    System.out.println("NEW UP WANTED LEFT CHANGE UP");
                    return;
                }
            } else if(direction.getY() == 1){       //DOWN
                entity.changeDirection(new Point2D(0, 1));   //DOWN
                System.out.println("NEW UP WANTED DOWN CHANGE DOWN");
                return;
            } else if(direction.getY() == -1){      //UP
                entity.changeDirection(new Point2D(0, -1));   //UP
                System.out.println("NEW UP WANTED UP CHANGE UP");
                return;
            }
        }
    }
}
