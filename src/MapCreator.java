import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MapCreator extends Application {
    String[][] map;

    Rectangle[][] tiles;

    int nbOfPixels = 8;
    int x, y;

    Pacman player;

    enum direction{
        up, left, down, right
    }




    public static final CountDownLatch latch = new CountDownLatch(1);
    public static MapCreator startUpTest = null;

    public static MapCreator waitForStartUpTest() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return startUpTest;
    }

    public static void setStartUpTest(MapCreator startUpTest0) {
        startUpTest = startUpTest0;
        latch.countDown();
    }

    public MapCreator() {
        setStartUpTest(this);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }




    @Override
    public void start(Stage stage) throws Exception {
        read();
        tiles = new Rectangle[map.length][map[0].length];
        x = map[0].length*8;
        y = map.length*8;
        Group root = new Group();
        Scene scene = new Scene(root, x, y, Color.BLACK);
        stage.setScene(scene);
        AnchorPane window = new AnchorPane();
        window.setMaxWidth(x);
        window.setMaxHeight(y);
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                Rectangle tile = new Rectangle(j*((double)x/map[0].length), i*((double)y/map.length), (double) x/map[0].length, (double) y/map.length);
                tiles[i][j] = tile;
                window.getChildren().add(tile);
                int middle = map[i][j].length();
                String[] elements = map[i][j].split("(?!^)");
                System.out.println(Arrays.toString(elements));
                switch (elements[0]){
                    case "E":
                        tile.setFill(Color.BLACK);
                        if (elements.length >= 2 && elements[1].equals("1")) {
                            tile.setStroke(Color.RED);
                            tile.setStrokeType(StrokeType.INSIDE);
                            tile.setStrokeMiterLimit(5);
                        } else if (elements.length >= 2 && elements[1].equals("2")) {
                            tile.setStroke(Color.PINK);
                            tile.setStrokeType(StrokeType.INSIDE);
                            tile.setStrokeMiterLimit(5);
                        } else if (elements.length >= 2 && elements[1].equals("3")) {
                            tile.setStroke(Color.CYAN);
                            tile.setStrokeType(StrokeType.INSIDE);
                            tile.setStrokeMiterLimit(5);
                        } else if (elements.length >= 2 && elements[1].equals("4")) {
                            tile.setStroke(Color.ORANGE);
                            tile.setStrokeType(StrokeType.INSIDE);
                            tile.setStrokeMiterLimit(5);
                        }
                        break;
                    case "W":
                        tile.setFill(Color.DARKBLUE);
                        break;
                    case "P":
                        tile.setFill(Color.BLACK);
                        if (elements.length >= 2 && elements[1].equals("P")) {
                            Circle point = new Circle((j * ((double) x / map[0].length)) + ((double) x / map[0].length) / 2, (i * ((double) y / map.length)) + ((double) y / map.length) / 2, (double) ((x / map[0].length) / 4), Color.YELLOW);
                            window.getChildren().add(point);
                            if (elements.length >= 3 && elements[2].equals("N")) {

                            }
                        } else if (elements.length >= 2 && elements[1].equals("G")) {
                            Circle gum = new Circle((j * ((double) x / map[0].length)) + ((double) x / map[0].length) / 2, (i * ((double) y / map.length)) + ((double) y / map.length) / 2, (double) ((x / map[0].length) / 2), Color.YELLOW);
                            window.getChildren().add(gum);
                        }
                        break;
                    case "T":
                        tile.setFill(Color.BLACK);
                        break;
                    case "G":
                        tile.setFill(Color.PINK);
                        break;
                    case "H":
                        tile.setFill(Color.BLACK);
                        if (elements.length >= 2 && elements[1].equals("1")) {

                        } else if (elements.length >= 2 && elements[1].equals("2")) {

                        } else if (elements.length >= 2 && elements[1].equals("3")) {

                        } else if (elements.length >= 2 && elements[1].equals("4")) {

                        }
                        break;
                }
            }
        }
        player = spawnPlayer(14, 26);
        scene.setOnKeyPressed(event ->{
            switch (event.getCode()){
                case Z:
                    player.setTranslateY(player.getTranslateY() - 1);
                    break;
                case Q:
                    player.setTranslateX(player.getTranslateX() - 1);
                    break;
                case S:
                    player.setTranslateY(player.getTranslateY() + 1);
                    break;
                case D:
                    player.setTranslateX(player.getTranslateX() + 1);
                    break;
            }
        });
        window.getChildren().add(player);
