package model;

import model.actions.DoorActioner;
import model.actions.ItemHandler;
import model.items.Item;
import model.character.Character;
import model.character.movement.CharMover;
import model.character.movement.CharTurner;
import model.map.*;
import model.map.mapObjects.MapObject;
import viewIso.characters.CharsDrawer;

import java.awt.*;
import java.util.List;

public class Battle {

    private Map map;
    private static List<Character> characters;
    private static Character chosenCharacter;
    private int timer = 0;
    private Item catchedItem = null;

    public Battle(Map map, List<Character> characters) {
        this.map = map;
        Battle.characters = characters;
    }

    public static void chooseCharacter(Character clickedCharacter) {
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

    public Item moveItem(int[] clickedInventorySlot, Point clickedItemPoint) {
        return ItemHandler.moveItem(chosenCharacter, clickedInventorySlot, clickedItemPoint);
    }

    public void incrementTimer() {
        timer++;
    }

    public Map getMap() {
        return map;
    }

    public static List<Character> getCharacters() {
        return characters;
    }

    public static Character getChosenCharacter() {
        return chosenCharacter;
    }
}
