package model.character;

import javafx.scene.paint.Color;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Character {

    CharacterType type;
    CharacterClass charClass;
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

    public CharacterClass getCharClass() {
        return charClass;
    }

    public void setCharClass(CharacterClass charClass) {
        this.charClass = charClass;
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

    public double getDoubleStrength(){
        return currentPA.strength;
    }
    public void setStrength(double strength){
        currentPA.strength = strength;
    }
    public String getStrength(){
        DecimalFormat formatter = new DecimalFormat("#", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentPA.strength);
    }
    public void setStrength(String strength){
        currentPA.strength = Double.parseDouble(strength);
    }

    public double getDoubleDurability(){
        return currentPA.durability;
    }
    public void setDurability(double durability){
        currentPA.durability = durability;
    }
    public String getDurability(){
        DecimalFormat formatter = new DecimalFormat("#", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentPA.durability);
    }
    public void setDurability(String durability){
        currentPA.durability = Double.parseDouble(durability);
    }

    public double getDoubleStamina(){
        return currentPA.stamina;
    }
    public void setStamina(double stamina){
        currentPA.stamina = stamina;
    }
    public String getStamina(){
        DecimalFormat formatter = new DecimalFormat("#", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentPA.stamina);
    }
    public void setStamina(String stamina){
        currentPA.stamina = Double.parseDouble(stamina);
    }

    public double getDoubleArm(){
        return currentPA.arm;
    }
    public void setArm(double arm){
        currentPA.arm = arm;
    }
    public String getArm(){
        DecimalFormat formatter = new DecimalFormat("#", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentPA.arm);
    }
    public void setArm(String arm){
        currentPA.arm = Double.parseDouble(arm);
    }

    public double getDoubleEye(){
        return currentPA.eye;
    }
    public void setEye(double eye){
        currentPA.eye = eye;
    }
    public String getEye(){
        DecimalFormat formatter = new DecimalFormat("#", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentPA.eye);
    }
    public void setEye(String eye){
        currentPA.eye = Double.parseDouble(eye);
    }

    public double getDoubleAgility(){
        return currentPA.agility;
    }
    public void setAgility(double agility){
        currentPA.agility = agility;
    }
    public String getAgility(){
        DecimalFormat formatter = new DecimalFormat("#", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentPA.agility);
    }
    public void setAgility(String agility){
        currentPA.agility = Double.parseDouble(agility);
    }

    public double getDoubleKnowledge(){
        return currentPA.knowledge;
    }
    public void setKnowledge(double knowledge){
        currentPA.knowledge = knowledge;
    }
    public String getKnowledge(){
        DecimalFormat formatter = new DecimalFormat("#", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentPA.knowledge);
    }
    public void setKnowledge(String knowledge){
        currentPA.knowledge = Double.parseDouble(knowledge);
    }

    public double getDoubleFocus(){
        return currentPA.focus;
    }
    public void setFocus(double focus){
        currentPA.focus = focus;
    }
    public String getFocus(){
        DecimalFormat formatter = new DecimalFormat("#", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentPA.focus);
    }
    public void setFocus(String focus){
        currentPA.focus = Double.parseDouble(focus);
    }

    public double getDoubleCharisma(){
        return currentPA.charisma;
    }
    public void setCharisma(double charisma){
        currentPA.charisma = charisma;
    }
    public String getCharisma(){
        DecimalFormat formatter = new DecimalFormat("#", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentPA.charisma);
    }
    public void setCharisma(String charisma){
        currentPA.charisma = Double.parseDouble(charisma);
    }



    public double getDoubleSpeed(){
        return currentSA.speed;
    }
    public void setSpeed(double speed){
        currentSA.speed = speed;
    }
    public String getSpeed(){
        DecimalFormat formatter = new DecimalFormat("#.0", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentSA.speed);
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
        DecimalFormat formatter = new DecimalFormat("#.0", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentSA.dmgMin);
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
        DecimalFormat formatter = new DecimalFormat("#.0", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentSA.dmgMax);
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
        DecimalFormat formatter = new DecimalFormat("#", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentSA.hitPoints);
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
        DecimalFormat formatter = new DecimalFormat("#.0", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentSA.range);
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
        DecimalFormat formatter = new DecimalFormat("#", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentSA.chanceToHit);
    }
    public void setChanceToHit(String chanceToHit){
        currentSA.chanceToHit = Double.parseDouble(chanceToHit);
    }

    public double getDoubleChanceToBeHit(){
        return currentSA.chanceToBeHit;
    }
    public void setChanceToBeHit(double chanceToBeHit){
        currentSA.chanceToBeHit = chanceToBeHit;
    }
    public String getChanceToBeHit(){
        DecimalFormat formatter = new DecimalFormat("#", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentSA.chanceToBeHit);
    }
    public void setChanceToBeHit(String chanceToBeHit){
        currentSA.chanceToBeHit = Double.parseDouble(chanceToBeHit);
    }

    public double getDoubleAttackDuration(){
        return currentSA.attackDuration;
    }
    public void setAttackDuration(double attackDuration){
        currentSA.attackDuration = attackDuration;
    }
    public String getAttackDuration(){
        DecimalFormat formatter = new DecimalFormat("#.0", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentSA.attackDuration);
    }
    public void setAttackDuration(String attackDuration){
        currentSA.attackDuration = Double.parseDouble(attackDuration);
    }

    public double getDoubleHeadArmor(){
        return currentSA.headArmor;
    }
    public void setHeadArmor(double headArmor){
        currentSA.headArmor = headArmor;
    }
    public String getHeadArmor(){
        DecimalFormat formatter = new DecimalFormat("#", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentSA.headArmor);
    }
    public void setHeadArmor(String headArmor){
        currentSA.headArmor = Double.parseDouble(headArmor);
    }

    public double getDoubleBodyArmor(){
        return currentSA.bodyArmor;
    }
    public void setBodyArmor(double bodyArmor){
        currentSA.bodyArmor = bodyArmor;
    }
    public String getBodyArmor(){
        DecimalFormat formatter = new DecimalFormat("#", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentSA.bodyArmor);
    }
    public void setBodyArmor(String bodyArmor){
        currentSA.bodyArmor = Double.parseDouble(bodyArmor);
    }

    public double getDoubleArmsArmor(){
        return currentSA.armsArmor;
    }
    public void setArmsArmor(double armsArmor){
        currentSA.armsArmor = armsArmor;
    }
    public String getArmsArmor(){
        DecimalFormat formatter = new DecimalFormat("#", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentSA.armsArmor);
    }
    public void setArmsArmor(String armsArmor){
        currentSA.armsArmor = Double.parseDouble(armsArmor);
    }

    public double getDoubleLegsArmor(){
        return currentSA.legsArmor;
    }
    public void setLegsArmor(double legsArmor){
        currentSA.legsArmor = legsArmor;
    }
    public String getLegsArmor(){
        DecimalFormat formatter = new DecimalFormat("#", DecimalFormatSymbols.getInstance( Locale.ENGLISH ));
        return formatter.format(currentSA.legsArmor);
    }
    public void setLegsArmor(String legsArmor){
        currentSA.legsArmor = Double.parseDouble(legsArmor);
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
