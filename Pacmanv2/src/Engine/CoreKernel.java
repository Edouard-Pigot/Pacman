package Engine;

import Entity.*;
import Gameplay.*;
import Entity.MovingEntity;
import Gameplay.Gameplay;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CoreKernel{
    public Scene scene;
    public InputEngine inputEngine;
    public PhysicsEngine physicsEngine;
    public GraphicsEngine graphicsEngine;
    public Map map;

    public void startEngines(Gameplay gameplay, Stage stage) throws FileNotFoundException {
        map = new Map();
        map.generate();

        physicsEngine = new PhysicsEngine(map, this);
        graphicsEngine = new GraphicsEngine(stage, map, this);
        inputEngine = new InputEngine(gameplay);

        scene = graphicsEngine.start(map);
    }

    public void moveEntity(Point2D direction, MovingEntity entity){
        graphicsEngine.moveEntity(direction, entity);
        physicsEngine.moveEntity(entity);
    }

    public ArrayList<Entity> checkCollision(MovingEntity entity){
        return physicsEngine.checkCollision(entity);
    }

    public ArrayList<Entity> checkPrediction(MovingEntity entity, Point2D direction){
        return physicsEngine.checkPrediction(entity, direction);
    }

    public void spawnEntity(Entity entity){
        graphicsEngine.spawnEntity((Node) entity);
        map.spawnEntity(entity);
    }

    public void removeEntity(Entity entity){
        graphicsEngine.removeEntity((Node) entity);
        map.removeEntity(entity);
    }

    public void removeStaticEntity(StaticEntity entity){
        graphicsEngine.removeStaticEntity(entity);
    }
}
