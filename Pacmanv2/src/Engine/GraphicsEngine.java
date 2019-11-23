package Engine;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class GraphicsEngine {
    public Scene start() throws FileNotFoundException {

        Map map = new Map();
        map.generate();

        Group root = new Group();
        Scene scene = new Scene(root, map.getEntitiesColumnCounter() * map.getStaticEntitySize(), map.getEntitiesRowCounter() * map.getStaticEntitySize(), Color.BLACK);

        AnchorPane window = new AnchorPane();

        for (int x = 0; x < map.getEntitiesColumnCounter(); x++){
            for (int y = 0; y < map.getEntitiesRowCounter(); y++){
                window.getChildren().add(map.getEntity(x, y));
            }
        }
        root.getChildren().add(window);
        return scene;
    }
}
