package model.character;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.StatsCalculator;
import model.armor.Armor;
import model.weapon.Weapon;

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
    private PrimaryAttributes basePA = new PrimaryAttributes();
    private PrimaryAttributes currentPA = new PrimaryAttributes();
    private SecondaryAttributes baseSA = new SecondaryAttributes();
    private SecondaryAttributes currentSA = new SecondaryAttributes();
    private Weapon[] weapons;
    private Armor[] armor;
    private int chosenWeapon = 0;


    private Image portrait;
    private CharState state = CharState.IDLE;
    private Point2D precisePosition;
    private double direction;
    private double currentSpeed;
    private Point destination;
    private List<Point2D> path;
    private int pathSection;
    private int msLeft = 0;

    private boolean chosen = false;
    private boolean targeted = false;
    private boolean ready = false;
    private boolean running = false;
    private boolean sneaking = false;
    private boolean wasDodging = false;
    private boolean wasParrying = false;
    private boolean wasBouncing = false;

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

        portrait = CharLoader.loadPortrait(this);
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

    public boolean isMale() {
        return male;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
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

    public double getCurrentSpeed() {
        return currentSpeed;
    }
    public void setCurrentSpeed(double currentSpeed) {
        this.currentSpeed = currentSpeed;
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

    public boolean isWasDodging() {
        return wasDodging;
    }
    public void setWasDodging(boolean wasDodging) {
        this.wasDodging = wasDodging;
    }

    public boolean isWasParrying() {
        return wasParrying;
    }
    public void setWasParrying(boolean wasParrying) {
        this.wasParrying = wasParrying;
    }

    public boolean isWasBouncing() {
        return wasBouncing;
    }
    public void setWasBouncing(boolean wasBouncing) {
        this.wasBouncing = wasBouncing;
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
    public PrimaryAttributes setCurrentPA(int strength, int durability, int stamina, int eye, int arm, int agility, int knowledge, int focus, int charisma) {
        setStrength(strength);
        setDurability(durability);
        setStamina(stamina);
        setArm(arm);
        setEye(eye);
        setAgility(agility);
        setKnowledge(knowledge);
        setFocus(focus);
        setSpirit(charisma);

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

    public Weapon[] getWeapons() {
        return weapons;
    }
    public void setWeapons(Weapon[] weapons) {
        this.weapons = weapons;
    }

    public Weapon getWeapon(){
        return weapons[chosenWeapon];
    }

    public Armor[] getArmor() {
        return armor;
    }
    public void setArmor(Armor[] armor) {
        this.armor = armor;
    }

    public int getChosenWeapon() {
        return chosenWeapon;
    }
    public void setChosenWeapon(int chosenWeapon) {
        this.chosenWeapon = chosenWeapon;
    }


    public int getMsLeft(){
        return currentSA.msLeft;
    }
    public void setMsLeft(int msLeft){
        currentSA.msLeft = msLeft;
    }

    public int getReflex(){
        return currentSA.reflex;
    }
    public void setReflex(int reflex){
        currentSA.reflex = reflex;
    }

    public int getStrength(){
        return currentPA.strength;
    }
    public void setStrength(int strength){
        currentPA.strength = strength;
        StatsCalculator.updateVimAndHP(this);
    }

    public int getDurability(){
        return currentPA.durability;
    }
    public void setDurability(int durability){
        currentPA.durability = durability;
        StatsCalculator.updateVimAndHP(this);
    }

    public int getStamina(){
        return currentPA.stamina;
    }
    public void setStamina(int stamina){
        currentPA.stamina = stamina;
        StatsCalculator.updateVimAndHP(this);
    }

    public int getVim(){
        return currentPA.vim;
    }
    public void setVim(int vim){
        currentPA.vim = vim;
    }

    public int getArm(){
        return currentPA.arm;
    }
    public void setArm(int arm){
        currentPA.arm = arm;
        StatsCalculator.updateDexterityAndSpeed(this);
    }

    public int getEye(){
        return currentPA.eye;
    }
    public void setEye(int eye){
        currentPA.eye = eye;
        StatsCalculator.updateDexterityAndSpeed(this);
    }

    public int getAgility(){
        return currentPA.agility;
    }
    public void setAgility(int agility){
        currentPA.agility = agility;
        StatsCalculator.updateDexterityAndSpeed(this);
    }

    public int getDexterity() {
        return currentPA.dexterity;
    }
    public void setDexterity(int dexterity) {
        currentPA.dexterity = dexterity;
    }

    public int getKnowledge(){
        return currentPA.knowledge;
    }
    public void setKnowledge(int knowledge){
        currentPA.knowledge = knowledge;
        StatsCalculator.updateIntelligenceAndMana(this);
    }

    public int getFocus(){
        return currentPA.focus;
    }
    public void setFocus(int focus){
        currentPA.focus = focus;
        StatsCalculator.updateIntelligenceAndMana(this);
    }

    public int getSpirit(){
        return currentPA.spirit;
    }
    public void setSpirit(int charisma){
        currentPA.spirit = charisma;
        StatsCalculator.updateIntelligenceAndMana(this);
    }

    public int getIntelligence() {
        return currentPA.intelligence;
    }
    public void setIntelligence(int intelligence) {
        currentPA.intelligence = intelligence;
    }

    public int getLoad(){
        return currentSA.load;
    }
    public void setLoad(int load){
        currentSA.load = load;
    }

    public int getCurrentLoad(){
        return currentSA.currentLoad;
    }
    public void setCurrentLoad(int currentLoad){
        currentSA.currentLoad = currentLoad;
    }

    public double getSpeed(){
        return currentSA.speed;
    }
    public void setSpeed(double speed){
        currentSA.speed = speed;
    }

    public double getDmgMin(){
        return currentSA.dmgMin;
    }
    public void setDmgMin(double dmgMin){
        currentSA.dmgMin = dmgMin;
    }

    public double getDmgMax(){
        return currentSA.dmgMax;
    }
    public void setDmgMax(double dmgMax){
        currentSA.dmgMax = dmgMax;
    }

    public int getHitPoints(){
        return currentSA.hitPoints;
    }
    public void setHitPoints(int hitPoints){
        currentSA.hitPoints = hitPoints;
    }

    public int getMana(){
        return currentSA.mana;
    }
    public void setMana(int mana){
        currentSA.mana = mana;
    }

    public int getVigor(){
        return currentSA.vigor;
    }
    public void setVigor(int vigor){
        currentSA.vigor = vigor;
    }


    public int getCurrentHitPoints(){
        return currentSA.currentHitPoints;
    }
    public void setCurrentHitPoints(int hitPoints){
        currentSA.currentHitPoints = hitPoints;
    }

    public int getCurrentMana(){
        return currentSA.currentMana;
    }
    public void setCurrentMana(int mana){
        currentSA.currentMana = mana;
    }

    public int getCurrentVigor(){
        return currentSA.currentVigor;
    }
    public void setCurrentVigor(int vigor){
        currentSA.currentVigor = vigor;
    }


    public double getRange(){
        return currentSA.range;
    }
    public void setRange(double range){
        currentSA.range = range;
    }

    public int getBlock(){
        return currentSA.block;
    }
    public void setBlock(int block){
        currentSA.block = block;
    }

    public int getMagicResistance(){
        return currentSA.magicResistance;
    }
    public void setMagicResistance(int magicResistance){
        currentSA.magicResistance = magicResistance;
    }

    public int getAccuracy(){
        return currentSA.accuracy;
    }
    public void setAccuracy(int accuracy){
        currentSA.accuracy = accuracy;
    }

    public int getAvoidance(){
        return currentSA.avoidance;
    }
    public void setAvoidance(int avoidance){
        currentSA.avoidance = avoidance;
    }

    public double getAttackSpeed(){
        return currentSA.attackSpeed;
    }
    public void setAttackSpeed(double attackDuration){
        currentSA.attackSpeed = attackDuration;
    }

    public int getHeadArmor(){
        return currentSA.headArmor;
    }
    public void setHeadArmor(int headArmor){
        currentSA.headArmor = headArmor;
    }

    public int getBodyArmor(){
        return currentSA.bodyArmor;
    }
    public void setBodyArmor(int bodyArmor){
        currentSA.bodyArmor = bodyArmor;
    }

    public int getArmsArmor(){
        return currentSA.armsArmor;
    }
    public void setArmsArmor(int armsArmor){
        currentSA.armsArmor = armsArmor;
    }

    public int getLegsArmor(){
        return currentSA.legsArmor;
    }
    public void setLegsArmor(int legsArmor){
        currentSA.legsArmor = legsArmor;
    }

    public void setArmor(int head, int body, int arms, int legs){
        setHeadArmor(head);
        setBodyArmor(body);
        setArmsArmor(arms);
        setLegsArmor(legs);
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
