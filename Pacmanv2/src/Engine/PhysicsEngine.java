package Engine;

import Entity.Entity;
import Entity.MovingEntity;
import Entity.StaticEntity;
import Gameplay.*;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class PhysicsEngine {
    private static Map map;
    private CoreKernel coreKernel;

    public PhysicsEngine(Map map, CoreKernel coreKernel) {
        this.map = map;
        this.coreKernel = coreKernel;
    }

    public ArrayList<Entity> checkCollision(MovingEntity entity){
        ArrayList<Entity> collidingEntities = new ArrayList<>();
        for(int i = 0; i < map.getEntitiesNumber(); i++){
            if(map.getEntity(i).getPhysicalPosition().getY() == entity.getPhysicalPosition().getX() && map.getEntity(i).getPhysicalPosition().getX() == entity.getPhysicalPosition().getY()){
                collidingEntities.add(map.getEntity(i));
            }
        }
        return collidingEntities;
    }

    public ArrayList<Entity> checkGraphicalPrediction(MovingEntity entity, Point2D direction){
        ArrayList<Entity> collidingEntities = new ArrayList<>();
        int[] bounds = entity.boxCollider().getColliderBounds();
        BoxCollider predictedBoxCollider = new BoxCollider(bounds[0] + (int) direction.getX(), bounds[1]  + (int) direction.getX(), bounds[2] + (int) direction.getY(), bounds[3] + (int) direction.getY());
        for(int i = 0; i < map.getEntitiesNumber(); i++){
            if(map.getEntity(i).boxCollider().isColliding(predictedBoxCollider)){
                collidingEntities.add(map.getEntity(i));
            }
        }
        return collidingEntities;
    }

    public Entity checkPhysicalPrediction(Entity entity, Point2D direction){
        for(int i = 0; i < map.getEntitiesNumber(); i++){
            if(map.getEntity(i).getPhysicalPosition().getY() == entity.getPhysicalPosition().getX()+direction.getX() && map.getEntity(i).getPhysicalPosition().getX() == entity.getPhysicalPosition().getY() + direction.getY()){
                return map.getEntity(i);
            }
        }
        return null;
    }

    public void moveEntity(MovingEntity entity){
        entity.setPhysicalPosition(map.getStaticEntitySize());
    }

    public Point2D calculateMove(Point2D target, Ghost ghost){
        double bestDistance = 10000;
        Point2D bestTile = new Point2D(0,0);
        Entity bestTileEntity = null;
        for(int y = -1; y < 2; y++){
            for(int x = -1; x < 2; x++){
                if((x == -1 && y == -1) || (x == -1 && y == 1) || (x == 1 && y == -1) || (x == 1 && y == 1) || (x == 0 && y == 0)) continue;
                Entity tile = checkPhysicalPrediction(ghost, new Point2D(x,y));
                if(!(tile instanceof Wall || tile instanceof Empty)){
                    int xB = (int) target.getX();
                    int yB = (int) target.getY();
                    int xA = (int) (ghost.getPhysicalPosition().getX() + x);
                    int yA = (int) (ghost.getPhysicalPosition().getY() + y);
                    double distance = Math.sqrt(Math.pow(xB - xA, 2) + Math.pow(yB - yA,2));
                    Point2D potentialDirection = new Point2D(x,y);
                    if(distance < bestDistance){
                        if(((ghost.getNewDirection().getX() == 1 && potentialDirection.getX() == -1) && (ghost.getNewDirection().getY() == 0 && potentialDirection.getY() == 0)) ||
                                ((ghost.getNewDirection().getX() == -1 && potentialDirection.getX() == 1) && (ghost.getNewDirection().getY() == 0 && potentialDirection.getY() == 0)) ||
                                ((ghost.getNewDirection().getX() == 0 && potentialDirection.getX() == 0) && (ghost.getNewDirection().getY() == 1 && potentialDirection.getY() == -1)) ||
                                ((ghost.getNewDirection().getX() == 0 && potentialDirection.getX() == 0) && (ghost.getNewDirection().getY() == -1 && potentialDirection.getY() == 1))) {
                            continue;
                        }
                        bestDistance = distance;
                        bestTile = potentialDirection;
                        bestTileEntity = tile;
                    }
                }
            }
        }
        return bestTile;
    }
    public Point2D convertPhysicalPositionToGraphicalPosition(Pacman pacman){
        return pacman.convertPhysicalPositionToGraphicalPosition();
    }
}






