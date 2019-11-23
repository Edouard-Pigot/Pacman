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
                //player.setTranslateY(player.getTranslateY() - 1);
                System.out.println("Z");
                break;
            case Q:
                //player.setTranslateX(player.getTranslateX() - 1);
                System.out.println("Q");
                break;
            case S:
                //player.setTranslateY(player.getTranslateY() + 1);
                System.out.println("S");
                break;
            case D:
                //player.setTranslateX(player.getTranslateX() + 1);
                System.out.println("D");
                break;
        }
    }
}
