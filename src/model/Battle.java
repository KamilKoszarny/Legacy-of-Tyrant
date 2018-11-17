package model;

import javafx.scene.paint.Color;
import model.actions.DoorActioner;
import model.armor.*;
import model.character.Character;
import model.character.CharacterClass;
import model.character.CharacterType;
import model.character.movement.CharMover;
import model.character.movement.CharTurner;
import model.map.*;
import model.map.heights.MapHeightType;
import model.map.mapObjects.MapObject;
import model.weapon.Weapon;
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
        Character czlehulec = new Character("Człehulec", Color.GRAY, CharacterType.HUMAN, CharacterClass.RASCAL, new Point(10,10), 1);
        czlehulec.setCurrentPA(13, 15, 15, 36, 40, 29, 15, 5, 25);
        czlehulec.setWeapons(new Weapon[]{Weapon.HUNTER_BOW, Weapon.TWO_HAND_SWORD});
        czlehulec.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.LEATHER_ARMOR, Helmet.NOTHING, Gloves.RAG_GLOVES, Boots.NOTHING, Belt.NOTHING});
        czlehulec.setEye(czlehulec.getEye() - 5);
        StatsCalculator.calcCharSA(czlehulec);

        Character slimako = new Character("Slimako", Color.BLACK, CharacterType.DWARF, CharacterClass.ADEPT, new Point(20,10), 2);
        slimako.setCurrentPA(25, 25, 13, 10, 15, 13, 44, 36, 20);
        slimako.setWeapons(new Weapon[]{Weapon.BIG_ADZE, Weapon.SPEAR});
        slimako.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.NOTHING, Helmet.CASQUE, Gloves.NOTHING, Boots.NOTHING, Belt.LEATHER_BELT});
        StatsCalculator.calcCharSA(slimako);

        Character skowronka = new Character("Skowronka", Color.RED, CharacterType.DWARF, CharacterClass.BULLY, new Point(10,20), 6);
        skowronka.setCurrentPA(39, 35, 25, 10, 15, 5, 10, 20, 10);
        skowronka.setWeapons(new Weapon[]{Weapon.CHOPPER, Weapon.SPIKE_CLUB});
        skowronka.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.GAMBISON, Helmet.NOTHING, Gloves.NOTHING, Boots.NOTHING, Belt.NOTHING});
        StatsCalculator.calcCharSA(skowronka);

        Character irith = new Character("Irith", Color.BLUE, CharacterType.ELF, CharacterClass.RASCAL, new Point(150,150), 7);
        irith.setCurrentPA(11, 16, 24, 40, 31, 38, 15, 12, 13);
        irith.setWeapons(new Weapon[]{Weapon.SHORT_BOW, Weapon.DAGGER});
        irith.setArmor(new Armor[]{Shield.NOTHING, BodyArmor.NOTHING, Helmet.NOTHING, Gloves.NOTHING, Boots.NOTHING, Belt.NOTHING});
        StatsCalculator.calcCharSA(irith);

        characters.addAll(new ArrayList<>(Arrays.asList(czlehulec, slimako, skowronka, irith)));
        CharMover.setCharacters(new ArrayList<>(characters));
        CharMover.pushCharsToClosestWalkable(map);
        MapGridCalc.regenerateGridGraph(map, map.getPoints().keySet(), characters);
    }

    public Map getMap() {
        return map;
    }

    public List<Character> getCharacters() {
        return characters;
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

    public Character getChosenCharacter() {
        return chosenCharacter;
    }

    public void openDoor(MapObject object){
        DoorActioner.openDoor(object, map, characters);
    }

    public void closeDoor(MapObject object){
        DoorActioner.closeDoor(object, map, characters);
    }

    public void incrementTimer() {
        timer++;
    }
}
