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
import Entity.StaticEntity;

import java.io.FileNotFoundException;

public class GraphicsEngine {
    private static Map map;
    private Stage stage;
    private AnchorPane window;
    private Text livesText;
    private Text scoreText;
    private Text timeText;

    public GraphicsEngine(Stage stage, Map map) {
        this.stage = stage;
        this.map = map;
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
        scoreText.setX(100);
        timeText.setX(180);

        livesText.setY(30);
        scoreText.setY(30);
        timeText.setY(30);

        livesText.setFont(Font.font ("Verdana", 15));
        scoreText.setFont(Font.font ("Verdana", 15));
        timeText.setFont(Font.font ("Verdana", 15));

        livesText = new Text();
        scoreText = new Text();
        timeText = new Text();

        livesText.setFill(Color.WHITE);
        scoreText.setFill(Color.WHITE);
        timeText.setFill(Color.WHITE);

        livesText.setX(15);
        scoreText.setX(100);
        timeText.setX(180);

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
            //entity.setLayoutX(map.getEntitiesColumnCounter() * map.getStaticEntitySize());

        } else if(entity.getGraphicalPosition().getX() > map.getEntitiesColumnCounter() * map.getStaticEntitySize()){
            entity.setGraphicalPosition(new Point2D(0, entity.getGraphicalPosition().getY()));
            //entity.setLayoutX(0);
        }
        if(entity.getGraphicalPosition().getY() < 0) {
            entity.setGraphicalPosition(new Point2D(map.getEntitiesColumnCounter(),map.getEntitiesRowCounter() * map.getStaticEntitySize()));
            //entity.setLayoutY(map.getEntitiesRowCounter() * map.getStaticEntitySize());
        } else if(entity.getGraphicalPosition().getY() > map.getEntitiesRowCounter() * map.getStaticEntitySize()){
            entity.setGraphicalPosition(new Point2D(map.getEntitiesColumnCounter() * map.getStaticEntitySize(), 0));
            //entity.setLayoutY(0);
        }
        if(direction.getX() == 1){
            entity.setGraphicalPosition(new Point2D(entity.getGraphicalPosition().getX() +1, entity.getGraphicalPosition().getY()));
            //entity.setLayoutX(entity.getGraphicalPosition().getX() +1);
            //System.out.println("X+1");
        } else if(direction.getX() == -1){
            entity.setGraphicalPosition(new Point2D(entity.getGraphicalPosition().getX() -1, entity.getGraphicalPosition().getY()));
            //entity.setLayoutX(entity.getGraphicalPosition().getX() -1);
            //System.out.println("X-1");
        } else if(direction.getY() == 1){
            entity.setGraphicalPosition(new Point2D(entity.getGraphicalPosition().getX(), entity.getGraphicalPosition().getY() +1));
            //entity.setLayoutY(entity.getGraphicalPosition().getY() +1);
            //System.out.println("Y+1");
        } else if(direction.getY() == -1){
            entity.setGraphicalPosition(new Point2D(entity.getGraphicalPosition().getX(), entity.getGraphicalPosition().getY() -1));
            //entity.setLayoutY(entity.getGraphicalPosition().getY() -1);
            //System.out.println("Y-1");
        } else{
            return;
        }
    }

    public void spawnEntity(Node entity){
        window.getChildren().add(entity);
    }

    public void updateTimeText(int time) {
        timeText.setText("Time : " + time);
        timeText.setFill(Color.WHITE);
    }

    public void updateScoreText(int score) {
        scoreText.setText("Score : " + score);
        scoreText.setFill(Color.WHITE);
    }

    public void updateLivesText(int lives) {
        livesText.setText("Lives : " + lives);
        livesText.setFill(Color.WHITE);
    }

    public void biggerPacman(Pacman pacman) {
        pacman.setRadius(8);
    }

    public void smallerPacman(Pacman pacman){
        pacman.setRadius(4);
    }

    public void removeStaticEntity(StaticEntity entity){
        window.getChildren().remove(entity);
    }
}
