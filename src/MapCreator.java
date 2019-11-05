import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MapCreator extends Application {
    int[][] map;

    Rectangle[][] tiles;

    int x, y;

    Player player;

    public static void main(String[] args) {
        launch(args);
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
                switch (map[i][j]){
                    case 0:
                        tile.setFill(Color.GREY);
                        break;
                    case 1:
                        tile.setFill(Color.BLUE);
                        break;
                    case 2:
                        tile.setFill(Color.YELLOW);
                        break;
                    case 3:
                        tile.setFill(Color.WHITE);
                        break;
                    case 4:
                        tile.setFill(Color.ORANGE);
                        break;
                    case 5:
                        tile.setFill(Color.GREEN);
                        break;
                    case 6:
                        tile.setFill(Color.BLACK);
                        break;
                    case 7:
                        tile.setFill(Color.RED);
                        break;
                    case 8:
                        tile.setFill(Color.LIGHTGRAY);
                        break;
                }
                window.getChildren().add(tile);
            }
        }
//        player = new Player(5,0, 100,0, this);
//        window.getChildren().add(player);
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
        ArrayList<ArrayList<Integer>> items = new ArrayList<>();
        int lineCounter = 0;
        Scanner fileScanner = new Scanner(new File("E:\\Desktop\\Cours\\GL\\Pacman\\Map"));
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            items.add(new ArrayList<>());
            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext()) {
                items.get(lineCounter).add(lineScanner.nextInt());
            }
            lineScanner.close();
            lineCounter++;
        }
        fileScanner.close();
        map = new int[items.size()][items.get(0).size()];
        for (int i = 0; i < items.size(); i++) {
            for(int j = 0; j < items.get(0).size(); j++){
                map[i][j] = items.get(i).get(j);
            }
        }
    }


    public void colorPlayerTile(){
        int[] mapCoordinate = ScreenToMap((player.getSprite())[0],(player.getSprite())[1]);
        tiles[mapCoordinate[1]][mapCoordinate[0]].setFill(Color.RED);
        player.setTile(mapCoordinate[1], mapCoordinate[0]);
    }

    public int[] ScreenToMap(double _xPixel, double _yPixel){
        double xCoordinate = _xPixel/((double) x/(map.length));
//        System.out.println(player.getSprite()[1]);
        xCoordinate = Math.floor(xCoordinate);
//        System.out.println(_xPixel);
//        System.out.println(xCoordinate);
        double yCoordinate = _yPixel/((double) y/(map[0].length));
//        System.out.println(yCoordinate);
        yCoordinate = Math.floor(yCoordinate);
//        System.out.println(_yPixel);
//        System.out.println(yCoordinate);
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
