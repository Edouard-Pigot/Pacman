package Engine;

import javafx.scene.Scene;

import java.io.FileNotFoundException;

public class CoreKernel{
    public Scene scene;
    public InputEngine inputEngine;

    public void startEngines() throws FileNotFoundException {
        PhysicsEngine physicsEngine = new PhysicsEngine();
        GraphicsEngine graphicsEngine = new GraphicsEngine();
        inputEngine = new InputEngine();
        physicsEngine.start();
        scene = graphicsEngine.start();
    }
}