//        colorPlayerTile();
        root.getChildren().add(window);
//        final Model model = new Model();
//        model.counter.addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(final ObservableValue<? extends Number> observable,
//                                final Number oldValue, final Number newValue) {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(model.counter.get() < 200) {
//                            player.setSprite(player.getSprite()[0], player.getSprite()[1] + 1);
//                        } else if(model.counter.get() >= 200 && model.counter.get() < 300) {
//                            player.setSprite(player.getSprite()[0] + 1, player.getSprite()[1]);
//                        } else if(model.counter.get() >= 300 && model.counter.get() < 400) {
//                            player.setSprite(player.getSprite()[0], player.getSprite()[1] - 1);
//                        } else if(model.counter.get() >= 400 && model.counter.get() < 500) {
//                            player.setSprite(player.getSprite()[0] - 1, player.getSprite()[1]);
//                        }
//                        colorPlayerTile();
//                    }
//                });
//
//            }
//        });
//        model.start();
        stage.show();
    }

    public void read() throws Exception{
        ArrayList<ArrayList<String>> items = new ArrayList<>();
        int lineCounter = 0;
        Scanner fileScanner = new Scanner(new File("E:\\Desktop\\Cours\\GL\\Pacman\\Map"));
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            items.add(new ArrayList<>());
            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext()) {
                items.get(lineCounter).add((String) lineScanner.next());
            }
            lineScanner.close();
            lineCounter++;
        }
        fileScanner.close();
        map = new String[items.size()][items.get(0).size()];
        for (int i = 0; i < items.size(); i++) {
            for(int j = 0; j < items.get(0).size(); j++){
                map[i][j] = items.get(i).get(j);
            }
        }
    }

    public Pacman spawnPlayer(int _spwX, int _spwY){
        //x = 14 / y = 26
        Image image = new Image("file:///E:/Desktop/Cours/GL/Pacman/Pacman1.png");
        return new Pacman(_spwX,_spwY, MapToScreen(_spwX, _spwY)[0], MapToScreen(_spwX, _spwY)[1],this, image);
    }

    public void colorPlayerTile(){
        int[] mapCoordinate = ScreenToMap((player.getSprite())[0],(player.getSprite())[1]);
        tiles[mapCoordinate[1]][mapCoordinate[0]].setFill(Color.RED);
        player.setTile(mapCoordinate[1], mapCoordinate[0]);
    }

    public int[] ScreenToMap(double _xPixel, double _yPixel){
        double xCoordinate = _xPixel/((double) x/(map.length));
        xCoordinate = Math.floor(xCoordinate);
        double yCoordinate = _yPixel/((double) y/(map[0].length));
        yCoordinate = Math.floor(yCoordinate);
        return new int[]{(int) xCoordinate, (int) yCoordinate};
    }

    public int[] MapToScreen(int _xSprite, int _ySprite){
        System.out.println("y " +map.length);
        System.out.println("x " + map[0].length);
        System.out.println("graph x" + tiles[0].length);
        System.out.println("graph y" + tiles.length);
        double xCoordinate = _xSprite*8 + nbOfPixels/2;
        xCoordinate = Math.floor(xCoordinate);
        double yCoordinate = _ySprite*8 + nbOfPixels/2;
        yCoordinate = Math.floor(yCoordinate);
        return new int[]{(int) xCoordinate, (int) yCoordinate};
    }
}

class Model extends Thread {
    public IntegerProperty counter;

    public Model(){
        counter = new SimpleIntegerProperty(this, "int", 0);
        setDaemon(true);
    }

    public int getInt() {
        return counter.get();
    }

    public IntegerProperty intProperty() {
        return counter;
    }


    @Override
    public void run() {
        while(true){
            try {
                TimeUnit.MILLISECONDS.sleep(16);
                counter.set(counter.get() + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
