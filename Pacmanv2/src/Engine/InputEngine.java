package Engine;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class InputEngine implements EventHandler<KeyEvent> {
    @Override
    public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case Z:
                System.out.println("Z");
                break;
            case Q:
                System.out.println("Q");
                break;
            case S:
                System.out.println("S");
                break;
            case D:
                System.out.println("D");
                break;
        }
    }
}
