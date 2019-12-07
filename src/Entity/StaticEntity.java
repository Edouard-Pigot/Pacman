package Entity;

import Engine.BoxCollider;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public interface StaticEntity extends Entity {
    Point2D physicalPosition = new Point2D(0,0);
    Point2D graphicalPosition = new Point2D(0,0);

    int identifiant = 0;
    String use = "";

    public void set_Id(int id);

    public void set_Use(String use);

    public int get_Id();

    public String get_Use();

    public void setPhysicalPosition(Point2D position);

    public void setGraphicalPosition(Point2D position);

    public Point2D getPhysicalPosition();

    public Point2D getGraphicalPosition();

    public BoxCollider boxCollider();

    public boolean isColliding(Entity other);

    public void setColor(Color color);
}
