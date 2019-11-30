package Engine;

import Entity.Entity;
import Entity.MovingEntity;
import Gameplay.Pacman;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class GraphicsEngine {
    private static Map map;
    private CoreKernel coreKernel;
    private Stage stage;
    private AnchorPane window;
    private Text livesText;
    private Text scoreText;
    private Text timeText;

    public GraphicsEngine(Stage stage, Map map, CoreKernel coreKernel) {
        this.stage = stage;
        this.map = map;
        this.coreKernel = coreKernel;
    }

    public Scene start(Map map) throws FileNotFoundException {
        Group root = new Group();
        Scene scene = new Scene(root, map.getEntitiesColumnCounter() * map.getStaticEntitySize(), map.getEntitiesRowCounter() * map.getStaticEntitySize(), Color.BLACK);

        livesText = new Text();
        scoreText = new Text();
        timeText = new Text();

        livesText.setFill(Color.WHITE);
        scoreText.setFill(Color.WHITE);
        timeText.setFill(Color.WHITE);

        livesText.setX(15);
        scoreText.setX(150);
        timeText.setX(300);

        livesText.setY(30);
        scoreText.setY(30);
        timeText.setY(30);

        livesText.setFont(Font.font ("Verdana", 15));
        scoreText.setFont(Font.font ("Verdana", 15));
        timeText.setFont(Font.font ("Verdana", 15));

        window = new AnchorPane();

        for (int x = 0; x < map.getEntitiesNumber(); x++){
            window.getChildren().add((Node) map.getEntity(x));
        }
        root.getChildren().add(window);
        root.getChildren().add(livesText);
        root.getChildren().add(scoreText);
        root.getChildren().add(timeText);
        return scene;
    }

    public void moveEntity(Point2D direction, MovingEntity entity){
        if(entity.getGraphicalPosition().getX() < 0) {
            entity.setGraphicalPosition(new Point2D(map.getEntitiesColumnCounter() * map.getStaticEntitySize(), entity.getGraphicalPosition().getY()));
        } else if(entity.getGraphicalPosition().getX() > map.getEntitiesColumnCounter() * map.getStaticEntitySize()){
            entity.setGraphicalPosition(new Point2D(0, entity.getGraphicalPosition().getY()));
        }
        if(entity.getGraphicalPosition().getY() < 0) {
            entity.setGraphicalPosition(new Point2D(map.getEntitiesColumnCounter(),map.getEntitiesRowCounter() * map.getStaticEntitySize()));
        } else if(entity.getGraphicalPosition().getY() > map.getEntitiesRowCounter() * map.getStaticEntitySize()){
            entity.setGraphicalPosition(new Point2D(map.getEntitiesColumnCounter() * map.getStaticEntitySize(), 0));
        }
        if(direction.getX() == 1){
            entity.setGraphicalPosition(new Point2D(entity.getGraphicalPosition().getX() +1, entity.getGraphicalPosition().getY()));
        } else if(direction.getX() == -1){
            entity.setGraphicalPosition(new Point2D(entity.getGraphicalPosition().getX() -1, entity.getGraphicalPosition().getY()));
        } else if(direction.getY() == 1){
            entity.setGraphicalPosition(new Point2D(entity.getGraphicalPosition().getX(), entity.getGraphicalPosition().getY() +1));
        } else if(direction.getY() == -1){
            entity.setGraphicalPosition(new Point2D(entity.getGraphicalPosition().getX(), entity.getGraphicalPosition().getY() -1));
        } else{
            return;
        }
    }

    public void spawnEntity(Node entity){
        window.getChildren().add(entity);
    }

    public void removeEntity(Node entity){
        window.getChildren().remove(entity);
    }

    public void updateTimeText(int time) {
        this.timeText.setText("Time : " + time);
        timeText.setFill(Color.WHITE);
    }

    public void updateScoreText(int score) {
        this.scoreText.setText("Score : " + score);
    }

    public void updateLivesText(int lives) {
        this.livesText.setText("Lives : " + lives);
    }

    public void biggerPacman(Pacman pacman) {
        Point2D graphic = pacman.convertPhysicalPositionToGraphicalPosition(pacman.getPhysicalPosition(),16);
        pacman.setGraphicalPosition(new Point2D(graphic.getX()+8,graphic.getY()+8));
        pacman.setRadius(8);
    }

    public void smallerPacman(Pacman pacman){
        pacman.setRadius(4);
    }
}
