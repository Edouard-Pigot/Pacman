package Gameplay;

import Engine.CoreKernel;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Gameplay extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        CoreKernel coreKernel = new CoreKernel();
        coreKernel.startEngines();

        Scene scene = coreKernel.scene;
        stage.setScene(scene);
        scene.setOnKeyPressed(coreKernel.inputEngine);
        stage.show();
    }
}
