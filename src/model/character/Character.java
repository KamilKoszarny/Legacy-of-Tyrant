package model.character;

import isoview.LabelsDrawer;
import isoview.characters.CharsDrawer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import model.actions.attack.AttackResult;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

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

    private Point2D precisePosition;
    private double direction;
    private Point destination;
    private List<Point2D> path;
    private int pathSection;
    private double pathAPCost;
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
        this.direction = direction;

        setPortrait(CharLoader.loadPortrait(this));
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isMale() {
        return male;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public CharacterType getType() {
        return type;
    }
    public void setType(CharacterType type) {
        this.type = type;
    }

    public CharacterClass getCharClass() {
        return charClass;
    }
    public void setCharClass(CharacterClass charClass) {
        this.charClass = charClass;
    }

    public Stats getStats() {
        return stats;
    }
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Image getPortrait() {
        return portrait;
    }
    public void setPortrait(Image portrait) {
        this.portrait = portrait;
    }

    public CharState getState() {
        return state;
    }
    public void setState(CharState state) {
        this.state = state;
        CharsDrawer.resetAnimation(this);
    }
    public boolean isTargeted() {
        return targeted;
    }
    public void setTargeted(boolean targeted) {
        this.targeted = targeted;
    }
    public boolean isReady() {
        return ready;
    }
    public void setReady(boolean ready) {
        this.ready = ready;
    }
    public boolean isRunning() {
        return running;
    }
    public void setRunning(boolean running) {
        this.running = running;
    }
    public boolean isSneaking() {
        return sneaking;
    }
    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
    }
    public boolean isAfterDodging() {
        return afterDodging;
    }
    public void setAfterDodging(boolean afterDodging) {
        this.afterDodging = afterDodging;
    }
    public boolean isAfterParrying() {
        return afterParrying;
    }
    public void setAfterParrying(boolean afterParrying) {
        this.afterParrying = afterParrying;
    }
    public boolean isAfterBouncing() {
        return afterBouncing;
    }
    public void setAfterBouncing(boolean afterBouncing) {
        this.afterBouncing = afterBouncing;
    }
    public AttackResult getAttackResult() {
        return attackResult;
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
    public Point2D getPrecisePosition() {
        return precisePosition;
    }

    public int getDirection() {
        return Math.round(Math.round(direction));
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }
    public double getPreciseDirection() {
        return direction;
    }
    public void setPreciseDirection(double direction) {
        this.direction = direction;
    }

    public Point getDestination() {
        return destination;
    }
    public void setDestination(Point destination) {
        this.destination = destination;
    }

    public List<Point2D> getPath() {
        return path;
    }
    public void setPath(List<Point2D> path) {
        this.path = path;
    }
    public int getPathSection() {
        return pathSection;
    }
    public void setPathSection(int pathSection) {
        this.pathSection = pathSection;
    }
    public List<Polygon> getPathView() {
        return pathView;
    }
    public void setPathView(List<Polygon> pathView) {
        this.pathView = pathView;
    }
    public double getPathAPCost() {
        return pathAPCost;
    }
    public void setPathAPCost(double pathAPCost) {
        this.pathAPCost = pathAPCost;
    }

    public Polygon getView() {
        return view;
    }
    public void setView(Polygon view) {
        this.view = view;
    }

    public Items getItems() {
        return items;
    }

    public void setDoubleValue(String propertyName, String value){
        String methodName = "set" + java.lang.Character.toString(propertyName.charAt(0)).toUpperCase() + propertyName.substring(1, propertyName.length());
        Double doubleValue = Double.parseDouble(value);
        try {
            Method method = this.getClass().getMethod(methodName, Double.TYPE);
            method.invoke(this, doubleValue);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
