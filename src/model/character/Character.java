package model.character;

import javafx.scene.paint.Color;
import model.map.Map;

import java.awt.*;
import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    boolean targeted = false;

    public Character() {
    }

    public Character(CharacterType type, String name, Color color) {
        this.type = type;
        this.name = name;
        this.color = color;
        this.position =  new Point(10,10);
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

    public boolean isTargeted() {
        return targeted;
    }

    public void setTargeted(boolean targeted) {
        this.targeted = targeted;
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


    public double getDoubleSpeed(){
        return currentSA.speed;
    }

    public void setSpeed(double speed){
        currentSA.speed = speed;
    }

    public String getSpeed(){
        return currentSA.speed.toString();
    }

    public void setSpeed(String speed){
        currentSA.speed = Double.parseDouble(speed);
    }


    public double getDoubleDmgMin(){
        return currentSA.dmgMin;
    }

    public void setDmgMin(double dmgMin){
        currentSA.dmgMin = dmgMin;
    }

    public String getDmgMin(){
        return currentSA.dmgMin.toString();
    }

    public void setDmgMin(String dmgMin){
        currentSA.dmgMin = Double.parseDouble(dmgMin);
    }


    public double getDoubleDmgMax(){
        return currentSA.dmgMax;
    }

    public void setDmgMax(double dmgMax){
        currentSA.dmgMax = dmgMax;
    }

    public String getDmgMax(){
        return currentSA.dmgMax.toString();
    }

    public void setDmgMax(String dmgMax){
        currentSA.dmgMax = Double.parseDouble(dmgMax);
    }


    public double getDoubleHitPoints(){
        return currentSA.hitPoints;
    }

    public void setHitPoints(double hitPoints){
        currentSA.hitPoints = hitPoints;
    }

    public String getHitPoints(){
        return currentSA.hitPoints.toString();
    }

    public void setHitPoints(String hitPoints){
        currentSA.hitPoints = Double.parseDouble(hitPoints);
    }


    public double getDoubleRange(){
        return currentSA.range;
    }

    public void setRange(double range){
        currentSA.range = range;
    }

    public String getRange(){
        return currentSA.range.toString();
    }

    public void setRange(String range){
        currentSA.range = Double.parseDouble(range);
    }


    public double getDoubleChanceToHit(){
        return currentSA.chanceToHit;
    }

    public void setChanceToHit(double chanceToHit){
        currentSA.chanceToHit = chanceToHit;
    }

    public String getChanceToHit(){
        return currentSA.chanceToHit.toString();
    }

    public void setChanceToHit(String chanceToHit){
        currentSA.chanceToHit = Double.parseDouble(chanceToHit);
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
