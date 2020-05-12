package model.character;

import model.items.Item;
import model.items.armor.*;
import model.items.weapon.Weapon;
import viewIso.characters.CharsDrawer;

import java.awt.image.BufferedImage;
import java.util.*;

public class Items {

    Character character;
    private Weapon[] weapons = new Weapon[2];
    private Armor[] armor = new Armor[10];
    private int chosenWeapon = 0;
    private Map<Item, int[]> inventory = new HashMap<>();
    private Map<String, BufferedImage> itemsSprites = new HashMap<>();

    public Items(Character character) {
        this.character = character;
    }

    public Weapon[] getWeapons() {
        return weapons;
    }
    public void setWeapons(Weapon[] weapons, boolean showSprite) {
        setWeapon(weapons[0], showSprite);
        setSpareWeapon(weapons[1]);
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
            CharsDrawer.createCharSpriteSheet(character);
        }
    }
    public Weapon getSpareWeapon() {
        return weapons[(chosenWeapon + 1)%2];
    }
    public void setSpareWeapon(Weapon weapon){
        weapons[(chosenWeapon + 1)%2] = weapon;
        if (weapon.getHands() == 2)
            setEquipmentPart(Shield.BLOCKED, 11, false);
        else if (getSpareShield().equals(Shield.BLOCKED))
            setEquipmentPart(Shield.NOTHING, 11, false);
        if (weapon.equals(Weapon.NOTHING) && getShield().equals(Shield.BLOCKED))
            setEquipmentPart(Shield.NOTHING, 11, false);
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

    public void setShield(Shield shield, boolean showSprite) {
        armor[0] = shield;
        if (showSprite) {
            itemsSprites.put("weapons", null);
            CharsDrawer.createCharSpriteSheet(character);
        }
    }
    public void setSpareShield(Shield shield) {
        armor[9] = shield;
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
            case 1: setSpareWeapon((Weapon) item); return;
            case 2: itemsSprites.put("shields", null); break;
            case 3: itemsSprites.put("armors", null); break;
            case 4: itemsSprites.put("heads", null); break;
            case 5: itemsSprites.put("hands", null); break;
            case 6: itemsSprites.put("feets", null); break;
        }
        this.armor[partNo - 2] = (Armor) item;
        if (partNo <= 6 && showSprite) {
            CharsDrawer.createCharSpriteSheet(character);
        }
    }

    public Map<Item, int[]> getInventory() {
        return inventory;
    }

    public Map<String, BufferedImage> getItemsSprites() {
        return itemsSprites;
    }

    public List<Item> listAllItems() {
        List<Item> items = new ArrayList<>();
        items.addAll(Arrays.asList(weapons));
        items.addAll(Arrays.asList(armor));
        items.addAll(inventory.keySet());
        return items;
    }
}
