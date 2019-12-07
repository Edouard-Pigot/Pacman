package Engine;

import Entity.Entity;
import Entity.MovingEntity;
import Gameplay.Ghost;
import Gameplay.Pacman;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
    private Group root;

    private int cpt = 0;

    public GraphicsEngine(Stage stage,Map map,CoreKernel coreKernel) {
        this.map = map;
        this.coreKernel = coreKernel;
        this.stage = stage;
    }

    public void setMap(Map map){
        this.map = map;

        for (int x = 0; x < map.getEntitiesNumber(); x++){
            window.getChildren().add((Node) map.getEntity(x));
        }
        root.getChildren().add(window);
    }

    public void reloadMap(Map map){
        root = new Group();
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

        Scene scene = new Scene(root, map.getEntitiesColumnCounter() * map.getStaticEntitySize(), map.getEntitiesRowCounter() * map.getStaticEntitySize(), Color.BLACK);
        window = new AnchorPane();

        setMap(map);
        coreKernel.scene = scene;
        root.getChildren().add(livesText);
        root.getChildren().add(scoreText);
        root.getChildren().add(timeText);
        stage.setScene(scene);
        stage.show();
    }

    public AnchorPane home() throws MalformedURLException {
        AnchorPane home = new AnchorPane ();
        home.setPrefSize(448,576);
        home.setStyle("-fx-background-color: BLack; -fx-border-color: Orange;");

        Button play = new Button("Play");
        Button rulesOfTheGame = new Button("Game Rules");

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

        rulesOfTheGame.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    rules(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        File titlefile = new File("Images/Titre.png");
        String titlelocalUrl = titlefile.toURI().toURL().toString();

        ImageView title = new ImageView(titlelocalUrl);

        File giffile = new File("Images/Pacman.gif");
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

    public void rules(Stage stage) throws MalformedURLException {
        AnchorPane rules = coreKernel.rules();
        Scene scene = new Scene(rules,448,576);
        stage.setScene(scene);
        stage.show();
    }

    public void home(Stage stage) throws MalformedURLException {
        AnchorPane home = coreKernel.home();
        Scene scene = new Scene(home,448,576);
        stage.setScene(scene);
        stage.show();
    }

    public void gameOver(Stage stage) throws MalformedURLException {
        AnchorPane gameOver = coreKernel.gameOver();
        Scene scene = new Scene(gameOver,448,576);
        stage.setScene(scene);
        stage.show();
    }

    public AnchorPane gameOver() throws MalformedURLException {
        AnchorPane gameOver = new AnchorPane ();
        gameOver.setPrefSize(448,576);
        gameOver.setStyle("-fx-background-color: BLack; -fx-border-color: Orange;");

        Button replay = new Button("Replay");
        Button leave = new Button("Quit");

        replay.setLayoutX(130.0);
        replay.setLayoutY(356.0);
        replay.setPrefHeight(50);
        replay.setPrefWidth(197);
        replay.setStyle("-fx-background-color: Orange;");

        replay.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    coreKernel.init(stage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
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

        File gameOverFile = new File("Images/gameover.png");
        String gameOverlocalUrl = gameOverFile.toURI().toURL().toString();
        ImageView gameOverImage = new ImageView(gameOverlocalUrl);

        gameOverImage.setFitHeight(324.0);
        gameOverImage.setFitWidth(324.0);
        gameOverImage.setLayoutX(62.0);
        gameOverImage.setLayoutY(32.0);

        gameOver.getChildren().addAll(replay,leave,gameOverImage);
        return gameOver;
    }

    public AnchorPane rules() throws MalformedURLException {
        AnchorPane rules = new AnchorPane ();
        rules.setPrefSize(448,576);
        rules.setStyle("-fx-background-color: BLack; -fx-border-color: Orange;");

        Button back = new Button("RETURN");

        back.setLayoutX(126);
        back.setLayoutY(484);
        back.setPrefHeight(50);
        back.setPrefWidth(197);
        back.setStyle("-fx-background-color: Orange;");

        back.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    coreKernel.home(stage);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        File commandsFile = new File("Images/zqsd.png");
        String commandsOverlocalUrl = commandsFile.toURI().toURL().toString();
        ImageView commands = new ImageView(commandsOverlocalUrl);

        commands.setFitHeight(94);
        commands.setFitWidth(132);
        commands.setLayoutX(60);
        commands.setLayoutY(147);

        File giffile = new File("Images/pacmanGif.gif");
        String giflocalUrl = giffile.toURI().toURL().toString();
        ImageView gif = new ImageView(giflocalUrl);

        gif.setFitHeight(82);
        gif.setFitWidth(225);
        gif.setLayoutX(211);
        gif.setLayoutY(179);

        Label labelCommands = new Label("Commands");
        Label labelTitle = new Label("RULES");
        Label labelObjectif = new Label("Run away from the ghost, \n " +
                "if they catch you,you will lose a life\n" +
                "and respawn.");
        Label labelRules = new Label(" * If you get caught 3 times you lose the game. \n" +
                " * To get rid of the ghosts temporarily eat a super pac-gum,\n" +
                "you will be able to eat the ghosts during a short period.\n" +
                " * Eat a mini pac-gum to get smaller and be able to move \n" +
                "with more ease inside the maze.\n" +
                " * You’re about to get caught? \n" +
                "Quick eat that quantum pac-gum to get through the walls\n" +
                "and get out of tricky situation! \n" +
                " * Hungry for some points? \n" +
                "Eat some fruits or other funny collectibles that spawn \n" +
                "at the center of the maze to get some more! \n" +
                " * Clear the map of every collectibles to get to the next level\n" +
                " * Have fun!  ");

        labelTitle.setFont(new Font(54));
        labelTitle.setLayoutX(157);
        labelTitle.setLayoutY(14);
        labelTitle.setPrefHeight(61);
        labelTitle.setPrefWidth(153);
        labelTitle.setTextFill(Color.ORANGE);

        labelObjectif.setLayoutX(214);
        labelObjectif.setLayoutY(101);
        labelObjectif.setPrefHeight(70);
        labelObjectif.setPrefWidth(211);
        labelObjectif.setTextFill(Color.YELLOW);

        labelCommands.setFont(new Font(26));
        labelCommands.setLayoutX(60);
        labelCommands.setLayoutY(109);
        labelCommands.setTextFill(Color.YELLOW);

        labelRules.setLayoutX(80);
        labelRules.setLayoutY(248);
        labelRules.setPrefHeight(236);
        labelRules.setPrefWidth(332);
        labelRules.setTextFill(Color.YELLOW);

        rules.getChildren().addAll(back,commands,gif,labelRules,labelObjectif,labelCommands,labelTitle);
        return rules;
    }

    public Scene start(Map map) throws FileNotFoundException {
        root = new Group();
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
        }
        if(direction.getX() == -1){
            entity.setGraphicalPosition(new Point2D(entity.getGraphicalPosition().getX() -1, entity.getGraphicalPosition().getY()));
        }
        if(direction.getY() == 1){
            entity.setGraphicalPosition(new Point2D(entity.getGraphicalPosition().getX(), entity.getGraphicalPosition().getY() +1));
        }
        if(direction.getY() == -1){
            entity.setGraphicalPosition(new Point2D(entity.getGraphicalPosition().getX(), entity.getGraphicalPosition().getY() -1));
        }
        return;
    }

    public void spawnEntity(Node entity){
        window.getChildren().add(entity);
        if(entity instanceof Ghost){
            ImageView eyes = ((Ghost)entity).getEyes();
            System.out.println(eyes);
            window.getChildren().add(eyes);
        }
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
        pacman.setFitHeight(16);
        pacman.setFitWidth(16);
    }

    public void smallerPacman(Pacman pacman){
        pacman.setFitHeight(8);
        pacman.setFitWidth(8);
    }

    public void center(MovingEntity entity){
        Point2D graphic = coreKernel.convertPhysicalPositionToGraphicalPosition(entity);
        entity.setGraphicalPosition(new Point2D(graphic.getX()+8,graphic.getY()+8));
    }

    public void changeEntityImage(Entity entity, int frame){
        if(entity instanceof Ghost){
            ((Ghost) entity).refreshImage(frame);
        }
        if(entity instanceof Pacman){
            ((Pacman) entity).refreshImage(frame);
        }
    }

}
