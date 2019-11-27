package Engine;

import Entity.Entity;
import Entity.MovingEntity;
import Entity.StaticEntity;
import Gameplay.Wall;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class PhysicsEngine {
    private static Map map;

    public PhysicsEngine(Map map) {
        this.map = map;
    }

    public ArrayList<Entity> checkCollision(MovingEntity entity){
        ArrayList<Entity> collidingEntities = new ArrayList<Entity>();
        for(int i = 0; i < map.getEntitiesNumber(); i++){
            if(map.getEntity(i).getPhysicalPosition() == entity.getPhysicalPosition()){
                collidingEntities.add(map.getEntity(i));
            }
        }
        return collidingEntities;
    }

    public ArrayList<Entity> checkPrediction(MovingEntity entity, Point2D direction){
        ArrayList<Entity> collidingEntities = new ArrayList<Entity>();
        int[] bounds = entity.boxCollider().getColliderBounds();
        BoxCollider predictedBoxCollider = new BoxCollider(bounds[0] + (int) direction.getX(), bounds[1]  + (int) direction.getX(), bounds[2] + (int) direction.getY(), bounds[3] + (int) direction.getY());
        for(int i = 0; i < map.getEntitiesNumber(); i++){
            if(map.getEntity(i).boxCollider().isColliding(predictedBoxCollider)){
                collidingEntities.add(map.getEntity(i));
            }
        }
        return collidingEntities;
    }

    public void moveEntity(Point2D direction, MovingEntity entity){

    public void powerPassThrough() {
    }
}






