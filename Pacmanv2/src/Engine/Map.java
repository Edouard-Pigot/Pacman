package Engine;

import Entity.*;
import Gameplay.*;
import javafx.geometry.Point2D;
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
        Scanner fileScanner = new Scanner(new File("Pacmanv2/src/Map"));
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            entitiesColumnCounter = 0;
            while (lineScanner.hasNext()) {
                entitiesNumber++;
                String[] elements = lineScanner.next().split("(?!^)");
                switch (elements[0]){
                    case "E":
                        if (elements.length >= 2) {
                            Empty e = new Empty(new Point2D(entitiesColumnCounter * staticEntitySize, entitiesRowCounter * staticEntitySize), new Point2D(entitiesRowCounter, entitiesColumnCounter), staticEntitySize, Color.BLACK);
                            e.set_Id(Integer.parseInt(elements[1]));
                            e.set_Use("C");
                            entities.add(e);
                        } else {
                            entities.add(new Empty(new Point2D(entitiesColumnCounter*staticEntitySize, entitiesRowCounter*staticEntitySize), new Point2D(entitiesRowCounter, entitiesColumnCounter), staticEntitySize, Color.BLACK));
                        }
                        break;
                    case "W":
                        entities.add(new Wall(new Point2D(entitiesColumnCounter*staticEntitySize, entitiesRowCounter*staticEntitySize), new Point2D(entitiesRowCounter, entitiesColumnCounter), staticEntitySize, Color.BLUE));
                        break;
                    case "P":
                        if (elements.length >= 2 && elements[1].equals("P")) {
                            entities.add(new PacGum(new Point2D(entitiesColumnCounter*staticEntitySize + staticEntitySize/2, entitiesRowCounter*staticEntitySize + staticEntitySize/2), new Point2D(entitiesRowCounter, entitiesColumnCounter), staticEntitySize/3, Color.YELLOW));
                            if (elements.length >= 3 && elements[2].equals("N")) {

                            }
                        } else if (elements.length >= 2 && elements[1].equals("G")) {
                            entities.add(new SuperPacGum(new Point2D(entitiesColumnCounter*staticEntitySize + staticEntitySize/2, entitiesRowCounter*staticEntitySize + staticEntitySize/2), new Point2D(entitiesRowCounter, entitiesColumnCounter), staticEntitySize/2, Color.YELLOW));
                        }  else if (elements.length >= 2 && elements[1].equals("1")) {
                            entities.add(new PowerPassThrough(new Point2D(entitiesColumnCounter*staticEntitySize + staticEntitySize/2, entitiesRowCounter*staticEntitySize + staticEntitySize/2), new Point2D(entitiesRowCounter, entitiesColumnCounter), staticEntitySize/2, Color.PURPLE));
                        } else if (elements.length >= 2 && elements[1].equals("2")) {
                            entities.add(new PowerSize(new Point2D(entitiesColumnCounter * staticEntitySize + staticEntitySize / 2, entitiesRowCounter * staticEntitySize + staticEntitySize / 2), new Point2D(entitiesRowCounter, entitiesColumnCounter), staticEntitySize / 2, Color.PINK));
                        } else if (elements.length >= 2 && elements[1].equals("E")) {
                            Path p = new Path(new Point2D(entitiesColumnCounter*staticEntitySize, entitiesRowCounter*staticEntitySize), new Point2D(entitiesRowCounter, entitiesColumnCounter), staticEntitySize, Color.BLACK);
                            p.set_Use("E");
                            entities.add(p);
                        } else {
                            entities.add(new Path(new Point2D(entitiesColumnCounter*staticEntitySize, entitiesRowCounter*staticEntitySize), new Point2D(entitiesRowCounter, entitiesColumnCounter), staticEntitySize, Color.BLACK));
                        }
                        break;
                    case "T":
                        entities.add(new Path(new Point2D(entitiesColumnCounter*staticEntitySize, entitiesRowCounter*staticEntitySize), new Point2D(entitiesRowCounter, entitiesColumnCounter), staticEntitySize, Color.BLACK));
                        break;
                    case "G":
                        entities.add(new Door(new Point2D(entitiesColumnCounter*staticEntitySize, entitiesRowCounter*staticEntitySize), new Point2D(entitiesRowCounter, entitiesColumnCounter), staticEntitySize, Color.PINK));
                        break;
                    case "H":
                        if (elements.length >= 2) {
                            Path p = new Path(new Point2D(entitiesColumnCounter*staticEntitySize, entitiesRowCounter*staticEntitySize), new Point2D(entitiesRowCounter, entitiesColumnCounter), staticEntitySize, Color.BLACK);
                            p.set_Id(Integer.parseInt(elements[1]));
                            p.set_Use("H");
                            entities.add(p);
                        } else {
                            entities.add(new Path(new Point2D(entitiesColumnCounter*staticEntitySize, entitiesRowCounter*staticEntitySize), new Point2D(entitiesRowCounter, entitiesColumnCounter), staticEntitySize, Color.BLACK));
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

    public void removeEntity(Entity entity){
        entities.remove(entity);
        entitiesNumber--;
    }

    public void spawnEntity(Entity entity){
        entities.add(entity);
        entitiesNumber++;
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

    public boolean containsScoreEntity(){
        for(Entity tmpEntity : entities)
            if(tmpEntity instanceof ScoreEntity)
                return true;
        return false;
    }

    public Point2D getGhostHomePosition(int id) {
        for(Entity entity : entities){
            if(entity instanceof Path){
                if(((StaticEntity) entity).get_Id() == id){
                    if(((StaticEntity) entity).get_Use().equals("H")) {
                        return entity.getPhysicalPosition();
                    }
                }
            }
        }
        return null;
    }

    public Point2D getGhostCornerPosition(int id){
        for(Entity entity : entities){
            if(entity instanceof Empty){
                if(((StaticEntity) entity).get_Id() == id){
                    if(((StaticEntity) entity).get_Use().equals("C")) {
                        return entity.getPhysicalPosition();
                    }
                }
            }
        }
        return null;
    }

    public Point2D getGhostGateExitPosition(){
        for(Entity entity : entities){
            if(entity instanceof Path){
                if(((StaticEntity) entity).get_Use().equals("E")) {
                    return new Point2D(entity.getPhysicalPosition().getX(), entity.getPhysicalPosition().getY()+1);
//                    return entity.getPhysicalPosition();
                }
            }
        }
        return null;
    }
}
