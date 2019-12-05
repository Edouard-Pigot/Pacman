package Engine;

import Gameplay.Gameplay;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class InputEngine implements EventHandler<KeyEvent> {
    CoreKernel coreKernel;

    public InputEngine(CoreKernel coreKernel) {
        this.coreKernel = coreKernel;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case Z:
                coreKernel.setPacmanDirection(new Point2D(0,-1));
                break;
            case Q:
                coreKernel.setPacmanDirection(new Point2D(-1,0));
                break;
            case S:
                coreKernel.setPacmanDirection(new Point2D(0,1));
                break;
            case D:
                coreKernel.setPacmanDirection(new Point2D(1,0));
                break;
        }
    }
}
