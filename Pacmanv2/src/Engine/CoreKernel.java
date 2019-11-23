package Engine;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class CoreKernel{
    public Scene startEngines() throws FileNotFoundException {
        PhysicsEngine physicsEngine = new PhysicsEngine();
        GraphicsEngine graphicsEngine = new GraphicsEngine();
        physicsEngine.start();
        return graphicsEngine.start();
    }
}
