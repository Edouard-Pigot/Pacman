package Engine;

import Gameplay.Gameplay;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class InputEngine implements EventHandler<KeyEvent> {
    Gameplay gameplay;

    public InputEngine(Gameplay gameplay) {
        this.gameplay = gameplay;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case Z:
                //System.out.println("Z");
                gameplay.setDirection(new Point2D(0,-1));
                break;
            case Q:
                //System.out.println("Q");
                gameplay.setDirection(new Point2D(-1,0));
                break;
            case S:
                //System.out.println("S");
                gameplay.setDirection(new Point2D(0,1));
                break;
            case D:
                //System.out.println("D");
                gameplay.setDirection(new Point2D(1,0));
                break;
        }
    }
}
