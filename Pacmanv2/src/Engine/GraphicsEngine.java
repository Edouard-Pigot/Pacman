package Engine;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;

public class GraphicsEngine extends Application{
    public boolean isReady = false;
    private static Scene scene;
    public static final CountDownLatch latch = new CountDownLatch(1);
    public static GraphicsEngine startUpTest = null;
    public Stage stage;

    public static GraphicsEngine waitForStartUpTest() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return startUpTest;
    }

    public static void setStartUpTest(GraphicsEngine startUpTest0) {
        startUpTest = startUpTest0;
        latch.countDown();
    }

    public GraphicsEngine() {
        setStartUpTest(this);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Group root = new Group();
        scene = new Scene(root, 500, 500, Color.BLACK);
        stage.setScene(scene);
        AnchorPane window = new AnchorPane();
        root.getChildren().add(window);
        isReady = true;
        stage.show();
    }

    public void setEventHandler(InputEngine inputEngine){
        scene.setOnKeyPressed(inputEngine);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }


}
