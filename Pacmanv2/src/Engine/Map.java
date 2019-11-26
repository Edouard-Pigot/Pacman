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
    private ArrayList<Entity> entities = new ArrayList<>();
    private int entitiesRowCounter = 0;
    private int entitiesColumnCounter = 0;
    private int entitiesNumber = 0;
    private int staticEntitySize = 16;

    public void generate() throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("E:\\Desktop\\Cours\\GL\\Pacman\\Pacmanv2\\src\\Map"));
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            entitiesColumnCounter = 0;
            while (lineScanner.hasNext()) {
                entitiesNumber++;
                String[] elements = lineScanner.next().split("(?!^)");
                switch (elements[0]){
                    case "E":
                        entities.add(new Empty(entitiesColumnCounter*staticEntitySize, entitiesRowCounter*staticEntitySize, staticEntitySize, Color.BLACK));
                        if (elements.length >= 2 && elements[1].equals("1")) {

                        } else if (elements.length >= 2 && elements[1].equals("2")) {

                        } else if (elements.length >= 2 && elements[1].equals("3")) {

                        } else if (elements.length >= 2 && elements[1].equals("4")) {

                        }
                        break;
                    case "W":
                        entities.add(new Wall(entitiesColumnCounter*staticEntitySize, entitiesRowCounter*staticEntitySize, staticEntitySize, Color.BLUE));
                        break;
                    case "P":
                        if (elements.length >= 2 && elements[1].equals("P")) {
                            entities.add(new PacGum(entitiesColumnCounter*staticEntitySize + staticEntitySize/2, entitiesRowCounter*staticEntitySize + staticEntitySize/2, staticEntitySize/3, Color.YELLOW));
                            if (elements.length >= 3 && elements[2].equals("N")) {

                            }
                        } else if (elements.length >= 2 && elements[1].equals("G")) {
                            entities.add(new SuperPacGum(entitiesColumnCounter*staticEntitySize + staticEntitySize/2, entitiesRowCounter*staticEntitySize + staticEntitySize/2, staticEntitySize/2, Color.YELLOW));
                        } else {
                            entities.add(new Path(entitiesColumnCounter*staticEntitySize, entitiesRowCounter*staticEntitySize, staticEntitySize, Color.BLACK));
                        }
                        break;
                    case "T":
                        entities.add(new Path(entitiesColumnCounter*staticEntitySize, entitiesRowCounter*staticEntitySize, staticEntitySize, Color.BLACK));
                        break;
                    case "G":
                        entities.add(new Path(entitiesColumnCounter*staticEntitySize, entitiesRowCounter*staticEntitySize, staticEntitySize, Color.PINK));
                        break;
                    case "H":
                        entities.add(new Path(entitiesColumnCounter*staticEntitySize, entitiesRowCounter*staticEntitySize, staticEntitySize, Color.BLACK));
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
            for (Entity entity : entities) {
                System.out.print(entity);
            }
    }

    public Entity getEntity(int nb){
        return entities.get(nb);
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

    public int getEntitiesNumber(){
        return  entitiesNumber;
    }
}
