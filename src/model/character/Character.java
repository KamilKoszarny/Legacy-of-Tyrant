package model.character;

import javafx.scene.paint.Color;
import model.map.Map;

import java.awt.*;
import java.awt.geom.Point2D;

public class Character {


    CharacterType type;
    String name;
    Color color;
    Point position;
    PrimaryAttributes basePA = new PrimaryAttributes();
    PrimaryAttributes currentPA = new PrimaryAttributes();
    SecondaryAttributes baseSA = new SecondaryAttributes();
    SecondaryAttributes currentSA = new SecondaryAttributes();

    int msLeft;

    boolean chosen = false;

    public Character() {
    }

    public Character(CharacterType type, String name, Color color, Point2D position) {
        this.type = type;
        this.name = name;
        this.color = color;
        this.position =  new Point((int)(position.getX()/ Map.RESOLUTION_M),(int)(position.getY()/Map.RESOLUTION_M));
    }

    public CharacterType getType() {
        return type;
    }

    public void setType(CharacterType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getMsLeft() {
        return msLeft;
    }

    public void setMsLeft(int msLeft) {
        this.msLeft = msLeft;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public PrimaryAttributes getBasePA() {
        return basePA;
    }

    public void setBasePA(PrimaryAttributes basePA) {
        this.basePA = basePA;
    }

    public PrimaryAttributes getCurrentPA() {
        return currentPA;
    }

    public void setCurrentPA(PrimaryAttributes currentPA) {
        this.currentPA = currentPA;
    }

    public SecondaryAttributes getBaseSA() {
        return baseSA;
    }

    public void setBaseSA(SecondaryAttributes baseSA) {
        this.baseSA = baseSA;
    }

    public SecondaryAttributes getCurrentSA() {
        return currentSA;
    }

    public void setCurrentSA(SecondaryAttributes currentSA) {
        this.currentSA = currentSA;
    }

    public double getSpeed(){
        return currentSA.speed;
    }

    public void setSpeed(double speed){
        currentSA.speed = speed;
    }
}
