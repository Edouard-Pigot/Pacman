package Engine;

import Entity.Entity;
import Entity.MovingEntity;
import Entity.StaticEntity;
import Gameplay.PacGum;
import Gameplay.Pacman;
import Gameplay.Wall;
import javafx.geometry.Point2D;
import javafx.scene.Node;
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

    public Entity checkPhysicalPrediction(MovingEntity entity, Point2D direction){
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

    public Point2D convertPhysicalPositionToGraphicalPosition(Pacman pacman){
        return pacman.convertPhysicalPositionToGraphicalPosition();
    }
}






