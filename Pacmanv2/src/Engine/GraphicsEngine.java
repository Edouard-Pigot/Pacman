package Engine;

import Entity.Entity;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class GraphicsEngine {
    private static Map map;
    private Stage stage;
    private AnchorPane window;

    public GraphicsEngine(Stage stage, Map map) {
        this.stage = stage;
        this.map = map;
    }

    public Scene start(Map map) throws FileNotFoundException {
        Group root = new Group();
        Scene scene = new Scene(root, map.getEntitiesColumnCounter() * map.getStaticEntitySize(), map.getEntitiesRowCounter() * map.getStaticEntitySize(), Color.BLACK);

        window = new AnchorPane();

        for (int x = 0; x < map.getEntitiesNumber(); x++){
            window.getChildren().add((Node) map.getEntity(x));
        }
        root.getChildren().add(window);
        return scene;
    }

    public static void moveEntity(Point2D direction, Node entity){
        if(direction.getX() == 1){
            entity.setLayoutX(entity.getLayoutX() +1);
        } else if(direction.getX() == -1){
            entity.setLayoutX(entity.getLayoutX() -1);
        } else if(direction.getY() == 1){
            entity.setLayoutY(entity.getLayoutY() +1);
        } else if(direction.getY() == -1){
            entity.setLayoutY(entity.getLayoutY() -1);
        }
        if(entity.getLayoutX() < 0) {
            entity.setLayoutX(map.getEntitiesColumnCounter() * map.getStaticEntitySize());
        } else if(entity.getLayoutX() > map.getEntitiesColumnCounter() * map.getStaticEntitySize()){
            entity.setLayoutX(0);
        }
        if(entity.getLayoutY() < 0) {
            entity.setLayoutY(map.getEntitiesRowCounter() * map.getStaticEntitySize());
        } else if(entity.getLayoutY() > map.getEntitiesRowCounter() * map.getStaticEntitySize()){
            entity.setLayoutY(0);
        }
    }

    public void spawnEntity(Node entity){
        window.getChildren().add(entity);
    }
}
