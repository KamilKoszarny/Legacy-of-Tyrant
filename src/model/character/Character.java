package model.character;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import model.actions.attack.AttackResult;
import model.items.Item;
import model.items.armor.*;
import model.items.weapon.Weapon;
import viewIso.LabelsDrawer;
import viewIso.characters.CharsDrawer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Character {

    private String name;
    private boolean male;
    private Color color;
    private CharacterType type;
    private CharacterClass charClass;
    private Stats stats = new Stats();

    private PrimaryAttributes basePA = new PrimaryAttributes();
    private PrimaryAttributes currentPA = new PrimaryAttributes();
    private SecondaryAttributes baseSA = new SecondaryAttributes();
    private SecondaryAttributes currentSA = new SecondaryAttributes();
    private Weapon[] weapons = new Weapon[2];
    private Armor[] armor = new Armor[10];
    private int chosenWeapon = 0;
    private Map<Item, int[]> inventory = new HashMap<>();

    private Image portrait;
    private Map<String, BufferedImage> itemsSprites = new HashMap<>();
    private CharState state = CharState.IDLE;
    private Point2D precisePosition;
    private double direction;
    private double currentSpeed;
    private Point destination;
    private List<Point2D> path;
    private int pathSection;
    private List<Polygon> pathView;

    private boolean chosen = false;
    private boolean targeted = false;
    private boolean ready = false;
    private boolean running = false;
    private boolean sneaking = false;
    private boolean afterDodging = false;
    private boolean afterParrying = false;
    private boolean afterBouncing = false;

    private AttackResult attackResult = null;

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

    public Map<String, BufferedImage> getItemsSprites() {
        return itemsSprites;
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

    public List<Polygon> getPathView() {
        return pathView;
    }
    public void setPathView(List<Polygon> pathView) {
        this.pathView = pathView;
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


    public Weapon[] getWeapons() {
        return weapons;
    }
    public void setWeapons(Weapon[] weapons, boolean showSprite) {
        setWeapon(weapons[0], showSprite);
        setSpareWeapon(weapons[1], showSprite);
    }
    public Armor[] getArmor() {
        return armor;
    }
    public void setArmor(Armor[] armor) {
        this.armor = armor;
    }

    public Weapon getWeapon(){
        return weapons[chosenWeapon];
    }
    public void setWeapon(Weapon weapon, boolean showSprite){
        weapons[chosenWeapon] = weapon;
        if (weapon.getHands() == 2)
            setEquipmentPart(Shield.BLOCKED, 2, false);
        else if (getShield().equals(Shield.BLOCKED))
            setEquipmentPart(Shield.NOTHING, 2, false);
        if (weapon.equals(Weapon.NOTHING) && getShield().equals(Shield.BLOCKED))
            setEquipmentPart(Shield.NOTHING, 2, false);
        if (showSprite) {
            itemsSprites.put("weapons", null);
            CharsDrawer.createCharSpriteSheet(this);
        }
    }
    public Weapon getSpareWeapon() {
        return weapons[(chosenWeapon + 1)%2];
    }
    public void setSpareWeapon(Weapon weapon, boolean showSprite){
        weapons[(chosenWeapon + 1)%2] = weapon;
        if (weapon.getHands() == 2)
            setEquipmentPart(Shield.BLOCKED, 11, showSprite);
        else if (getSpareShield().equals(Shield.BLOCKED))
            setEquipmentPart(Shield.NOTHING, 11, showSprite);
        if (weapon.equals(Weapon.NOTHING) && getShield().equals(Shield.BLOCKED))
            setEquipmentPart(Shield.NOTHING, 11, showSprite);
    }

    public Shield getShield() {
        return (Shield) armor[0];
    }
    public BodyArmor getBodyArmorItem() {
        return (BodyArmor) armor[1];
    }
    public Helmet getHelmet() {
        return (Helmet) armor[2];
    }
    public Gloves getGloves() {
        return (Gloves) armor[3];
    }
    public Boots getBoots() {
        return (Boots) armor[4];
    }
    public Belt getBelt() {
        return (Belt) armor[5];
    }
    public Amulet getAmulet() {
        return (Amulet) armor[6];
    }
    public Ring getRing1() {
        return (Ring) armor[7];
    }
    public Ring getRing2() {
        return (Ring) armor[8];
    }
    public Shield getSpareShield() {
        return (Shield) armor[9];
    }

    public Item getEquipmentPart(int partNo) {
        switch (partNo) {
            case 0: return getWeapon();
            case 1: return getSpareWeapon();
            default: return armor[partNo - 2];
        }
    }
    public void setEquipmentPart(Item item, int partNo, boolean showSprite) {
        switch (partNo) {
            case 0: setWeapon((Weapon) item, showSprite); return;
            case 1: setSpareWeapon((Weapon) item, showSprite); return;
            case 2: itemsSprites.put("shields", null); break;
            case 3: itemsSprites.put("armors", null); break;
            case 4: itemsSprites.put("heads", null); break;
            case 5: itemsSprites.put("hands", null); break;
            case 6: itemsSprites.put("feets", null); break;
        }
        this.armor[partNo - 2] = (Armor) item;
        if (partNo <= 6 && showSprite) {
            CharsDrawer.createCharSpriteSheet(this);
        }
    }

    public Map<Item, int[]> getInventory() {
        return inventory;
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

    public AttackResult getAttackResult() {
        return attackResult;
    }

    public void setAttackResult(AttackResult attackResult) {
        this.attackResult = attackResult;
        LabelsDrawer.resetDamageLabel(this);
    }
}
