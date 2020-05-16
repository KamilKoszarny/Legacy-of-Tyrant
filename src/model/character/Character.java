package model.character;

import isoview.LabelsDrawer;
import isoview.characters.CharsDrawer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import lombok.Getter;
import lombok.Setter;
import model.actions.attack.AttackResult;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Getter
@Setter
public class Character {

    private String name;
    private boolean male;
    private Color color;
    private CharacterType type;
    private CharacterClass charClass;
    private Image portrait;
    private Stats stats = new Stats(this);

    private CharState state = CharState.IDLE;
    private boolean targeted = false;
    private boolean ready = false;
    private boolean running = false;
    private boolean sneaking = false;
    private boolean afterDodging = false;
    private boolean afterParrying = false;
    private boolean afterBouncing = false;

    private AttackResult attackResult = null;
    private float attackAPCost;

    private Point2D precisePosition;
    private double preciseDirection;
    private Point destination;
    private List<Point2D> path;
    private int pathSection;
    private float pathAPCost;
    private List<Polygon> pathView;
    private Polygon view = new Polygon();

    private Items items = new Items(this);


    public Character() {
    }

    public Character(String name, boolean male, Color color, CharacterType type, CharacterClass charClass, Point position, int direction) {
        this.name = name;
        this.male = male;
        this.color = color;
        this.type = type;
        this.charClass = charClass;
        precisePosition = new Point2D(position.x, position.y);
        this.preciseDirection = direction;

        setPortrait(CharLoader.loadPortrait(this));
    }

    public void setState(CharState state) {
        this.state = state;
        CharsDrawer.resetAnimation(this);
    }

    public void setAttackResult(AttackResult attackResult) {
        this.attackResult = attackResult;
        LabelsDrawer.resetDamageLabel(this);
    }

    public Point getPosition() {
        return new Point(Math.round(Math.round(precisePosition.getX())), Math.round(Math.round(precisePosition.getY())));
    }
    public void setPosition(Point position) {
        precisePosition = new Point2D(position.x, position.y);
    }
    public void setPosition(Point2D position) {
        precisePosition = position;
    }

    public int getDirection() {
        return Math.round(Math.round(preciseDirection));
    }

    public void clearPath() {
        path = null;
        pathView = null;
        pathAPCost = 0;
        pathSection = 0;
    }

    public void setDoubleValue(String propertyName, String value){
        String methodName = "set" + java.lang.Character.toString(propertyName.charAt(0)).toUpperCase() + propertyName.substring(1);
        Double doubleValue = Double.parseDouble(value);
        try {
            Method method = this.getClass().getMethod(methodName, Double.TYPE);
            method.invoke(this, doubleValue);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
