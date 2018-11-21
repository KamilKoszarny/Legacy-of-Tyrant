package model;

import javafx.scene.paint.Color;
import model.actions.DoorActioner;
import model.character.StatsCalculator;
import model.items.Item;
import model.items.ItemsCalculator;
import model.items.armor.*;
import model.character.Character;
import model.character.CharacterClass;
import model.character.CharacterType;
import model.character.movement.CharMover;
import model.character.movement.CharTurner;
import model.map.*;
import model.map.heights.MapHeightType;
import model.map.mapObjects.MapObject;
import model.items.weapon.Weapon;
import viewIso.characters.CharsDrawer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Battle {

    private Map map;
    private List<Character> characters;
    private Character chosenCharacter;
    private int timer = 0;
    private Item catchedItem = null;

    public Battle() {
        initMap();
        initCharacters();
    }

    private void initMap(){
        boolean[] roadSides = {true, false, true, true};
        boolean[] riverSides = {false, true, false, true};
        boolean[] waterSides = {false, true, false, false};
        map = new Map(50, 50, MapType.VILLAGE, MapHeightType.PEAK, roadSides, riverSides, waterSides);
    }

    private void initCharacters(){
        characters = new ArrayList<>();
        Character czlehulec = new Character("Czlehulec", true, Color.GOLD, CharacterType.HUMAN, CharacterClass.RASCAL, new Point(10,10), 1);
        czlehulec.setCurrentPA(13, 15, 15, 36, 40, 29, 15, 5, 25);
        czlehulec.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.LEATHER_ARMOR, Helmet.NOTHING, Gloves.RAG_GLOVES, Boots.NOTHING, Belt.NOTHING, Amulet.BRONZE_AMULET, Ring.SILVER_RING, Ring.NOTHING, Shield.BLOCKED});
        czlehulec.setWeapons(new Weapon[]{Weapon.HUNTER_BOW, Weapon.TWO_HAND_SWORD});
        czlehulec.setEye(czlehulec.getEye() - 5);
        StatsCalculator.calcCharSA(czlehulec);

        Character slimako = new Character("Slimako", true, Color.GOLD, CharacterType.DWARF, CharacterClass.ADEPT, new Point(20,10), 2);
        slimako.setCurrentPA(25, 25, 13, 10, 15, 13, 44, 36, 20);
        slimako.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.NOTHING, Helmet.CASQUE, Gloves.NOTHING, Boots.NOTHING, Belt.LEATHER_BELT, Amulet.NOTHING, Ring.SILVER_RING, Ring.GOLD_RING, Shield.BLOCKED});
        slimako.setWeapons(new Weapon[]{Weapon.BIG_ADZE, Weapon.SPEAR});
        StatsCalculator.calcCharSA(slimako);

        Character skowronka = new Character("Skowronka", false, Color.GOLD, CharacterType.DWARF, CharacterClass.BULLY, new Point(10,20), 6);
        skowronka.setCurrentPA(39, 35, 25, 10, 15, 5, 10, 20, 10);
        skowronka.setArmor(new Armor[]{Shield.BUCKLER, BodyArmor.GAMBISON, Helmet.NOTHING, Gloves.NOTHING, Boots.NOTHING, Belt.NOTHING, Amulet.NOTHING, Ring.NOTHING, Ring.NOTHING, Shield.WOODEN_SHIELD});
        skowronka.setWeapons(new Weapon[]{Weapon.CHOPPER, Weapon.SPIKE_CLUB});
        StatsCalculator.calcCharSA(skowronka);

        Character irith = new Character("Irith", false, Color.BLUE, CharacterType.ELF, CharacterClass.RASCAL, new Point(150,150), 7);
        irith.setCurrentPA(11, 16, 24, 40, 31, 38, 15, 12, 13);
        irith.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.NOTHING, Helmet.NOTHING, Gloves.NOTHING, Boots.NOTHING, Belt.NOTHING, Amulet.NOTHING, Ring.NOTHING, Ring.NOTHING, Shield.NOTHING});
        irith.setWeapons(new Weapon[]{Weapon.SHORT_BOW, Weapon.DAGGER});
        StatsCalculator.calcCharSA(irith);

        characters.addAll(new ArrayList<>(Arrays.asList(czlehulec, slimako, skowronka, irith)));
        CharMover.setCharacters(new ArrayList<>(characters));
        CharMover.pushCharsToClosestWalkable(map);
        MapGridCalc.regenerateGridGraph(map, map.getPoints().keySet(), characters);
    }

    public void chooseCharacter(Character clickedCharacter) {
        for (Character character: characters) {
            character.setChosen(false);
        }
        clickedCharacter.setChosen(true);
        chosenCharacter = clickedCharacter;
    }

    public void turnCharacter(Point turnPoint) {
        CharTurner.turnCharacter(chosenCharacter, turnPoint, true);
    }

    public void startRunCharacter(Point runPoint) {
        CharMover.startRunCharacter(chosenCharacter, runPoint, map);
    }

    public void updateCharacters(int ms) {
        for (Character character: characters) {
            if (character.getDestination() != null)
                if (character.getPath().isEmpty())
                    CharMover.stopCharacter(character, map);
                else
                    CharMover.updateCharacterMove(character, ms, map);
            else if (timer%3 == 0) {
                CharsDrawer.nextFrame(character);
            }
        }
    }

    public void openDoor(MapObject object){
        DoorActioner.openDoor(object, map, characters);
    }

    public void closeDoor(MapObject object){
        DoorActioner.closeDoor(object, map, characters);
    }

    public Item moveItem(int clickedItemNo, Point clickedItemPoint, boolean itemCatch) {
        if (itemCatch) {
            if (moveItem(clickedItemNo)) return null;
        } else {
            if (clickedItemNo >= 0) {
                Item underItem = chosenCharacter.getEquipmentPart(clickedItemNo);
                if (catchedItem.getClass().equals(underItem.getClass()) && !underItem.equals(Shield.BLOCKED)) {
                    chosenCharacter.setEquipmentPart(catchedItem, clickedItemNo);
                    if (!underItem.getName().equals("NOTHING"))
                        catchedItem = underItem;
                    else
                        catchedItem = null;
                }
            } else {
                //drop
            }
        }

        System.out.println(catchedItem);
        return catchedItem;
    }

    private boolean moveItem(int clickedItemNo) {
        catchedItem = chosenCharacter.getEquipmentPart(clickedItemNo);
        if (catchedItem.getName().equals("NOTHING") || catchedItem.getName().equals("BLOCKED")) {
            catchedItem = null;
            return true;
        }
        chosenCharacter.setEquipmentPart(ItemsCalculator.getNothingItemByNo(clickedItemNo), clickedItemNo);
        return false;
    }

    public void incrementTimer() {
        timer++;
    }

    public Map getMap() {
        return map;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public Character getChosenCharacter() {
        return chosenCharacter;
    }

    public Item getCatchedItem() {
        return catchedItem;
    }
}
