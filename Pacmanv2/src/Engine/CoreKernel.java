package Engine;

import javafx.scene.Scene;

public class CoreKernel{
    public Scene scene;
    public void start(){
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(GraphicsEngine.class);
            }
        }.start();
        GraphicsEngine graphicsEngine = GraphicsEngine.waitForStartUpTest();
        while(!graphicsEngine.isReady){
            System.out.println("WAIT");
        }
        InputEngine inputEngine = new InputEngine();
        graphicsEngine.setEventHandler(inputEngine);
    }
}
