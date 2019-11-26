package Engine;

import Entity.Entity;
import Gameplay.Gameplay;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class CoreKernel{
    public Scene scene;
    public InputEngine inputEngine;
    public PhysicsEngine physicsEngine;
    public GraphicsEngine graphicsEngine;
    public Map map;

    public void startEngines(Gameplay gameplay, Stage stage) throws FileNotFoundException {
        map = new Map();
        map.generate();

        physicsEngine = new PhysicsEngine(map);
        graphicsEngine = new GraphicsEngine(stage, map);
        inputEngine = new InputEngine(gameplay);

        scene = graphicsEngine.start(map);
    }

    public Point2D checkCollision(Entity entity, Point2D newDirection, Point2D oldDirection, Circle circle){
        return PhysicsEngine.checkCollision(entity, newDirection, oldDirection, circle);
    }

    public void movePacman(Point2D direction, Node entity){
        GraphicsEngine.moveEntity(direction, entity);
    }

    public void spawnEntity(Node entity){
        graphicsEngine.spawnEntity(entity);
    }
}
