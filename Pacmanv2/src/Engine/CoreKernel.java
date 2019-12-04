package Engine;

import Gameplay.Pacman;
import Entity.*;
import Gameplay.Ghost;
import Gameplay.Gameplay;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class CoreKernel{
    public Scene scene;
    public InputEngine inputEngine;
    public PhysicsEngine physicsEngine;
    public GraphicsEngine graphicsEngine;
    public Map map;
    public Gameplay gameplay;
    public SoundEngine soundEngine;

    public Map generateMap() throws FileNotFoundException{
        map = new Map();
        map.generate();
        return map;
    }

    public void startEngines(Gameplay gameplay,Stage stage) throws FileNotFoundException {
        this.gameplay = gameplay;
        generateMap();

        physicsEngine = new PhysicsEngine(map, this);
        graphicsEngine = new GraphicsEngine(stage,map,this);
        inputEngine = new InputEngine(gameplay);

        soundEngine = new SoundEngine();
        soundEngine.start();

        scene = graphicsEngine.start(map);
    }

    public void play(Stage stage) throws FileNotFoundException {
        gameplay.play(stage);
    }


    public void home (Stage stage) throws Exception {
        gameplay.home(stage);
    }

    public void rules (Stage stage) throws Exception {
        gameplay.rules(stage);
    }

    public void moveEntity(Point2D direction, MovingEntity entity){
        graphicsEngine.moveEntity(direction, entity);
        physicsEngine.moveEntity(entity);
    }

    public ArrayList<Entity> checkCollision(MovingEntity entity){
        return physicsEngine.checkCollision(entity);
    }

    public ArrayList<Entity> checkGraphicalPrediction(MovingEntity entity, Point2D direction){
        return physicsEngine.checkGraphicalPrediction(entity, direction);
    }

    public Entity checkPhysicalPrediction(MovingEntity entity, Point2D direction){
        return physicsEngine.checkPhysicalPrediction(entity, direction);
    }

    public void spawnEntity(Entity entity){
        graphicsEngine.spawnEntity((Node) entity);
        map.spawnEntity(entity);
    }

    public void removeEntity(Entity entity){
        graphicsEngine.removeEntity((Node) entity);
        map.removeEntity(entity);
    }

    public void updateScoreText(int score) {
        graphicsEngine.updateScoreText(score);
    }

    public void updateTimeText(int time) {
        graphicsEngine.updateTimeText(time);
    }

    public void updateLivesText(int score) {
        graphicsEngine.updateLivesText(score);
    }

    public void biggerPacman(Pacman pacman){
        graphicsEngine.biggerPacman(pacman);
    }

    public void centerPacman(Pacman pacman){
        graphicsEngine.centerPacman(pacman);
    }

    public void smallerPacman(Pacman pacman){
        graphicsEngine.smallerPacman(pacman);
    }

    public Point2D calculateMove(Point2D targetCoordinate, Ghost ghost){
        return physicsEngine.calculateMove(targetCoordinate, ghost);
    }

    public AnchorPane home() throws MalformedURLException {
        return graphicsEngine.home();
    };

    public AnchorPane gameOver() throws MalformedURLException {
        return graphicsEngine.gameOver();
    }

    public AnchorPane rules() throws MalformedURLException {
        return graphicsEngine.rules();
    }

    public Point2D convertPhysicalPositionToGraphicalPosition(Pacman pacman){
        return physicsEngine.convertPhysicalPositionToGraphicalPosition(pacman);
    }

    public void playBeginningSound(){
        soundEngine.playBeginningSound();
    }

    public void playChompSound(){
        soundEngine.playChompSound();
    }

    public void playDeathSound(){
        soundEngine.playDeathSound();
    }

    public void playEatFruitSound(){
        soundEngine.playEatFruitSound();
    }

    public void playEatGhostSound(){
        soundEngine.playEatGhostSound();
    }

    public Point2D getGhostHomePosition(Ghost ghost){
        return map.getGhostHomePosition(ghost.getGhostId());
    }

    public Point2D getGhostCornerPosition(Ghost ghost){
        return map.getGhostCornerPosition(ghost.getGhostId());
    }

    public Point2D getGhostGateExitPosition(Ghost ghost){
        return map.getGhostGateExitPosition();
    }
}
