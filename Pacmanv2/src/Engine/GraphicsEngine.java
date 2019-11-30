package Engine;

import Entity.MovingEntity;
import Gameplay.Pacman;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class GraphicsEngine {
    private static Map map;
    private CoreKernel coreKernel;
    private Stage stage;
    private AnchorPane window;
    private Text livesText;
    private Text scoreText;
    private Text timeText;

    public GraphicsEngine(Stage stage,Map map,CoreKernel coreKernel) {
        this.map = map;
        this.coreKernel = coreKernel;
        this.stage = stage;
    }

    public AnchorPane home() throws MalformedURLException {
        AnchorPane home = new AnchorPane ();
        home.setPrefSize(448,576);
        home.setStyle("-fx-background-color: BLack; -fx-border-color: Orange;");

        Button play = new Button("Jouer");
        Button rulesOfTheGame = new Button("Règles du jeu");

        play.setLayoutX(125);
        play.setLayoutY(313);
        play.setPrefHeight(50);
        play.setPrefWidth(197);
        play.setStyle("-fx-background-color: Orange;");

        play.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    coreKernel.play(stage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        rulesOfTheGame.setLayoutX(125);
        rulesOfTheGame.setLayoutY(378);
        rulesOfTheGame.setPrefHeight(50);
        rulesOfTheGame.setPrefWidth(197);
        rulesOfTheGame.setStyle("-fx-background-color: Orange;");

        File titlefile = new File("C:\\Users\\Roger\\IdeaProjects\\Pacman\\Pacmanv2\\src\\Titre.png");
        String titlelocalUrl = titlefile.toURI().toURL().toString();

        ImageView title = new ImageView(titlelocalUrl);

        File giffile = new File("C:\\Users\\Roger\\IdeaProjects\\Pacman\\Pacmanv2\\src\\Pacman.gif");
        String giflocalUrl = giffile.toURI().toURL().toString();
        ImageView gif = new ImageView(giflocalUrl);


        title.setFitHeight(122);
        title.setFitWidth(388);
        title.setLayoutX(29);
        title.setLayoutY(14);

        gif.setFitHeight(191);
        gif.setFitWidth(285);
        gif.setLayoutX(55);
        gif.setLayoutY(108);

        home.getChildren().addAll(play,rulesOfTheGame,title,gif);
        return home;
    }

    public AnchorPane gameOver() throws MalformedURLException {
        AnchorPane gameOver = new AnchorPane ();
        gameOver.setPrefSize(448,576);
        gameOver.setStyle("-fx-background-color: BLack; -fx-border-color: Orange;");

        Button replay = new Button("Rejouer");
        Button leave = new Button("Quitter");

        replay.setLayoutX(130.0);
        replay.setLayoutY(356.0);
        replay.setPrefHeight(50);
        replay.setPrefWidth(197);
        replay.setStyle("-fx-background-color: Orange;");

        replay.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    coreKernel.play(stage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        leave.setLayoutX(130.0);
        leave.setLayoutY(417.0);
        leave.setPrefHeight(50);
        leave.setPrefWidth(197);
        leave.setStyle("-fx-background-color: Orange;");

        leave.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });

        File gameOverFile = new File("C:\\Users\\Roger\\IdeaProjects\\Pacman\\Pacmanv2\\src\\GO.png");
        String gameOverlocalUrl = gameOverFile.toURI().toURL().toString();
        ImageView gameOverImage = new ImageView(gameOverlocalUrl);

        gameOverImage.setFitHeight(324.0);
        gameOverImage.setFitWidth(324.0);
        gameOverImage.setLayoutX(62.0);
        gameOverImage.setLayoutY(32.0);

        gameOver.getChildren().addAll(replay,leave,gameOverImage);
        return gameOver;
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
        Point2D graphic = coreKernel.convertPhysicalPositionToGraphicalPosition(pacman);
        pacman.setGraphicalPosition(new Point2D(graphic.getX()+8,graphic.getY()+8));
        pacman.setRadius(8);
    }

    public void smallerPacman(Pacman pacman){
        pacman.setRadius(4);
    }
}
