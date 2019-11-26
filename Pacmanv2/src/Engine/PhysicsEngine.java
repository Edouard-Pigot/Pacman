package Engine;

import Entity.Entity;
import Gameplay.Wall;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class PhysicsEngine {
    private static Map map;

    public PhysicsEngine(Map map) {
        this.map = map;
    }

    public static Point2D checkCollision(Entity entity, Point2D newDirection, Point2D oldDirection, Circle futurPosition){
        futurPosition.setLayoutX(futurPosition.getLayoutX() + newDirection.getX());
        futurPosition.setLayoutY(futurPosition.getLayoutY() + newDirection.getY());
        for(int i = 0; i < map.getEntitiesNumber(); i++){
            if(futurPosition.getBoundsInParent().intersects(((Node) map.getEntity(i)).getBoundsInParent()) && map.getEntity(i) instanceof Wall){
                System.out.println("FUTUR HIT");
                System.out.println(map.getEntity(i).getClass());
                futurPosition.setLayoutX(futurPosition.getLayoutX() - newDirection.getX());
                futurPosition.setLayoutY(futurPosition.getLayoutY() - newDirection.getY());
                futurPosition.setLayoutX(futurPosition.getLayoutX() + oldDirection.getX());
                futurPosition.setLayoutY(futurPosition.getLayoutY() + oldDirection.getY());
                for(int j = 0; j < map.getEntitiesNumber(); j++){
                    if(futurPosition.getBoundsInParent().intersects(((Node) map.getEntity(j)).getBoundsInParent()) && map.getEntity(j) instanceof Wall){
                        System.out.println("ACTUAL HIT");
                        System.out.println(map.getEntity(j).getClass());
                        futurPosition.setLayoutX(futurPosition.getLayoutX() - newDirection.getX());
                        futurPosition.setLayoutY(futurPosition.getLayoutY() - newDirection.getY());
                        return new Point2D(0,0);
                    }
                }
                return oldDirection;
            }
        }
        return newDirection;
    }

}
