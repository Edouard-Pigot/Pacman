package Engine;

import Entity.Entity;
import Gameplay.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
    private ArrayList<ArrayList<Entity>> entities = new ArrayList<>();
    private int entitiesRowCounter = 0;
    private int entitiesColumnCounter = 0;
    private int staticEntitySize = 16;

    public void generate() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("E:\\Desktop\\Cours\\GL\\Pacman\\Pacmanv2\\src\\Map"));
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            entities.add(new ArrayList<>());
            Scanner lineScanner = new Scanner(line);
            entitiesColumnCounter = 0;
            while (lineScanner.hasNext()) {
                String[] elements = lineScanner.next().split("(?!^)");
                switch (elements[0]){
                    case "E":
                        entities.get(entitiesRowCounter).add(new Empty(entitiesColumnCounter*staticEntitySize + staticEntitySize/2, entitiesRowCounter*staticEntitySize + staticEntitySize/2, staticEntitySize, Color.BLACK));
                        if (elements.length >= 2 && elements[1].equals("1")) {

                        } else if (elements.length >= 2 && elements[1].equals("2")) {

                        } else if (elements.length >= 2 && elements[1].equals("3")) {

                        } else if (elements.length >= 2 && elements[1].equals("4")) {

                        }
                        break;
                    case "W":
                        entities.get(entitiesRowCounter).add(new Wall(entitiesColumnCounter*staticEntitySize + staticEntitySize/2, entitiesRowCounter*staticEntitySize + staticEntitySize/2, staticEntitySize, Color.BLUE));
                        break;
                    case "P":
                        if (elements.length >= 2 && elements[1].equals("P")) {
                            entities.get(entitiesRowCounter).add(new PacGum(entitiesColumnCounter*staticEntitySize + staticEntitySize, entitiesRowCounter*staticEntitySize + staticEntitySize, staticEntitySize/3, Color.YELLOW));
                            if (elements.length >= 3 && elements[2].equals("N")) {

                            }
                        } else if (elements.length >= 2 && elements[1].equals("G")) {
                            entities.get(entitiesRowCounter).add(new SuperPacGum(entitiesColumnCounter*staticEntitySize + staticEntitySize, entitiesRowCounter*staticEntitySize + staticEntitySize, staticEntitySize/2, Color.YELLOW));
                        } else {
                            entities.get(entitiesRowCounter).add(new Path(entitiesColumnCounter*staticEntitySize + staticEntitySize/2, entitiesRowCounter*staticEntitySize + staticEntitySize/2, staticEntitySize, Color.WHITE));
                        }
                        break;
                    case "T":
                        entities.get(entitiesRowCounter).add(new Path(entitiesColumnCounter*staticEntitySize + staticEntitySize/2, entitiesRowCounter*staticEntitySize + staticEntitySize/2, staticEntitySize, Color.WHITE));
                        break;
                    case "G":
                        entities.get(entitiesRowCounter).add(new Path(entitiesColumnCounter*staticEntitySize + staticEntitySize/2, entitiesRowCounter*staticEntitySize + staticEntitySize/2, staticEntitySize, Color.WHITE));
                        break;
                    case "H":
                        entities.get(entitiesRowCounter).add(new Path(entitiesColumnCounter*staticEntitySize + staticEntitySize/2, entitiesRowCounter*staticEntitySize + staticEntitySize/2, staticEntitySize, Color.WHITE));
                        if (elements.length >= 2 && elements[1].equals("1")) {

                        } else if (elements.length >= 2 && elements[1].equals("2")) {

                        } else if (elements.length >= 2 && elements[1].equals("3")) {

                        } else if (elements.length >= 2 && elements[1].equals("4")) {

                        }
                        break;
                }
                entitiesColumnCounter++;
            }
            lineScanner.close();
            entitiesRowCounter++;
        }
        fileScanner.close();
    }

    public void debug(){
        for (ArrayList<Entity> entityArray : entities) {
            for (Entity entity : entityArray) {
                System.out.print(entity);
            }
            System.out.print("\n");
        }
    }

    public Node getEntity(int x, int y){
        return (Node) entities.get(y).get(x);
    }

    public int getEntitiesRowCounter() {
        return entitiesRowCounter;
    }

    public int getEntitiesColumnCounter() {
        return entitiesColumnCounter;
    }

    public int getStaticEntitySize() {
        return staticEntitySize;
    }
}
